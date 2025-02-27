import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WorkingDaysService {
  private apiUrl = environment.apiURL
  constructor(private http: HttpClient) { }

  getWorkingDays():Observable<any>{
    return this.http.get(`${this.apiUrl}/working-days`)
  }
}
