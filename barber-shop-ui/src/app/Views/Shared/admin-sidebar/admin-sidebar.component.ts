import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatSelectModule } from "@angular/material/select";
import { MatTimepickerModule } from "@angular/material/timepicker";
import { MatInputModule } from "@angular/material/input";
import { DayOfWeek } from '../../../Interfaces/day-of-week';
import { ActivatedRoute } from '@angular/router';
import { WorkingDaysService } from '../../../Services/working-days.service';
import { WorkingDay } from '../../../Interfaces/working-day';
import { Shift } from '../../../Interfaces/shift';
import { NgxMaskDirective, provideNgxMask } from "ngx-mask";
import { ServiceBShopService } from '../../../Services/service-bshop.service';
import { ServiceBShop } from '../../../Interfaces/service-bshop';
import { Booking } from '../../../Interfaces/booking';

@Component({
  selector: 'app-admin-sidebar',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, ReactiveFormsModule, MatSelectModule, MatTimepickerModule, MatInputModule, NgxMaskDirective,],
  providers: [provideNgxMask()],
  templateUrl: './admin-sidebar.component.html',
  styleUrls: ['./admin-sidebar.component.scss']
})
export class AdminSidebarComponent implements OnInit, OnChanges {
  @Input() opened: boolean = false;
  @Input() mode: string = "";
  @Input() selectedItem!: any;
  @Output() toggleEvent = new EventEmitter<void>();
  @Output() fetchupdates = new EventEmitter<void>();

  daysOfWeek = Object.values(DayOfWeek)
  workingDayForm: FormGroup
  serviceForm: FormGroup
  currentPage: string = ''
  selectedTime: string = ''
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private workingDaysService: WorkingDaysService, private bshopService: ServiceBShopService) {
    this.workingDayForm = this.fb.group({
      dayOfWeek: ['', Validators.required],
      isOpen: ['', Validators.required],
      shiftList: this.fb.array([])
    })
    this.serviceForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.currentPage = this.route.snapshot.url.join('/')

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes[('mode')] && this.mode == 'edit') {
      this.patchFormsValues()

    } else {
      this.serviceForm.reset()
      this.workingDayForm.reset()
      this.shifts.clear()
    }
  }

  private patchFormsValues() {
    switch (this.currentPage) {
      case 'horarios-funcionamento':
        const day = Object.keys(DayOfWeek)
        this.workingDayForm.patchValue({
          dayOfWeek: day.indexOf(this.selectedItem.dayOfWeek),
          isOpen: this.selectedItem.isOpen,
          shiftList: this.selectedItem.shiftList.forEach((shift: Shift) => {
            this.shifts.push(this.fb.group({
              startTime: shift.startTime,
              endTime: shift.endTime
            }))
          })
        })
        break
      case 'servicos':
        this.serviceForm.patchValue({
          name: this.selectedItem.name,
          description: this.selectedItem.description,
          price: this.selectedItem.price / 10
        })
        break
    }

  }

  get shifts(): FormArray {
    return this.workingDayForm.get('shiftList') as FormArray;
  }

  newShift(): FormGroup {
    return this.fb.group({
      startTime: ['', [Validators.required]],
      endTime: ['', Validators.required]
    })
  }

  addShift() {
    if (this.shifts.length < 3) {
      this.shifts.push(this.newShift())
    }
  }

  removeShift(index: number) {
    this.shifts.removeAt(index)
  }

  onSubmit() {
    switch (this.currentPage) {
      case 'horarios-funcionamento':
        this.mode == 'add' ? this.postWorkingDayMethod(this.processForm()) : this.putWorkingDayMethod(this.processForm())
        break
      case 'servicos':
        this.mode == 'add' ? this.postServiceBshopMethod() : this.putServiceBshopMethod()
        break

    }
    this.fetchupdates.emit()
  }

  private processForm(): WorkingDay {
    const shiftList = this.workingDayForm.get('shiftList')?.value

    const form: WorkingDay = {
      isOpen: this.workingDayForm.get('isOpen')?.value,
      dayOfWeek: this.workingDayForm.get('dayOfWeek')?.value,
      shiftList: []
    };

    for (let i = 0; i < this.workingDayForm.get('shiftList')?.value.length; i++) {
      const shift = shiftList[i]

      if (shift.startTime == shift.endTime) alert('Horário de início e fim não podem ser iguais')
      else if (shift.startTime > shift.endTime) alert('Horário de início não pode ser maior que o de fim')
      else {
        form.shiftList.push(
          {
            id: this.mode === 'edit' && this.selectedItem?.shiftList?.[i]?.id ? this.selectedItem.shiftList[i].id : null,
            endTime: this.formatTime(shift.endTime),
            startTime: this.formatTime(shift.startTime)
          }
        )
      }
    }
    return form
  }

  private formatTime(time: string): string {
    let hours: string;
    let minutes: string;

    if (time.includes(':')) {
      // Format hh:mm:ss or hh:mm
      const parts = time.split(':');
      hours = parts[0];
      minutes = parts[1];
    } else {
      // Format hhmm
      hours = time.substring(0, 2);
      minutes = time.substring(2, 4);
    }

    return `${hours}:${minutes}`;
  }

  private async putWorkingDayMethod(form: WorkingDay) {
    this.workingDaysService.putWorkingDay(this.selectedItem.id, form).subscribe({
      next: () => {
        this.fetchupdates.emit();
        alert('Horário de funcionamento atualizado com sucesso');
      },
      error(er) {
        alert(er.error.message);
      }
    })
  }

  private async postServiceBshopMethod() {
    const form: ServiceBShop = {
      name: this.serviceForm.get('name')?.value,
      description: this.serviceForm.get('description')?.value,
      price: this.serviceForm.get('price')?.value * 10
    }

    this.bshopService.postServiceBShop(form).subscribe({
      next: () => {
        alert('Serviço cadastrado com sucesso');
        this.fetchupdates.emit();
      },
      error(er) {
        alert(er.error.message);
      }
    })
  }

  private async putServiceBshopMethod() {
    const form: ServiceBShop = {
      name: this.serviceForm.get('name')?.value,
      description: this.serviceForm.get('description')?.value,
      price: this.serviceForm.get('price')?.value * 10
    }

    this.bshopService.putServiceBshop(this.selectedItem.id, form).subscribe({
      next: () => {
        this.fetchupdates.emit();
        this.toggleEvent.emit();
        alert('Serviço atualizado com sucesso')
      },
      error(er) {
        alert(er.error.message)
      }
    })
  }

  private async postWorkingDayMethod(form: WorkingDay) {
    this.workingDaysService.postWorkingDay(form).subscribe({
      next: () => {
        this.fetchupdates.emit();
        alert('Horário de funcionamento cadastrado com sucesso')
      },
      error(er) {
        alert(er.error.message)
      }
    })
  }

  async deleteWorkingDayMethod() {
    if (confirm('Deseja realmente excluir este horário de funcionamento?')) {
      this.workingDaysService.deleteWorkingDay(this.selectedItem.id).subscribe({
        next: () => {
          this.fetchupdates.emit();
          this.toggleEvent.emit();
          alert('Horário de funcionamento excluído com sucesso')
        },
        error(er) {
          alert(er.error.message)
        }
      })
    }
  }
  async deleteServiceBshopMethod() {
    if (confirm('Deseja realmente excluir este serviço?')) {
      this.bshopService.deleteServiceBShop(this.selectedItem.id).subscribe({
        next: () => {
          this.fetchupdates.emit();
          this.toggleEvent.emit();
          alert('Serviço excluído com sucesso')
        },
        error(er) {
          alert(er.error.message)
        }
      })
    }
  }
}