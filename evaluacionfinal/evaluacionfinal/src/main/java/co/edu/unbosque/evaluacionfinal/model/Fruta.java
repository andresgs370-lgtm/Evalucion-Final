package co.edu.unbosque.evaluacionfinal.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fruta")
public class Fruta {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	private String nombre;
	private int cantidad;
	private double precio;

	@Column(length = 500)
	private String urlImagen;

	public Fruta() {
	}

	public Fruta(String nombre, int cantidad, double precio, String urlImagen) {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
		this.urlImagen = urlImagen;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public int getCantidad() { return cantidad; }
	public void setCantidad(int cantidad) { this.cantidad = cantidad; }

	public double getPrecio() { return precio; }
	public void setPrecio(double precio) { this.precio = precio; }

	public String getUrlImagen() { return urlImagen; }
	public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, cantidad, precio, urlImagen);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Fruta other = (Fruta) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre) && cantidad == other.cantidad
				&& Double.compare(precio, other.precio) == 0 && Objects.equals(urlImagen, other.urlImagen);
	}

	@Override
	public String toString() {
		return "Fruta [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio
				+ ", urlImagen=" + urlImagen + "]";
	}
}