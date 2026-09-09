import { Routes } from '@angular/router';
import { ProductList } from './features/products/product-list/product-list';
import { ProductDetail } from './features/products/product-detail/product-detail';
import { WishlistList } from './features/wishlist/wishlist-list/wishlist-list';
import { HistoryList } from './features/history/history-list/history-list';

export const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  { path: 'products', component: ProductList },
  { path: 'products/:id', component: ProductDetail },
  { path: 'wishlist', component: WishlistList },
  { path: 'history', component: HistoryList }
];