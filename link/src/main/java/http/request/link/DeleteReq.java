package http.request.link;

import java.util.UUID;

import org.json.JSONObject;

import http.request.InvalidRequestException;
import http.request.Request;

public class DeleteReq extends Request {
    private String hash;
    private UUID user;

    public DeleteReq(String body) {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        

        hash = parseString(obj, "hash");
        user = parseUUID(obj,"user");
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (hash.isEmpty()) {
            throw new InvalidRequestException("invalid hash");        
        }

        if (user == null) {
            throw new InvalidRequestException("invalid user");        
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

    public UUID getUser() {
        return user;
    }
}
