import {Component, inject} from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {TokenService} from '../../../../services/token/token.service';

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

  jwtService = inject(TokenService);

  logout() {
    this.jwtService.removeToken();
    window.location.reload();
  }
}
