import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../Services/booking.service';
import { MatExpansionModule } from "@angular/material/expansion";
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-bookings',
  imports: [MatExpansionModule, CommonModule],
  templateUrl: './bookings.component.html',
  styleUrl: './bookings.component.scss'
})
export class BookingsComponent implements OnInit {
  myBookings: any[] = [];
  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.bookingService.getBookings().subscribe({
      next: (data) => {
        this.myBookings = data;
        console.log(this.myBookings);
      }
    });
  }
}
