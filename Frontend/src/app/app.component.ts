import { Component } from '@angular/core';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Frontend';
  constructor(private userService: UserService) {
  }
  setCurrentUser() {
    const userSring = localStorage.getItem('user');
    if (!userSring) return;

    const user = JSON.parse(userSring);
    this.userService.setCurrentUser(user);
  }
}
