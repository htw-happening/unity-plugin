package blue.happening.unity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import blue.happening.HappeningClient;
import blue.happening.sdk.Happening;
import com.unity3d.player.UnityPlayerActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Main extends UnityPlayerActivity {

    private static Happening happening;
    private static Callback callback;
    private static Context context;

    public Main() {
        happening = new Happening();
        callback = new Callback();
        context = MyApplication.getContext();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        context = MyApplication.getContext();
        happening.register(context, callback);
    }

    @Override
    protected void onDestroy() {
        happening.deregister();
        super.onDestroy();
    }

    /**
     * Get all available devices.
     * @return JSON string: {'id': x, 'name': x}
     */
    public static String getDevices() {
        List<HappeningClient> devices = happening.getDevices();
        JSONObject res = new JSONObject();
        try {
            JSONArray arr = new JSONArray();
            for (HappeningClient device : devices) {
                JSONObject obj = new JSONObject();
                obj.put("id", device.getClientId());
                obj.put("name", device.getClientName());
                arr.put(obj);
            }
            res.put("devices", arr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    /**
     * Send data to a device.
     * @param json JSON string: {'id': x, 'data': x}
     */
    public static void sendData(String json) {
        String id, data;
        JSONObject res = new JSONObject();
        try {
            res.getJSONObject(json);
            id = res.getString("id");
            data = res.getString("data");
            makeToast("JAVA: " + id + " | " + data);
            happening.sendDataTo(id, data.getBytes());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void makeToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
