import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
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
  constructor(private httpClient:HttpClient) { }

  signup(data:any) {
    return this.httpClient.post(this.url+"/auth/register", data,{
      headers:new HttpHeaders().set('Content-Type', 'application/json')
    })
  }

  login(data:any) {
    return this.httpClient.post(this.url+"/auth/login", data,{
      headers:new HttpHeaders().set('Content-Type', 'application/json')
    }).pipe(
      map(response => {
        const user = response;
        if(user) {
          this.setCurrentUser(user);
        }
        return user
      })
    )
  }

  setCurrentUser(user : any) {
    // const roles = this.getDecodedToken(user.accessToken).role;
    // user.roles = roles;
    localStorage.setItem('user',JSON.stringify(user));
    this.currentUserSource.next(user);
  }

  getDecodedToken(token: string) {
    return JSON.parse(atob(token.split('.')[1]));
  }

  checkToken(){
    return this.httpClient.get(this.url+"/user/checkToken");
  }
}
