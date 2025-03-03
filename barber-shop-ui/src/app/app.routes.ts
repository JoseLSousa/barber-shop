import { Routes } from '@angular/router';
import { LayoutComponent } from './Views/Shared/layout/layout.component';
import { authenticationGuard } from './Guards/authentication.guard';

export const routes: Routes = [
    {
        path: '', component: LayoutComponent, children: [
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            {
                path: 'home',
                loadComponent: () => import('./Views/home/home.component').then(c => c.HomeComponent)
            },
            {
                path: 'agenda', canActivate: [authenticationGuard], children: [
                    { path: '', loadComponent: () => import('./Views/bookings/bookings.component').then(c => c.BookingsComponent) },
                    {
                        path: 'novo-agendamento', loadComponent: () => import('./Views/booking-handler/booking-handler.component').then(c => c.BookingHandlerComponent)
                    }
                ]
            },
        ]
    },
    { path: 'login', loadComponent: () => import('./Views/login/login.component').then(c => c.LoginComponent) },
    { path: 'cadastro', loadComponent: () => import('./Views/register/register.component').then(c => c.RegisterComponent) }
];
