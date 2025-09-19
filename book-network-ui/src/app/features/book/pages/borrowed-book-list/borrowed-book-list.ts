import {Component, inject, OnInit, signal} from '@angular/core';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';
import { BookService } from '../../../../services/services/book.service';
import {PaginationFooter} from '../../components/pagination-footer/pagination-footer';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {FormsModule} from '@angular/forms';
import {Rating} from '../../components/rating/rating';
import {FeedbackService} from '../../../../services/services/feedback.service';
import {Feedback} from '../../components/feedback/feedback';

@Component({
  selector: 'app-borrowed-book-list',
  imports: [
    PaginationFooter,
    FormsModule,
    Rating,
    Feedback,
  ],
  templateUrl: './borrowed-book-list.html',
  styleUrl: './borrowed-book-list.scss'
})
export class BorrowedBookList implements OnInit {

  borrowedBooks = signal<PageResponseBorrowedBookResponse>({});
  totalPages: number = 0;
  selectedBook: BorrowedBookResponse | null = null;
  feedbackRequest: FeedbackRequest = {bookId: 0, comment: "", note: 0};

  bookService = inject(BookService);
  feedbackService = inject(FeedbackService);

  ngOnInit(): void {
    this.findAllBorrowedBooks(0);
  }

  private findAllBorrowedBooks(page: number) {
    this.bookService.findAllBorrowedBooks({
      page: page,
      size: 5
    }).subscribe({
      next: response => {
        this.borrowedBooks.set(response);
        this.totalPages = response.totalPages || 0;
      }
    })
  }

  onPageChanged(page: number) {
    this.findAllBorrowedBooks(page);
  }

  returnBook(hasFeedback: boolean) {
    this.bookService.returnBorrowedBook({
      'book-id': this.selectedBook?.id as number
    }).subscribe({
      next: () => {
        if (hasFeedback) {
          this.giveFeedback();
        }
        this.selectedBook = null;
        this.findAllBorrowedBooks(0);
      }
    })
  }

  private giveFeedback() {
    this.feedbackRequest.bookId = this.selectedBook?.id as number;
    this.feedbackService.saveFeedback({
      body: this.feedbackRequest
    }).subscribe({
      next: () => {
      }
    })
  }

}
