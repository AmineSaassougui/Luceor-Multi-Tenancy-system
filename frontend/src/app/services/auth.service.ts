import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {RouterWrapper} from '../router-wrapper';
import {catchError} from 'rxjs/operators';
import {UserWithoutPass} from '../user-without-pass';
import {RentalSummaryDTO} from '../rental-summary-dto';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8083'; // Update the API URL here
  private isAuthenticated = false;

  constructor(private http: HttpClient) {}

  getAllRouters(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/router/get`);
  }

  signup(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/user/signup`, userData);
  }

  login(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/user/login`, userData);
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/user/forgotPassword`, { email });
  }



  // Add a method to save the token to local storage
  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Add a method to get the token from local storage
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Add a method to remove the token from local storage
  removeToken(): void {
    localStorage.removeItem('token');
  }


  getUserInfo() {
    // Get the user's token from local storage
    const token = localStorage.getItem('token');
    // Ensure you have a valid token before making the request
    if (!token) {
      // Handle the case where there's no token (e.g., redirect to login)
      return null;
    }
    // Set up headers with the token for authentication
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    // Make an authenticated GET request to fetch user info
    return this.http.get(`${this.apiUrl}/user/getInfo`, { headers });
  }



  getRoutersByAdmin(): Observable<RouterWrapper[]> {
    // Get the user's token from local storage
    const token = localStorage.getItem('token');
    // Ensure you have a valid token before making the request
    if (!token) {
      // Handle the case where there's no token (e.g., redirect to login)
      return throwError('Token is missing'); // You can use throwError from 'rxjs' to return an error observable
    }
    // Set up headers with the token for authentication
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    // Make an authenticated GET request to fetch routers by admin
    return this.http.get<RouterWrapper[]>(`${this.apiUrl}/router/listbyAdmin`, { headers });
  }


  deleteRouter(idR: number, headers: HttpHeaders): Observable<any> {
    // Make an authenticated DELETE request to delete the router by idR
    return this.http.delete<any>(`${this.apiUrl}/router/delete/${idR}`, { headers });
  }



  addRouter(routerData: any): Observable<any> {
    const url = `${this.apiUrl}/router/add`;

    // Set the HTTP headers including the JWT token
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.getToken()}`
    });

    return this.http.post(url, routerData, { headers });
  }


  getAllUsers(): Observable<UserWithoutPass[]> {
    // Get the user's token from local storage
    const token = this.getToken();

    // Ensure you have a valid token before making the request
    if (!token) {
      // Handle the case where there's no token (e.g., redirect to login)
      return throwError('Token is missing'); // You can use throwError from 'rxjs' to return an error observable
    }

    // Set up headers with the token for authentication
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    // Make an authenticated GET request to fetch all users
    return this.http.get<UserWithoutPass[]>(`${this.apiUrl}/user/get`, { headers });
  }

  activateAccount(requestMap: any): Observable<any> {
    // Set the HTTP headers including the JWT token
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.getToken()}`,
    });

    return this.http.post(`${this.apiUrl}/user/activateaccount`, requestMap, { headers });
  }

  changePassword(requestPayload: { oldPassword: string; newPassword: string }): Observable<string> {
    // Get the user's token from local storage
    const token = this.getToken();

    // Ensure you have a valid token before making the request
    if (!token) {
      // Handle the case where there's no token (e.g., redirect to login)
      return throwError('Token is missing'); // You can use throwError from 'rxjs' to return an error observable
    }

    // Set up headers with the token for authentication
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    // Make an authenticated POST request to change the password
    return this.http.post<string>(`${this.apiUrl}/user/changePassword`, requestPayload, { headers });
  }



  getRouterById(idR: number): Observable<RouterWrapper> {
    const token = this.getToken();

    if (!token) {
      return throwError('Token is missing');
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http
      .get<RouterWrapper>(`${this.apiUrl}/router/getById/${idR}`, { headers })
      .pipe(catchError((error) => this.handleError(error)));
  }



  rentRouter(routerRentalDTO: any): Observable<any> {
    // Get the user's token from local storage
    const token = this.getToken();

    // Ensure you have a valid token before making the request
    if (!token) {
      // Handle the case where there's no token (e.g., redirect to login)
      return throwError('Token is missing');
    }

    // Set up headers with the token for authentication
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });

    // Make an HTTP POST request to your backend's rentRouter endpoint
    return this.http.post<any>(`${this.apiUrl}/rental/rentRouter`, routerRentalDTO, { headers });
  }

  getRentalSummariesByUser(): Observable<RentalSummaryDTO[]> {
    // Get the user's token from local storage
    const token = this.getToken();

    // Ensure you have a valid token before making the request
    if (!token) {
      // Handle the case where there's no token (e.g., redirect to login)
      return throwError('Token is missing');
    }

    // Set up headers with the token for authentication
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    // Make an authenticated GET request to fetch rental summaries by user
    return this.http
      .get<RentalSummaryDTO[]>(`${this.apiUrl}/rental/listByUser`, { headers })
      .pipe(catchError((error) => this.handleError(error)));
  }

  // ... Other methods ...

  // Add an error handling method if needed
  private handleError(error: any): Observable<never> {
    // Handle errors here (e.g., log them or show a user-friendly message)
    console.error('An error occurred:', error);
    return throwError('Something went wrong, please try again later.');
  }
  updateAuthenticationStatus(isAuthenticated: boolean) {
    this.isAuthenticated = isAuthenticated;
  }

  isAuthenticatedUser(): boolean {
    // Example: Check if the user has a valid token.
    const token = localStorage.getItem('token');
    return !!token; // Return true if there is a token, false otherwise.
  }


  listAllRentalSummariesForAdmin(): Observable<RentalSummaryDTO[]> {
    // Get the user's token from local storage
    const token = this.getToken();

    // Ensure you have a valid token before making the request
    if (!token) {
      // Handle the case where there's no token (e.g., redirect to login)
      return throwError('Token is missing');
    }

    // Set up headers with the token for authentication
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    // Make an authenticated GET request to fetch all rental summaries for admins
    return this.http
      .get<RentalSummaryDTO[]>(`${this.apiUrl}/rental/listAllRentals`, { headers })
      .pipe(catchError((error) => this.handleError(error)));
  }

  deleteRentalById(rentalId: number): Observable<any> {
    // Get the user's token from local storage
    const token = this.getToken();

    // Ensure you have a valid token before making the request
    if (!token) {
      // Handle the case where there's no token (e.g., redirect to login)
      return throwError('Token is missing');
    }

    // Set up headers with the token for authentication
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    // Make an authenticated DELETE request to delete the rental by ID
    return this.http
      .delete<any>(`${this.apiUrl}/rental/delete/${rentalId}`, { headers })
      .pipe(catchError((error) => this.handleError(error)));
  }



  searchRouters(keyword: string): Observable<RouterWrapper[]> {
    // Get the user's token from local storage
    const token = this.getToken();

    // Ensure you have a valid token before making the request
    if (!token) {
      // Handle the case where there's no token (e.g., redirect to login)
      return throwError('Token is missing');
    }

    // Set up headers with the token for authentication
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    // Make an authenticated GET request to search routers by keyword
    return this.http
      .get<RouterWrapper[]>(`${this.apiUrl}/router/search?keyword=${keyword}`, { headers })
      .pipe(catchError((error) => this.handleError(error)));
  }


}
