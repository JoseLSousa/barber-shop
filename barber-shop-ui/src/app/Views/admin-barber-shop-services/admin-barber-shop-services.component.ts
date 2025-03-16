import { Component, OnInit } from '@angular/core';
import { AdminSidebarComponent } from "../Shared/admin-sidebar/admin-sidebar.component";
import { CommonModule } from '@angular/common';
import { ServiceBShopService } from '../../Services/service-bshop.service';
import { ServiceBShop } from '../../Interfaces/service-bshop';
import { MatTableModule } from "@angular/material/table";

@Component({
  selector: 'app-admin-barber-shop-services',
  imports: [CommonModule, AdminSidebarComponent, MatTableModule],
  templateUrl: './admin-barber-shop-services.component.html',
  styleUrl: './admin-barber-shop-services.component.scss'
})
export class AdminBarberShopServicesComponent implements OnInit {
  openSidebar: boolean = false;
  sidebarMode: 'edit' | 'add' = 'add'
  servicesList: ServiceBShop[] = []
  selectedItem!: ServiceBShop
  displayedColumns: string[] = ['service', 'price', 'options'];
  constructor(private bshopService: ServiceBShopService) {

  }
  ngOnInit(): void {
    this.fetchServices()
  }

  toggleSidebar(service?: ServiceBShop) {
    if (service) {
      this.sidebarMode = 'edit';
      this.selectedItem = service;
    } else {
      this.sidebarMode = 'add';
    }
    this.openSidebar = !this.openSidebar;
  }

  fetchServices() {
    this.bshopService.getServicesBShop().subscribe(res => this.servicesList = res)
  }
}
