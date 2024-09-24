import { ApplicationConfig } from '@angular/core';
import { provideRouter, withEnabledBlockingInitialNavigation, withHashLocation, withInMemoryScrolling, withRouterConfig, withViewTransitions } from '@angular/router';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes, 
	withRouterConfig({
		onSameUrlNavigation: 'reload'
	}),
	withInMemoryScrolling({
		scrollPositionRestoration: 'top',
		anchorScrolling: 'enabled'
	}),
	withEnabledBlockingInitialNavigation(),
	withViewTransitions(),
	withHashLocation()
)]
};
