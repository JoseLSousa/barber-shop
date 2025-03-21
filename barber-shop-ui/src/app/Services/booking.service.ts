import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
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
  getSearchBookings(date: string, direction?: string):Observable<Booking[]>{
    let params = new HttpParams()
    params = params.set('date', date);
    !!direction ? params.set('direction', direction): 'asc'
     return this.http.get<Booking[]>(`${this.apiUrl}/bookings/search`, {params: params});
  }

  getAvailableHours(date: string):Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/bookings/available-hours?date=${date}`);
  }
  postBooking(body: PostBooking):Observable<string>{
    return this.http.post<string>(`${this.apiUrl}/bookings`, body);
  }

  putBooking(id: string, body: Booking):Observable<void>{
    return this.http.put<void>(`${this.apiUrl}/bookings/${id}`, body);
  }

  deleteBooking(id: string):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/bookings/${id}`);
  }
}
