import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { History } from '../../shared/models/history.model';
import { ApiResponse } from '../../shared/models/api-response.model';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  private apiUrl = 'https://wisharquitecturahexagonal-production.up.railway.app/history';

  constructor(private http: HttpClient) {}

  getHistory(): Observable<History[]> {
    return this.http.get<ApiResponse<History[]>>(this.apiUrl)
      .pipe(map(response => response.data));
  }
}