
package co.edu.unbosque.evaluacionfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.evaluacionfinal.dto.CountryDTO;
import co.edu.unbosque.evaluacionfinal.service.CountryService;

@RestController
@RequestMapping("/external/paises")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/{nombre}")
    public ResponseEntity<?> buscarPais(@PathVariable String nombre) {

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
