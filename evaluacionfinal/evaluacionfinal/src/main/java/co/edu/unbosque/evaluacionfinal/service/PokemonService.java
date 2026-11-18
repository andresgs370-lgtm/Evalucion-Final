package co.edu.unbosque.evaluacionfinal.service;

import java.net.URI; 
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.edu.unbosque.evaluacionfinal.dto.PokemonDTO;

@Service
public class PokemonService {

    private static final String URL_API = "https://pokeapi.co/api/v2/pokemon/";

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)).build();

    public PokemonDTO buscarPokemon(String nombre) throws Exception {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar el nombre de un pokémon");
        }

        String nombreCodificado = URLEncoder.encode(nombre.trim().toLowerCase(), StandardCharsets.UTF_8)
                .replace("+", "%20");

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(URL_API + nombreCodificado))
                .timeout(Duration.ofSeconds(15)).header("Accept", "application/json").build();

        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            throw new IllegalArgumentException("Pokémon no encontrado: " + nombre);
        }

        if (response.statusCode() != 200) {
            throw new Exception("La PokeAPI respondió con código " + response.statusCode());
        }

        JsonObject pokemon = JsonParser.parseString(response.body()).getAsJsonObject();

        int id = pokemon.get("id").getAsInt();
        String nombrePokemon = pokemon.get("name").getAsString();

        double altura = pokemon.get("height").getAsDouble() / 10;
        double peso = pokemon.get("weight").getAsDouble() / 10;

        int experienciaBase = (pokemon.has("base_experience") && !pokemon.get("base_experience").isJsonNull())
                ? pokemon.get("base_experience").getAsInt()
                : 0;

        List<String> tipos = new ArrayList<>();
        JsonArray arregloTipos = pokemon.getAsJsonArray("types");
        for (JsonElement e : arregloTipos) {
            tipos.add(e.getAsJsonObject().getAsJsonObject("type").get("name").getAsString());
        }

        List<String> habilidades = new ArrayList<>();
        JsonArray arregloHabilidades = pokemon.getAsJsonArray("abilities");
        for (JsonElement e : arregloHabilidades) {
            habilidades.add(e.getAsJsonObject().getAsJsonObject("ability").get("name").getAsString());
        }

        Map<String, Integer> estadisticas = new LinkedHashMap<>();
        JsonArray arregloStats = pokemon.getAsJsonArray("stats");
        for (JsonElement e : arregloStats) {
            JsonObject stat = e.getAsJsonObject();
            estadisticas.put(stat.getAsJsonObject("stat").get("name").getAsString(),
                    stat.get("base_stat").getAsInt());
        }

        String imagen = "";
        JsonObject sprites = pokemon.getAsJsonObject("sprites");

        if (sprites.has("other") && sprites.get("other").isJsonObject()
                && sprites.getAsJsonObject("other").has("official-artwork")
                && sprites.getAsJsonObject("other").get("official-artwork").isJsonObject()) {

            JsonObject artwork = sprites.getAsJsonObject("other").getAsJsonObject("official-artwork");

            if (artwork.has("front_default") && !artwork.get("front_default").isJsonNull()) {
                imagen = artwork.get("front_default").getAsString();
            }
        }

        if (imagen.isEmpty() && sprites.has("front_default") && !sprites.get("front_default").isJsonNull()) {
            imagen = sprites.get("front_default").getAsString();
        }

        return new PokemonDTO(id, nombrePokemon, altura, peso, experienciaBase, tipos, habilidades, estadisticas,
                imagen);
    }
}
