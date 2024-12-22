package http.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;

public abstract class Handler {
    protected static String getPathParam(URI uri, int position) {
        String[] parts = uri.getPath().split("/");

        if (parts.length > position) {
            return parts[position];
        }

        return "";
    }

    protected static void processOK(HttpExchange t)  throws IOException {
        t.sendResponseHeaders(200, 0);

        OutputStream os = t.getResponseBody();
        os.close();
    }

    protected static void processOK(HttpExchange t, String responseString)  throws IOException {
        byte[] response = responseString.getBytes(StandardCharsets.UTF_8);

        t.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        t.sendResponseHeaders(200, response.length);

        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();
    }

    protected static void processInvalidRequest(HttpExchange t)  throws IOException {
        t.sendResponseHeaders(400, 0);

        OutputStream os = t.getResponseBody();
        os.close();
    }

    protected static String readBody(HttpExchange t) throws IOException {
        InputStreamReader isr =  new InputStreamReader(t.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);

        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }

        br.close();
        isr.close();

        return buf.toString();
    }
}
