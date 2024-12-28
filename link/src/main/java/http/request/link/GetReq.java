package http.request.link;

import org.json.JSONObject;

import http.request.InvalidRequestException;
import http.request.Request;

public class GetReq extends Request {
    private String hash;

    public GetReq(String hash) {
        this.hash = hash;
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (hash.isEmpty()) {
            throw new InvalidRequestException("invalid hash");        
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
}
