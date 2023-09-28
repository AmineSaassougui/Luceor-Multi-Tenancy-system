import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {RentalSummaryDTO} from '../../rental-summary-dto';

@Component({
  selector: 'app-admin-rental-summariesforhim',
  templateUrl: './admin-rental-summariesforhim.component.html',
  styleUrls: ['./admin-rental-summariesforhim.component.scss']
})
export class AdminRentalSummariesforhimComponent implements OnInit {

  userInfo: any;
  constructor(private authService: AuthService) { }
  rentalSummaries: RentalSummaryDTO[] = [];

  ngOnInit(): void {
    this.authService.getUserInfo().subscribe(
      (response: any) => {
        this.userInfo = response;
      },
      (error: any) => {
        // Handle error (e.g., redirect to  if authentication fails)
      }
    );

    this.fetchRentalSummariesForAdmin();
  }

  calculateEndDate(rentalDate: Date): Date {
    const endDate = new Date(rentalDate);
    endDate.setMonth(endDate.getMonth() + 1); // Add 1 month
    return endDate;
  }
  deleteRental(rentalId: number): void {
    this.authService.deleteRentalById(rentalId).subscribe(
      (response) => {
        // Rental deleted successfully, you can handle the response if needed
        // Refresh the rental summaries list to reflect the changes
        this.fetchRentalSummariesForAdmin();
      },
      (error) => {
        console.error('Error deleting rental:', error);
        // Handle the error as needed (e.g., show an error message to the user)
      }
    );
    window.location.reload();

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
