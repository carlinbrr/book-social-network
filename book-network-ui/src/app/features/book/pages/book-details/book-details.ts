import {Component, inject, OnInit} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {PageResponseFeedbackResponse} from '../../../../services/models/page-response-feedback-response';
import {BookService} from '../../../../services/services/book.service';
import {FeedbackService} from '../../../../services/services/feedback.service';
import {ActivatedRoute} from '@angular/router';
import {Rating} from '../../components/rating/rating';
import {PaginationFooter} from '../../components/pagination-footer/pagination-footer';

@Component({
  selector: 'app-book-details',
  imports: [
    Rating,
    PaginationFooter
  ],
  templateUrl: './book-details.html',
  styleUrl: './book-details.scss'
})
export class BookDetails implements OnInit {

  book: BookResponse = {};
  feedbacks: PageResponseFeedbackResponse = {};
  totalPages: number = 0;
  bookId: number = 0;

  bookService = inject(BookService);
  feedbackService = inject(FeedbackService);
  activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.bookId = this.activatedRoute.snapshot.params['bookId'];
    if (this.bookId) {
      this.bookService.findById({
        'book-id': this.bookId
      }).subscribe({
        next: (book) => {
          this.book = book;
          this.findAllFeedbacks(0);
        }
      });
    }
  }

  findAllFeedbacks(page: number) {
    this.feedbackService.findAllFeedbacksByBook({
      'book-id': this.bookId,
      page: page,
      size: 5
    }).subscribe({
      next: (feedbackResponse) => {
        this.feedbacks = feedbackResponse;
        this.totalPages = feedbackResponse.totalPages || 0;
      }
    });
  }

  onPageChanges(page: number) {
    this.findAllFeedbacks(page);
  }

}
