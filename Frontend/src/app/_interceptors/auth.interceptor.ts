import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, throwError } from "rxjs";
import { UserService } from "../services/user.service";
import { TokenStorageService } from "../services/token-storage.service";
import { catchError, filter, switchMap, take, tap } from "rxjs/operators";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    constructor(private tokenService: TokenStorageService, private authService: UserService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<Object>> {
        let authReq = req;
        const token = this.tokenService.getToken();
        if (token != null) {
            authReq = this.addTokenHeader(req, token);
        }

        return next.handle(authReq).pipe(catchError(error => {
            if (error instanceof HttpErrorResponse && !authReq.url.includes('auth/login') && error.status === 403) {

                return this.handle401Error(authReq, next);
            }
            return throwError(error);
        }));
    }

    private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
        if (!this.isRefreshing) {
            this.isRefreshing = true;
            this.refreshTokenSubject.next(null);
            
            const token = this.tokenService.getRefreshToken();
            
            if (token) {
                return this.authService.refreshToken(token).pipe(
                    tap((token: any) => {
                        console.log(token)
                        this.isRefreshing = false;
                        this.tokenService.saveToken(token.accessToken);
                        console.log(token.accessToken)
                        this.refreshTokenSubject.next(token.accessToken);

                        return next.handle(this.addTokenHeader(request, token.accessToken));
                    }),
                    catchError((err) => {
                        this.isRefreshing = false;
                        this.tokenService.signOut();
                        return throwError(err);
                    })
                );
            }

        }

        return this.refreshTokenSubject.pipe(
            filter(token => token !== null),
            take(1),
            switchMap((token) => next.handle(this.addTokenHeader(request, token)))
        );
    }

    private addTokenHeader(request: HttpRequest<any>, token: string) {
        /* for Spring Boot back-end */
        return request.clone({ headers: request.headers.set('Authorization', 'Bearer ' + token) });
    }
}
