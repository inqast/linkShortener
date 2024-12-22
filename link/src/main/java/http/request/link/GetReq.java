package http.request.link;

import org.json.JSONObject;

import http.request.Request;

public class GetReq extends Request {
    private String hash;

    public GetReq(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean isValid() {
        if (hash.isEmpty()) {
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
}
