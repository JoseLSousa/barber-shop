import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../Services/booking.service';
import { MatTableModule } from "@angular/material/table";
import { CommonModule } from '@angular/common';
import { TimePipe } from '../../Pipes/time.pipe';
import { RouterLink } from '@angular/router';
import { Booking } from '../../Interfaces/booking';
@Component({
  selector: 'app-bookings',
  imports: [CommonModule, MatTableModule, TimePipe, RouterLink],
  templateUrl: './bookings.component.html',
  styleUrl: './bookings.component.scss'
})
export class BookingsComponent implements OnInit {
  myBookings: Booking[] = [];
  displayedColumns: string[] = ['service', 'dayOfMonth', 'time', 'price', 'options'];
  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.bookingService.getBookings().subscribe({
      next: (data) => {
        this.myBookings = data;
      }
    });
  }

  deleteBooking(bookingId: string) {
    if(confirm("Deseja realmente excluir?")){
      this.bookingService.deleteBooking(bookingId).subscribe({
        next: () => {
          this.myBookings = this.myBookings.filter(booking => booking.bookingId !== bookingId);
        },
        error: () => {
          alert("Erro ao excluir agendamento");
        }
      });
    }
  }
}
