import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Wishlist, WishlistRequest } from '../../shared/models/wishlist.model';
import { ApiResponse } from '../../shared/models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  private apiUrl = 'https://wisharquitecturahexagonal-production.up.railway.app/wishlist';

  constructor(private http: HttpClient) {}

  getWishlist(): Observable<Wishlist[]> {
    return this.http.get<ApiResponse<Wishlist[]>>(this.apiUrl)
      .pipe(map(response => response.data));
  }

  createWishlist(item: WishlistRequest): Observable<Wishlist> {
    return this.http.post<ApiResponse<Wishlist>>(this.apiUrl, item)
      .pipe(map(response => response.data));
  }

  updateWishlist(id: number, item: WishlistRequest): Observable<Wishlist> {
    return this.http.put<ApiResponse<Wishlist>>(`${this.apiUrl}/${id}`, item)
      .pipe(map(response => response.data));
  }

  deleteWishlist(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`)
      .pipe(map(() => undefined));
  }
}