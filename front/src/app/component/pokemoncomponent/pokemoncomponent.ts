import { Component } from '@angular/core';
import { Pokemonservice} from '../../services/pokemonservice';
import {HttpClient} from '@angular/common/http';
@Component({
  selector: 'app-pokemoncomponent',
  standalone: false,
  styleUrl: './pokemoncomponent.css',
  templateUrl: './pokemoncomponent.html',
})
export class Pokemoncomponent {
  nombrePokemon: string='';
  filtro: string=''
  respuestaPokemon: string='';

  constructor(private pokemonservice: Pokemonservice) { }

  enviardatos() {
    const Payload ={
      nombre: this.nombrePokemon,
      filtro: this.filtro,
    };
    this.pokemonservice.buscarPokemon(Payload).subscribe({
      next: (respuesta)=> {
        this.respuestaPokemon = typeof respuesta== 'string'? respuesta:JSON.stringify(respuesta);
      },
      error: (error)=> {
        console.error('Error', error);
        this.respuestaPokemon = 'error al conectar con el backend.';
      }
    });
  }
}
