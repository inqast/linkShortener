package http.handler.link;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import http.handler.Handler;
import http.request.link.IndexReq;

public class IndexHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();

        switch (t.getRequestMethod()) {
            case ("GET"):
                IndexReq req = new IndexReq(getPathParam(t.getRequestURI(), 2));
                if (!req.isValid()) {
                    processInvalidRequest(t);

                    return;
                }
    
                processOK(t);
                break;
            default:
                throw new AssertionError();
        }
    }

}
