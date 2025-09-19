import {Component, inject, OnInit} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {BookService} from '../../../../services/services/book.service';
import {ActivatedRoute} from '@angular/router';
import {Rating} from '../../components/rating/rating';
import {Feedback} from '../../components/feedback/feedback';

@Component({
  selector: 'app-book-details',
  imports: [
    Rating,
    Feedback
  ],
  templateUrl: './book-details.html',
  styleUrl: './book-details.scss'
})
export class BookDetails implements OnInit {

  book: BookResponse = {};
  bookId: number = 0;
  totalFeedbacks: number = 0;

  bookService = inject(BookService);
  activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.bookId = this.activatedRoute.snapshot.params['bookId'];
    if (this.bookId) {
      this.bookService.findById({
        'book-id': this.bookId
      }).subscribe({
        next: (book) => {
          this.book = book;
        }
      });
    }
  }

}
