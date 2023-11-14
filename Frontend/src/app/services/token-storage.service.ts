import { Injectable } from "@angular/core";
import { UserService } from "./user.service";
import { take } from "rxjs/operators";

const ACCESS_TOKEN = 'accessToken';
const REFRESH_TOKEN = 'refreshToken';
const USER = 'user';

@Injectable({
    providedIn: 'root'
})
export class TokenStorageService {

    constructor(private userService: UserService) { }

    signOut(): void {
        localStorage.clear(); 
    }

    public saveToken(token: string): void {
        localStorage.removeItem(ACCESS_TOKEN);
        localStorage.setItem(ACCESS_TOKEN, token);

        // this.userService.currentUser$.pipe(take(1)).subscribe({
        //     next: response => {
        //         const user = response;
        //         if (user) {
        //             if (user.username) {
        //                 this.saveUser({ ...user, accessToken: token });
        //             }
        //         }
        //     }
        // })
        
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
        
        this.userService.currentUser$.pipe(take(1)).subscribe({
            next: response => {
                const user = response;
                if (user) {
                    // console.log(user)
                    return user
                }
                return {};
            }
        })
       
    }
}