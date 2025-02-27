import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceBShopService {
  private apiUrl = environment.apiURL
  constructor(private http: HttpClient) { }

  getServiceBShops(): Observable<any> {
    return this.http.get(`${this.apiUrl}/services-barber-shop`);
  }
}
