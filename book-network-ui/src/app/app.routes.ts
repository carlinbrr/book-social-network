import { Routes } from '@angular/router';
import {authGuard} from './services/guard/auth-guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login').then(m => m.Login)
  },
  {
    path: 'register',
    loadComponent: () => import('./pages/register/register').then(m => m.Register)
  },
  {
    path: 'activate-account',
    loadComponent: () => import('./pages/activate-account/activate-account').then(m => m.ActivateAccount)
  },
  {
    path: 'books',
    loadChildren: () => import("./features/book/book.routes").then(r => r.bookRoutes),
    canActivate:[authGuard]
  }

];
