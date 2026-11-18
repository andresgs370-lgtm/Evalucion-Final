package co.edu.unbosque.evaluacionfinal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.evaluacionfinal.dto.CompraDTO;
import co.edu.unbosque.evaluacionfinal.dto.FrutaDTO;
import co.edu.unbosque.evaluacionfinal.dto.VerduraDTO;

@Service
public class CompraService {

	private final FrutaService frutaService;
	private final VerduraService verduraService;

	public CompraService(FrutaService frutaService, VerduraService verduraService) {
		this.frutaService = frutaService;
		this.verduraService = verduraService;
	}

	// Valida todo el carrito, luego resta del inventario y devuelve el total
	@Transactional
	public double comprar(List<CompraDTO> carrito) {
		if (carrito == null || carrito.isEmpty()) {
			throw new IllegalArgumentException("El carrito está vacío");
		}
		double total = 0;
		// 1. Validar
		for (CompraDTO item : carrito) {
			if (item.getCantidad() <= 0) {
				throw new IllegalArgumentException("Cantidad inválida para el producto " + item.getId());
			}
			if ("fruta".equalsIgnoreCase(item.getTipo())) {
				FrutaDTO f = frutaService.getById(item.getId());
				if (f == null) throw new IllegalArgumentException("La fruta " + item.getId() + " no existe");
				if (f.getCantidad() < item.getCantidad())
					throw new IllegalArgumentException("No hay suficiente " + f.getNombre() + " (quedan " + f.getCantidad() + ")");
				total += f.getPrecio() * item.getCantidad();
			} else if ("verdura".equalsIgnoreCase(item.getTipo())) {
				VerduraDTO v = verduraService.getById(item.getId());
				if (v == null) throw new IllegalArgumentException("La verdura " + item.getId() + " no existe");
				if (v.getCantidad() < item.getCantidad())
					throw new IllegalArgumentException("No hay suficiente " + v.getNombre() + " (quedan " + v.getCantidad() + ")");
				total += v.getPrecio() * item.getCantidad();
			} else {
				throw new IllegalArgumentException("Tipo inválido: " + item.getTipo() + " (use fruta o verdura)");
			}
		}
		// 2. Restar del inventario general
		for (CompraDTO item : carrito) {
			if ("fruta".equalsIgnoreCase(item.getTipo())) {
				frutaService.restarCantidad(item.getId(), item.getCantidad());
			} else {
				verduraService.restarCantidad(item.getId(), item.getCantidad());
			}
		}
		return total;
	}
}