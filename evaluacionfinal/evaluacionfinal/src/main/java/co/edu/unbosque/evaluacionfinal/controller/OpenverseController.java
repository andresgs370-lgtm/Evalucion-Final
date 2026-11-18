
package co.edu.unbosque.evaluacionfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.evaluacionfinal.dto.OpenverseResponseDTO;
import co.edu.unbosque.evaluacionfinal.service.OpenverseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/external/openverse")
@Tag(
    name = "Openverse API",
    description = "Búsqueda de imágenes con Openverse"
)
@SecurityRequirement(name = "bearerAuth")
public class OpenverseController {

    @Autowired
    private OpenverseService openverseService;

    @Operation(
        summary = "Buscar imágenes",
        description = "Busca imágenes por palabra clave y devuelve "
                + "sus URLs, autores y licencias."
    )
    @GetMapping("/imagenes/{busqueda}")
    public ResponseEntity<?> buscarImagenes(

            @Parameter(
                description = "Palabra para buscar imágenes",
                example = "perros"
            )
            @PathVariable String busqueda,

            @Parameter(
                description = "Cantidad de imágenes (entre 1 y 20)",
                example = "5"
            )
            @RequestParam(defaultValue = "5") int cantidad) {

        try {

            OpenverseResponseDTO respuesta =
                    openverseService.buscarImagenes(busqueda, cantidad);

            return ResponseEntity.ok(respuesta);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Error al consultar Openverse: "
                            + e.getMessage());
        }
    }
    

@Operation(
    summary = "Visualizar miniatura de una imagen",
    description = "Obtiene la imagen directamente desde Openverse"
)
@ApiResponse(
    responseCode = "200",
    description = "Imagen obtenida correctamente",
    content = @Content(
        mediaType = "image/*",
        schema = @Schema(type = "string", format = "binary")
    )
)
@GetMapping("/miniatura/{id}")
public ResponseEntity<?> visualizarImagen(
        @PathVariable String id) {

    try {

        var respuesta = openverseService.obtenerMiniatura(id);

        String tipoContenido = respuesta.headers()
                .firstValue("Content-Type")
                .orElse("image/jpeg");

        MediaType tipo = MediaType.parseMediaType(tipoContenido);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .contentType(tipo)
                .body(respuesta.body());

    } catch (IllegalArgumentException e) {

        return ResponseEntity.badRequest()
                .body("El identificador de imagen no es válido");

    } catch (Exception e) {

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body("Error al obtener la imagen: " + e.getMessage());
    }
}

}

