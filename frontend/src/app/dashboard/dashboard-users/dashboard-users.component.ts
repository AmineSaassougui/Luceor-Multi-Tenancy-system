import { Component, OnInit } from '@angular/core';
import {UserWithoutPass} from '../../user-without-pass';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-dashboard-users',
  templateUrl: './dashboard-users.component.html',
  styleUrls: ['./dashboard-users.component.scss']
})
export class DashboardUsersComponent implements OnInit {
  users: UserWithoutPass[] = [];

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.authService.getAllUsers().subscribe((data) => {
      this.users = data;
    });
  }

  activateUser(userId: number, status: string): void {
    // Prepare the request payload
    const requestPayload = {
      id: userId,
      status, // Ensure that status is passed as a string here
    };

    console.log('Request Payload:', requestPayload); // Log the request payload

    // Call the activateAccount method from AuthService
    this.authService.activateAccount(requestPayload).subscribe(
      (response) => {
        // Handle the successful response (e.g., show a success message)
        console.log('User status updated successfully', response);
        window.location.reload();
        this.loadUsers();
      },
      (error) => {
        // Handle errors (e.g., show an error message)
        console.error('Error updating user status', error);
      }
    );
  }




}
