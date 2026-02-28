import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

class HttpHandler {
	public String getResponse(String url) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();

		try {
			CompletableFuture<HttpResponse<String>> future = client.sendAsync(request,
					HttpResponse.BodyHandlers.ofString());
			String response = future.thenApply(HttpResponse::body)
					.join();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
}
