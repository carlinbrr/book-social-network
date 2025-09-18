import {Component, inject, OnInit, signal} from '@angular/core';
import {BookService} from '../../../../services/services/book.service';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {BookCard} from '../../components/book-card/book-card';
import {BookAction, BookActionType} from '../../models/book-card.model';
import {PaginationFooter} from '../../components/pagination-footer/pagination-footer';
import {Router} from '@angular/router';

@Component({
  selector: 'app-book-list',
  imports: [
    BookCard,
    PaginationFooter
  ],
  templateUrl: './book-list.html',
  styleUrl: './book-list.scss'
})
export class BookList implements OnInit {

  bookResponse = signal<PageResponseBookResponse>({});
  message = signal<string>('');
  isMessageSuccess : boolean = true;
  totalPages : number = 0;

  bookService = inject(BookService);
  router = inject(Router);

  ngOnInit(): void {
      this.findAllBooks(0);
  }

  private findAllBooks(page: number) {
    this.bookService.findAllBooks({
      page: page,
      size: 5
    }).subscribe({
      next: result => {
        this.bookResponse.set(result);
        this.totalPages = result.totalPages || 0;
      }
    })
  }

  onPageChanges(page: number) {
    this.findAllBooks(page);
  }

  handleBookAction(bookAction: BookAction) {
    this.message.set('');
    this.isMessageSuccess = false;
    const book = bookAction.book;

    switch (bookAction.actionType) {
      case BookActionType.BORROW:
        this.bookService.borrowBook({
          'book-id': book.id as number
        }).subscribe({
          next: () => {
            this.message.set('Book successfully added to your list');
            this.isMessageSuccess = true;
          },
          error: err => {
            console.log(err);
            this.isMessageSuccess = false;
            this.message.set(err.error.error);
          }
        });
        break;

      case BookActionType.SHOW_DETAILS:
        this.router.navigate(['books', 'details', book.id]);
    }
  }

}
