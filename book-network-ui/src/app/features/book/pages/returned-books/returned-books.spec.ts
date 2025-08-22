import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnedBooks } from './returned-books';

describe('ReturnedBooks', () => {
  let component: ReturnedBooks;
  let fixture: ComponentFixture<ReturnedBooks>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReturnedBooks]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReturnedBooks);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
