import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PaginationFooter} from '../../components/pagination-footer/pagination-footer';
import {Rating} from '../../components/rating/rating';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {BookService} from '../../../../services/services/book.service';
import {Router} from '@angular/router';
import {BookResponse} from '../../../../services/models/book-response';

@Component({
  selector: 'app-waiting-list',
  imports: [
    FormsModule,
    PaginationFooter,
    Rating,
    ReactiveFormsModule
  ],
  templateUrl: './waiting-list.html',
  styleUrl: './waiting-list.scss'
})
export class WaitingList implements OnInit{

  books = signal<PageResponseBookResponse>({});
  totalPages: number = 0;

  bookService = inject(BookService);
  router= inject(Router);

  ngOnInit(): void {
    this.getWaitingList(0);
  }

  getWaitingList(page: number) {
    this.bookService.getWaitingList({
      page: 0,
      size: 5
    }).subscribe({
      next: response => {
        this.books.set(response);
        this.totalPages = response.totalPages || 0;
      }
    });
  }

  onPageChanged(page: number) {
    this.getWaitingList(page);
  }

  showInfo(bookId: number) {
    this.router.navigate(['books', 'details', bookId]);
  }

}
