import { HttpInterceptorFn } from '@angular/common/http';
import {inject} from '@angular/core';
import {KeycloakService} from '../keycloak/keycloak';

export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(KeycloakService).keycloak.token;

  if ( req.url.includes('authenticate') || req.url.includes('register') ) {
    return next(req);
  }

  if (token) {
    const authRequest = req.clone({
      headers: req.headers.append('Authorization', `Bearer ${token}`)
    });
    return next(authRequest);
  }

  return next(req);
};
