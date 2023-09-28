import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {RentalSummaryDTO} from '../../rental-summary-dto';

@Component({
  selector: 'app-admin-rental-summaries',
  templateUrl: './admin-rental-summaries.component.html',
  styleUrls: ['./admin-rental-summaries.component.scss']
})
export class AdminRentalSummariesComponent implements OnInit {

  constructor(private authService: AuthService) { }

  rentalSummaries: RentalSummaryDTO[] = [];

  ngOnInit(): void {
    this.fetchRentalSummariesForAdmin();
  }

  calculateEndDate(rentalDate: Date): Date {
    const endDate = new Date(rentalDate);
    endDate.setMonth(endDate.getMonth() + 1); // Add 1 month
    return endDate;
  }

  fetchRentalSummariesForAdmin() {
    this.authService.listAllRentalSummariesForAdmin().subscribe(
      (summaries) => {
        // Sort the rental summaries by router name in ascending order
        this.rentalSummaries = summaries.sort((a, b) => {
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
