import {Component, inject} from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {KeycloakService} from '../../../../services/keycloak/keycloak';

@Component({
  selector: 'app-menu',
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './menu.html',
  styleUrl: './menu.scss'
})
export class Menu {

  keycloakService = inject(KeycloakService);

  get userName() {
    return this.keycloakService.profile?.firstName;
  }

  manageAccount() {
    this.keycloakService.accountManagement();
  }

  async logout() {
    this.keycloakService.logout();
  }
}
