package blue.happening.unity;

import blue.happening.sdk.HappeningCallback;
import com.unity3d.player.UnityPlayer;
import org.json.JSONException;
import org.json.JSONObject;

import static blue.happening.unity.Main.makeToast;

public class Callback implements HappeningCallback {

    @Override
    public void onClientAdded(String s) {
        UnityPlayer.UnitySendMessage("Main", "onClientAdded", s);
    }

    @Override
    public void onClientUpdated(String s) {
        UnityPlayer.UnitySendMessage("Main", "onClientUpdated", s);
    }

    @Override
    public void onClientRemoved(String s) {
        UnityPlayer.UnitySendMessage("Main", "onClientRemoved", s);
    }

    @Override
    public void onParcelQueued(long l) {
        UnityPlayer.UnitySendMessage("Main", "onParcelQueued", String.valueOf(l));
    }

    @Override
    public void onMessageReceived(byte[] bytes, int id) {
        try {
            makeToast("Callback: " + id + " | " + new JSONObject(new String(bytes)).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject res = new JSONObject();
        try {
            res.put("id", id);
            res.put("data", new String(bytes));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        UnityPlayer.UnitySendMessage("Main", "onMessageReceived", res.toString());
    }

}
