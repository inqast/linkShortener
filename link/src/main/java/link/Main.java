package link;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import http.handler.dock.DocsHandler;
import http.handler.link.IndexHandler;
import http.handler.link.LinkHandler;


public class Main {
    public static void main(String[] args) {
        HttpServer server;

        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (Exception e) {
            return;
        }

        server.createContext("/docs", new DocsHandler());
        server.createContext("/link", new LinkHandler());
        server.createContext("/links", new IndexHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}