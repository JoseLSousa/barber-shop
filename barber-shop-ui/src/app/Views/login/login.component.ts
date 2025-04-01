import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../Services/auth.service';


@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent {
  loginForm: FormGroup
  hide = signal(true) // For password
  errorMessage = signal('')
  loginErrorMessage: boolean = false
  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    })
    this.loginForm.valueChanges.subscribe(() => this.updateErrorMessage())

  }

  onLoginSubmit() {
    if (this.loginForm.valid) {
      this.loginErrorMessage = false
      this.authService.postLogin(this.loginForm.value).subscribe(
        (res) => {
          localStorage.setItem("token", res.token)
          localStorage.setItem("name", res.name)
          localStorage.setItem('role', res.role)
          this.router.navigate(['home'])
        },
        () => {
          this.loginErrorMessage = true
        }
      )
    }
  }
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide())
    event.stopPropagation()
  }


  updateErrorMessage() {
    if (this.loginForm.get('email')?.hasError('required')) {
      this.errorMessage.set('Você precisa digitar um email');
    } else if (this.loginForm.get('email')?.hasError('email')) {
      this.errorMessage.set('Email inválido');
    } else {
      this.errorMessage.set('');
    }
  }
}
