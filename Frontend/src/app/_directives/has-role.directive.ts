import { Directive, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { UserService } from '../services/user.service';
import { take } from 'rxjs/operators';

@Directive({
  selector: '[appHasRole]'
})
export class HasRoleDirective {

  @Input() set appHasRole(roles: string) {
    if (this.user.roles.some((r: string) => r == roles)) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }

  user: any;

  constructor(private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef, private userService: UserService) {

    this.userService.currentUser$.pipe(take(1)).subscribe({
      next: user => {
        if (user) {
          this.user = user;
        }
      }
    })

  }

}