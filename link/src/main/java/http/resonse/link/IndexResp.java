package http.resonse.link;

import org.json.JSONObject;

import domain.link.Link;
import http.resonse.Response;

public class IndexResp extends Response {
    private Link[] links;
    

    public IndexResp(Link[] links) {
        this.links = links;
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public Link[] getLinks() {
        return links;
    }
}
