package cmd;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpServer;

import config.json.Config;
import hash.Hash;
import http.handler.dock.DocsHandler;
import http.handler.link.IndexHandler;
import http.handler.link.LinkHandler;
import repo.link.LinkRepo;
import service.Service;


public class Main {
    public static void main(String[] args) {
        HttpServer server;

        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (Exception e) {
            return;
        }

        Config cfg;
        try {
            byte[] configContent = Files.readAllBytes(Paths.get("config.json"));
            cfg = new Config(configContent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
            return;
        }
        
        LinkRepo repo = new LinkRepo();
        Hash hasher = new Hash();
        Service service = new Service(repo, hasher, cfg);

        server.createContext("/docs", new DocsHandler());
        server.createContext("/link", new LinkHandler(service));
        server.createContext("/links", new IndexHandler(service));

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}