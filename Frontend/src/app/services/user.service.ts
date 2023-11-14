import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  url = environment.apiUrl;
  private currentUserSource = new BehaviorSubject<any | null>(null);
  currentUser$ = this.currentUserSource.asObservable();
  tokenPayload: any;
  constructor(private httpClient: HttpClient) { }

  signup(data: any) {
    return this.httpClient.post(this.url + "/auth/register", data, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    })
  }

  login(data: any) {
    return this.httpClient.post(this.url + "/auth/login", data, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    }).pipe(
      map(response => {
        const user = response;
        if (user) {
          this.setCurrentUser(user);
        }
        return user
      })
    )
  }

  setCurrentUser(user: any) {
    this.tokenPayload = jwtDecode(user.accessToken);
    user.roles = this.tokenPayload?.role;
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSource.next(user);
  }

  getDecodedToken(token: string) {
    return JSON.parse(atob(token.split('.')[1]));
  }

  checkToken() {
    return this.httpClient.get(this.url + "/user/checkToken");
  }

  refreshToken(token: string) {
    console.log(token)
    return this.httpClient.post('http://localhost:8080/auth/refreshtoken', {
      refreshToken: token
    }, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }
}
