package handles;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import results.ClearResult;
import services.ClearService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ClearHandler extends Handlers implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                ClearService service = new ClearService();
                ClearResult result = service.clear();
                this.setMessage(result.getMessage());
                OutputStream respBody = exchange.getResponseBody();
                this.writeString(this.convertMessage(), respBody);
                respBody.close();
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
            } catch (IOException ex) {
                System.out.println("sendResponseHeader error");
            }

        }
    }

}
