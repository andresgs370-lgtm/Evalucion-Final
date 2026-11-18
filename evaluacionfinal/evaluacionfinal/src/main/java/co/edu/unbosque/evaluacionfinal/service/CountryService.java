package co.edu.unbosque.evaluacionfinal.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.edu.unbosque.evaluacionfinal.dto.CountryDTO;

@Service
public class CountryService {

    private static final String URL_API = "https://restcountries.com/v3.1/name/";

    private final ExternalHTTPRequestHandler httpHandler = new ExternalHTTPRequestHandler();

    public CountryDTO buscarPais(String nombre) throws Exception {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un país");
        }

        String nombreCodificado = URLEncoder.encode(nombre.trim(), StandardCharsets.UTF_8)
                .replace("+", "%20");

        String url = URL_API + nombreCodificado + "?fullText=true";

        String respuesta = httpHandler.doGet(url);

        if (respuesta == null) {
            throw new Exception("No se recibió respuesta de la API");
        }

        // Convertir el JSON a un arreglo
        JsonElement elemento = JsonParser.parseString(respuesta);

        if (!elemento.isJsonArray()) {
            throw new IllegalArgumentException("País no encontrado");
        }

        JsonArray paises = elemento.getAsJsonArray();

        if (paises.size() == 0) {
            throw new IllegalArgumentException("País no encontrado");
        }

        JsonObject pais = paises.get(0).getAsJsonObject();

        // Nombres
        JsonObject nombrePais = pais.getAsJsonObject("name");
        String nombreComun = nombrePais.get("common").getAsString();
        String nombreOficial = nombrePais.get("official").getAsString();

        // Capital
        String capital = "";
        if (pais.has("capital") && pais.get("capital").isJsonArray()
                && pais.getAsJsonArray("capital").size() > 0) {
            capital = pais.getAsJsonArray("capital").get(0).getAsString();
        }

        // Región y subregión
        String region = pais.has("region") ? pais.get("region").getAsString() : "";
        String subregion = pais.has("subregion") ? pais.get("subregion").getAsString() : "";

        // Población y área
        long poblacion = pais.has("population") ? pais.get("population").getAsLong() : 0;
        double area = pais.has("area") ? pais.get("area").getAsDouble() : 0;

        // Idiomas
        StringJoiner idiomas = new StringJoiner(", ");
        if (pais.has("languages") && pais.get("languages").isJsonObject()) {
            JsonObject listaIdiomas = pais.getAsJsonObject("languages");
            for (Map.Entry<String, JsonElement> idioma : listaIdiomas.entrySet()) {
                idiomas.add(idioma.getValue().getAsString());
            }
        }

        // Moneda (primera encontrada)
        String moneda = "";
        if (pais.has("currencies") && pais.get("currencies").isJsonObject()) {
            JsonObject monedas = pais.getAsJsonObject("currencies");
            for (Map.Entry<String, JsonElement> entrada : monedas.entrySet()) {
                JsonObject datosMoneda = entrada.getValue().getAsJsonObject();
                moneda = datosMoneda.get("name").getAsString();
                break;
            }
        }

        // Código
        String codigo = pais.has("cca3") ? pais.get("cca3").getAsString() : "";

        // Bandera
        String bandera = "";
        if (pais.has("flags") && pais.getAsJsonObject("flags").has("png")) {
            bandera = pais.getAsJsonObject("flags").get("png").getAsString();
        }

        return new CountryDTO(nombreComun, nombreOficial, capital, region, subregion,
                poblacion, area, idiomas.toString(), moneda, codigo, bandera);
    }
}