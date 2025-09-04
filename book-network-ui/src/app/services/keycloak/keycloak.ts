import {inject, Injectable} from '@angular/core';
import Keycloak from 'keycloak-js';
import {Environment} from '../environment/environment';
import {UserProfile} from '../models/user-profile';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;
  private _profile: UserProfile | undefined;

  environmentService = inject(Environment);

  get keycloak() {
    if (!this._keycloak) {
      this._keycloak = new Keycloak({
        url: this.environmentService.get('application.keycloak.url'),
        realm: this.environmentService.get('application.keycloak.realm'),
        clientId: this.environmentService.get('application.keycloak.clientId')
      });
    }
    return this._keycloak;
  }

  get profile() {
    return this._profile;
  }

  async init() {
    console.log('Initializing keycloak');
    const authenticated = await this.keycloak.init({
      onLoad: 'login-required'
    });

    if (authenticated) {
      this._profile = await this.keycloak.loadUserProfile();
      this._profile.token = this.keycloak?.token;
    }
  }

  login() {
    return this.keycloak.login();
  }

  logout() {
    return this.keycloak.logout({
      redirectUri: this.environmentService.get('logout-redirectUri')
    });
  }

  accountManagement() {
    return this.keycloak.accountManagement();
  }

}
