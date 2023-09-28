import { Component, OnInit } from '@angular/core';
import { RouterWrapper } from '../../router-wrapper';
import { AuthService } from '../../services/auth.service';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'll-dashboard-saved-item',
  templateUrl: './dashboard-saved-item.component.html',
  styleUrls: ['./dashboard-saved-item.component.scss'],
})
export class DashboardSavedItemComponent implements OnInit {
  view = 'list';
  products: RouterWrapper[] = []; // Define the type for products
  showConfirmation = false; // Add a property for the confirmation dialog
  idToDelete: number | null = null; // Track the ID of the router to delete

  constructor(private authService: AuthService) {} // Inject AuthService

  ngOnInit(): void {
    // Call the getRoutersByAdmin method from AuthService
    this.authService.getRoutersByAdmin().subscribe(
      (data) => {
        // Assign the response data to this.products and generate random image URLs
        this.products = data.map((product) => ({
          ...product,
          imageUrl: this.getRandomProductImage(),
        }));
      },
      (error) => {
        console.error('Error fetching routers by admin:', error);
      }
    );
  }

  // Function to select a random product image from a predefined list
  private getRandomProductImage(): string {
    const productImages = [
      'assets/images/products/product 1.png',
      'assets/images/products/product 2.png',
      'assets/images/products/product 3.png',
      'assets/images/products/product 4.png',
    ];
    // Get a random index
    const randomIndex = this.getRandomIndex(productImages.length);

    // Return the image URL of the randomly selected product
    return productImages[randomIndex];
  }

  // Function to get a random index within a specified range
  private getRandomIndex(max: number): number {
    return Math.floor(Math.random() * max);
  }

  DeleteRouter(idR: number) {
    // Display the confirmation dialog and store the ID of the router to delete
    this.showConfirmation = true;
    this.idToDelete = idR;
  }

  confirmDelete() {
    if (this.idToDelete !== null) {
      // Get the user's token from local storage
      const token = this.authService.getToken();

      // Ensure you have a valid token before making the request
      if (!token) {
        // Handle the case where there's no token (e.g., redirect to login)
        console.error('Token is missing');
        return;
      }

      // Set up headers with the token for authentication
      const headers = new HttpHeaders({
        Authorization: `Bearer ${token}`,
      });

      // Make an authenticated DELETE request to delete the router by idR
      this.authService.deleteRouter(this.idToDelete, headers).subscribe(
        (response) => {
          // Handle success (e.g., show a success message)
          console.log('Router deleted successfully:', response);
          // Reload the page or update your data
          window.location.reload();
        },
        (error) => {
          // Handle error (e.g., show an error message)
          console.error('Error deleting router:', error);
        }
      );

      // Hide the confirmation dialog after confirming
      this.showConfirmation = false;
    }
  }

  cancelDelete() {
    // Hide the confirmation dialog without taking any action
    this.showConfirmation = false;
    this.idToDelete = null; // Reset the ID to delete
  }
}
