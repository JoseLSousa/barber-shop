import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PostBooking } from '../Interfaces/post-booking';
import { Booking } from '../Interfaces/booking';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = environment.apiURL
  constructor(private http: HttpClient) { }

  getBookings():Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.apiUrl}/bookings`);
  }
  getAvailableHours(dayOfMonth: string):Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/bookings/available-hours?day=${dayOfMonth}`);
  }
  postBooking(body: PostBooking):Observable<string>{
    return this.http.post<string>(`${this.apiUrl}/bookings`, body);
  }

  deleteBooking(id: string):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/bookings/${id}`);
  }
}
