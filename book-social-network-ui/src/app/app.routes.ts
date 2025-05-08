import { Routes } from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {ActivateAccountComponent} from './pages/activate-account/activate-account.component';
import {authGuard} from './services/guard/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'activate-account',
    component: ActivateAccountComponent
  },
  {
    path: 'books',
    loadChildren: () => import('./modules/book/book.module')
      .then(m => m.BookModule),
    canActivate : [authGuard] // on a besoin du guard que pour les fonctionnalités sur les livres. Par contre pour se logguer 'login', inscription 'register', ou activation de compte on a pas besoin d'un token donc pas de notre guard
  }
];
