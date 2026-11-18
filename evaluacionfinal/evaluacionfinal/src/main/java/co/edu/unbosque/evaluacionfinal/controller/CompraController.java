package co.edu.unbosque.evaluacionfinal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.evaluacionfinal.dto.CompraDTO;
import co.edu.unbosque.evaluacionfinal.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/compra")
@CrossOrigin(origins = "*")
@Tag(name = "Compras", description = "El cliente compra y se resta del inventario")
@SecurityRequirement(name = "bearerAuth")
public class CompraController {

	@Autowired
	private CompraService compraServ;

	@Operation(summary = "Comprar", description = "Body: [{\"tipo\":\"fruta\",\"id\":1,\"cantidad\":2}]")
	@PostMapping(path = "/comprar", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> comprar(@RequestBody List<CompraDTO> carrito) {
		try {
			double total = compraServ.comprar(carrito);
			return new ResponseEntity<>(Map.of("mensaje", "Compra realizada", "total", total), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(Map.of("mensaje", e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}