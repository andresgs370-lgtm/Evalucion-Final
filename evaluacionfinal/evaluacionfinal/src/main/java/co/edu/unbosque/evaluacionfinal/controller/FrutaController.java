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

import co.edu.unbosque.evaluacionfinal.dto.FrutaDTO;
import co.edu.unbosque.evaluacionfinal.service.FrutaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/fruta")
@CrossOrigin(origins = "*")
@Tag(name = "Frutas", description = "CRUD de frutas (getall: USER/ADMIN, resto: ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class FrutaController {

	@Autowired
	private FrutaService frutaServ;

	@Operation(summary = "Listar todas las frutas")
	@GetMapping("/getall")
	ResponseEntity<List<FrutaDTO>> getAll() {
		return new ResponseEntity<>(frutaServ.getAll(), HttpStatus.OK);
	}

	@GetMapping("/getbyid/{id}")
	ResponseEntity<?> getById(@PathVariable Long id) {
		FrutaDTO found = frutaServ.getById(id);
		if (found == null) {
			return new ResponseEntity<>("Fruta no encontrada", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(found, HttpStatus.OK);
	}

	@Operation(summary = "Crear fruta", description = "Si urlImagen va vacía se busca en Spoonacular")
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> create(@RequestBody FrutaDTO nueva) {
		int status = frutaServ.create(nueva);
		if (status == 0) {
			return new ResponseEntity<>("Fruta creada correctamente", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<>("Ya existe una fruta con ese nombre", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>("Datos inválidos", HttpStatus.BAD_REQUEST);
	}

	@PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> update(@PathVariable Long id, @RequestBody FrutaDTO datos) {
		int status = frutaServ.updateById(id, datos);
		if (status == 0) {
			return new ResponseEntity<>("Fruta actualizada", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("Fruta no encontrada", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Datos inválidos", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/delete/{id}")
	ResponseEntity<String> delete(@PathVariable Long id) {
		int status = frutaServ.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Fruta eliminada", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("Fruta no encontrada", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/count")
	ResponseEntity<Long> count() {
		return new ResponseEntity<>(frutaServ.count(), HttpStatus.OK);
	}
}