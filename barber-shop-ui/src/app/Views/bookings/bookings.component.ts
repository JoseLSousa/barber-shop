import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../Services/booking.service';
import { MatTableModule } from "@angular/material/table";
import { CommonModule } from '@angular/common';
import { TimePipe } from '../../Pipes/time.pipe';
import { RouterLink } from '@angular/router';
import { Booking } from '../../Interfaces/booking';
import { DayOfWeekPipePipe } from '../../Pipes/day-of-week-pipe.pipe';
@Component({
  selector: 'app-bookings',
  imports: [CommonModule, MatTableModule, TimePipe, RouterLink, DayOfWeekPipePipe],
  templateUrl: './bookings.component.html',
  styleUrl: './bookings.component.scss'
})
export class BookingsComponent implements OnInit {
  myBookings: Booking[] = [];
  displayedColumns: string[] = ['service', 'dayOfWeek', 'date', 'time', 'price','isDone', 'options'];
  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.bookingService.getBookings().subscribe({
      next: (data) => {
        this.myBookings = data;        
      }
    });
  }

  deleteBooking(bookingId: string) {
    if (confirm("Deseja realmente cancelar este agendamento?")) {
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
