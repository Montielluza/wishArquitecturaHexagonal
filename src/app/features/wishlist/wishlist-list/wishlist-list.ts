import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Wishlist } from '../../../shared/models/wishlist.model';
import { WishlistService } from '../../../core/services/wishlist.service';
import { Modal } from '../../../shared/components/modal/modal';

@Component({
  selector: 'app-wishlist-list',
  imports: [CommonModule, Modal],
  templateUrl: './wishlist-list.html',
  styleUrl: './wishlist-list.scss',
})
export class WishlistList implements OnInit {
  items: Wishlist[] = [];
  loading = true;
  errorMessage = '';
  showModal = false;
  itemToDelete: number | null = null;

  constructor(private wishlistService: WishlistService) {}

  ngOnInit(): void {
    this.loadWishlist();
  }

  loadWishlist(): void {
    this.loading = true;
    this.errorMessage = '';
    this.wishlistService.getWishlist().subscribe({
      next: (data) => {
        this.items = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No fue posible obtener la wishlist. Inténtalo nuevamente.';
        this.loading = false;
      }
    });
  }

  updateQuantity(item: Wishlist, quantity: number): void {
    if (quantity < 1) return;
    this.wishlistService.updateWishlist(item.id, { productId: item.product.id, quantity }).subscribe({
      next: () => this.loadWishlist(),
      error: () => {
        this.errorMessage = 'No fue posible actualizar el producto. Inténtalo nuevamente.';
      }
    });
  }

  askDelete(id: number): void {
    this.itemToDelete = id;
    this.showModal = true;
  }

  confirmDelete(): void {
    if (this.itemToDelete === null) return;
    this.wishlistService.deleteWishlist(this.itemToDelete).subscribe({
      next: () => {
        this.showModal = false;
        this.itemToDelete = null;
        this.loadWishlist();
      },
      error: () => {
        this.errorMessage = 'No fue posible eliminar el producto. Inténtalo nuevamente.';
        this.showModal = false;
      }
    });
  }

  cancelDelete(): void {
    this.showModal = false;
    this.itemToDelete = null;
  }
}