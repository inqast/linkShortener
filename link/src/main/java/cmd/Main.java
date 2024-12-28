package cmd;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import hash.Hash;
import http.handler.dock.DocsHandler;
import http.handler.link.IndexHandler;
import http.handler.link.LinkHandler;
import repo.ILinkRepo;
import repo.link.LinkRepo;
import service.IHash;
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

        ILinkRepo repo = new LinkRepo();
        IHash hasher = new Hash();
        IService service = new Service(repo, hasher);

        server.createContext("/docs", new DocsHandler());
        server.createContext("/link", new LinkHandler(service));
        server.createContext("/links", new IndexHandler(service));

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}