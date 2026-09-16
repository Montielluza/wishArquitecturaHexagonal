import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Product } from '../../../shared/models/product.model';
import { ProductService } from '../../../core/services/product.service';
import { WishlistService } from '../../../core/services/wishlist.service';
import { Spinner } from '../../../shared/components/spinner/spinner';
import { CurrencyFormatPipe } from '../../../shared/pipes/currency-format-pipe';

@Component({
  selector: 'app-product-detail',
  imports: [CommonModule, RouterLink, Spinner, CurrencyFormatPipe],
  templateUrl: './product-detail.html',
  styleUrl: './product-detail.scss',
})
export class ProductDetail implements OnInit {
  product: Product | null = null;
  loading = true;
  errorMessage = '';
  successMessage = '';

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private wishlistService: WishlistService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProductById(id).subscribe({
      next: (data) => {
        this.product = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No fue posible obtener el producto. Inténtalo nuevamente.';
        this.loading = false;
      }
    });
  }

  onAddToWishlist(): void {
    if (!this.product) return;
    this.successMessage = '';
    this.errorMessage = '';
    this.wishlistService.createWishlist({ productId: this.product.id, quantity: 1 }).subscribe({
      next: () => {
        this.successMessage = 'Producto agregado a tu wishlist.';
      },
      error: (err) => {
        this.errorMessage = err?.error?.message ?? 'No fue posible agregar el producto a la wishlist.';
      }
    });
  }
}