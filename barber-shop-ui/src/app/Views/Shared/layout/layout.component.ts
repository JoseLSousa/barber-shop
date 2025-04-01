import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from "../navbar/navbar.component";
import { FooterComponent } from "../footer/footer.component";
import { AdminNavbarComponent } from "../admin-navbar/admin-navbar.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-layout',
  imports: [RouterOutlet, NavbarComponent, FooterComponent, AdminNavbarComponent, CommonModule],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {
isAdmin: boolean = localStorage.getItem('role') === 'ADMIN' ? true : false
}
