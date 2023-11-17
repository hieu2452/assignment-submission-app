import { Routes } from '@angular/router';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ManageProductComponent } from './manage-product/manage-product.component';
import { RouteGuardService } from '../services/route-guard.service';
import { ManageCategoryComponent } from './manage-category/manage-category.component';
import { ViewBillProductsComponent } from './dialog/view-bill-products/view-bill-products.component';
import { ViewBillComponent } from './view-bill/view-bill.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { ManageOrderComponent } from './manage-order/manage-order.component';


export const MaterialRoutes: Routes = [
    {
        path:'product',
        component:ManageProductComponent,
        canActivate: [RouteGuardService],
        data:{
            expectedRole:['admin']
        }
    },
    {
        path:'category',
        component:ManageCategoryComponent,
        canActivate: [RouteGuardService],
        data:{
            expectedRole:['admin']
        },
    },
    {
        path:'bill',
        component:ViewBillComponent,
        canActivate: [RouteGuardService],
        data:{
            expectedRole: ['admin','user']
        }
    },
    {
        path:'user',
        component:ManageUserComponent,
        canActivate: [RouteGuardService],
        data:{
            expectedRole: ['admin']
        }
    },
    {
        path:'order',
        component:ManageOrderComponent,
        data:{
            expectedRole: ['admin','user']
        }
    },
];