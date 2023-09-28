import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-change-password-dialog',
  templateUrl: './change-password-dialog.component.html',
  styleUrls: ['./change-password-dialog.component.scss']
})
export class ChangePasswordDialogComponent implements OnInit {
  oldPassword = '';
  newPassword = '';

  constructor(public dialogRef: MatDialogRef<ChangePasswordDialogComponent>,
              private dialog: MatDialog) {}

  ngOnInit(): void {}

  // Function to handle the password change
  changePassword(): void {
    // Check if passwords are valid (you can add validation logic here)
    if (this.oldPassword && this.newPassword) {
      // Emit the entered passwords to the parent component
      this.dialogRef.close({ oldPassword: this.oldPassword, newPassword: this.newPassword });
    }
  }

  cancelChange(): void {
    // Close the dialog
    this.dialog.closeAll();
  }

}
