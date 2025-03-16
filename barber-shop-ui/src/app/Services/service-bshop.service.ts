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

  getServicesBShop(): Observable<ServiceBShop[]> {
    return this.http.get<ServiceBShop[]>(`${this.apiUrl}/services-barber-shop`);
  }
  postServiceBShop(service: ServiceBShop): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/services-barber-shop`, service)
  }

  putServiceBshop(serviceId: string, service: ServiceBShop): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/services-barber-shop/${serviceId}`, service)
  }

  deleteServiceBShop(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/services-barber-shop/${id}`)
  }
}
