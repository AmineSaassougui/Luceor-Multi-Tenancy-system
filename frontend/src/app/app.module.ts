import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared.module';
import { NgxSkeletonLoaderModule } from 'ngx-skeleton-loader';
import {RouterModule} from '@angular/router';
import {AuthModule} from './auth/auth.module';
import { NotificationComponent } from './notification/notification.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSnackBarModule} from '@angular/material/snack-bar';

@NgModule({
  declarations: [AppComponent, NotificationComponent],
  imports: [BrowserModule, AppRoutingModule, BrowserAnimationsModule, SharedModule,
    NgxSkeletonLoaderModule, RouterModule, AuthModule, MatDialogModule, MatSnackBarModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
