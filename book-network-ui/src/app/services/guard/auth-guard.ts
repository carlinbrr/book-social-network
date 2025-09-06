import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {KeycloakService} from '../keycloak/keycloak';

export const authGuard: CanActivateFn = async (route, state) => {
  const keycloakService = inject(KeycloakService);
  const router = inject(Router);

  if (keycloakService.keycloak.isTokenExpired()) {
    console.log("Not authenticated, redirecting to login page...");
    await keycloakService.login();
    return false;
  }
  return true;
};
