import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from '@angular/material/input';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-register',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, MatInputModule, ReactiveFormsModule, MatIconModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerForm: FormGroup
  hide = signal(true) // For password
  emailErrorMessage = signal('')
  passwordErrorMessage = signal('')
  NameErrorMessage = signal('')
  constructor(private formBuilder: FormBuilder) {
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    })
  }
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide())
    event.stopPropagation()
  }

  updateEmailErrorMessage() {
    if (this.registerForm.get('email')?.hasError('required')) {
      this.emailErrorMessage.set('Você precisa digitar um email');
    } else if (this.registerForm.get('email')?.hasError('email')) {
      this.emailErrorMessage.set('Email inválido');
    } else {
      this.emailErrorMessage.set('');
    }
  }

  updateNameErrorMessage() {
    if (this.registerForm.get('name')?.hasError('required')) {
      this.NameErrorMessage.set('Você precisa digitar um nome');
    } else {
      this.NameErrorMessage.set('');
    }
  }
  updatePasswordErrorMessage() {
    console.log(this.registerForm.get('password')?.hasError('required'))
    if (this.registerForm.get('password')?.hasError('required')) {
      this.passwordErrorMessage.set('Você precisa digitar uma senha');
    } else {
      this.passwordErrorMessage.set('');
    }
  }
}