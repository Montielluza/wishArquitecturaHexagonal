import { Product } from './product.model';

export interface Wishlist {
  id: number;
  quantity: number;
  createdAt: string;
  product: Product;
}

export interface WishlistRequest {
  productId: number;
  quantity: number;
}