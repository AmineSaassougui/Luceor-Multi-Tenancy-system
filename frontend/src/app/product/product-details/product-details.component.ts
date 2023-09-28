import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {RouterWrapper} from '../../router-wrapper';

@Component({
  selector: 'll-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss']
})
export class ProductDetailsComponent implements OnInit {
  routerData: RouterWrapper | null = null;


  constructor(
    private authService: AuthService,
    private route: ActivatedRoute
  ) {}


  ngOnInit(): void {
    // Get the router ID from the route parameters
    const routerId = this.route.snapshot.paramMap.get('idR'); // Make sure the route parameter name matches your configuration

    if (routerId) {
      // Call the method to fetch router data by ID
      this.authService.getRouterById(Number(routerId)).subscribe(
        (data) => {
          this.routerData = data;
        },
        (error) => {
          console.error('Error fetching router data:', error);
        }
      );
    }
  }

  rentRouter(routerId: number) {
    // Create a RouterRentalDTO object with the necessary data
    const rentalDTO = {
      routerId, // Pass the router ID to rent
      // Include any additional data needed for the rental
    };

    // Call the rentRouter method from your AuthService
    this.authService.rentRouter(rentalDTO).subscribe(
      (response) => {
        // Handle the successful rental, e.g., show a success message
        console.log('Router rented successfully:', response);
        // You can also redirect the user to a confirmation page
      },
      (error) => {
        // Handle errors, e.g., display an error message
        console.error('Error renting router:', error);
      }
    );
  }



}
