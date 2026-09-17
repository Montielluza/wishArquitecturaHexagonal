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

  private fallbackImages: { [key: string]: string } = {
    'zenbook': 'zenbook.jpg',
    'mouse': 'mouse.webp',
    'teclado': 'teclado.jpg',
    'keychron': 'teclado.jpg',
    'monitor': 'monitor.jpg',
    'ultragear': 'monitor.jpg'
  };

  get imageUrl(): string {
    if (this.product.image) {
      return this.product.image;
    }
    const name = this.product.name.toLowerCase();
    for (const key in this.fallbackImages) {
      if (name.includes(key)) {
        return this.fallbackImages[key];
      }
    }
    return 'favicon.ico';
  }

  onAddClick() {
    this.addToWishlist.emit(this.product);
  }
}