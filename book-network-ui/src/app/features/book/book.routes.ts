import { Routes } from '@angular/router';

export const bookRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/main/main').then(m => m.Main),
    children: [
      {
        path: '',
        loadComponent: () => import('./pages/book-list/book-list').then(m => m.BookList)
      },
      {
        path: 'my-books',
        loadComponent: () => import('./pages/my-books/my-books').then(m => m.MyBooks)
      },
      {
        path: 'manage',
        loadComponent: () => import('./pages/manage-book/manage-book').then(m => m.ManageBook)
      },
      {
        path: 'manage/:bookId',
        loadComponent: () => import('./pages/manage-book/manage-book').then(m => m.ManageBook)
      },
      {
        path: 'my-borrowed-books',
        loadComponent: () => import('./pages/borrowed-book-list/borrowed-book-list').then(m => m.BorrowedBookList)
      },
      {
        path: 'my-returned-books',
        loadComponent: () => import('./pages/returned-books/returned-books').then(m => m.ReturnedBooks)
      }
    ]
  }
];
