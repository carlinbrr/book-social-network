import {Component, inject, OnInit, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {BookRequest} from '../../../../services/models/book-request';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {BookService} from '../../../../services/services/book.service';

@Component({
  selector: 'app-manage-book',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './manage-book.html',
  styleUrl: './manage-book.scss'
})
export class ManageBook implements OnInit {

  errorMsg = signal<Array<string>>([]);
  selectedPicture : string | undefined;
  selectedBookCover : any;
  bookRequest : BookRequest = {
    authorName: '',
    isbn: '',
    synopsis: '',
    title: ''
  };

  bookService = inject(BookService);

  router = inject(Router);

  activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    const bookId = this.activatedRoute.snapshot.params['bookId'];
    if (bookId) {
      this.bookService.findById({
        'book-id': bookId
      }).subscribe({
        next: book => {
          this.bookRequest = {
            id: book.id,
            title: book.title as string,
            authorName: book.authorName as string,
            isbn: book.isbn as string,
            synopsis: book.synopsis as string,
            shareable: book.shareable
          }
          if (book.cover) {
            this.selectedPicture = 'data:image/jpg;base64,' + book.cover;
          }
        }
      })
    }
  }

  onFileSelected(event: any) {
    this.selectedBookCover = event.target.files[0];
    if (this.selectedBookCover) {
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      }
      reader.readAsDataURL(this.selectedBookCover);
    }
  }

  saveBook() {
    this.bookService.saveBook(
      {
        body: this.bookRequest
      }).subscribe({
      next: bookId => {
        if ( !this.selectedBookCover ) {
          this.router.navigate(['books', 'my-books']);
          return;
        }
        this.bookService.uploadBookCoverPicture({
          'book-id': bookId,
          body: {
            file: this.selectedBookCover
          }
        }).subscribe({
          next: () => {
            this.router.navigate(['books', 'my-books']);
          }
        })
      },
      error: err => {
        this.errorMsg.set(err.error.validationErrors);
      }
    })
  }
}
