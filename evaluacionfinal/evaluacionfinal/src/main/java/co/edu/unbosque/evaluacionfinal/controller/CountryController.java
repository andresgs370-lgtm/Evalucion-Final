
package co.edu.unbosque.evaluacionfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.evaluacionfinal.dto.CountryDTO;
import co.edu.unbosque.evaluacionfinal.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/external/paises")
@CrossOrigin(origins = "*")
@Tag(name = "RestCountries", description = "Consulta de países usando la API externa RestCountries")
@SecurityRequirement(name = "bearerAuth")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Operation(summary = "Buscar información de un país por nombre",
            description = "Devuelve datos relevantes del país. REQUISITO: Solo accesible para rol ADMIN.")
    @GetMapping("/{nombre}")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<?> buscarPais(
            @Parameter(description = "Nombre del país", example = "colombia")
            @PathVariable String nombre) {

        try {

            CountryDTO pais = countryService.buscarPais(nombre);

            return new ResponseEntity<>(pais, HttpStatus.OK);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error al consultar la API de países",
                    HttpStatus.BAD_GATEWAY
            );
        }
    }
}