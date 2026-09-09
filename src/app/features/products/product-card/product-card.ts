import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Product } from '../../../shared/models/product.model';
import { CurrencyFormatPipe } from '../../../shared/pipes/currency-format-pipe';

@Component({
  selector: 'app-product-card',
  imports: [CurrencyFormatPipe],
  templateUrl: './product-card.html',
  styleUrl: './product-card.scss',
})
export class ProductCard {
  @Input({ required: true }) product!: Product;
  @Output() addToWishlist = new EventEmitter<Product>();

  onAddClick() {
    this.addToWishlist.emit(this.product);
  }
}