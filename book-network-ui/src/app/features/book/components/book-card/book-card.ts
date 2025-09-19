import {Component, EventEmitter, input, Input, output, Output} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {BookAction, BookActionType} from '../../models/book-card.model';
import {Rating} from '../rating/rating';

@Component({
  selector: 'app-book-card',
  imports: [
    Rating
  ],
  templateUrl: './book-card.html',
  styleUrl: './book-card.scss'
})
export class BookCard {

  book= input<BookResponse>({});

  manage = input<boolean>(false);

  bookAction= output<BookAction>();

  get bookCover(): string {
    return 'data:image/jpg;base64,' + this.book().cover;
  }

  onShowDetails() {
    this.bookAction.emit({
      book: this.book(),
      actionType: BookActionType.SHOW_DETAILS
    })
  }

  onBorrow() {
    this.bookAction.emit({
      book: this.book(),
      actionType: BookActionType.BORROW
    })
  }

  onAddToWaitingList() {
    this.bookAction.emit({
      book: this.book(),
      actionType: BookActionType.ADD_TO_WAITING_LIST
    })
  }

  onEdit() {
    this.bookAction.emit({
      book: this.book(),
      actionType: BookActionType.EDIT
    })
  }

  onShare() {
    this.bookAction.emit({
        book: this.book(),
      actionType: BookActionType.SHARE
    })
  }

  onArchive() {
    this.bookAction.emit({
      book: this.book(),
      actionType: BookActionType.ARCHIVE
    })
  }
}
