package http.request.link;

import java.util.UUID;

import org.json.JSONObject;

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
    public boolean isValid() {
        if (hash.isEmpty()) {
            return false;
        }

        if (user == null) {
            return false;
        }

        return true;
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
