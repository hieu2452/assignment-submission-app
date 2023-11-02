import { ChangeDetectorRef, Component, OnDestroy } from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import { jwtDecode } from "jwt-decode"
<<<<<<< HEAD
 import { MenuItems } from 'src/app/shared/menu-items';
=======
import { HttpClient } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';
>>>>>>> 2d3e0eb8874e8626ce87d96fd3e33c6862968682
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: []
})
export class AppSidebarComponent implements OnDestroy {
  mobileQuery: MediaQueryList;
  userRole:any;
  token:any = localStorage.getItem('token');
  tokenPayload:any;

  private _mobileQueryListener: () => void;

  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
<<<<<<< HEAD
    public menuItems:MenuItems
=======
    private userService:UserService
>>>>>>> 2d3e0eb8874e8626ce87d96fd3e33c6862968682
  ) {
    this.tokenPayload = jwtDecode(this.token);
    this.userRole = this.tokenPayload?.role;
    this.mobileQuery = media.matchMedia('(min-width: 768px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  getProduct() {
    this.userService.getProduct().subscribe({
      next: response =>{
        console.log(response);
      },
      error: err => console.log(err)
    })
  }  
}
