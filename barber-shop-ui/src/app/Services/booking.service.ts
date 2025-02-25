import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = environment.apiURL
  constructor(private http: HttpClient) { }

  getBookings():Observable<any> {
    return this.http.get(`${this.apiUrl}/bookings/user-id`);
  }
}
