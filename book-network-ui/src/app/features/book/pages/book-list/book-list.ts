import {Component, inject, OnInit, signal} from '@angular/core';
import {BookService} from '../../../../services/services/book.service';
import {PageResponseBookResponse} from '../../../../services/models/page-response-book-response';
import {BookCard} from '../../components/book-card/book-card';
import {BookAction, BookActionType} from '../../models/book-card.model';
import {PaginationFooter} from '../../components/pagination-footer/pagination-footer';
import {ActivatedRoute, Router} from '@angular/router';

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
  searchTerm: string = '';

  bookService = inject(BookService);
  router = inject(Router);
  activatedRoute = inject(ActivatedRoute)

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(
      params => {
        this.searchTerm =  params['name'] || '';
        console.log(this.searchTerm);
        this.findAllBooks(0);
      }
    );
    this.findAllBooks(0);
  }

  private findAllBooks(page: number) {
    this.bookService.findAllBooks({
      page: page,
      size: 5,
      searchTerm: this.searchTerm
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
            this.message.set('Book successfully borrowed, and added to your list');
            this.isMessageSuccess = true;
          },
          error: err => {
            this.isMessageSuccess = false;
            this.message.set(err.error.error);
          }
        });
        break;

      case BookActionType.SHOW_DETAILS:
        this.router.navigate(['books', 'details', book.id]);
        break;

      case BookActionType.ADD_TO_WAITING_LIST:
        if ( book.isInWaitingList ) {
          this.bookService.removeFromWaitingList({
            'book-id': book.id as number
          }).subscribe({
            next: () => {
              book.isInWaitingList = false;
            }
          });
          break;
        }

        this.bookService.addToWaitingList({
          'book-id': book.id as number
        }).subscribe({
          next: () => {
            book.isInWaitingList = true;
          }
        });
    }
  }

}
