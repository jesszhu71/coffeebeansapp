import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Shoppage } from './shoppage';
import { CoffeeShopService } from '../../services/coffeeshop';

describe('Shoppage', () => {
  let component: Shoppage;
  let fixture: ComponentFixture<Shoppage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Shoppage],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: () => '1',
              },
            },
          },
        },
        {
          provide: CoffeeShopService,
          useValue: {
            getCoffeeShopById: () => of({
              id: 1,
              name: 'Bean & Bloom',
              city: 'Seattle',
              state: 'WA',
              fullAddress: '123 Main St',
              outlets: true,
              bathrooms: true,
              outdoorSeating: true,
              cashOk: true,
            }),
            getAllReviewsForCoffeeShopById: () => of([]),
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Shoppage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
