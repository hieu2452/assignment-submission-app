import { Routes } from '@angular/router';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ManageProductComponent } from './manage-product/manage-product.component';
import { RouteGuardService } from '../services/route-guard.service';
import { ManageCategoryComponent } from './manage-category/manage-category.component';


export const MaterialRoutes: Routes = [
    {
        path:'product',
        component:ManageProductComponent,
        data:{
            expectRole:['']
        }
    },
    {
        path:'category',
        component:ManageCategoryComponent,
        data:{
            expectRole:['']
        }
    }
];