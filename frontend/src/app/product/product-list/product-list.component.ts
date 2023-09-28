import { Component, OnInit } from '@angular/core';
import { productsDB } from '../../shared/data/products';
import { RouterWrapper } from '../../router-wrapper';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'll-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent implements OnInit {
  isLoaded: boolean;
  advanceSearchExpanded = false;
  products: RouterWrapper[] = [];
  searchKeyword: any ;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Call the method to fetch products from your ProductService
    this.authService.getAllRouters().subscribe((data: RouterWrapper[]) => {
      // Assign the fetched products to the products array
      this.products = data.map((product) => ({
        ...product,
        imageUrl: this.getRandomProductImage(),
      }));
    });
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

  searchRouters(): void {
    if (this.searchKeyword.trim() !== '') {
      // Modify this part to call the search method from your service
      this.authService.searchRouters(this.searchKeyword).subscribe(
        (data: RouterWrapper[]) => {
          // Update the products array with the search results
          this.products = data.map((router) => ({
            ...router,
            imageUrl: this.getRandomProductImage(),
          }));
        },
        (error) => {
          // Handle the error (e.g., show a message to the user)
          console.error('Error searching routers:', error);
        }
      );
    } else {
      // If the search keyword is empty, you can decide what to do
      // For example, load all routers or display a message to the user
      this.loadAllRouters(); // Call a method to load all routers
    }
  }

  // Add a method to load all routers when the search bar is empty
  loadAllRouters(): void {
    this.authService.getAllRouters().subscribe((data: RouterWrapper[]) => {
      // Assign the fetched products to the products array
      this.products = data.map((product) => ({
        ...product,
        imageUrl: this.getRandomProductImage(),
      }));
    });
  }

}
