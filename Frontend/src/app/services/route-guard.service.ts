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

    if (!tokenPayload.role.includes('admin') && !tokenPayload.role.includes('user')) {
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

    return isAuthorized;
  }
}
