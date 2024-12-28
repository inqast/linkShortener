package http.request;

import java.util.UUID;

import org.json.JSONObject;

public abstract class Request {
    public abstract void validate() throws InvalidRequestException;
    public abstract String toJSON();

    protected String parseString(JSONObject obj, String key) {
        try {
            return obj.getString(key);
        } catch (Exception e) {
            return "";
        }
    }

    protected int parseInt(JSONObject obj, String key) {
        try {
            return obj.getInt(key);
        } catch (Exception e) {
            return 0;
        }
    }

    protected UUID parseUUID(JSONObject obj, String key) {
        try {
            String s = obj.getString(key);
            
            return UUID.fromString(s);
        } catch (Exception e) {
            return UUID.randomUUID();
        }
    }
}
