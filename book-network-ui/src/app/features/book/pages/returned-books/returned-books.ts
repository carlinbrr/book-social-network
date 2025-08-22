import {Component, inject, OnInit, signal} from '@angular/core';
import {PaginationFooter} from '../../components/pagination-footer/pagination-footer';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {BookService} from '../../../../services/services/book.service';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';

@Component({
  selector: 'app-returned-books',
  imports: [
    PaginationFooter
  ],
  templateUrl: './returned-books.html',
  styleUrl: './returned-books.scss'
})
export class ReturnedBooks implements OnInit {

  returnedBooks = signal<PageResponseBorrowedBookResponse>({});
  message = signal<string>('');

  isMessageSuccess : boolean = true;
  totalPages: number = 0;

  bookService = inject(BookService);

  ngOnInit(): void {
    this.findAllReturnedBooks(0);
  }

  private findAllReturnedBooks(page: number) {
    this.bookService.findAllReturnedBooks({
      page: page,
      size: 5
    }).subscribe({
      next: response => {
        this.returnedBooks.set(response);
        this.totalPages = response.totalPages || 0;
      }
    })
  }

  onPageChanged(page: number) {
    this.findAllReturnedBooks(page);
  }

  approveBookReturn(returnedBook: BorrowedBookResponse) {

    if (!returnedBook.returned) {
      this.message.set('You cannot approve this book. It has not been returned yet!');
      this.isMessageSuccess = false;
      return;
    }
    this.bookService.approveReturnBorrowedBook({
      'book-id': returnedBook.id as number
    }).subscribe({
      next: () => {
        this.message.set('The book return has been approved!');
        this.isMessageSuccess = true;
        this.findAllReturnedBooks(0);
      },
      error: err => {
        this.message.set(err.error.error);
        this.isMessageSuccess = false;
      }
    })
  }
}
