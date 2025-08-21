import {Component, inject, OnInit, signal} from '@angular/core';
import {BookCard} from '../../components/book-card/book-card';
import {PaginationFooter} from '../../components/pagination-footer/pagination-footer';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {BookService} from '../../../../services/services/book.service';
import {RouterLink} from '@angular/router';
import {BookAction, BookActionType} from '../../models/book-card.model';

@Component({
  selector: 'app-my-books',
  imports: [
    BookCard,
    PaginationFooter,
    RouterLink
  ],
  templateUrl: './my-books.html',
  styleUrl: './my-books.scss'
})
export class MyBooks implements OnInit {

  bookResponse = signal<PageResponseBookResponse>({});
  totalPages : number = 0;

  bookService = inject(BookService);

  ngOnInit(): void {
    this.findAllBooks(0);
  }

  private findAllBooks(page: number) {
    this.bookService.findAllBooksByOwner({
      page: page,
      size: 5
    }).subscribe({
      next: result => {
        this.bookResponse.set(result);
        this.totalPages = this.bookResponse().totalPages || 0;
      }
    })
  }

  onPageChanges(page: number) {
    this.findAllBooks(page);
  }

  handleBookAction(bookAction: BookAction) {
    const book = bookAction.book;
    switch (bookAction.actionType) {
      case BookActionType.EDIT:
        break;

      case BookActionType.SHARE:
        break;

      case BookActionType.ARCHIVE:
        break;
    }
  }

}
