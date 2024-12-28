package http.request.link;

import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;

import http.request.InvalidRequestException;
import http.request.Keys;
import http.request.Request;

public class UpdateReq extends Request {
    private int hash;
    private String link;
    private UUID user;
    private int limit;
    private Date deadline;

    public UpdateReq(String body) throws InvalidRequestException {
        JSONObject obj;
        try {
            obj = new JSONObject(body);
        } catch (Exception e) {
            return;
        }
        
        hash = parseInt(obj, Keys.HASH);
        link = parseString(obj, Keys.LINK);
        limit = parseInt(obj, Keys.LIMIT);
        user = parseUUID(obj,Keys.USER);
        deadline = parseDate(obj, Keys.DEADLINE);
    }

    @Override
    public void validate() throws InvalidRequestException {
        if (hash == 0) {
            throw new InvalidRequestException("invalid hash");
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

    public int getHash() {
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

    public Date getDeadline() {
        return deadline;
    }
}
