package co.edu.unbosque.evaluacionfinal.dto;

public class FrutaDTO {

	private Long id;
	private String nombre;
	private int cantidad;
	private double precio;
	private String urlImagen;

	public FrutaDTO() {
	}

	public FrutaDTO(String nombre, int cantidad, double precio, String urlImagen) {
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
	public String toString() {
		return "FrutaDTO [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio
				+ ", urlImagen=" + urlImagen + "]";
	}
}