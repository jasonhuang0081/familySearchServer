package mainServer;

import com.sun.net.httpserver.HttpServer;
import handles.*;

import java.io.IOException;
import java.net.InetSocketAddress;

class MainServer {
    private static final int MAX_WAITING_CONNECTIONS = 5;

    public static void main(String[] args) {
        int portNumber = Integer.parseInt(args[0]);
        new MainServer().run(portNumber);
    }

    private void run(int portNumber) {
        System.out.println("Initializing HTTP Server");
        HttpServer server;
        try {
            InetSocketAddress serverAddress = new InetSocketAddress(portNumber);
            server = HttpServer.create(serverAddress, MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);
        System.out.println("Creating contexts");
        server.createContext("/clear", new ClearHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonAllHandler());
        server.createContext("/person/", new PersonHandler());
        server.createContext("/event", new EventAllHandler());
        server.createContext("/event/", new EventHandler());
        server.createContext("/", new FileHandler());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
        System.out.println("FamilyMapServer listening on port " + portNumber);
    }
}
