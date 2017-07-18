package blue.happening.unity;

import blue.happening.HappeningClient;
import blue.happening.sdk.HappeningCallback;
import com.unity3d.player.UnityPlayer;
import org.json.JSONException;
import org.json.JSONObject;

import static blue.happening.unity.Main.clientToJson;

public class Callback implements HappeningCallback {

    @Override
    public void onClientAdded(HappeningClient client) {
        UnityPlayer.UnitySendMessage("Happening", "onClientAdded", clientToJson(client).toString());
    }

    @Override
    public void onClientUpdated(HappeningClient client) {
        UnityPlayer.UnitySendMessage("Happening", "onClientUpdated", clientToJson(client).toString());
    }

    @Override
    public void onClientRemoved(HappeningClient client) {
        UnityPlayer.UnitySendMessage("Happening", "onClientRemoved", clientToJson(client).toString());
    }

    @Override
    public void onMessageReceived(byte[] bytes, HappeningClient source) {
        JSONObject res = new JSONObject();
        try {
            res.put("source", clientToJson(source));
            res.put("data", new String(bytes));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        UnityPlayer.UnitySendMessage("Happening", "onMessageReceived", res.toString());
    }

    @Override
    public void onMessageLogged(int type, int action) {
        // Callback to log mesh messages
    }

}
