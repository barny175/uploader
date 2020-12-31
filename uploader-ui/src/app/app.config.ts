import { NgModule, InjectionToken } from '@angular/core';

export const APP_CONFIG = new InjectionToken<AppConfig>('app.config');

export class AppConfig {
  baseUrl: string;
}

export const CONFIG: AppConfig = {
    baseUrl: 'http://localhost:18080',
};

@NgModule({
  providers: [{ provide: APP_CONFIG, useValue: CONFIG }],
})
export class AppConfigModule {

}
