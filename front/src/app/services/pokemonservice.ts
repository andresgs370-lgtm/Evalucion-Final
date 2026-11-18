import {Injectable, Service} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class Pokemonservice {
  private apiurl="http://localhost:8080/api/pokemon";

  constructor(private http: HttpClient) {
  }

  buscarPokemon(datos:{nombre:String,filtro:String}):Observable<any> {
    return  this.http.post<any>(this.apiurl,datos)
  }
}
