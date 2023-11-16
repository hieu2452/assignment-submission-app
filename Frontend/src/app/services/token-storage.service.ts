import { Injectable } from "@angular/core";
import { UserService } from "./user.service";
import { take } from "rxjs/operators";
import { Router } from "@angular/router";

const ACCESS_TOKEN = 'accessToken';
const REFRESH_TOKEN = 'refreshToken';
const USER = 'user';

@Injectable({
    providedIn: 'root'
})
export class TokenStorageService {

    constructor(private userService: UserService, private router: Router) { }

    signOut(): void {
        localStorage.clear();
        this.router.navigate(['/']);
    }

    public saveToken(token: string): void {
        localStorage.removeItem(ACCESS_TOKEN);
        localStorage.setItem(ACCESS_TOKEN, token);

        const user = this.getUser();
        console.log(user)
        if (user) {
            this.saveUser({ ...user, accessToken: token });
        }
    }

    public getToken(): string | null {
        return localStorage.getItem(ACCESS_TOKEN);
    }

    public saveRefreshToken(token: string): void {
        localStorage.removeItem(REFRESH_TOKEN);
        localStorage.setItem(REFRESH_TOKEN, token);
    }

    public getRefreshToken(): string | null {
        return localStorage.getItem(REFRESH_TOKEN);
    }

    public saveUser(user: any): void {
        localStorage.removeItem(USER);
        localStorage.setItem(USER, JSON.stringify(user));
    }

    public getUser(): any {
        const user = localStorage.getItem(USER);
        if (user) {
            return JSON.parse(user);
        }

        return {};

    }
}