import { Injectable } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Router } from '@angular/router';
import { SnackbarService } from '../services/snackbar.service';
import { AuthService } from './auth.service';
import { jwtDecode } from "jwt-decode"
import { GlobalConstants } from '../shared/global-constants';
import { log } from 'console';
@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {
  constructor(public auth: AuthService,
    public router: Router,
    private snackbarService: SnackbarService) { }

  canActivate(router: ActivatedRouteSnapshot): boolean {
    
    let expectRoleArray = router.data;
    expectRoleArray = expectRoleArray.expectedRole;
    let isAuthorized = false;

    const token: any = localStorage.getItem('accessToken');

    var tokenPayload: any;

    try {
      tokenPayload = jwtDecode(token);

    } catch (err) {
      localStorage.clear();
      this.router.navigate(['/']);
    }

    if (!tokenPayload.role.includes('manager') && !tokenPayload.role.includes('employee')) {
      this.router.navigate(['/']);
      localStorage.clear();
      return false;
    }


    tokenPayload.role.some((r: any) => {
      if (expectRoleArray.includes(r)) {
        isAuthorized = true;
      }
    })

    if (!isAuthorized) {
      this.snackbarService.openSnackBar(GlobalConstants.unauthroized, GlobalConstants.error);
      this.router.navigate(['/cafe/dashboard']);
      return false;
    }


    // console.log(tokenPayload)
    // console.log(expectedRole)

    // for (let token of tokenPayload.role) {
    //   if (token == 'manager' || token == 'employee') {
    //     if (this.auth.isAuthenticated() && expectedRole.includes(token)) {
    //       isAuthorized = true;
    //       console.log(isAuthorized)
    //       // return true;
    //     }
    //     // this.snackbarService.openSnackBar(GlobalConstants.unauthroized, GlobalConstants.error);
    //     // this.router.navigate(['/cafe/dashboard']);
    //     // return false;
    //   }
    //   else {
    //     this.router.navigate(['/']);
    //     localStorage.clear();
    //     return false;
    //   }
    // }

    return isAuthorized;
  }
}
