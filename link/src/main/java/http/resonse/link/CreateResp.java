package http.resonse.link;

import java.util.UUID;

import org.json.JSONObject;

import http.resonse.Response;

public class CreateResp extends Response {
    private String hash;
    private UUID user;

    public CreateResp(String hash, UUID user) {
        this.hash = hash;
        this.user = user;
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
