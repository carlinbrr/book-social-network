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
      }
    ]
  }
];
