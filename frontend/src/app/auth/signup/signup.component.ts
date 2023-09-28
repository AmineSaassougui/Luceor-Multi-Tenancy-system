import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';


@Component({
  selector: 'll-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  signupForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService, private snackBar: MatSnackBar,
    private router: Router,


  ) {
    this.signupForm = this.fb.group({
      name: ['', Validators.required],
      contactNumber: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      address: [''],
    });
  }

  signup() {
    if (this.signupForm.valid) {
      const userData = this.signupForm.value;
      this.authService.signup(userData).subscribe(
        (response) => {
          console.log('Signup successful:', response);
          if (response && response.message) {
            // Display the success message in a snackbar
            this.snackBar.open(response.message, 'Close', {
              duration: 5000, // Duration in milliseconds
              panelClass: ['centered-snackbar']
            });
          } else {
            // Fallback message if the response does not have a specific message
            this.snackBar.open('User registered successfully.', 'Close', {
              duration: 5000, // Duration in milliseconds
              panelClass: ['centered-snackbar']
            });
          }
          this.router.navigate(['']);

        },
        (error) => {
          console.error('Signup failed:', error);

          if (error && error.error && error.error.message) {
            // Display the specific error message from the server in a snackbar
            this.snackBar.open(error.error.message, 'Close', {
              duration: 5000, // Duration in milliseconds
              panelClass: ['centered-snackbar']
            });
          } else {
            // Fallback message if the error response does not contain a specific message
            this.snackBar.open('An error occurred during signup.', 'Close', {
              duration: 5000, // Duration in milliseconds
              panelClass: ['centered-snackbar']

            });
          }
        }
      );
    }
  }
  ngOnInit(): void {}
}
