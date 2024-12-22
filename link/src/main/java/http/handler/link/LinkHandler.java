package http.handler.link;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import http.handler.Handler;
import http.request.link.CreateReq;
import http.request.link.DeleteReq;
import http.request.link.GetReq;
import http.request.link.UpdateReq;
import http.resonse.link.CreateResp;
import http.resonse.link.GetResp;

public class LinkHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        switch (t.getRequestMethod()) {
            case ("GET"):
                handleGet(t);
                break;
            case ("POST"):
                handleCreate(t);
                break;
            case ("PATCH"):
                handleUpdate(t);
                break;
            case ("DELETE"):
                handleDelete(t);
                break;
            default:
                throw new AssertionError();
        }
    }

    private void handleGet(HttpExchange t) throws IOException {
        GetReq req = new GetReq(getPathParam(t.getRequestURI(), 2));
        if (!req.isValid()) {
            processInvalidRequest(t);

            return;
        }

        GetResp resp = new GetResp("http://lala.jopa/lalsd");
    
        processOK(t, resp.toJSON());
    }

    private void handleCreate(HttpExchange t) throws IOException {
        CreateReq req = new CreateReq(readBody(t));
        if (!req.isValid()) {
            processInvalidRequest(t);

            return;
        }

        CreateResp resp = new CreateResp("lalala", req.getUser());
    
        processOK(t, resp.toJSON());
    }

    private void handleUpdate(HttpExchange t) throws IOException {
        UpdateReq req = new UpdateReq(readBody(t));
        if (!req.isValid()) {
            processInvalidRequest(t);

            return;
        }

        processOK(t);
    }

    private void handleDelete(HttpExchange t) throws IOException {
        DeleteReq req = new DeleteReq(readBody(t));
        if (!req.isValid()) {
            processInvalidRequest(t);

            return;
        }
        
        processOK(t);
    }
}