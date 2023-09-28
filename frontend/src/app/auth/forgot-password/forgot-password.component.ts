import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar'; // Import MatSnackBar

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {
  forgotPasswordForm: FormGroup;
  resetMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar // Inject MatSnackBar
  ) {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  submitForgotPasswordForm() {
    if (this.forgotPasswordForm.valid) {
      const email = this.forgotPasswordForm.value.email;
      this.authService.forgotPassword(email).subscribe(
        (response) => {
          console.log('Response from backend:', response); // Log the response
          this.resetMessage = 'Password reset email sent successfully.';

          // Display a success message in a snackbar
          this.snackBar.open('Password reset email sent successfully.', 'Close', {
            duration: 5000, // Duration in milliseconds
            panelClass: ['centered-snackbar', 'success-snackbar'], // Add a CSS class for styling
            verticalPosition: 'top', // Set to 'top' for top-center position
            horizontalPosition: 'center', // Set to 'center' for horizontal center alignment
          });
        },
        (error) => {
          console.error('Error from backend:', error); // Log the error
          this.resetMessage = 'Password reset failed. Please check your email and try again.';

          // Display the error message in a snackbar
          this.snackBar.open('Password reset failed. Please check your email and try again.', 'Close', {
            duration: 5000, // Duration in milliseconds
            panelClass: ['centered-snackbar', 'error-snackbar'], // Add a CSS class for styling
            verticalPosition: 'top', // Set to 'top' for top-center position
            horizontalPosition: 'center', // Set to 'center' for horizontal center alignment
          });
        }
      );
    }
  }

  ngOnInit(): void {}
}
