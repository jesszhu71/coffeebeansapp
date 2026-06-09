import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CoffeeShopService } from './services/coffeeshop';
import { OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { BrowserModule } from '@angular/platform-browser';
import { ChangeDetectorRef } from '@angular/core';
import { CoffeeShop } from './models/coffeeshop';
import { Review } from './models/review';


@Component({
  selector: 'app-root',
  imports: [CommonModule, 
    RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
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

  constructor(private coffeeShopService: CoffeeShopService, private cdr: ChangeDetectorRef) { // runs first

  }

  protected readonly title = signal('coffeebeans_ui');
    ngOnInit(): void { // run second
      this.getTotalCoffeeShops();
      this.getTopPickCoffeeShop();
      this.getTopPickCoffeeShopReview();
      this.getTopPickCoffeeShopRating();
      // localStorage.setItem('mySavedData', JSON.stringify(this.totalCoffeeShops))
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
}


