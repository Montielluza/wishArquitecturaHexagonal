import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { History } from '../../../shared/models/history.model';
import { HistoryService } from '../../../core/services/history.service';

@Component({
  selector: 'app-history-list',
  imports: [CommonModule],
  templateUrl: './history-list.html',
  styleUrl: './history-list.scss',
})
export class HistoryList implements OnInit {
  history: History[] = [];
  loading = true;
  errorMessage = '';

  constructor(private historyService: HistoryService) {}

  ngOnInit(): void {
    this.loadHistory();
  }

  loadHistory(): void {
    this.loading = true;
    this.errorMessage = '';
    this.historyService.getHistory().subscribe({
      next: (data) => {
        this.history = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No fue posible obtener el historial. Inténtalo nuevamente.';
        this.loading = false;
      }
    });
  }
}