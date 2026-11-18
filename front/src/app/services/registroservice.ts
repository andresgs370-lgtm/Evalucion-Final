import { Injectable,inject } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class Registroservice {
  private  http= inject(HttpClient);
  private apiurl= "http://localhost:8080/api/registro"

  Registro(datosUsuario:{nombre:string, apellidos:string, numero:string, edad:String, contrasena:string, contrasena2:string, rol:String}):Observable<any> {
    return this.http.post<any>(this.apiurl,datosUsuario)
  }
}
