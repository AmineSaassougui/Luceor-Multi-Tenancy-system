import { Component, OnInit } from '@angular/core';
import {NotificationService} from '../services/notification.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {
  showNotification = false;
  message = '';
  constructor(private notificationService: NotificationService) { }

  ngOnInit() {
    this.notificationService.showNotification$.subscribe((message) => {
      this.message = message;
      this.showNotification = true;

      // Automatically hide the notification after 4 seconds
      setTimeout(() => {
        this.showNotification = false;
      }, 4000);
    });
  }

}
