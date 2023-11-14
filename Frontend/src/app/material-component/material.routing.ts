import { Routes } from '@angular/router';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ManageProductComponent } from './manage-product/manage-product.component';
import { RouteGuardService } from '../services/route-guard.service';
import { ManageCategoryComponent } from './manage-category/manage-category.component';
import { ViewBillProductsComponent } from './dialog/view-bill-products/view-bill-products.component';
import { ViewBillComponent } from './view-bill/view-bill.component';
import { ManageUserComponent } from './manage-user/manage-user.component';


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
        },
    },
    {
        path:'bill',
        component:ViewBillComponent,
        data:{
            expectedRole: ['']
        }
    },
    {
        path:'user',
        component:ManageUserComponent,
        data:{
            expectedRole: ['']
        }
    },
];