import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authenticationGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');

  if (!token) {
    router.navigate(['/login']);
    return false;
  }

  if (state.url.includes('area-restrita') && role !== 'ADMIN') {
    router.navigate(['/']);
    return false;
  }

  if (state.url.includes('agenda')) return true

  return true
};
