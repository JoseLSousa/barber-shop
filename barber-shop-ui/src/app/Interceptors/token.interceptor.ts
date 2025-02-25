import { HttpInterceptorFn } from '@angular/common/http';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  
  const token = localStorage.getItem('token');
  if (token) {
    const clone = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    })
    return next(clone);
  }
  console.log("reste");
  return next(req);
};
