import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Authservice } from '../../services/authservice';

@Component({
  selector: 'app-logincomponent',
  standalone: false,
  styleUrl: './logincomponent.css',
  templateUrl: './logincomponent.html',
})
export class Logincomponent {
  username: string = '';
  password: string = '';
  mostrarPassword: boolean = false;
  mensaje: string = '';

  constructor(private auth: Authservice, private router: Router) {}

  ingresar() {
    this.auth.login({ username: this.username, password: this.password }).subscribe({
      next: (res) => {
        this.router.navigate([res.role === 'ADMIN' ? '/admin' : '/tienda']);
      },
      error: () => {
        this.mensaje = 'Usuario o contraseña incorrectos';
      },
    });
  }
}
