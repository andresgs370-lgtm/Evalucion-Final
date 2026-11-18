import { HttpInterceptorFn } from '@angular/common/http';

// Agrega el JWT a todas las peticiones: Authorization: Bearer <token>
export const tokeninterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');
  if (token) {
    req = req.clone({ setHeaders: { Authorization: 'Bearer ' + token } });
  }
  return next(req);
};
