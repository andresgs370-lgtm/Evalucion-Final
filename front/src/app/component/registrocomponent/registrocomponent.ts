import { Component } from '@angular/core';

@Component({
  selector: 'app-registrocomponent',
  standalone: false,
  styleUrl: './registrocomponent.css',
  templateUrl: './registrocomponent.html',
})
export class Registrocomponent {
  mostrarPassword: boolean = false;
  mostrarConfirmarPassword: boolean = false;
}
