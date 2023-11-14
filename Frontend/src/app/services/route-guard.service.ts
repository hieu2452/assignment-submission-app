import { Injectable } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Router } from '@angular/router';
import { SnackbarService } from '../services/snackbar.service';
import { AuthService } from './auth.service';
import { jwtDecode } from "jwt-decode"
import { GlobalConstants } from '../shared/global-constants';
@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {
  constructor(public auth: AuthService,
    public router: Router,
    private snackbarService: SnackbarService) { }

  canActivate(router: ActivatedRouteSnapshot): boolean {
    let expectRoleArray = router.data;
    expectRoleArray = expectRoleArray.expectRole;
    const token: any = localStorage.getItem('accessToken');

    var tokenPayload: any;

    try {
      tokenPayload = jwtDecode(token);
    } catch (err) {
      localStorage.clear();
      this.router.navigate(['/']);
    }

    let expectedRole = '';

    for (let i = 0; i < expectRoleArray.length; i++) {
      if (tokenPayload.role.some((r: any) => r == expectRoleArray[i])) {
        expectedRole = tokenPayload.role;
      }
    }


    if (tokenPayload.role == 'manager' || tokenPayload.role == 'employee') {
      if (this.auth.isAuthenticated() && tokenPayload.role == expectedRole) {
        return true;
      }

      this.snackbarService.openSnackBar(GlobalConstants.unauthroized, GlobalConstants.error);
      this.router.navigate(['/cafe/dashboard']);
      return false;
    }
    else {
      this.router.navigate(['/']);
      localStorage.clear();
      return false;
    }
  }
}
