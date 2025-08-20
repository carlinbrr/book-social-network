import {Component, input} from '@angular/core';

@Component({
  selector: 'app-rating',
  imports: [],
  templateUrl: './rating.html',
  styleUrl: './rating.scss'
})
export class Rating {

  rating = input<number>(0);
  maxRating : number = 5;

  get fullStarsArray(): Array<any> {
    return Array(Math.floor(this.rating()));
  }

  get hasHalfStar() : boolean {
    return this.rating() % 1 !== 0;
  }

  get emptyStarsArray(): Array<any> {
    return Array(this.maxRating - Math.ceil(this.rating()));
  }

}
