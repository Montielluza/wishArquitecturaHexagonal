import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { WishlistService } from '../../../core/services/wishlist.service';

@Component({
  selector: 'app-wishlist-form',
  imports: [ReactiveFormsModule],
  templateUrl: './wishlist-form.html',
  styleUrl: './wishlist-form.scss',
})
export class WishlistForm {
  form: FormGroup;
  errorMessage = '';
  successMessage = '';

  constructor(private fb: FormBuilder, private wishlistService: WishlistService) {
    this.form = this.fb.group({
      productId: ['', Validators.required],
      quantity: [1, [Validators.required, Validators.min(1), Validators.max(100)]]
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
        error: () => {
          this.errorMessage = 'No fue posible agregar el producto. Inténtalo nuevamente.';
        }
      });
    }
  }
}