import { Component, signal } from '@angular/core';
import { Router, RouterLink, RouterOutlet, NavigationEnd } from '@angular/router';
import { CoffeeShopService } from './services/coffeeshop';
import { OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { BrowserModule } from '@angular/platform-browser';
import { ChangeDetectorRef } from '@angular/core';
import { CoffeeShop } from './models/coffeeshop';
import { Review } from './models/review';
import { OnDestroy } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, takeUntil } from 'rxjs/operators';


@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterOutlet, RouterLink, ReactiveFormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit, OnDestroy {
  //totalCoffeeShops : number = 0;
  totalCoffeeShops = signal(0);
  topPickCoffeeShop = signal({
    id: 0,
    name: 'Roast & Relax',
    city: 'Downtown',
    state: 'CA',
    fullAddress: '123 Main St, Downtown, CA 90210',
    outlets: true,
    bathrooms: true,
    outdoorSeating: true,
    cashOk: true,
  })
  topPickCoffeeShopReview = signal({
    id: 0,
    user_id: 0,
    shop_id: 0,
    review: 'Cozy neighborhood spot with handcrafted espresso, pastry pairings, and fast Wi-Fi.',
    rating: 5,
  })
  topPickCoffeeShopRating = signal(0);

  displayCoffeeShops = signal<CoffeeShop[]>([]);
  //availableCities = signal<string[]>([]);
  //searchTerm = signal('');
  searchControl = signal(new FormControl(''));
  filterOptions = signal({
    outlets: false,
    bathrooms: false,
    outdoorSeating: false,
    cashOk: false,
  });
  filteredItems = signal<CoffeeShop[]>([]);
  filteredCoffeeShopCount = signal(0);
  isDetailsPage = signal(false);

  private destroy$ = new Subject<void>();


  constructor(
    private coffeeShopService: CoffeeShopService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {
    this.isDetailsPage.set(this.router.url.startsWith('/coffeeshops'));
  }

  protected readonly title = signal('coffeebeans_ui');
    ngOnInit(): void { // run second
      this.getTotalCoffeeShops();
      this.getTopPickCoffeeShop();
      this.getTopPickCoffeeShopReview();
      this.getTopPickCoffeeShopRating();
      this.getAllCoffeeShops();
      this.setFilterControl();
      this.filteredItems = signal<CoffeeShop[]>([...this.displayCoffeeShops()]);
      this.filterResults(this.searchControl().value || '');


      this.searchControl().valueChanges.pipe(
        debounceTime(300),         // Wait 300ms after the user stops typing
        distinctUntilChanged(),    // Only trigger if the value actually changed
        takeUntil(this.destroy$)   // Clean up subscription to prevent memory leaks
      ).subscribe(term => {
        this.filterResults(term || '');
      });

      this.router.events.pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd),
        takeUntil(this.destroy$)
      ).subscribe(event => {
        this.isDetailsPage.set(event.urlAfterRedirects.startsWith('/coffeeshops'));
      });

      // localStorage.setItem('mySavedData', JSON.stringify(this.totalCoffeeShops))
    }

    updateSearchResults(): void {
      this.filterResults(this.searchControl().value || '');
    }

    handleSearchInput(event: Event): void {
      const input = event.target as HTMLInputElement | null;
      const value = input?.value ?? '';

      this.searchControl().setValue(value, { emitEvent: false });
      this.updateSearchResults();
    }
    
    setFilterControl(): void {
      this.searchControl().setValue('');
      this.cdr.detectChanges();
    }

    handleFilterChange(
      option: 'outlets' | 'bathrooms' | 'outdoorSeating' | 'cashOk',
      checked: boolean
    ): void {
      const nextFilters = { ...this.filterOptions() };
      nextFilters[option] = checked;
      this.filterOptions.set(nextFilters);
      this.updateSearchResults();
    }

    filterResults(term: string): Array<CoffeeShop> {
      const cleanTerm = term.toLowerCase().trim();
      const activeFilters = this.filterOptions();
      const hasActiveFilters = Object.values(activeFilters).some(Boolean);

      const nextFilteredItems = this.displayCoffeeShops().filter(item => {
        const matchesText = !cleanTerm || item.name.toLowerCase().includes(cleanTerm) || item.city.toLowerCase().startsWith(cleanTerm);

        if (!matchesText) {
          return false;
        }

        if (!hasActiveFilters) {
          return true;
        }

        return (
          (!activeFilters.outlets || item.outlets) &&
          (!activeFilters.bathrooms || item.bathrooms) &&
          (!activeFilters.outdoorSeating || item.outdoorSeating) &&
          (!activeFilters.cashOk || item.cashOk)
        );
      });

      this.filteredItems.set(nextFilteredItems);
      this.filteredCoffeeShopCount.set(nextFilteredItems.length);

      console.log('Filtered items:', this.filteredItems());
      return this.filteredItems();
    }
    
    getTotalCoffeeShops(): number {
      this.coffeeShopService.getTotalCoffeeShops().subscribe(count => {
        this.totalCoffeeShops = signal(count);
        this.cdr.detectChanges()
        console.log(`Total coffee shops: ${this.totalCoffeeShops}`);
      });
      
      return this.totalCoffeeShops();   
    }
    getTopPickCoffeeShopRating(): number {
      this.coffeeShopService.getTopPickCoffeeShopRating().subscribe(rating => {
        this.topPickCoffeeShopRating = signal(rating);
        this.cdr.detectChanges();
        console.log(`Top pick coffee shop rating: ${this.topPickCoffeeShopRating()}`);
      });
      return this.topPickCoffeeShopRating();
    }

    getTopPickCoffeeShop(): CoffeeShop {
      this.coffeeShopService.getTopCoffeeShop().subscribe(coffeeShop => {
        this.topPickCoffeeShop = signal(coffeeShop);
        this.cdr.detectChanges();
        console.log(`Top pick coffee shop: ${this.topPickCoffeeShop().name}`);
      });
      return this.topPickCoffeeShop();
    }
    getTopPickCoffeeShopReview(): Review {
      this.coffeeShopService.getTopPickCoffeeShopReview().subscribe(review => {
        this.topPickCoffeeShopReview = signal(review);
        this.cdr.detectChanges();
        console.log(`Top pick coffee shop review: ${this.topPickCoffeeShopReview().review}`);
      });
      return this.topPickCoffeeShopReview();
    }

    getAllCoffeeShops(): Array<CoffeeShop> {
      this.coffeeShopService.getAllCoffeeShops().subscribe(coffeeShops => {
        this.displayCoffeeShops = signal(coffeeShops);
        this.cdr.detectChanges();
        this.filteredItems = signal(coffeeShops);
        console.log(`All coffee shops: ${this.displayCoffeeShops().length}`);
      });
      this.filterResults(this.searchControl().value || '');
      return this.displayCoffeeShops();
    }

    getAllCoffeeShopsByCity(city: string): void {
      this.coffeeShopService.getAllCoffeeShopsByCity(city).subscribe(coffeeShops => {
        this.displayCoffeeShops = signal(coffeeShops);
        this.cdr.detectChanges();
        console.log(`Coffee shops in ${city}: ${this.displayCoffeeShops().length}`);
      });
    }


    ngOnDestroy() {
      this.destroy$.next();
      this.destroy$.complete();
    }
}


