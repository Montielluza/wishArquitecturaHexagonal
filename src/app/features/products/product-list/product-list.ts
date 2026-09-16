import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../../shared/models/product.model';
import { ProductService } from '../../../core/services/product.service';
import { WishlistService } from '../../../core/services/wishlist.service';
import { ProductCard } from '../product-card/product-card';
import { Spinner } from '../../../shared/components/spinner/spinner';

@Component({
  selector: 'app-product-list',
  imports: [CommonModule, ProductCard, Spinner],
  templateUrl: './product-list.html',
  styleUrl: './product-list.scss',
})
export class ProductList implements OnInit {
  products: Product[] = [];
  loading = true;
  errorMessage = '';
  successMessage = '';

  constructor(
    private productService: ProductService,
    private wishlistService: WishlistService
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.errorMessage = '';
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No fue posible obtener los productos. Inténtalo nuevamente.';
        this.loading = false;
      }
    });
  }

  onAddToWishlist(product: Product): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.wishlistService.createWishlist({ productId: product.id, quantity: 1 }).subscribe({
      next: () => {
        this.successMessage = `"${product.name}" se agregó a tu wishlist.`;
      },
      error: (err) => {
        this.errorMessage = err?.error?.message ?? 'No fue posible agregar el producto a la wishlist.';
      }
    });
  }
}