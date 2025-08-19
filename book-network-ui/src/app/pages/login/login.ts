import {Component, inject, signal} from '@angular/core';
import {AuthenticationRequest, AuthenticationResponse} from '../../services/models';
import { FormsModule } from '@angular/forms';
import {AuthenticationService} from '../../services/services/authentication.service';
import {Router} from '@angular/router';
import {TokenService} from '../../services/token/token.service';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  authRequest: AuthenticationRequest = {email: '', password: ''};

  errorMsg = signal<Array<string>>([]);

  authenticationService = inject(AuthenticationService);
  router = inject(Router);
  tokenService = inject(TokenService);

  login() {
    this.errorMsg.set([]);
    this.authenticationService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (res : AuthenticationResponse) => {
        this.tokenService.token = res.token as string;
        this.router.navigate(['books']);
      },
      error: err => {
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMsg.set(err.error.validationErrors);
        } else {
          this.errorMsg.update(errors => [...errors, err.error.error]);
        }
      }
    })

  }

  register() {
    this.router.navigate(['register']);
  }
}
