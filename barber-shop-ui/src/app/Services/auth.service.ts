import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Login } from '../Interfaces/login';
import { Observable } from 'rxjs';
import { LoginResponse } from '../Interfaces/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiURL

  constructor(private http: HttpClient) { }


  postLogin(body: Login): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, body)
  }
}
