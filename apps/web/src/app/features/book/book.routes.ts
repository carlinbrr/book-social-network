import { Routes } from '@angular/router';
import {authGuard} from '../../services/guard/auth-guard';

export const bookRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/main/main').then(m => m.Main),
    canActivate: [authGuard],
    children: [
      {
        path: '',
        loadComponent: () => import('./pages/book-list/book-list').then(m => m.BookList),
        canActivate: [authGuard]
      },
      {
        path: 'details/:bookId',
        loadComponent: () => import('./pages/book-details/book-details').then(m => m.BookDetails),
        canActivate: [authGuard]
      },
      {
        path: 'my-books',
        loadComponent: () => import('./pages/my-books/my-books').then(m => m.MyBooks),
        canActivate: [authGuard]
      },
      {
        path: 'manage',
        loadComponent: () => import('./pages/manage-book/manage-book').then(m => m.ManageBook),
        canActivate: [authGuard]
      },
      {
        path: 'manage/:bookId',
        loadComponent: () => import('./pages/manage-book/manage-book').then(m => m.ManageBook),
        canActivate: [authGuard]
      },
      {
        path: 'my-waiting-list',
        loadComponent: () => import('./pages/waiting-list/waiting-list').then(m => m.WaitingList),
        canActivate: [authGuard]
      },
      {
        path: 'my-returned-books',
        loadComponent: () => import('./pages/returned-books/returned-books').then(m => m.ReturnedBooks),
        canActivate: [authGuard]
      },
      {
        path: 'my-borrowed-books',
        loadComponent: () => import('./pages/borrowed-book-list/borrowed-book-list').then(m => m.BorrowedBookList),
        canActivate: [authGuard]
      }
    ]
  }
];
