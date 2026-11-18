import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class Registroservice {
  private apiurl= "http://localhost:8080/api/registro"
  constructor(private http:HttpClient) { };

  Registro(datosUsuario:{nombre:string, apellidos:string, numero:string, edad:String, contrasena:string, contrasena2:string, rol:String}):Observable<any> {
    return this.http.post<any>(this.apiurl,datosUsuario)
  }
}
