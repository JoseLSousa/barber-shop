import { Routes } from '@angular/router';
import { HomeComponent } from './Views/home/home.component';
import { LayoutComponent } from './Views/Shared/layout/layout.component';

export const routes: Routes = [
    {
        path: '', component: LayoutComponent, children: [
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            { path: 'home', component: HomeComponent },
        ]
    }
];
