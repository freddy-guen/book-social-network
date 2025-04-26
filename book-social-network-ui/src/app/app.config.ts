import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {httpTokenInterceptor} from './services/interceptor/http-token.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    //injection globale de HttpClient pour les appels Http
    provideHttpClient(
      // On donne la liste des intercepteurs fonctionnels qu'on veut appliquer
      withInterceptors([
        httpTokenInterceptor
      ])
    )
  ]
};
