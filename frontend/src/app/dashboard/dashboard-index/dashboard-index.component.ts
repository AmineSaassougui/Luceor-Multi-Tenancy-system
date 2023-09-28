import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {RouterWrapper} from '../../router-wrapper';
import {RentalSummaryDTO} from '../../rental-summary-dto';

@Component({
  selector: 'll-dashboard-index',
  templateUrl: './dashboard-index.component.html',
  styleUrls: ['./dashboard-index.component.scss']
})
export class DashboardIndexComponent implements OnInit {
  orders = [];
  products: RouterWrapper[]; // Define the type for products
  rentalSummaries: RentalSummaryDTO[] = [];
  rentalSummariesa: RentalSummaryDTO[] = [];



  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Call the getRoutersByAdmin method from AuthService
    this.authService.getRoutersByAdmin().subscribe(
      (data) => {
        // Assign the response data to this.products
        this.products = data;
      },
      (error) => {
        console.error('Error fetching routers by admin:', error);
      }
    );
    this.fetchRentalSummariesByUser();
    this.fetchRentalSummariesForAdmin();





    this.orders = [
      {
        id: 'e5dcdfsf',
        orderBy: 'Dean Lynch',
        productId: 'cdfsfe5d',
        created: '25.05.2021, 10:00',
        status: 'complated',
        price: 2145.0
      },
      {
        id: 'e5dcdfsf',
        orderBy: 'Lynch Dean',
        productId: 'cdfsfe5d',
        created: '25.05.2021, 10:00',
        status: 'pending',
        price: 2145.0
      },
      {
        id: 'e5dcdfsf',
        orderBy: 'Lynch Dean',
        productId: 'cdfsfe5d',
        created: '25.05.2021, 10:00',
        status: 'rejected',
        price: 2145.0
      },
      {
        id: 'e5dcdfsf',
        orderBy: 'Dean Lynch',
        productId: 'cdfsfe5d',
        created: '25.05.2021, 10:00',
        status: 'initialized',
        price: 2145.0
      },
      {
        id: 'e5dcdfsf',
        orderBy: 'Dean Lynch',
        productId: 'cdfsfe5d',
        created: '25.05.2021, 10:00',
        status: 'complated',
        price: 2145.0
      }
    ];
  }
  fetchRentalSummariesByUser() {
    this.authService.getRentalSummariesByUser().subscribe(
      (summaries) => {
        // Sort the rental summaries by router name in ascending order
        this.rentalSummaries = summaries.sort((a, b) => {
          const nameA = a.routerName.toLowerCase();
          const nameB = b.routerName.toLowerCase();
          return nameA.localeCompare(nameB);
        });
      },
      (error) => {
        console.error('Error fetching rental summaries:', error);
        // Handle the error as needed (e.g., show an error message to the user)
      }
    );
  }

  fetchRentalSummariesForAdmin() {
    this.authService.listAllRentalSummariesForAdmin().subscribe(
      (summaries) => {
        // Sort the rental summaries by router name in ascending order
        this.rentalSummariesa = summaries.sort((a, b) => {
          const nameA = a.routerName.toLowerCase();
          const nameB = b.routerName.toLowerCase();
          return nameA.localeCompare(nameB);
        });
      },
      (error) => {
        console.error('Error fetching rental summaries for admin:', error);
        // Handle the error as needed (e.g., show an error message to the user)
      }
    );
  }

}
