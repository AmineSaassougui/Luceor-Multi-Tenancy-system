// auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root', // Ensure the guard is provided in the root module
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    // Implement your authentication logic here
    if (this.authService.isAuthenticatedUser()) {
      return true; // User is authenticated, allow access to the route
    } else {
      // User is not authenticated, handle the redirection or other actions here
      // For example, you can redirect to the login page:
      this.router.navigate(['/login']);
      return false; // Prevent access to the route
    }
  }
}
