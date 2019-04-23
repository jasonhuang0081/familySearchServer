package handles;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.FillRequest;
import results.FillResult;
import services.FillService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler extends Handlers implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                String urlPath = exchange.getRequestURI().toString();
                String[] split = urlPath.split("/");
                String userName = null;
                int generation = 4;
                for (int i = 0; i < split.length; i++) {
                    if (i == 2) {
                        userName = split[i];
                    }
                    if (i == 3 && split[i].length() == 1) {
                        generation = Integer.parseInt(split[i]);
                    }
                    if (i == 3 && split[i].length() != 1) {
                        this.setMessage("Invalid number of generations");
                        writeString(this.convertMessage(), respBody);
                        exchange.getResponseBody().close();
                    }
                }
                FillRequest request = new FillRequest(userName, generation);
                FillService service = new FillService();
                FillResult result = service.fill(request);
                this.setMessage(result.getMessage());
                writeString(this.convertMessage(), respBody);
                exchange.getResponseBody().close();
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
