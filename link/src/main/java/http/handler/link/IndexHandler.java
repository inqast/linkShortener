package http.handler.link;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import domain.link.Link;
import http.handler.Handler;
import http.request.link.IndexReq;
import http.resonse.link.IndexResp;
import service.IService;

public class IndexHandler extends Handler implements HttpHandler {
     private IService service;

    public IndexHandler(IService service) {
        this.service = service;
    }

    @Override
    public String handleMethods(HttpExchange t) throws Exception {
        switch (t.getRequestMethod()) {
            case ("GET"):
                return handleIndex(t);
            default:
                throw new AssertionError("unknown method");
        }
    }

    private String handleIndex(HttpExchange t) throws Exception {
        IndexReq req = new IndexReq(getPathParam(t.getRequestURI(), 2));
        req.validate();

        Link[] links = service.index(req.getUser());

        IndexResp resp = new IndexResp(links);

        return resp.toJSON();
    }
}
