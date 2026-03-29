import { Routes } from '@angular/router';
import {authGuard} from './services/guard/auth-guard';

export const routes: Routes = [
  {
    path: 'books',
    loadChildren: () => import("./features/book/book.routes").then(r => r.bookRoutes),
    canActivate:[authGuard]
  },
  {
    path: '',
    redirectTo: 'books',
    pathMatch: 'full'
  }
];
