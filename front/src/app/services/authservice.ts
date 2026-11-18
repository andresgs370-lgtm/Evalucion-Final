import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class Authservice {
  private apiurl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  login(datos: { username: string; password: string }): Observable<any> {
    return this.http.post<any>(this.apiurl + '/login', datos).pipe(
      tap((res) => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('role', res.role);
        localStorage.setItem('username', res.username);
      }),
    );
  }

  logout() {
    localStorage.clear();
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  estaLogueado(): boolean {
    return this.getToken() != null;
  }
}
