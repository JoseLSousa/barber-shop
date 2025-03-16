import { CommonModule, DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TimePipe } from '../../Pipes/time.pipe';
import { MatTableModule } from "@angular/material/table";
import { BookingService } from '../../Services/booking.service';
import { Booking } from '../../Interfaces/booking';
import { DayOfWeekPipePipe } from '../../Pipes/day-of-week-pipe.pipe';

@Component({
  selector: 'app-admin-bookings',
  imports: [DayOfWeekPipePipe, TimePipe, CommonModule, MatTableModule],
  templateUrl: './admin-bookings.component.html',
  styleUrl: './admin-bookings.component.scss'
})
export class AdminBookingsComponent implements OnInit {
  displayedColumns: string[] = ['service', 'dayOfMonth', 'time', 'price', 'options'];
  bookingsList: Booking[] = []
  constructor(private bookingsService: BookingService) {

  }

  ngOnInit(): void {
    this.fetchBookings()
  }

  fetchBookings() {
    this.bookingsService.getBookings().subscribe(bookings => {
      this.bookingsList = bookings
    })
  }

  deleteBooking(bookingId: string) {  
    if(confirm('Tem certeza que deseja finalizar este agendamento?')){
      this.bookingsService.deleteBooking(bookingId).subscribe({
        next: () => {
          alert('Agendamento finalizado com sucesso!')
          this.fetchBookings()
        },
        error: (err) => {
          console.log(err.error.message)
        }
      })
    }

  }
}
