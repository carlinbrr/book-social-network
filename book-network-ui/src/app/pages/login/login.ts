import {Component, inject, OnInit, signal} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {Router} from '@angular/router';
import {KeycloakService} from '../../services/keycloak/keycloak';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login implements OnInit {

  router = inject(Router);
  keycloakService = inject(KeycloakService);


  async ngOnInit() {
    await this.keycloakService.init();
    await this.keycloakService.login();
  }

}
