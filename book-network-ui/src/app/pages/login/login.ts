import {Component, inject, signal} from '@angular/core';
import {AuthenticationRequest, AuthenticationResponse} from '../../services/models';
import { FormsModule } from '@angular/forms';
import {AuthenticationService} from '../../services/services/authentication.service';
import {Router} from '@angular/router';

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

  authService = inject(AuthenticationService);
  router = inject(Router);

  login() {
    this.errorMsg.set([]);
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (res : AuthenticationResponse) => {
        // todo save the token
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
