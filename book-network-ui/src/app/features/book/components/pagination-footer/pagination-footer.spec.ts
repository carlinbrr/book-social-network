import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginationFooter } from './pagination-footer';

describe('PaginationFooter', () => {
  let component: PaginationFooter;
  let fixture: ComponentFixture<PaginationFooter>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginationFooter]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginationFooter);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
