import { Routes } from '@angular/router';
import { HomeComponent } from './Views/home/home.component';
import { LayoutComponent } from './Views/Shared/layout/layout.component';
import { BookingsComponent } from './Views/bookings/bookings.component';
import { LoginComponent } from './Views/login/login.component';

export const routes: Routes = [
    {
        path: '', component: LayoutComponent, children: [
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            { path: 'home', component: HomeComponent },
            { path: 'agenda', component: BookingsComponent },
        ]
    },
    { path: 'login', component: LoginComponent }
];
