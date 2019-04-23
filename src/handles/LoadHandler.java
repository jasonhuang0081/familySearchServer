package handles;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.LoadRequest;
import results.LoadResult;
import services.LoadService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler extends Handlers implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                InputStream reqBody = exchange.getRequestBody();
                Reader reader = new InputStreamReader(reqBody);
                Gson gson = new Gson();
                LoadRequest request = gson.fromJson(reader, LoadRequest.class);
                LoadService service = new LoadService();
                LoadResult result = service.load(request);
                String out = gson.toJson(result);
                OutputStream respBody = exchange.getResponseBody();
                writeString(out, respBody);
                exchange.getResponseBody().close();
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                this.setMessage("Invalid request");
                writeString(this.convertMessage(), respBody);
                exchange.getResponseBody().close();
            }
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                OutputStream respBody = exchange.getResponseBody();
                this.setMessage("Invalid request data");
                writeString(this.convertMessage(), respBody);
                exchange.getResponseBody().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
