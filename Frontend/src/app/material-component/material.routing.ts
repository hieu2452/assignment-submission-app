import { Routes } from '@angular/router';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ManageProductComponent } from './manage-product/manage-product.component';
import { RouteGuardService } from '../services/route-guard.service';


export const MaterialRoutes: Routes = [
    {
        path:'product',
        component:ManageProductComponent,
        canActivate:[RouteGuardService],
        data:{
            expectRole:['employee']
        }
    },
];
