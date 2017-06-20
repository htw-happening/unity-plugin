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

    private static Happening happening = new Happening();
    private static Callback callback = new Callback();
    private static Context context;

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
     * Get available devices.
     * @return JSON String: {'clients': [...]}
     */
    public static String getClients() {
        List<HappeningClient> clients = happening.getClients();
        JSONObject res = new JSONObject();
        try {
            JSONArray arr = new JSONArray();
            for (HappeningClient client : clients) {
                arr.put(clientToJson(client));
            }
            res.put("clients", arr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    /**
     * Send data to a device.
     * @param json JSON String: {'source': x, 'data': x}
     */
    public static void sendData(String json) {
        String source, data;
        try {
            JSONObject res = new JSONObject(json);
            source = res.getString("source");
            data = res.getString("data");
            HappeningClient client = clientFromJson(source);
            if (client != null) {
                happening.sendMessage(data.getBytes(), client);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get json object of a client
     * @param client Happening Client
     * @return JSONObject: {'uuid': x, 'name': x}
     */
    static JSONObject clientToJson(HappeningClient client) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("uuid", client.getUuid());
            obj.put("name", client.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    static HappeningClient clientFromJson(String json) {
        String uuid, name;
        try {
            JSONObject obj = new JSONObject(json);
            uuid = obj.getString("uuid");
            name = obj.getString("name");
            return new HappeningClient(uuid, name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void makeToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
