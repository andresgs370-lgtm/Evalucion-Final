package co.edu.unbosque.evaluacionfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.evaluacionfinal.dto.PokemonDTO;
import co.edu.unbosque.evaluacionfinal.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/external/pokemon")
@Tag(name = "PokeAPI", description = "Consulta de pokémon usando la PokeAPI externa")
@SecurityRequirement(name = "bearerAuth")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @Operation(summary = "Filtrar pokémon",
            description = "1 = 50 más fuertes (suma de estadísticas base); 2 a 19 = tipo (agua, fuego, planta, etc.).")
    @GetMapping("/filtro/{opcion}")
    public ResponseEntity<?> filtrar(
            @Parameter(description = "Opción del filtro (1 a 19)", example = "2")
            @PathVariable int opcion) {

        try {

            return new ResponseEntity<>(pokemonService.buscarPorFiltro(opcion), HttpStatus.OK);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            return new ResponseEntity<>("Error al consultar la PokeAPI", HttpStatus.BAD_GATEWAY);
        }
    }

    @Operation(summary = "Buscar un pokémon por nombre",
            description = "Devuelve id, tipos, habilidades, estadísticas, medidas e imagen del pokémon.")
    @GetMapping("/{nombre}")
    public ResponseEntity<?> buscarPokemon(
            @Parameter(description = "Nombre del pokémon (ej: pikachu, charizard) o su número", example = "pikachu")
            @PathVariable String nombre) {

        try {

            PokemonDTO pokemon = pokemonService.buscarPokemon(nombre);

            return new ResponseEntity<>(pokemon, HttpStatus.OK);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {

            return new ResponseEntity<>("Error al consultar la PokeAPI", HttpStatus.BAD_GATEWAY);
        }
    }
}
