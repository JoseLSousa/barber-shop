import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatSelectModule } from "@angular/material/select";
import { MatTimepickerModule } from "@angular/material/timepicker";
import { MatInputModule } from "@angular/material/input";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { NgxMaskDirective, provideNgxMask } from "ngx-mask";
import { ActivatedRoute } from '@angular/router';

import { DayOfWeek } from '../../../Interfaces/day-of-week';
import { WorkingDay } from '../../../Interfaces/working-day';
import { Shift } from '../../../Interfaces/shift';
import { ServiceBShop } from '../../../Interfaces/service-bshop';

import { WorkingDaysService } from '../../../Services/working-days.service';
import { ServiceBShopService } from '../../../Services/service-bshop.service';
import { BookingService } from '../../../Services/booking.service';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import moment from 'moment';
import { Booking } from '../../../Interfaces/booking';

@Component({
  selector: 'app-admin-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatTimepickerModule,
    MatInputModule,
    NgxMaskDirective,
    MatDatepickerModule,
    MatMomentDateModule
  ],
  providers: [provideNgxMask()],
  templateUrl: './admin-sidebar.component.html',
  styleUrls: ['./admin-sidebar.component.scss']
})
export class AdminSidebarComponent implements OnInit, OnChanges {
  @Input() opened: boolean = false;
  @Input() mode: string = "add";
  @Input() selectedItem!: any;
  @Output() toggleEvent = new EventEmitter<void>();
  @Output() fetchupdates = new EventEmitter<void>();

  daysOfWeek = Object.values(DayOfWeek);
  workingDayForm: FormGroup;
  serviceForm: FormGroup;
  bookingForm: FormGroup;
  bShopServices: ServiceBShop[] = [];
  currentPage: string = '';
  availableTimes: string[] = [];
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private workingDaysService: WorkingDaysService,
    private bshopService: ServiceBShopService,
    private bookingService: BookingService
  ) {
    this.workingDayForm = this.createWorkingDayForm();
    this.serviceForm = this.createServiceForm();
    this.bookingForm = this.createBookingForm();
  }

  ngOnInit(): void {
    this.currentPage = this.route.snapshot.url.join('/');
    this.currentPage === 'agendamentos' ? this.fetchBShopServices() : null;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.mode === 'edit') {
      this.patchFormsValues();
    } else {
      this.resetForms();
    }
  }

  private createWorkingDayForm(): FormGroup {
    return this.fb.group({
      dayOfWeek: ['', Validators.required],
      isOpen: ['', Validators.required],
      shiftList: this.fb.array([])
    });
  }

  private createServiceForm(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', Validators.required]
    });
  }

  private createBookingForm(): FormGroup {
    return this.fb.group({
      date: ['', Validators.required],
      time: ['', Validators.required],
      isDone: ['', Validators.required],
      serviceBShopId: ['', Validators.required]
    });
  }

  private resetForms(): void {
    this.serviceForm.reset();
    this.workingDayForm.reset();
    this.shifts.clear();
    this.bookingForm.reset();
  }

  private patchFormsValues(): void {
    switch (this.currentPage) {
      case 'horarios-funcionamento':
        this.patchWorkingDayForm();
        break;
      case 'servicos':
        this.patchServiceForm();
        break;
      case 'agendamentos':
        this.patchBookingForm();
        break;
    }
  }

  private patchWorkingDayForm(): void {
    const dayIndex = Object.keys(DayOfWeek).indexOf(this.selectedItem.dayOfWeek);
    this.workingDayForm.patchValue({
      dayOfWeek: dayIndex,
      isOpen: this.selectedItem.isOpen
    });
    this.selectedItem.shiftList.forEach((shift: Shift) => {
      this.shifts.push(this.createShiftGroup(shift));
    });
  }

  private patchServiceForm(): void {
    this.serviceForm.patchValue({
      name: this.selectedItem.name,
      description: this.selectedItem.description,
      price: this.selectedItem.price / 10
    });
  }

  private patchBookingForm(): void {
    this.availableTimes.push(this.selectedItem.time);
    this.fetchAvailableTimes(this.selectedItem.date);

    let serviceId = this.bShopServices.find(s => s.name === this.selectedItem.serviceName)?.id;
    let dataMoment = moment(this.selectedItem.date).toDate();

    this.bookingForm.patchValue({
      date: dataMoment,
      time: this.selectedItem.time,
      isDone: this.selectedItem.isDone,
      serviceBShopId: serviceId
    });

  }

  get shifts(): FormArray {
    return this.workingDayForm.get('shiftList') as FormArray;
  }

  private createShiftGroup(shift?: Shift): FormGroup {
    return this.fb.group({
      startTime: [shift?.startTime || '', Validators.required],
      endTime: [shift?.endTime || '', Validators.required]
    });
  }

  addShift(): void {
    if (this.shifts.length < 3) {
      this.shifts.push(this.createShiftGroup());
    }
  }

  removeShift(index: number): void {
    this.shifts.removeAt(index);
  }

  onSubmit(): void {
    switch (this.currentPage) {
      case 'horarios-funcionamento':
        this.handleWorkingDaySubmission();
        break;
      case 'servicos':
        this.handleServiceSubmission();
        break;
      case 'agendamentos':
        this.handleBookingSubmission();
    }
    this.fetchupdates.emit();
  }

  private handleWorkingDaySubmission() {
    const form = this.processWorkingDayForm();
    if (this.mode === 'add') {
      this.postWorkingDay(form);
    } else {
      this.putWorkingDay(form);
    }
  }

  private handleServiceSubmission() {
    const form = this.processServiceForm();
    if (this.mode === 'add') {
      this.postService(form);
    } else {
      this.putService(form);
    }
  }
  private handleBookingSubmission() {
    const form = this.processBookingForm();
    this.putBooking(form);
  }

  private processWorkingDayForm(): WorkingDay {
    const shiftList = this.shifts.value.map((shift: Shift, index: number) => {
      if (shift.startTime === shift.endTime) {
        alert('Horário de início e fim não podem ser iguais');
        return null;
      }
      if (shift.startTime > shift.endTime) {
        alert('Horário de início não pode ser maior que o de fim');
        return null;
      }
      return {
        id: this.mode === 'edit' ? this.selectedItem?.shiftList?.[index]?.id : null,
        startTime: this.formatTime(shift.startTime),
        endTime: this.formatTime(shift.endTime)
      };
    }).filter(Boolean);

    return {
      dayOfWeek: this.workingDayForm.get('dayOfWeek')?.value,
      isOpen: this.workingDayForm.get('isOpen')?.value,
      shiftList
    };
  }

  private processServiceForm(): ServiceBShop {
    return {
      name: this.serviceForm.get('name')?.value,
      description: this.serviceForm.get('description')?.value,
      price: this.serviceForm.get('price')?.value * 10
    };
  }

  processBookingForm(): Booking {
    return {
      date: this.bookingForm.get('date')?.value,
      time: this.bookingForm.get('time')?.value,
      serviceBShopId: this.bookingForm.get('serviceBShopId')?.value,
      isDone: this.bookingForm.get('isDone')?.value,
    }
  }

  private formatTime(time: string): string {
    const [hours, minutes] = time.includes(':') ? time.split(':') : [time.substring(0, 2), time.substring(2, 4)];
    return `${hours}:${minutes}`;
  }

  private postWorkingDay(form: WorkingDay) {
    this.workingDaysService.postWorkingDay(form).subscribe({
      next: () => this.handleSuccess('Horário de funcionamento cadastrado com sucesso'),
      error: (err) => this.handleError(err)
    });
  }

  private putWorkingDay(form: WorkingDay) {
    this.workingDaysService.putWorkingDay(this.selectedItem.id, form).subscribe({
      next: () => this.handleSuccess('Horário de funcionamento atualizado com sucesso'),
      error: (err) => this.handleError(err)
    });
  }

  private postService(form: ServiceBShop) {
    this.bshopService.postServiceBShop(form).subscribe({
      next: () => this.handleSuccess('Serviço cadastrado com sucesso'),
      error: (err) => this.handleError(err)
    });
  }

  private putService(form: ServiceBShop) {
    this.bshopService.putServiceBshop(this.selectedItem.id, form).subscribe({
      next: () => this.handleSuccess('Serviço atualizado com sucesso'),
      error: (err) => this.handleError(err)
    });
  }
  private putBooking(form: Booking) {
    this.bookingService.putBooking(this.selectedItem.bookingId, form).subscribe({
      next: () => this.handleSuccess('Agendamento atualizado com sucesso'),
      error: (err) => this.handleError(err)
    });
  }

  private handleSuccess(message: string) {
    alert(message);
    this.fetchupdates.emit();
    this.toggleEvent.emit();
  }

  private handleError(err: any) {
    alert(err.error.message);
  }

  fetchAvailableTimes(date: string) {
    this.bookingService.getAvailableHours(date).subscribe({
      next: (times) => this.availableTimes.push(...times),
      error: (err) => this.handleError(err)
    });
  }

  private fetchBShopServices() {
    this.bshopService.getServicesBShop().subscribe({
      next: (services) => this.bShopServices.push(...services),
      error: (err) => this.handleError(err)
    });
  }

  deleteWorkingDay() {
    if (confirm('Deseja realmente excluir este horário de funcionamento?')) {
      this.workingDaysService.deleteWorkingDay(this.selectedItem.id).subscribe({
        next: () => this.handleSuccess('Horário de funcionamento excluído com sucesso'),
        error: (err) => this.handleError(err)
      });
    }
  }

  deleteService() {
    if (confirm('Deseja realmente excluir este serviço?')) {
      this.bshopService.deleteServiceBShop(this.selectedItem.id).subscribe({
        next: () => this.handleSuccess('Serviço excluído com sucesso'),
        error: (err) => this.handleError(err)
      });
    }
  }

  deleteBooking() {
    if (confirm('Deseja realmente excluir este agendamento?')) {
      this.bookingService.deleteBooking(this.selectedItem.bookingId).subscribe({
        next: () => this.handleSuccess('Agendamento excluído com sucesso'),
        error: (err) => this.handleError(err)
      });
    }
  }
}