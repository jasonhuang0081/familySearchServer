package handles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.EventAllRequest;
import results.EventAllResult;
import services.EventAllService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class EventAllHandler extends Handlers implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {

        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    EventAllRequest request = new EventAllRequest(authToken);
                    EventAllService service = new EventAllService();
                    EventAllResult result = service.eventAll(request);
                    if (result.getData() == null) {
                        this.setMessage(result.getMessage());
                        writeString(this.convertMessage(), respBody);
                        exchange.getResponseBody().close();
                    } else {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String out = gson.toJson(result);
                        writeString(out, respBody);
                        exchange.getResponseBody().close();
                    }
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    this.setMessage("No Auth token");
                    writeString(this.convertMessage(), respBody);
                    exchange.getResponseBody().close();
                }
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                this.setMessage("Invalid request");
                writeString(this.convertMessage(), respBody);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
