import {Component, inject} from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {KeycloakService} from '../../../../services/keycloak/keycloak';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-menu',
  imports: [
    RouterLink,
    RouterLinkActive,
    FormsModule
  ],
  templateUrl: './menu.html',
  styleUrl: './menu.scss'
})
export class Menu {

  searchTerm: string = '';

  keycloakService = inject(KeycloakService);
  router = inject(Router)

  get userName() {
    return this.keycloakService.profile?.firstName;
  }

  manageAccount() {
    this.keycloakService.accountManagement();
  }

  async logout() {
    this.keycloakService.logout();
  }

  searchBooks() {
    if ( this.searchTerm ) {
      this.router.navigate(['books'], {
        queryParams: {
          name: this.searchTerm
        }
      });
      this.searchTerm = '';
    } else {
      this.router.navigate(['books']);
    }
  }

}
