import { TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';
import { App } from './app';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should refresh the filtered list when the search query is shortened by backspace', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;

    app.displayCoffeeShops = signal([
      { id: 1, name: 'Bean & Bloom', city: 'Seattle', state: 'WA', fullAddress: '123 Main St', outlets: true, bathrooms: true, outdoorSeating: true, cashOk: true },
      { id: 2, name: 'Coffee Harbor', city: 'Portland', state: 'OR', fullAddress: '456 Pine St', outlets: true, bathrooms: false, outdoorSeating: true, cashOk: false },
      { id: 3, name: 'Brew & Bean', city: 'Austin', state: 'TX', fullAddress: '789 Oak St', outlets: true, bathrooms: true, outdoorSeating: false, cashOk: true },
    ] as any);

    app.searchControl().setValue('bean');
    app.updateSearchResults();
    expect(app.filteredItems().length).toBe(2);

    app.searchControl().setValue('be');
    app.updateSearchResults();
    expect(app.filteredItems().length).toBe(3);
    expect(app.filteredItems().map(item => item.name)).toContain('Bean & Bloom');
  });
});
