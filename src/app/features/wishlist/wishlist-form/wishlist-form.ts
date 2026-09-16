import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { WishlistService } from '../../../core/services/wishlist.service';
import { ProductService } from '../../../core/services/product.service';
import { Product } from '../../../shared/models/product.model';

@Component({
  selector: 'app-wishlist-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './wishlist-form.html',
  styleUrl: './wishlist-form.scss',
})
export class WishlistForm implements OnInit {
  form: FormGroup;
  products: Product[] = [];
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private wishlistService: WishlistService,
    private productService: ProductService
  ) {
    this.form = this.fb.group({
      productId: ['', Validators.required],
      quantity: [1, [Validators.required, Validators.min(1), Validators.max(100)]]
    });
  }

  ngOnInit(): void {
    this.productService.getProducts().subscribe({
      next: (data) => (this.products = data),
      error: () => {
        this.errorMessage = 'No fue posible cargar el catálogo de productos.';
      }
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.errorMessage = '';
      this.successMessage = '';
      this.wishlistService.createWishlist(this.form.value).subscribe({
        next: () => {
          this.successMessage = 'Producto agregado a la wishlist.';
          this.form.reset({ productId: '', quantity: 1 });
        },
        error: (err) => {
          this.errorMessage = err?.error?.message ?? 'No fue posible agregar el producto. Inténtalo nuevamente.';
        }
      });
    }
  }
}