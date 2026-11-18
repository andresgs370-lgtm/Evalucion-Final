 	package co.edu.unbosque.evaluacionfinal.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.stereotype.Component;

@Component 
public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10))
            .build();

 
	public String doGet(String url) {
	    HttpRequest request = HttpRequest.newBuilder()
	            .GET()
	            .uri(URI.create(url))
	            .header("Content-Type", "application/json")
	            .build();

	    HttpResponse<String> response = null;

	    try {
	        response = HTTP_CLIENT.send(
	                request,
	                HttpResponse.BodyHandlers.ofString()
	        );
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
}