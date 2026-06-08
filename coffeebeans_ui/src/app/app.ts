import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CoffeeShopService } from './services/coffeeshop';
import { OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { BrowserModule } from '@angular/platform-browser';
import { ChangeDetectorRef } from '@angular/core';


@Component({
  selector: 'app-root',
  imports: [CommonModule, 
    RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  //totalCoffeeShops : number = 0;
  totalCoffeeShops = signal(0)

  constructor(private coffeeShopService: CoffeeShopService, private cdr: ChangeDetectorRef) { // runs first

  }

  protected readonly title = signal('coffeebeans_ui');
    ngOnInit(): void { // run second
      this.getTotalCoffeeShops();
      // localStorage.setItem('mySavedData', JSON.stringify(this.totalCoffeeShops))
    }
    
    getTotalCoffeeShops(): number {
      this.coffeeShop.getTotalCoffeeShops().subscribe(count => {
        this.totalCoffeeShops = signal(count);
        this.cdr.detectChanges()
        console.log(`Total coffee shops: ${this.totalCoffeeShops}`);
      });
      
      return this.totalCoffeeShops();   
    }
    onRefresh() {
      this.getTotalCoffeeShops(); // Calls the service and updates the view
    }
}


