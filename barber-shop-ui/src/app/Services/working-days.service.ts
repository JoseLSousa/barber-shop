import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { WorkingDay } from '../Interfaces/working-day';

@Injectable({
  providedIn: 'root'
})
export class WorkingDaysService {
  private apiUrl = environment.apiURL
  constructor(private http: HttpClient) { }

  getWorkingDays():Observable<WorkingDay[]>{
    return this.http.get<WorkingDay[]>(`${this.apiUrl}/working-days`)
  }
}
