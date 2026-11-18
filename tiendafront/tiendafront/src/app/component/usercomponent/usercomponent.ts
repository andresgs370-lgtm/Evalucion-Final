import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { Producto, Productoservice } from '../../services/productoservice';

interface ItemTienda extends Producto {
  tipo: string;      // 'fruta' o 'verdura'
  aComprar: number;  // cantidad que el cliente quiere
}

@Component({
  selector: 'app-usercomponent',
  standalone: false,
  styleUrl: './usercomponent.css',
  templateUrl: './usercomponent.html',
})
export class Usercomponent implements OnInit {
  productos: ItemTienda[] = [];
  mensaje: string = '';
  error: boolean = false;

  constructor(private productoservice: Productoservice) {}

  ngOnInit() {
    this.cargar();
  }

  // Trae frutas y verduras y las junta en una sola lista general
  cargar() {
    forkJoin({
      frutas: this.productoservice.getAll('fruta'),
      verduras: this.productoservice.getAll('verdura'),
    }).subscribe({
      next: ({ frutas, verduras }) => {
        this.productos = [
          ...(frutas ?? []).map((p) => ({ ...p, tipo: 'fruta', aComprar: 0 })),
          ...(verduras ?? []).map((p) => ({ ...p, tipo: 'verdura', aComprar: 0 })),
        ];
      },
      error: () => this.mostrar('Error al cargar los productos', true),
    });
  }

  get total(): number {
    return this.productos.reduce((s, p) => s + p.precio * (p.aComprar || 0), 0);
  }

  comprar() {
    const carrito = this.productos
      .filter((p) => p.aComprar > 0)
      .map((p) => ({ tipo: p.tipo, id: p.id!, cantidad: p.aComprar }));
    if (carrito.length === 0) {
      this.mostrar('Selecciona al menos un producto', true);
      return;
    }
    this.productoservice.comprar(carrito).subscribe({
      next: (res) => {
        this.mostrar(res.mensaje + '. Total: $' + res.total, false);
        this.cargar(); // recarga: ya se restó del inventario
      },
      error: (err) => this.mostrar(err.error?.mensaje || 'Error en la compra', true),
    });
  }

  mostrar(msg: string, esError: boolean) {
    this.mensaje = msg;
    this.error = esError;
  }
}
