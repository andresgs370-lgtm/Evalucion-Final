package co.edu.unbosque.evaluacionfinal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.evaluacionfinal.service.SpoonacularService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/external/spoonacular")
@CrossOrigin(origins = "*")
@Tag(name = "Spoonacular", description = "Busca la imagen de una fruta o verdura")
@SecurityRequirement(name = "bearerAuth")
public class SpoonacularController {

	@Autowired
	private SpoonacularService spoonacularServ;

	@Operation(summary = "Obtener URL de imagen", description = "Ej: manzana, zanahoria, apple, carrot")
	@GetMapping("/imagen/{nombre}")
	ResponseEntity<?> imagen(@PathVariable String nombre) {
		return new ResponseEntity<>(Map.of("urlImagen", spoonacularServ.buscarImagen(nombre)), HttpStatus.OK);
	}
}