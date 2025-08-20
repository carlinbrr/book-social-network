import {Component, inject, OnInit, signal} from '@angular/core';
import {BookService} from '../../../../services/services/book.service';
import { Router } from '@angular/router';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {BookCard} from '../../components/book-card/book-card';

@Component({
  selector: 'app-book-list',
  imports: [
    BookCard
  ],
  templateUrl: './book-list.html',
  styleUrl: './book-list.scss'
})
export class BookList implements OnInit {

  bookResponse = signal<PageResponseBookResponse>({});

  page = 0;
  size = 5;
  pages : Array<number> = [];

  bookService = inject(BookService);
  router = inject(Router);

  ngOnInit(): void {
      this.findAllBooks();
  }

  private findAllBooks() {
    this.bookService.findAllBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: result => {
        this.bookResponse.set(result);
        this.pages = Array(result.totalPages);
      }
    })
  }

  goToFirstPage() {

  }

  goToPreviousPage() {

  }

  goToPage(number: number) {

  }

  gotToNextPage() {

  }

  goToLastPage() {

  }

  get isLastPage() : boolean {
    return this.page + 1 === this.bookResponse().totalPages;
  }
}
