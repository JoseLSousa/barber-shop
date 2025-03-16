import { Component, OnInit } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatStepperModule } from '@angular/material/stepper';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { WorkingDaysService } from '../../Services/working-days.service';
import { BookingService } from '../../Services/booking.service';
import { TimePipe } from '../../Pipes/time.pipe';
import { ServiceBShopService } from '../../Services/service-bshop.service';
import { ServicePricePipe } from '../../Pipes/service-price.pipe';
import { Router } from '@angular/router';

@Component({
  selector: 'app-booking-handler',
  imports: [MatInputModule, MatFormFieldModule, MatStepperModule, MatButtonModule, MatSelectModule, ReactiveFormsModule, CommonModule, TimePipe, ServicePricePipe],
  templateUrl: './booking-handler.component.html',
  styleUrl: './booking-handler.component.scss'
})
export class BookingHandlerComponent implements OnInit {
  newBookingForm: FormGroup
  workingDaysList: any[] = []
  availableHours: any[] = []
  servicesBShop: any[] = []
  message: string = 'Aguarde...'
  success: boolean = false
  constructor(private formBuilder: FormBuilder,
    private workingDays: WorkingDaysService,
    private bookingService: BookingService,
    private serviceBShop: ServiceBShopService,
    private router: Router) {
    this.newBookingForm = this.formBuilder.group({
      time: ['', Validators.required],
      workingDayId: ['', Validators.required],
      serviceBShopId: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.workingDays.getWorkingDays().subscribe(res => this.workingDaysList = res)
    this.serviceBShop.getServicesBShop().subscribe(res => this.servicesBShop = res)
  }

  onChange() {
    if (this.newBookingForm.value.workingDayId) {
      this.bookingService.getAvailableHours(this.newBookingForm.value.workingDayId).subscribe({
        next: (data) => {
          this.availableHours = data
        }
      })
    }
  }
  postSubmit() {
    if (this.newBookingForm.valid) {
      this.bookingService.postBooking(this.newBookingForm.value).subscribe({
        next: (data) => {
          this.message = 'Agendamento realizado com sucesso!'
          this.success = true
        },
        error: () => {
          this.message = 'Erro ao agendar'
          this.success = false
        }
      })
    }
  }

  returnToBookings(){
    this.router.navigateByUrl('/agenda')
  }
}
