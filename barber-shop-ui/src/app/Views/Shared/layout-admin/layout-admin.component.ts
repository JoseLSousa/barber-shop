import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from "../footer/footer.component";
import { AdminNavbarComponent } from "../admin-navbar/admin-navbar.component";
@Component({
  selector: 'app-layout-admin',
  imports: [RouterOutlet, FooterComponent, FooterComponent, AdminNavbarComponent],
  templateUrl: './layout-admin.component.html',
  styleUrl: './layout-admin.component.scss'
})
export class LayoutAdminComponent {
}
