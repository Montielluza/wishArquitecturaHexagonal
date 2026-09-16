import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { History } from '../../shared/models/history.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  private apiUrl = `${environment.apiUrl}/history`;

  constructor(private http: HttpClient) {}

  getHistory(): Observable<History[]> {
    return this.http.get<ApiResponse<History[]>>(this.apiUrl)
      .pipe(map(response => response.data));
  }
}