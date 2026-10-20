import { Component, OnDestroy, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CoffeeShopService } from '../../services/coffeeshop';
import { CoffeeShop } from '../../models/coffeeshop';
import { Review } from '../../models/review';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-shoppage',
  imports: [CommonModule, RouterModule],
  templateUrl: './shoppage.html',
  styleUrl: './shoppage.css',
})
export class Shoppage implements OnInit, OnDestroy {
  coffeeShop = signal<CoffeeShop | null>(null);
  reviews = signal<Review[]>([]);
  loading = signal(true);

  private destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private coffeeShopService: CoffeeShopService
  ) {}

  ngOnInit(): void {
    const rawId = this.route.snapshot.paramMap.get('id');
    const id = rawId ? Number(rawId) : NaN;

    if (!Number.isFinite(id)) {
      this.loading.set(false);
      return;
    }

    this.coffeeShopService.getCoffeeShopById(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe(shop => {
        this.coffeeShop.set(shop);
        this.loading.set(false);
      });

    this.coffeeShopService.getAllReviewsForCoffeeShopById(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe(reviews => {
        this.reviews.set(reviews ?? []);
      });
  }

  getAverageRating(): string {
    const list = this.reviews();
    if (!list.length) {
      return '—';
    }
    const total = list.reduce((sum, review) => sum + review.rating, 0);
    return (total / list.length).toFixed(1);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

