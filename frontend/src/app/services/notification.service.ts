import { Injectable } from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {private showNotificationSource = new Subject<string>();
  showNotification$ = this.showNotificationSource.asObservable();

  constructor() {}

  showNotification(message: string) {
    this.showNotificationSource.next(message);
  }
}
