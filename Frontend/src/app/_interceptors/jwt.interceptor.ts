import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable} from 'rxjs';
import { UserService } from '../services/user.service';
import { take } from 'rxjs/operators';


@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private userService: UserService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.userService.currentUser$.pipe(take(1)).subscribe({
      next: (user : any) => {
        if (user) {
          request = request.clone({
            setHeaders: { 
              Authorization: `Bearer ${user.accessToken}`
            }
          })
        }
      }
    })
    return next.handle(request);
  }
}