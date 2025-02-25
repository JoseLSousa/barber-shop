import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() { }

  getUserName(): string {
    if (localStorage.getItem('name') === null) return ''
    return `${localStorage.getItem('name')}`
  }

  logout(){
    localStorage.clear()
  }
}
