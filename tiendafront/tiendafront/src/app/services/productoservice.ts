import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Producto {
  id?: number;
  nombre: string;
  cantidad: number;
  precio: number;
  urlImagen: string;
}

// tipo = 'fruta' o 'verdura' -> mismo servicio para los dos productos
@Injectable({ providedIn: 'root' })
export class Productoservice {
  private apiurl = 'http://localhost:8080/';

  constructor(private http: HttpClient) {}

  getAll(tipo: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiurl + tipo + '/getall');
  }

  crear(tipo: string, p: Producto): Observable<string> {
    return this.http.post(this.apiurl + tipo + '/create', p, { responseType: 'text' });
  }

  actualizar(tipo: string, id: number, p: Producto): Observable<string> {
    return this.http.put(this.apiurl + tipo + '/update/' + id, p, { responseType: 'text' });
  }

  eliminar(tipo: string, id: number): Observable<string> {
    return this.http.delete(this.apiurl + tipo + '/delete/' + id, { responseType: 'text' });
  }

  buscarImagen(nombre: string): Observable<any> {
    return this.http.get<any>(this.apiurl + 'external/spoonacular/imagen/' + encodeURIComponent(nombre));
  }

  comprar(carrito: { tipo: string; id: number; cantidad: number }[]): Observable<any> {
    return this.http.post<any>(this.apiurl + 'compra/comprar', carrito);
  }
}
