package co.edu.unbosque.evaluacionfinal.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.edu.unbosque.evaluacionfinal.dto.PokemonDTO;

@Service
public class PokemonService {

    private static final String BASE = "https://pokeapi.co/api/v2";
    private static final String URL_POKEMON = BASE + "/pokemon/";
    private static final String URL_TIPO = BASE + "/type/";
    private static final int TOTAL_POKEMON = 1025;   
    private static final int ID_MAXIMO_BASE = 10000; 
    private static final int TOP_FUERTES = 50;


    private static final Map<Integer, String> TIPOS = Map.ofEntries(
            Map.entry(2, "water"), Map.entry(3, "fire"), Map.entry(4, "grass"),
            Map.entry(5, "electric"), Map.entry(6, "rock"), Map.entry(7, "ground"),
            Map.entry(8, "normal"), Map.entry(9, "fighting"), Map.entry(10, "dark"),
            Map.entry(11, "steel"), Map.entry(12, "psychic"), Map.entry(13, "ghost"),
            Map.entry(14, "bug"), Map.entry(15, "poison"), Map.entry(16, "flying"),
            Map.entry(17, "fairy"), Map.entry(18, "ice"), Map.entry(19, "dragon"));

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)).build();

      private static final ExecutorService EJECUTOR = Executors.newFixedThreadPool(8, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    private final Map<Integer, List<PokemonDTO>> cache = new ConcurrentHashMap<>();

     public PokemonDTO buscarPokemon(String nombre) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar el nombre de un pokémon");
        }
        String codificado = URLEncoder.encode(nombre.trim().toLowerCase(), StandardCharsets.UTF_8)
                .replace("+", "%20");
        JsonObject json = obtenerJson(URL_POKEMON + codificado, "Pokémon no encontrado: " + nombre);
        return convertir(json);
    }

     public List<PokemonDTO> buscarPorFiltro(int filtro) throws Exception {
        if (filtro != 1 && !TIPOS.containsKey(filtro)) {
            throw new IllegalArgumentException("Filtro inválido. Use un valor entre 1 y 19");
        }

        List<PokemonDTO> enCache = cache.get(filtro);
        if (enCache != null) {
            return enCache;
        }

        List<PokemonDTO> resultado;
        if (filtro == 1) {
            resultado = descargar(urlsDeTodos()).stream()
                    .sorted(Comparator.comparingInt(this::sumaEstadisticas).reversed())
                    .limit(TOP_FUERTES)
                    .toList();
        } else {
            resultado = descargar(urlsPorTipo(TIPOS.get(filtro))).stream()
                    .sorted(Comparator.comparingInt(PokemonDTO::getId))
                    .toList();
        }

        cache.put(filtro, resultado);
        return resultado;
    }

    private List<String> urlsPorTipo(String tipo) throws Exception {
        JsonObject json = obtenerJson(URL_TIPO + tipo, "Tipo no encontrado: " + tipo);
        List<String> urls = new ArrayList<>();
        for (JsonElement e : json.getAsJsonArray("pokemon")) {
            String url = e.getAsJsonObject().getAsJsonObject("pokemon").get("url").getAsString();
            if (idDesdeUrl(url) < ID_MAXIMO_BASE) {
                urls.add(url);
            }
        }
        return urls;
    }

    private List<String> urlsDeTodos() throws Exception {
        JsonObject json = obtenerJson(URL_POKEMON + "?limit=" + TOTAL_POKEMON, "Listado no disponible");
        List<String> urls = new ArrayList<>();
        for (JsonElement e : json.getAsJsonArray("results")) {
            urls.add(e.getAsJsonObject().get("url").getAsString());
        }
        return urls;
    }

    private int idDesdeUrl(String url) {
        String[] partes = url.split("/");
        return Integer.parseInt(partes[partes.length - 1]);
    }

    private List<PokemonDTO> descargar(List<String> urls) {
        List<CompletableFuture<PokemonDTO>> futuros = urls.stream()
                .map(url -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return convertir(obtenerJson(url, "Pokémon no encontrado"));
                    } catch (Exception e) {
                        throw new CompletionException(e);
                    }
                }, EJECUTOR))
                .toList();

        return futuros.stream().map(CompletableFuture::join).toList();
    }

     private JsonObject obtenerJson(String url, String mensaje404) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
                .timeout(Duration.ofSeconds(15)).header("Accept", "application/json").build();

        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            throw new IllegalArgumentException(mensaje404);
        }
        if (response.statusCode() != 200) {
            throw new Exception("La PokeAPI respondió con código " + response.statusCode());
        }
        return JsonParser.parseString(response.body()).getAsJsonObject();
    }

     private PokemonDTO convertir(JsonObject pokemon) {
        int id = pokemon.get("id").getAsInt();
        String nombre = pokemon.get("name").getAsString();
        double altura = pokemon.get("height").getAsDouble() / 10;
        double peso = pokemon.get("weight").getAsDouble() / 10;

        int experienciaBase = (pokemon.has("base_experience") && !pokemon.get("base_experience").isJsonNull())
                ? pokemon.get("base_experience").getAsInt()
                : 0;

        List<String> tipos = new ArrayList<>();
        for (JsonElement e : pokemon.getAsJsonArray("types")) {
            tipos.add(e.getAsJsonObject().getAsJsonObject("type").get("name").getAsString());
        }

        List<String> habilidades = new ArrayList<>();
        for (JsonElement e : pokemon.getAsJsonArray("abilities")) {
            habilidades.add(e.getAsJsonObject().getAsJsonObject("ability").get("name").getAsString());
        }

        Map<String, Integer> estadisticas = new LinkedHashMap<>();
        for (JsonElement e : pokemon.getAsJsonArray("stats")) {
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

        return new PokemonDTO(id, generacionPorId(id), nombre, altura, peso, experienciaBase, tipos,
                habilidades, estadisticas, imagen);
    }

    private String generacionPorId(int id) {
        if (id <= 151) return "Generación I";
        if (id <= 251) return "Generación II";
        if (id <= 386) return "Generación III";
        if (id <= 493) return "Generación IV";
        if (id <= 649) return "Generación V";
        if (id <= 721) return "Generación VI";
        if (id <= 809) return "Generación VII";
        if (id <= 905) return "Generación VIII";
        if (id <= 1025) return "Generación IX";
        return "Desconocida";
    }

    private int sumaEstadisticas(PokemonDTO p) {
        return p.getEstadisticas().values().stream().mapToInt(Integer::intValue).sum();
    }
}