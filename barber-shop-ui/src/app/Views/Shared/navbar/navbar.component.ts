import { AfterContentInit, Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { UserService } from '../../../Services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit {
  userName: string = ''
  admin: boolean = localStorage.getItem('role') === 'ADMIN'
  constructor(private userService: UserService, private router: Router) { }
  ngOnInit(): void {
    this.userName = this.userService.getUserName()
  }

  logout() {
    this.userService.logout()
    this.router.navigate(['/login'])
  }
}
