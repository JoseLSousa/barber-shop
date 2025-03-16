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

  postWorkingDay(wd: WorkingDay): Observable<WorkingDay>{
    return this.http.post<WorkingDay>(`${this.apiUrl}/working-days`, wd)
  }

  putWorkingDay(id: string, wd: WorkingDay): Observable<WorkingDay>{
    return this.http.put<WorkingDay>(`${this.apiUrl}/working-days/${id}`, wd)
  }

  deleteWorkingDay(id: string): Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/working-days/${id}`)
  }
}
