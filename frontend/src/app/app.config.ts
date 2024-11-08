import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
import {routes} from './app.routes';
import {provideHttpClient, withInterceptors, HttpRequest, HttpHandlerFn} from "@angular/common/http";
import {httpErrorInterceptor} from "./shared/services/error-interceptor";

function intercept(request: HttpRequest<unknown>, next: HttpHandlerFn) {
    console.log("Interception has taken place!")
    
    const token = localStorage.getItem('authToken');
    if (token) {
        request = request.clone({
            headers: request.headers.set('Authorization', 'Bearer ' + token)
        });
    }
    return next(request);
}

export const appConfig: ApplicationConfig = {
    providers: [
        provideZoneChangeDetection({eventCoalescing: true}),
        provideRouter(routes),
        provideHttpClient(withInterceptors([intercept, httpErrorInterceptor])),
    ]
};
