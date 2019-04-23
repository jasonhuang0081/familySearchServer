package handles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.RegisterRequest;
import results.RegisterResult;
import services.RegisterService;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler extends Handlers implements HttpHandler {
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
            try {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                OutputStream respBody = exchange.getResponseBody();
                this.setMessage("Internal error");
                writeString(this.convertMessage(), respBody);
                exchange.getResponseBody().close();
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("sendResponseHeader error");
            }
        }
    }

    private void checkValidInput(HttpExchange exchange) throws IOException {
        InputStream reqBody = exchange.getRequestBody();
        OutputStream respBody = exchange.getResponseBody();
        Reader reader = new InputStreamReader(reqBody);
        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(reader, RegisterRequest.class);
        char gender = request.getGender().charAt(0);
        if (request.getFirstName() == null || request.getPassword() == null || request.getEmail() == null
                || request.getUserName() == null || request.getLastName() == null ||
                !(gender == 'f' || gender == 'm') || request.getGender().length() != 1) {
            this.setMessage("Request property missing or has invalid value");
            writeString(this.convertMessage(), respBody);
            exchange.getResponseBody().close();
        } else {
            checkDuplicated(request, respBody, exchange);
        }
    }

    private void checkDuplicated(RegisterRequest request, OutputStream respBody, HttpExchange exchange) throws IOException {
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);
        if (result == null) {
            this.setMessage("Username already taken by another user");
            writeString(this.convertMessage(), respBody);
            exchange.getResponseBody().close();
        } else            // if there is no duplicated, we can print the result to web
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String out = gson.toJson(result);
            writeString(out, respBody);
            exchange.getResponseBody().close();
        }
    }

}
