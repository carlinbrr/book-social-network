import {Component, inject, input, OnInit, output} from '@angular/core';
import {PaginationFooter} from "../pagination-footer/pagination-footer";
import {Rating} from "../rating/rating";
import {PageResponseFeedbackResponse} from '../../../../services/models/page-response-feedback-response';
import {FeedbackService} from '../../../../services/services/feedback.service';

@Component({
  selector: 'app-feedback',
    imports: [
        PaginationFooter,
        Rating
    ],
  templateUrl: './feedback.html',
  styleUrl: './feedback.scss'
})
export class Feedback implements OnInit {

  feedbacks: PageResponseFeedbackResponse = {};
  totalPages: number = 0;

  bookId = input<number>(0);
  size = input<number>(5);

  totalFeedbacks = output<number>();

  feedbackService = inject(FeedbackService);

  ngOnInit(): void {
    this.findAllFeedbacks(0);
  }

  findAllFeedbacks(page: number) {
    this.feedbackService.findAllFeedbacksByBook({
      'book-id': this.bookId(),
      page: page,
      size: this.size()
    }).subscribe({
      next: (feedbackResponse) => {
        this.feedbacks = feedbackResponse;
        this.totalPages = feedbackResponse.totalPages || 0;
        this.totalFeedbacks.emit(feedbackResponse.totalElements || 0);
      }
    });
  }

  onPageChanges(page: number) {
    this.findAllFeedbacks(page);
  }

}
