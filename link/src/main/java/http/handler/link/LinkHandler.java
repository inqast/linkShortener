package http.handler.link;

import com.sun.net.httpserver.HttpExchange;

import domain.link.Link;
import http.handler.Handler;
import http.request.link.CreateReq;
import http.request.link.DeleteReq;
import http.request.link.GetReq;
import http.request.link.UpdateReq;
import http.resonse.link.CreateResp;
import http.resonse.link.GetResp;
import service.IService;

public class LinkHandler extends Handler {
    private IService service;

    public LinkHandler(IService service) {
        this.service = service;
    }

    @Override
    public String handleMethods(HttpExchange t) throws Exception {
        switch (t.getRequestMethod()) {
            case ("GET"):
                return handleGet(t);
            case ("POST"):
                return handleCreate(t);
            case ("PATCH"):
                return handleUpdate(t);
            case ("DELETE"):
                return handleDelete(t);
            default:
                throw new AssertionError("unknown method");
        }
    }

    private String handleGet(HttpExchange t) throws Exception {
        GetReq req = new GetReq(getPathParam(t.getRequestURI(), 2));
        req.validate();
    
        Link link = service.read(req.getHash(), getCurrentDate());

        GetResp resp = new GetResp(link.getLink());

        return resp.toJSON();
    }

    private String handleCreate(HttpExchange t) throws Exception {
        CreateReq req = new CreateReq(readBody(t));
        req.validate();

        int hash = service.create(req.getLink(), req.getUser(), req.getLimit(), req.getDeadline(), getCurrentDate());

        CreateResp resp = new CreateResp(hash, req.getUser());

        return resp.toJSON();
    }

    private String handleUpdate(HttpExchange t) throws Exception {
        UpdateReq req = new UpdateReq(readBody(t));
        req.validate();

        service.update(req.getHash(), req.getLink(), req.getUser(), req.getLimit(), req.getDeadline(), getCurrentDate());

        return emptyResult;
    }

    private String handleDelete(HttpExchange t) throws Exception {
        DeleteReq req = new DeleteReq(readBody(t));
        req.validate();

        service.delete(req.getHash(), req.getUser());

        return emptyResult;
    }
}