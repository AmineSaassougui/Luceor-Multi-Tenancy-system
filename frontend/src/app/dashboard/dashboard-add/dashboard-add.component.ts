import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-dashboard-add',
  templateUrl: './dashboard-add.component.html',
  styleUrls: ['./dashboard-add.component.scss']
})
export class DashboardAddComponent implements OnInit {
  routerData: any = {}; // Initialize an empty object to store router data


  constructor(private authService: AuthService) { }
  onSubmit() {
    // Call the AuthService method to add a new router
    this.authService.addRouter(this.routerData).subscribe(
      (response) => {
        // Handle success, e.g., show a success notification
        console.log('Router added successfully:', response);
      },
      (error) => {
        // Handle error, e.g., show an error notification
        console.error('Error adding router:', error);
      }
    );
  }

  ngOnInit(): void {
  }

}
