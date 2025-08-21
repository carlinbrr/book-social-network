import {Component, input, OnChanges, output} from '@angular/core';

@Component({
  selector: 'app-pagination-footer',
  imports: [],
  templateUrl: './pagination-footer.html',
  styleUrl: './pagination-footer.scss'
})
export class PaginationFooter implements OnChanges {

  totalPages = input.required<number>();

  pageChanged = output<number>();

  current_page : number = 0;
  pages: Array<number> = [];

  ngOnChanges(): void {
    this.pages = Array(this.totalPages());
  }

  goToFirstPage() {
    this.current_page = 0;
    this.pageChanged.emit(this.current_page);
  }

  goToPreviousPage() {
    this.current_page--;
    this.pageChanged.emit(this.current_page);
  }

  goToPage(page: number) {
    this.current_page = page;
    this.pageChanged.emit(this.current_page);
  }

  gotToNextPage() {
    this.current_page++;
    this.pageChanged.emit(this.current_page);
  }

  goToLastPage() {
    this.current_page = this.totalPages();
    this.pageChanged.emit(this.current_page);

  }

  get isLastPage() : boolean {
    return this.current_page + 1 === this.totalPages();
  }

}
