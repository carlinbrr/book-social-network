import {
  ApplicationConfig, inject, provideAppInitializer,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {httpTokenInterceptor} from './services/interceptor/http-token-interceptor';
import {Environment} from './services/environment/environment';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient( withInterceptors([httpTokenInterceptor])),
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideAppInitializer(() => {
      const environmentService = inject(Environment);
      environmentService.loadConfig();
    }),
  ]
};
