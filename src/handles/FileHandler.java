package handles;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;


public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String urlPath = exchange.getRequestURI().toString();
        if (urlPath == null || urlPath.equals("/")) {
            urlPath = "index.html";
        }
        String filePath = "source" + File.separator + "web" + File.separator + urlPath;
        String sendBackPath;
        File file = new File(filePath);
        if (!file.isFile()) {
            sendBackPath = "source" + File.separator + "web" + File.separator + "HTML"
                    + File.separator + "404.html";
        } else {
            sendBackPath = filePath;
        }
        try {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            File reFile = new File(sendBackPath);
            OutputStream respBody = exchange.getResponseBody();
            Files.copy(reFile.toPath(), respBody);
            respBody.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("can't access web page file");
        }

    }
}
