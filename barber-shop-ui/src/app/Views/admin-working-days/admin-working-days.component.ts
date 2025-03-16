import { Component, OnInit, } from '@angular/core';
import { MatExpansionModule } from "@angular/material/expansion";
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from "@angular/material/sidenav";
import { WorkingDay } from '../../Interfaces/working-day';
import { WorkingDaysService } from '../../Services/working-days.service';
import { CommonModule } from '@angular/common';
import { TimePipe } from '../../Pipes/time.pipe';
import { AdminSidebarComponent } from "../Shared/admin-sidebar/admin-sidebar.component";
import { DayOfWeekPipePipe } from '../../Pipes/day-of-week-pipe.pipe';

@Component({
  selector: 'app-admin-working-days',
  imports: [MatExpansionModule, CommonModule, MatDividerModule, MatListModule, TimePipe, AdminSidebarComponent, MatSidenavModule, DayOfWeekPipePipe],
  templateUrl: './admin-working-days.component.html',
  styleUrl: './admin-working-days.component.scss'
})
export class AdminWorkingDaysComponent implements OnInit {
  workingDaysList: WorkingDay[] = []
  openSidebar: boolean = false;
  sidebarMode: 'edit' | 'add' = 'add'
  selectedWorkingDay!: WorkingDay 
  constructor(private workingDaysService: WorkingDaysService) { }

  ngOnInit(): void {
    this.fetchWorkingDays()
    
  }
  
  toggleSidebar(edit?:boolean, workingDay?: WorkingDay) {
    if(!!workingDay) this.selectedWorkingDay = workingDay
    edit ? this.sidebarMode = 'edit' : this.sidebarMode = 'add'
    this.openSidebar = !this.openSidebar;
  }


  handleCloseSidebar() {
    this.fetchWorkingDays()
    this.openSidebar = false;
    
  }

  fetchWorkingDays() {
    this.workingDaysService.getWorkingDays().subscribe(res => this.workingDaysList = res)
  }
}
