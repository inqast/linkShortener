package http.request.link;

import java.util.UUID;

import org.json.JSONObject;

import http.request.InvalidRequestException;
import http.request.Request;

public class UpdateReq extends Request {
    private String hash;
    private String link;
    private UUID user;
    private int limit;

    public UpdateReq(String body) {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        
        hash = parseString(obj, "hash");
        link = parseString(obj, "link");
        limit = parseInt(obj,"limit");
        user = parseUUID(obj,"user");
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (link.isEmpty()) {
            throw new InvalidRequestException("invalid link");
        }

        if (user == null) {
            throw new InvalidRequestException("invalid link");
        }
    }

    @Override
    public String toJSON() {
        JSONObject obj = new JSONObject(this);

        return obj.toString();
    }

    public String getHash() {
        return hash;
    }

    public String getLink() {
        return link;
    }

    public int getLimit() {
        return limit;
    }

    public UUID getUser() {
        return user;
    }
}
