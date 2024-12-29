package cmd;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

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
        Config cfg;
        Connection con;

        try {
            byte[] configContent = Files.readAllBytes(Paths.get("link/config.json"));
            cfg = new Config(configContent);

            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String passwd = System.getenv("DB_PASSWD");

            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, passwd);

            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
            return;
        }
        
        LinkRepo repo = new LinkRepo(con);
        Hash hasher = new Hash();
        Service service = new Service(repo, hasher, cfg);

        server.createContext("/docs", new DocsHandler());
        server.createContext("/link", new LinkHandler(service));
        server.createContext("/links", new IndexHandler(service));

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}