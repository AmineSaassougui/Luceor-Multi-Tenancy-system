import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatMenuModule } from '@angular/material/menu';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardLayoutComponent } from './dashboard-layout/dashboard-layout.component';
import { DashboardIndexComponent } from './dashboard-index/dashboard-index.component';
import { SharedModule } from '../shared/shared.module';
import { DashboardSavedItemComponent } from './dashboard-saved-item/dashboard-saved-item.component';
import { DashboardProfileComponent } from './dashboard-profile/dashboard-profile.component';
import { DashboardOrderComponent } from './dashboard-order/dashboard-order.component';
import { DashboardAddComponent } from './dashboard-add/dashboard-add.component';
import {FormsModule} from '@angular/forms';
import { DashboardUsersComponent } from './dashboard-users/dashboard-users.component';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatFormFieldModule} from '@angular/material/form-field';
import { DashboardChangepasswordComponent } from './dashboard-changepassword/dashboard-changepassword.component';
import { ChangePasswordDialogComponent } from './dashboard-profile/change-password-dialog/change-password-dialog.component';
import { DashboardRentalComponent } from './dashboard-rental/dashboard-rental.component';
import { AdminRentalSummariesComponent } from './admin-rental-summaries/admin-rental-summaries.component';
import { AdminRentalSummariesforhimComponent } from './admin-rental-summariesforhim/admin-rental-summariesforhim.component';

@NgModule({
  declarations: [
    DashboardLayoutComponent,
    DashboardIndexComponent,
    DashboardSavedItemComponent,
    DashboardProfileComponent,
    DashboardOrderComponent,
    DashboardAddComponent,
    DashboardUsersComponent,
    DashboardChangepasswordComponent,
    ChangePasswordDialogComponent,
    DashboardRentalComponent,
    AdminRentalSummariesComponent,
    AdminRentalSummariesforhimComponent
  ],
    imports: [CommonModule, DashboardRoutingModule, SharedModule, MatMenuModule, FormsModule, MatTooltipModule, MatFormFieldModule]
})
export class DashboardModule {}
