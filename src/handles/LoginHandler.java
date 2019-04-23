package handles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.LoginRequest;
import results.LoginResult;
import services.LoginService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler extends Handlers implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                checkValidInput(exchange);
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

    private void checkValidInput(HttpExchange exchange) throws IOException {
        InputStream reqBody = exchange.getRequestBody();
        OutputStream respBody = exchange.getResponseBody();
        Reader reader = new InputStreamReader(reqBody);
        Gson gson = new Gson();
        LoginRequest request = gson.fromJson(reader, LoginRequest.class);
        if (request.getPassword() == null || request.getUserName() == null) {
            this.setMessage("Request property missing or has invalid value");
            writeString(this.convertMessage(), respBody);
            exchange.getResponseBody().close();
        } else {
            checkAuthentity(request, respBody, exchange);
        }
    }

    private void checkAuthentity(LoginRequest request, OutputStream respBody, HttpExchange exchange) throws IOException {
        LoginService service = new LoginService();
        LoginResult result = service.login(request);
        if (result == null) {
            this.setMessage("Invalid password or User name does not exist");
            writeString(this.convertMessage(), respBody);
            exchange.getResponseBody().close();
        } else            // if matches data base
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String out = gson.toJson(result);
            writeString(out, respBody);
            exchange.getResponseBody().close();
        }
    }

}
