import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { MatMenuModule } from "@angular/material/menu";

import { UserService } from '../../../Services/user.service';

@Component({
  selector: 'app-admin-navbar',
  imports: [RouterLink, RouterLinkActive, CommonModule, MatMenuModule],
  templateUrl: './admin-navbar.component.html',
  styleUrl: './admin-navbar.component.scss'
})
export class AdminNavbarComponent implements OnInit {
  userName: string = ''
  constructor(private userService: UserService, public router: Router) { }


  ngOnInit() {
    this.userName = this.userService.getUserName();
  }

  logOff() {
    this.userService.logout();
    this.router.navigate(["/login"]);
  }
}
