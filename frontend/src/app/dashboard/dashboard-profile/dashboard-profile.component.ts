import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {ActivatedRoute} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ChangePasswordDialogComponent} from './change-password-dialog/change-password-dialog.component';


@Component({
  selector: 'll-dashboard-profile',
  templateUrl: './dashboard-profile.component.html',
  styleUrls: ['./dashboard-profile.component.scss']
})
export class DashboardProfileComponent implements OnInit {
  userInfo: any; // Define a property to store the user information
  oldPassword = '';
  newPassword = '';
  constructor(private authService: AuthService,
              private route: ActivatedRoute,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    // Call the getUserInfo method to fetch user information
    this.authService.getUserInfo().subscribe(
      (response: any) => {
        this.userInfo = response;
      },
      (error: any) => {
        // Handle error (e.g., redirect to  if authentication fails)
      }
    );
  }
  // Function to open the change password dialog
  openPasswordChangeDialog(): void {
    const dialogRef = this.dialog.open(ChangePasswordDialogComponent, {
      width: '400px', // Adjust the width as needed
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        const requestPayload = {
          oldPassword: result.oldPassword,
          newPassword: result.newPassword,
        };

        // Call the changePassword method from AuthService
        this.authService.changePassword(requestPayload).subscribe(
          (response) => {
            // Handle the successful response (e.g., show a success message)
            this.snackBar.open('Password changed successfully', 'Close', {
              duration: 3000,
            });
          },
          (error) => {
            // Handle errors (e.g., show an error message)
            this.snackBar.open('Error changing password', 'Close', {
              duration: 3000,
            });
          }
        );
      }
    });
  }


}
