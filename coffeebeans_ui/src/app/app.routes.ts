import { Routes } from '@angular/router';
import { Shoppage } from './components/shoppage/shoppage';

export const routes: Routes = [
  {
    path: 'coffeeshops/:id',
    component: Shoppage,
  },
];
