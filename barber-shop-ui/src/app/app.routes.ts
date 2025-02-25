import { Routes } from '@angular/router';
import { HomeComponent } from './Views/home/home.component';
import { LayoutComponent } from './Views/Shared/layout/layout.component';
import { BookingsComponent } from './Views/bookings/bookings.component';
import { LoginComponent } from './Views/login/login.component';
import { RegisterComponent } from './Views/register/register.component';
import { BookingHandlerComponent } from './Views/booking-handler/booking-handler.component';

export const routes: Routes = [
    {
        path: '', component: LayoutComponent, children: [
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            { path: 'home', component: HomeComponent },
            {
                path: 'agenda', children: [
                    { path: '', component: BookingsComponent },
                    {
                        path: ':id', component: BookingHandlerComponent
                    }
                ]
            },
        ]
    },
    { path: 'login', component: LoginComponent },
    { path: 'cadastro', component: RegisterComponent }
];
