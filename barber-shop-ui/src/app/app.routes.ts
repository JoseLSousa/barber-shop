import { Routes } from '@angular/router';
import { LayoutComponent } from './Views/Shared/layout/layout.component';
import { authenticationGuard } from './Guards/authentication.guard';
import { LayoutAdminComponent } from './Views/Shared/layout-admin/layout-admin.component';

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
            {
                path: 'sobre', loadComponent: () => import('./Views/about/about.component').then(c => c.AboutComponent)
            }
        ]
    },
    {
        path: 'area-restrita', component: LayoutAdminComponent, canActivate: [authenticationGuard],
        children: [
            {
                path: '', redirectTo: 'agendamentos', pathMatch: 'full'
            },
            {
                path: 'agendamentos', loadComponent: () => import('./Views/admin-bookings/admin-bookings.component').then(c => c.AdminBookingsComponent)
            },
            {
                path: 'horarios-funcionamento', loadComponent: () => import('./Views/admin-working-days/admin-working-days.component').then(c => c.AdminWorkingDaysComponent)
            },
            {
                path: 'servicos', loadComponent: () => import('./Views/admin-barber-shop-services/admin-barber-shop-services.component').then(c => c.AdminBarberShopServicesComponent)
            }
        ]
    },

    { path: 'login', loadComponent: () => import('./Views/login/login.component').then(c => c.LoginComponent) },
    { path: 'cadastro', loadComponent: () => import('./Views/register/register.component').then(c => c.RegisterComponent) }
];
