import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-dashboard-changepassword',
  templateUrl: './dashboard-changepassword.component.html',
  styleUrls: ['./dashboard-changepassword.component.scss']
})
export class DashboardChangepasswordComponent implements OnInit {


  constructor() { }
  ngOnInit(): void {
    // Set the countdown target date (replace with your target date)
    const targetDate = new Date('2023-12-31T00:00:00').getTime();

    // Update the countdown every 1 second
    const countdown = setInterval(function() {
      const currentDate = new Date().getTime();
      const timeLeft = targetDate - currentDate;

      const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
      const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

      document.getElementById('days').textContent = days.toString();
      document.getElementById('hours').textContent = hours.toString();
      document.getElementById('minutes').textContent = minutes.toString();
      document.getElementById('seconds').textContent = seconds.toString();

      if (timeLeft <= 0) {
        clearInterval(countdown);
        document.getElementById('days').textContent = '0';
        document.getElementById('hours').textContent = '0';
        document.getElementById('minutes').textContent = '0';
        document.getElementById('seconds').textContent = '0';
      }
    }, 1000);
  }
}
