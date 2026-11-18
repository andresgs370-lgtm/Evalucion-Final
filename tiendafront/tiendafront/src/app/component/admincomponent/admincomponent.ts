import { Component, OnInit } from '@angular/core';
import { Producto, Productoservice } from '../../services/productoservice';

@Component({
  selector: 'app-admincomponent',
  standalone: false,
  styleUrl: './admincomponent.css',
  templateUrl: './admincomponent.html',
})
export class Admincomponent implements OnInit {
  tipo: string = 'fruta'; // 'fruta' o 'verdura'
  productos: Producto[] = [];
  form: Producto = this.vacio();
  editandoId: number | null = null;
  mensaje: string = '';

  constructor(private productoservice: Productoservice) {}

  ngOnInit() {
    this.cargar();
  }

  vacio(): Producto {
    return { nombre: '', cantidad: 0, precio: 0, urlImagen: '' };
  }

  cambiarTipo(t: string) {
    this.tipo = t;
    this.cancelar();
    this.cargar();
  }

  cargar() {
    this.productoservice.getAll(this.tipo).subscribe({
      next: (lista) => (this.productos = lista ?? []),
      error: () => (this.mensaje = 'Error al cargar los productos'),
    });
  }

  buscarImagen() {
    if (!this.form.nombre) return;
    this.productoservice.buscarImagen(this.form.nombre).subscribe((res) => (this.form.urlImagen = res.urlImagen));
  }

  guardar() {
    const peticion = this.editandoId == null
      ? this.productoservice.crear(this.tipo, this.form)
      : this.productoservice.actualizar(this.tipo, this.editandoId, this.form);
    peticion.subscribe({
      next: (msg) => {
        this.mensaje = msg;
        this.cancelar();
        this.cargar();
      },
      error: (err) => (this.mensaje = err.error || 'Error al guardar'),
    });
  }

  editar(p: Producto) {
    this.editandoId = p.id!;
    this.form = { ...p };
  }

  eliminar(p: Producto) {
    if (!confirm('¿Eliminar ' + p.nombre + '?')) return;
    this.productoservice.eliminar(this.tipo, p.id!).subscribe({
      next: (msg) => {
        this.mensaje = msg;
        this.cargar();
      },
      error: (err) => (this.mensaje = err.error || 'Error al eliminar'),
    });
  }

  cancelar() {
    this.editandoId = null;
    this.form = this.vacio();
  }
}
