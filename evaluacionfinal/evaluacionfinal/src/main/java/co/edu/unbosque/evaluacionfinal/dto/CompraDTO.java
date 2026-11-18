package co.edu.unbosque.evaluacionfinal.dto;

// Un item del carrito: tipo = "fruta" o "verdura", id del producto y cantidad a comprar
public class CompraDTO {

	private String tipo;
	private Long id;
	private int cantidad;

	public CompraDTO() {
	}

	public CompraDTO(String tipo, Long id, int cantidad) {
		this.tipo = tipo;
		this.id = id;
		this.cantidad = cantidad;
	}

	public String getTipo() { return tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public int getCantidad() { return cantidad; }
	public void setCantidad(int cantidad) { this.cantidad = cantidad; }

	@Override
	public String toString() {
		return "CompraDTO [tipo=" + tipo + ", id=" + id + ", cantidad=" + cantidad + "]";
	}
}