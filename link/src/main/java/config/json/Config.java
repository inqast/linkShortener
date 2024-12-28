package config.json;

import org.json.JSONException;
import org.json.JSONObject;

import config.IConfig;

public class Config implements IConfig {
    private JSONObject cfg;

    public Config(byte[] configData) throws JSONException {
        cfg = new JSONObject(configData);
    }

    @Override
    public int getIntValue(String key) {
        try {
            return cfg.getInt(key);
        } catch (Exception e) {
            return 0;
        }
    }
}
