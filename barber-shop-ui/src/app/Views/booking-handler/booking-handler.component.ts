import { AfterViewInit, Component, OnInit } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatStepperModule } from '@angular/material/stepper';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from "@angular/material/datepicker";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { WorkingDaysService } from '../../Services/working-days.service';
import { BookingService } from '../../Services/booking.service';
import { TimePipe } from '../../Pipes/time.pipe';
import { ServiceBShopService } from '../../Services/service-bshop.service';
import { ServicePricePipe } from '../../Pipes/service-price.pipe';
import { Router } from '@angular/router';
import { MAT_DATE_LOCALE, provideNativeDateAdapter } from '@angular/material/core';
import { Booking } from '../../Interfaces/booking';

@Component({
  selector: 'app-booking-handler',
  imports: [MatInputModule,
    MatFormFieldModule,
    MatStepperModule,
    MatButtonModule,
    MatSelectModule,
    ReactiveFormsModule,
    CommonModule,
    TimePipe,
    ServicePricePipe,
    MatDatepickerModule,],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }, provideNativeDateAdapter()],
  templateUrl: './booking-handler.component.html',
  styleUrl: './booking-handler.component.scss'
})
export class BookingHandlerComponent implements OnInit {
  bookingForm: FormGroup
  availableHours: string[] = []
  servicesBShop: any[] = []
  message: string = 'Aguarde...'
  success: boolean = false
  constructor(private formBuilder: FormBuilder,
    private bookingService: BookingService,
    private serviceBShop: ServiceBShopService,
    private router: Router) {
    this.bookingForm = this.formBuilder.group({
      date: [new Date().toISOString().split('T')[0], Validators.required],
      time: ['', Validators.required],
      serviceBShopId: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.fetchAvailableTimes()
    this.serviceBShop.getServicesBShop().subscribe(res => this.servicesBShop = res)
  }

  fetchAvailableTimes() {
    this.availableHours = []
    this.bookingService.getAvailableHours(this.bookingForm.value.date).subscribe({
      next: (data) => {
        this.availableHours = data
      }
    })
  }

  postSubmit() {
    const form = this.processbookingForm()
    if (this.bookingForm.valid) {
      this.bookingService.postBooking(form).subscribe({
        next: () => {
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

  private processbookingForm(): Booking {
    return {
      date: new Date(this.bookingForm.value.date).toISOString().split('T')[0],
      time: this.bookingForm.get('time')?.value,
      serviceBShopId: this.bookingForm.get('serviceBShopId')?.value,
    }
  }

  returnToBookings() {
    this.router.navigateByUrl('/agenda')
  }
}
