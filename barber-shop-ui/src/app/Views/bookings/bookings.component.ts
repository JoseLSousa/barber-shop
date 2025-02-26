import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../Services/booking.service';
import { MatTableModule } from "@angular/material/table";
import { CommonModule } from '@angular/common';
import { TimePipe } from '../../Pipes/time.pipe';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-bookings',
  imports: [CommonModule, MatTableModule, TimePipe, RouterLink],
  templateUrl: './bookings.component.html',
  styleUrl: './bookings.component.scss'
})
export class BookingsComponent implements OnInit {
  myBookings: any[] = [];
  displayedColumns: string[] = ['service', 'dayOfMonth', 'time', 'price', 'options'];
  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.bookingService.getBookings().subscribe({
      next: (data) => {
        this.myBookings = data;
      }
    });
  }
}
