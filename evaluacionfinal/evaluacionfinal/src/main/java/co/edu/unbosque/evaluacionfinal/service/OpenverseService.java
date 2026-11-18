package co.edu.unbosque.evaluacionfinal.service;

import java.net.URI; 
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.edu.unbosque.evaluacionfinal.dto.OpenverseImageDTO;
import co.edu.unbosque.evaluacionfinal.dto.OpenverseResponseDTO;

@Service
public class OpenverseService {

    private static final String URL_API =
            "https://api.openverse.org/v1/images/";

    private static final HttpClient HTTP_CLIENT =
            HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

    public OpenverseResponseDTO buscarImagenes(
            String busqueda, int cantidad) throws Exception {

        if (busqueda == null || busqueda.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe ingresar un término de búsqueda");
        }

        if (cantidad < 1 || cantidad > 20) {
            throw new IllegalArgumentException(
                    "La cantidad debe estar entre 1 y 20");
        }

        String terminoCodificado = URLEncoder.encode(
                busqueda.trim(), StandardCharsets.UTF_8);

        String url = URL_API + "?q=" + terminoCodificado
                + "&page_size=" + cantidad;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .header("User-Agent", "SpringFirstApp/1.0")
                .build();

        HttpResponse<String> response = HTTP_CLIENT.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception(
                    "Openverse respondió con código "
                    + response.statusCode());
        }

        JsonObject json = JsonParser.parseString(
                response.body()).getAsJsonObject();

        JsonArray resultados = json.getAsJsonArray("results");

        List<OpenverseImageDTO> imagenes = new ArrayList<>();

        for (JsonElement elemento : resultados) {

            JsonObject imagen = elemento.getAsJsonObject();

            OpenverseImageDTO dto = new OpenverseImageDTO(
                    obtenerTexto(imagen, "id"),
                    obtenerTexto(imagen, "title"),
                    obtenerTexto(imagen, "url"),
                    obtenerTexto(imagen, "thumbnail"),
                    obtenerTexto(imagen, "creator"),
                    obtenerTexto(imagen, "license"),
                    obtenerTexto(imagen, "license_version"),
                    obtenerTexto(imagen, "foreign_landing_url")
            );

            imagenes.add(dto);
        }

        return new OpenverseResponseDTO(
                imagenes.size(), imagenes);
    }

    private String obtenerTexto(JsonObject objeto, String campo) {

        if (objeto.has(campo) && !objeto.get(campo).isJsonNull()) {
            return objeto.get(campo).getAsString();
        }

        return "";
    }

public HttpResponse<byte[]> obtenerMiniatura(String id)
        throws Exception {

    UUID.fromString(id);

    String url = URL_API + id + "/thumb/";

    HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(url))
            .timeout(Duration.ofSeconds(20))
            .header("Accept", "image/*")
            .header("User-Agent", "SpringFirstApp/1.0")
            .build();

    HttpResponse<byte[]> response = HTTP_CLIENT.send(
            request,
            HttpResponse.BodyHandlers.ofByteArray());

    if (response.statusCode() != 200) {
        throw new Exception(
                "No fue posible obtener la imagen. Código: "
                + response.statusCode());
    }

    return response;
}

}
