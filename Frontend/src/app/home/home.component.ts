import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { SignupComponent } from '../signup/signup.component';
import { LoginComponent } from '../login/login.component';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { take } from 'rxjs/operators';
import { log } from 'console';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  user = {};

  constructor(private dialog: MatDialog, private router: Router, private userSerivce: UserService) {
    this.userSerivce.currentUser$.pipe(take(1)).subscribe({
      next: currentUser => {
        if (currentUser) {
          this.user = currentUser
          console.log(currentUser)
        }
      }
    })

  }

  ngOnInit(): void {
    this.checkLogin();
  }

  handleSignupAction() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = "550px";
    this.dialog.open(SignupComponent, dialogConfig);
  }

  handleLoginAction() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = "550px";
    this.dialog.open(LoginComponent, dialogConfig)
  }

  // checkLogin() {
  //   if (!this.user) return;

  //   this.router.navigate(['/cafe/dashboard']);
  // }

  checkLogin() {
    const userSring = localStorage.getItem('user');
    if (!userSring) return;
    this.router.navigate(['/cafe/dashboard']);
    const user = JSON.parse(userSring);
    console.log(user)
    this.userSerivce.setCurrentUser(user);
    
  }

}
