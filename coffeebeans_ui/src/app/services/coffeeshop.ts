import { Service } from '@angular/core';
import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { Review } from '../models/review';
import { CoffeeShop } from '../models/coffeeshop';

// @Service()
@Injectable({
  providedIn: 'root'
})
export class CoffeeShopService {
    private readonly apiBaseUrl = 'http://localhost:9000';

    constructor (private httpClient:HttpClient){
    }

    getAllCoffeeShops() : Observable<any>{
        return this.httpClient.get<Array<CoffeeShop>>(`${this.apiBaseUrl}/coffeeshop/getAll`);

    }

    getCoffeeShopById(id : number) : Observable<any>{
        return this.httpClient.get<CoffeeShop>(`${this.apiBaseUrl}/coffeeshop/get/${id}`);
    }

    getAllReviewsForCoffeeShopById(id : number) : Observable<any>{
        return this.httpClient.get<Array<Review>>(`${this.apiBaseUrl}/review/byShop/${id}`);
    }

    getCoffeeShopHealth(){
        console.log("health check: ok");
    }
     
    // get total count of coffee shops
    getTotalCoffeeShops() : Observable<number>{
        return this.httpClient.get<number>(`${this.apiBaseUrl}/coffeeshop/getCount`);
    }

    // get top rec from db
    getTopCoffeeShop(): Observable<CoffeeShop>{
        return this.httpClient.get<CoffeeShop>(`${this.apiBaseUrl}/coffeeshop/getTop`);
    }

    // get top rec by neighborhood

    // 







}
