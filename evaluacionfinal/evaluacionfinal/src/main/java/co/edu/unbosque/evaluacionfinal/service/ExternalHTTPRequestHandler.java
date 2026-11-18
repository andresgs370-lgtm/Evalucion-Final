package co.edu.unbosque.evaluacionfinal.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import co.edu.unbosque.evaluacionfinal.dto.FactDTO;

@Component
public class ExternalHTTPRequestHandler {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final Gson GSON = new GsonBuilder().create();
    private static final Gson GSON_PRETTY = new GsonBuilder().setLenient().setPrettyPrinting().create();

  
    private static final String API_KEY_ENV = "EXTERNAL_API_KEY";

    public String doGet(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = null;

        try {
            response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        if (response == null || response.statusCode() != 200) {
            System.err.println("Fallo al consumir la API o la respuesta fue nula.");
            return null;
        }

        return response.body();
    }

    // ---------- Métodos estáticos (versión de tu rama) ----------
    private static HttpResponse<String> send(HttpRequest request) {
        try {
            return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new IllegalStateException("Error de red al llamar a " + request.uri().getHost(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Petición interrumpida", e);
        }
    }

    private static HttpRequest.Builder baseRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .header("Accept", "application/json");
    }

    private static void validarExito(HttpResponse<String> response) {
        int status = response.statusCode();
        if (status < 200 || status >= 300) {
            throw new IllegalStateException("La API externa respondió con código " + status);
        }
    }

    public static String doGetAndParse(String url) {
        HttpResponse<String> response = send(baseRequest(url).GET().build());
        validarExito(response);
        return prettyPrintUsingGson(response.body());
    }

    public static String doGetWithHeaders(String url) {
        String apiKey = System.getenv(API_KEY_ENV);
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Falta la variable de entorno " + API_KEY_ENV);
        }
        HttpResponse<String> response = send(baseRequest(url).header("X-Apikey", apiKey).GET().build());
        validarExito(response);
        return prettyPrintUsingGson(response.body());
    }

    public static FactDTO doGetAndConvertToDto(String url) {
        HttpResponse<String> response = send(baseRequest(url).GET().build());
        validarExito(response);
        FactDTO fact = GSON.fromJson(response.body(), FactDTO.class);
        fact.setStatuscode(response.statusCode());
        return fact;
    }

    public static List<FactDTO> doGetAndConvertToDtoList(String url) {
        HttpResponse<String> response = send(baseRequest(url).GET().build());
        validarExito(response);
        Type tipoLista = new TypeToken<List<FactDTO>>() {
        }.getType();
        return GSON.fromJson(response.body(), tipoLista);
    }

    public static String doPost(String url, String json) {
        HttpResponse<String> response = send(baseRequest(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build());
        validarExito(response);
        return prettyPrintUsingGson(response.body());