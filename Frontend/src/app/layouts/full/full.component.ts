import { MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnDestroy, AfterViewInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';


/** @title Responsive sidenav */
@Component({
  selector: 'app-full-layout',
  templateUrl: 'full.component.html',
  styleUrls: []
})
export class FullComponent implements OnDestroy, AfterViewInit {
  mobileQuery: MediaQueryList;

  private _mobileQueryListener: () => void;

  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private userService: UserService
  ) {
    this.mobileQuery = media.matchMedia('(min-width: 768px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
    this.setCurrentUser();
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
  ngAfterViewInit() { }

  setCurrentUser() {
    const userSring = localStorage.getItem('user');
    if (!userSring) return;
    const user = JSON.parse(userSring);
    this.userService.setCurrentUser(user);
  }
}
