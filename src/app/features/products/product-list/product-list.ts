import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../../shared/models/product.model';
import { ProductService } from '../../../core/services/product.service';
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

  constructor(private productService: ProductService) {}

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
    console.log('Agregar a wishlist:', product);
  }
}