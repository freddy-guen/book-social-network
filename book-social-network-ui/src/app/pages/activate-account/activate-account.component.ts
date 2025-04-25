import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services/authentication.service';
import {NgIf} from '@angular/common';
import {CodeInputModule} from 'angular-code-input';

@Component({
  selector: 'app-activate-account',
  imports: [
    NgIf,
    CodeInputModule
  ],
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {

  message : string = '';
  isOk : boolean = true;
  submitted : boolean = false;

  constructor(
    private router : Router,
    private authService : AuthenticationService
  ) {
  }

  onCodeCompleted(code: string)
  {
    this.confirmAccount(code);
  }

  redirectToLogin()
  {
    this.router.navigate(['login']);
  }

  private confirmAccount(code: string)
  {
    this.authService.confirm({
      token : code
    }).subscribe({
      next : () => {
        this.message = 'Votre compte a été activé avec succès.';
        this.submitted = true;
        this.isOk = true;
      },
      error : () => {
        this.message = 'Le code utilisé est expiré ou invalide.';
        this.submitted = true;
        this.isOk = false;
      }
    })
  }
}
