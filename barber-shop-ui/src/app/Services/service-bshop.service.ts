import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServiceBShop } from '../Interfaces/service-bshop';

@Injectable({
  providedIn: 'root'
})
export class ServiceBShopService {
  private apiUrl = environment.apiURL
  constructor(private http: HttpClient) { }

  getServiceBShops(): Observable<ServiceBShop[]> {
    return this.http.get<ServiceBShop[]>(`${this.apiUrl}/services-barber-shop`);
  }
}
