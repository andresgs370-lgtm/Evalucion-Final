package co.edu.unbosque.evaluacionfinal.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Consume la API de Spoonacular para obtener la URL de la imagen de una fruta o verdura.
 * GET https://api.spoonacular.com/food/ingredients/search?query=apple&number=1&apiKey=KEY
 * Respuesta: "image": "apple.jpg"  ->  https://img.spoonacular.com/ingredients_250x250/apple.jpg
 */
@Service
public class SpoonacularService {

	private static final String URL_BUSQUEDA = "https://api.spoonacular.com/food/ingredients/search";
	private static final String URL_IMAGEN = "https://img.spoonacular.com/ingredients_250x250/";

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(10)).build();

	// Spoonacular está en inglés: traducimos los nombres más comunes
	private static final Map<String, String> TRADUCCION = Map.ofEntries(
			Map.entry("manzana", "apple"), Map.entry("banano", "banana"), Map.entry("banana", "banana"),
			Map.entry("platano", "plantain"), Map.entry("pera", "pear"), Map.entry("uva", "grapes"),
			Map.entry("uvas", "grapes"), Map.entry("fresa", "strawberries"), Map.entry("fresas", "strawberries"),
			Map.entry("naranja", "orange"), Map.entry("mandarina", "mandarin"), Map.entry("limon", "lemon"),
			Map.entry("mango", "mango"), Map.entry("piña", "pineapple"), Map.entry("pina", "pineapple"),
			Map.entry("sandia", "watermelon"), Map.entry("melon", "cantaloupe"), Map.entry("papaya", "papaya"),
			Map.entry("kiwi", "kiwi"), Map.entry("aguacate", "avocado"), Map.entry("cereza", "cherries"),
			Map.entry("coco", "coconut"), Map.entry("durazno", "peach"), Map.entry("mora", "blackberries"),
			Map.entry("zanahoria", "carrot"), Map.entry("tomate", "tomato"), Map.entry("lechuga", "lettuce"),
			Map.entry("cebolla", "onion"), Map.entry("papa", "potato"), Map.entry("brocoli", "broccoli"),
			Map.entry("pepino", "cucumber"), Map.entry("espinaca", "spinach"), Map.entry("ajo", "garlic"),
			Map.entry("pimenton", "red bell pepper"), Map.entry("repollo", "cabbage"), Map.entry("apio", "celery"),
			Map.entry("calabacin", "zucchini"), Map.entry("berenjena", "eggplant"), Map.entry("coliflor", "cauliflower"),
			Map.entry("maiz", "corn"), Map.entry("arveja", "peas"), Map.entry("remolacha", "beets"),
			Map.entry("champiñon", "mushrooms"), Map.entry("ahuyama", "pumpkin"), Map.entry("rabano", "radishes"));

	@Value("${spoonacular.apikey:}")
	private String apiKey;

	public String buscarImagen(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return "";
		}
		String termino = traducir(nombre);
		try {
			if (apiKey == null || apiKey.isBlank()) {
				throw new IllegalStateException("Falta spoonacular.apikey en application.properties");
			}
			String url = URL_BUSQUEDA + "?number=1&query="
					+ URLEncoder.encode(termino, StandardCharsets.UTF_8) + "&apiKey=" + apiKey;
			HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
					.timeout(Duration.ofSeconds(15)).header("Accept", "application/json").build();
			HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				throw new IllegalStateException("Spoonacular respondió con código " + response.statusCode());
			}
			JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
			JsonArray results = json.getAsJsonArray("results");
			if (results != null && results.size() > 0) {
				return URL_IMAGEN + results.get(0).getAsJsonObject().get("image").getAsString();
			}
		} catch (Exception e) {
			System.err.println("Spoonacular no disponible (" + e.getMessage() + "), usando URL directa");
		}
		return URL_IMAGEN + termino.replace(" ", "-") + ".jpg";
	}

	private String traducir(String nombre) {
		String limpio = nombre.trim().toLowerCase()
				.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
		return TRADUCCION.getOrDefault(limpio, limpio);
	}
}