import { CommonModule } from '@angular/common';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { TimePipe } from '../../Pipes/time.pipe';
import { MatTableModule } from "@angular/material/table";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { BookingService } from '../../Services/booking.service';
import { Booking } from '../../Interfaces/booking';
import { DayOfWeekPipePipe } from '../../Pipes/day-of-week-pipe.pipe';
import { provideNativeDateAdapter } from '@angular/material/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminSidebarComponent } from "../Shared/admin-sidebar/admin-sidebar.component";

@Component({
  selector: 'app-admin-bookings',
  imports: [DayOfWeekPipePipe, TimePipe, CommonModule, MatTableModule, MatDatepickerModule, MatFormFieldModule, ReactiveFormsModule, FormsModule, MatInputModule, AdminSidebarComponent],
  providers: [provideNativeDateAdapter()],
  templateUrl: './admin-bookings.component.html',
  styleUrl: './admin-bookings.component.scss'
})
export class AdminBookingsComponent implements OnInit, OnChanges {
  displayedColumns: string[] = ['customer','service', 'dayOfWeek', 'date', 'time', 'price', 'isDone', 'options'];
  bookingsList: Booking[] = []
  openSidebar: boolean = false;
  sidebarMode: 'edit' | 'add' = 'add'
  selectedBooking!: Booking
  date = new FormControl(new Date());
  constructor(private bookingsService: BookingService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    changes[('date')] ? this.fetchBookings(): null
  }


  ngOnInit(): void {
    this.fetchBookings()
  }

  fetchBookings() {
    let formattedDate = this.date.value!.toISOString().split('T')[0]

    this.bookingsService.getSearchBookings(formattedDate).subscribe(bookings => {
      this.bookingsList = bookings
    })
  }


  toggleSidebar(booking?: Booking) {

    if (!!booking) {
      this.selectedBooking = booking
      this.sidebarMode = 'edit'
    }
    else {
      this.sidebarMode = 'add'
    }
    this.openSidebar = !this.openSidebar;
  }


  handleCloseSidebar() {
    this.fetchBookings()
    this.openSidebar = false;

  }
}
