import {Component, inject, OnInit, signal} from '@angular/core';
import {BookCard} from '../../components/book-card/book-card';
import {PaginationFooter} from '../../components/pagination-footer/pagination-footer';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {BookService} from '../../../../services/services/book.service';
import {Router, RouterLink} from '@angular/router';
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
  router = inject(Router);

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
        this.totalPages = result.totalPages || 0;
      }
    })
  }

  onPageChanged(page: number) {
    this.findAllBooks(page);
  }

  handleBookAction(bookAction: BookAction) {
    const book = bookAction.book;
    switch (bookAction.actionType) {
      case BookActionType.EDIT:
        this.router.navigate(['books', 'manage', book.id]);
        break;
      case BookActionType.SHARE:
        this.bookService.updateShareableStatus({
          'book-id': book.id as number
        }).subscribe({
          next: () => {
            book.shareable = !book.shareable;
          }
        });
        break;
      case BookActionType.ARCHIVE:
        this.bookService.updateArchivedStatus({
          'book-id': book.id as number
        }).subscribe({
          next: () => {
            book.archived = !book.archived;
          }
        });
    }
  }

}
