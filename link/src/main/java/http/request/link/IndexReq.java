package http.request.link;

import java.util.UUID;

import org.json.JSONObject;

import http.request.Request;

public class IndexReq extends Request {
    private UUID user;

    public IndexReq(String body) {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        

        user = parseUUID(obj,"user");
    }

    @Override
    public boolean isValid() {
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

    public UUID getUser() {
        return user;
    }
}
