import {Component, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {BookRequest} from '../../../../services/models/book-request';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-manage-book',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './manage-book.html',
  styleUrl: './manage-book.scss'
})
export class ManageBook {

  errorMsg = signal<Array<string>>([]);
  selectedPicture : string | undefined;
  selectedBookCover : any;
  bookRequest : BookRequest = {
    authorName: '',
    isbn: '',
    synopsis: '',
    title: ''
  };

  onFileSelected(event: any) {
    this.selectedBookCover = event.target.files[0];
    console.log(this.selectedBookCover);
    if (this.selectedBookCover) {
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      }
      reader.readAsDataURL(this.selectedBookCover);
    }
  }
}
