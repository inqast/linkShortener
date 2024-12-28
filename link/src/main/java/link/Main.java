package link;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import http.handler.dock.DocsHandler;
import http.handler.link.IndexHandler;
import http.handler.link.LinkHandler;
import service.IService;
import service.Service;


public class Main {
    public static void main(String[] args) {
        HttpServer server;

        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (Exception e) {
            return;
        }

        IService service = new Service();

        server.createContext("/docs", new DocsHandler());
        server.createContext("/link", new LinkHandler(service));
        server.createContext("/links", new IndexHandler(service));

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}