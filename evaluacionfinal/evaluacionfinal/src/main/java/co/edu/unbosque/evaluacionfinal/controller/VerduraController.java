package co.edu.unbosque.evaluacionfinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.evaluacionfinal.dto.VerduraDTO;
import co.edu.unbosque.evaluacionfinal.service.VerduraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/verdura")
@CrossOrigin(origins = "*")
@Tag(name = "Verduras", description = "CRUD de verduras (getall: USER/ADMIN, resto: ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class VerduraController {

	@Autowired
	private VerduraService verduraServ;

	@Operation(summary = "Listar todas las verduras")
	@GetMapping("/getall")
	ResponseEntity<List<VerduraDTO>> getAll() {
		return new ResponseEntity<>(verduraServ.getAll(), HttpStatus.OK);
	}

	@GetMapping("/getbyid/{id}")
	ResponseEntity<?> getById(@PathVariable Long id) {
		VerduraDTO found = verduraServ.getById(id);
		if (found == null) {
			return new ResponseEntity<>("Verdura no encontrada", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(found, HttpStatus.OK);
	}

	@Operation(summary = "Crear verdura", description = "Si urlImagen va vacía se busca en Spoonacular")
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> create(@RequestBody VerduraDTO nueva) {
		int status = verduraServ.create(nueva);
		if (status == 0) {
			return new ResponseEntity<>("Verdura creada correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Ya existe una verdura con ese nombre", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>("Datos inválidos", HttpStatus.BAD_REQUEST);
	}

	@PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> update(@PathVariable Long id, @RequestBody VerduraDTO datos) {
		int status = verduraServ.updateById(id, datos);
		if (status == 0) {
			return new ResponseEntity<>("Verdura actualizada", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("Verdura no encontrada", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Datos inválidos", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/delete/{id}")
	ResponseEntity<String> delete(@PathVariable Long id) {
		int status = verduraServ.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Verdura eliminada", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("Verdura no encontrada", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/count")
	ResponseEntity<Long> count() {
		return new ResponseEntity<>(verduraServ.count(), HttpStatus.OK);
	}
}