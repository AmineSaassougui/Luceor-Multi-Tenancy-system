import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardIndexComponent } from './dashboard-index/dashboard-index.component';
import { DashboardLayoutComponent } from './dashboard-layout/dashboard-layout.component';
import { DashboardOrderComponent } from './dashboard-order/dashboard-order.component';
import { DashboardProfileComponent } from './dashboard-profile/dashboard-profile.component';
import { DashboardSavedItemComponent } from './dashboard-saved-item/dashboard-saved-item.component';
import {DashboardAddComponent} from './dashboard-add/dashboard-add.component';
import {DashboardUsersComponent} from './dashboard-users/dashboard-users.component';
import {DashboardChangepasswordComponent} from './dashboard-changepassword/dashboard-changepassword.component';
import {DashboardRentalComponent} from './dashboard-rental/dashboard-rental.component';
import {AdminRentalSummariesComponent} from './admin-rental-summaries/admin-rental-summaries.component';
import {
  AdminRentalSummariesforhimComponent
} from './admin-rental-summariesforhim/admin-rental-summariesforhim.component';

const DashboardChildrenRoute: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: DashboardIndexComponent
  },
  {
    path: 'saved-items',
    component: DashboardSavedItemComponent
  },
  {
    path: 'profile',
    component: DashboardProfileComponent
  },
  {
    path: 'orders',
    component: DashboardOrderComponent
  },
  {
    path: 'add',
    component: DashboardAddComponent
  },
  {
    path: 'users',
    component: DashboardUsersComponent
  },
  {
    path: 'password',
    component: DashboardChangepasswordComponent
  },
  {
    path: 'rentals',
    component: DashboardRentalComponent
  },
  {
    path: 'admin_rentals',
    component: AdminRentalSummariesComponent
  },
  {
    path: 'admin_rentals_for_him',
    component: AdminRentalSummariesforhimComponent
  }
];

const routes: Routes = [
  {
    path: '',
    component: DashboardLayoutComponent,
    children: DashboardChildrenRoute
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {}
