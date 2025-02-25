import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../Services/booking.service';

@Component({
  selector: 'app-bookings',
  imports: [],
  templateUrl: './bookings.component.html',
  styleUrl: './bookings.component.scss'
})
export class BookingsComponent implements OnInit {

  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.bookingService.getBookings().subscribe((res => console.log(res)));
  }
}
