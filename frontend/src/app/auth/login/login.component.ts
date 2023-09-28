import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Component({
  selector: 'll-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  login() {
    if (this.loginForm.valid) {
      const userData = this.loginForm.value;
      this.authService.login(userData).subscribe(
        (response) => {
          console.log('Login successful:', response);

          // Create a configuration object for the success snackbar
          const snackBarConfig: MatSnackBarConfig = {
            duration: 5000, // Duration in milliseconds
            panelClass: ['centered-snackbar', 'success-snackbar'],
            verticalPosition: 'top', // Set to 'top' for top-center position
            horizontalPosition: 'center', // Set to 'center' for horizontal center position
          };

          // Display a success message in a snackbar
          this.snackBar.open('Login successful', 'Close', snackBarConfig);

          // Save the token to local storage
          this.authService.saveToken(response.token);
          // Update the authentication status to true
          this.authService.updateAuthenticationStatus(true);
          this.router.navigate(['']);
        },
        (error) => {
          console.error('Error:', error);

          // Extract the error message from the HTTP response
          const errorMessage = error.error?.message || 'An error occurred';

          // Create a configuration object for the error snackbar
          const snackBarConfig: MatSnackBarConfig = {
            duration: 5000, // Duration in milliseconds
            panelClass: ['centered-snackbar', 'error-snackbar'],
            verticalPosition: 'top', // Set to 'top' for top-center position
            horizontalPosition: 'center', // Set to 'center' for horizontal center position
          };

          // Display the error message in a snackbar
          this.snackBar.open(errorMessage, 'Close', snackBarConfig);

          // Log the specific error message to the console
          console.error(`error: ${errorMessage}`);
        }
      );
    }
  }

  ngOnInit(): void {}
}
