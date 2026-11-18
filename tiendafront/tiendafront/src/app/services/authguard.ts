import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Authservice } from './authservice';

// Deja entrar solo si hay token y el rol coincide con el de la ruta
export const authguard: CanActivateFn = (route) => {
  const auth = inject(Authservice);
  const router = inject(Router);
  if (auth.estaLogueado() && auth.getRole() === route.data['role']) {
    return true;
  }
  return router.parseUrl('/login');
};
