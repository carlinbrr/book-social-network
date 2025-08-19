import {Component, inject, signal} from '@angular/core';
import {RouterLink} from '@angular/router';
import {AuthenticationService} from '../../services/services/authentication.service';
import {CodeInputModule} from 'angular-code-input';

@Component({
  selector: 'app-activate-account',
  imports: [
    CodeInputModule,
    RouterLink
  ],
  templateUrl: './activate-account.html',
  styleUrl: './activate-account.scss'
})
export class ActivateAccount {

  message = signal<string>('');
  isOkay : boolean = true;
  submitted: boolean = false;

  authenticationService = inject(AuthenticationService);


  onCodeCompleted(token: string) {
    this.confirmAccount(token);
  }

  private confirmAccount(token: string) {
    this.authenticationService.confirm({
      token: token
    }).subscribe({
      next: () => {
        this.message.set('Your account has been activated\n Now you can proceed to login');
        this.submitted = true;
        this.isOkay = true;
      },
      error: () => {
        this.message.set('Token is invalid or has been expired');
        this.submitted = true;
        this.isOkay = false;
      }

    })
  }

}
