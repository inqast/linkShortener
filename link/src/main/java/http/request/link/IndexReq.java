package http.request.link;

import java.util.UUID;

import org.json.JSONObject;

import http.request.InvalidRequestException;
import http.request.Keys;
import http.request.Request;

public class IndexReq extends Request {
    private UUID user;

    public IndexReq(String body) throws InvalidRequestException {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        

        user = parseUUID(obj, Keys.USER);
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (user == null) {
            throw new InvalidRequestException("invalid link");
        }
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
