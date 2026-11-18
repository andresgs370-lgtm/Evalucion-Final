import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Router } from '@angular/router';
import {Registroservice} from '../../services/registroservice';

@Component({
  selector: 'app-registrocomponent',
  standalone: false,
  styleUrl: './registrocomponent.css',
  templateUrl: './registrocomponent.html',
})
export class Registrocomponent {
  mostrarPassword: boolean = false;
  mostrarConfirmarPassword: boolean = false;
  nombre: string='';
  apellido: string='';
  edad: string='';
  numero: string=''
  contrasena: string=''
  contrasena2: string=''
  rol: string='';
  mensaje: string='';

  constructor(private registroservice : Registroservice, private router: Router) {
  }

  registrar()  {
    if (this.contrasena!== this.contrasena2) {
      this.mensaje = 'las contrasenas no coinciden';
      return;
    }
    const nuevoUsuario= {
      nombre: this.nombre,
      apellidos: this.apellido,
      edad: this.edad,
      numero: this.numero,
      contrasena: this.contrasena,
      contrasena2: this.contrasena2,
      rol: this.rol,
    };

    this.registroservice.Registro(nuevoUsuario).subscribe({
      next: (respuesta)=>{
        console.log('registro exitoso:',respuesta);
        this.mensaje='registro exitoso mi papa';
      if (respuesta.token){
        localStorage.setItem('token', respuesta.token);
      }
      this.router.navigate(['/login']);
      },
      error: err => {
        console.log('error en el registro',err);
        this.mensaje=err.error?.message||'error al registrar el usuario verificar los datos';
      }

    })
  }
}
