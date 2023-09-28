import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'll-dashboard-layout',
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.scss']
})
export class DashboardLayoutComponent implements OnInit {
  userInfo: any;
  isLessThenLargeDevice;

  constructor(private breakpointObserver: BreakpointObserver, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.breakpointObserver.observe(['(max-width: 1199px)']).subscribe(({ matches }) => {
      this.isLessThenLargeDevice = matches;
    });

    // Call the getUserInfo method to fetch user information
    this.authService.getUserInfo().subscribe(
      (response: any) => {
        this.userInfo = response;
      },
      (error: any) => {
        // Handle error (e.g., redirect to login if authentication fails)
      }
    );
  }
  onLogout(): void {
    this.authService.removeToken();
    this.router.navigate(['auth/login']);
  }
}
