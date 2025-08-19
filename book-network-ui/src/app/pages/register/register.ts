import {Component, inject, signal} from '@angular/core';
import {RegistrationRequest} from '../../services/models/registration-request';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services/authentication.service';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule
  ],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {

  registerRequest: RegistrationRequest = {email: '', firstName: '', lastName: '', password: ''};
  errorMsg = signal<Array<string>>([]);
  router = inject(Router);
  authenticationService = inject(AuthenticationService);


  register() {
    this.errorMsg.set([]);
    this.authenticationService.register({
      body: this.registerRequest
    }).subscribe({
      next: () => {
        this.router.navigate(['activate-account']);
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.errorMsg.set(err.error.validationErrors);
        } else {
          this.errorMsg.update(errors => [...errors, err.error.error]);
        }
      }
    })
  }

  login() {
    this.router.navigate(['login']);
  }

}
