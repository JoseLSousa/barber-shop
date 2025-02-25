import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../Interfaces/login-request';
import { Observable } from 'rxjs';
import { LoginResponse } from '../Interfaces/login-response';
import { RegisterRequest } from '../Interfaces/register-request';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiURL

  constructor(private http: HttpClient) { }


  postLogin(body: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, body)
  }

  postRegister(body: RegisterRequest): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/auth/register`, body);
  }
}
