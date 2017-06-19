using UnityEngine;
using System;
using System.Collections.Generic;

public class HappeningPlugin {

    private AndroidJavaClass Happening;

    public HappeningPlugin() {
        Happening = new AndroidJavaClass("blue.happening.unity.Main");
    }

	private string getDevicesJSON() {
		return Happening.CallStatic<string>("getDevices");
	}

	public Devices getDevices() {
		string json = getDevicesJSON();
		Toast(json);
		return JsonUtility.FromJson<Devices>(json);
	}

	public void sendData(String id, String data) {
		Package pkg = new Package(id, data);
		string json = JsonUtility.ToJson(pkg);
		Toast(id + " | " + data);
		Happening.CallStatic("sendData", json);
	}

    public void onClientAdded(String client) {
        Toast("onClientAdded");
    }

    public void onClientUpdated(String client) {
        Toast("onClientUpdated");
    }

    public void onClientRemoved(String client) {
        Toast("onClientRemoved");
    }

    public void onParcelQueued(String client) {
        Toast("onParcelQueued");
    }

    public void onMessageReceived(String json) {
		Package pkg = JsonUtility.FromJson<Package>(json);
		Toast("onMessageReceived: " + pkg.data + " from " + pkg.id);
    }

    public void Toast(String msg) {
        Happening.CallStatic("makeToast", msg);
    }

}

[System.Serializable]
public struct Devices {

    public List<Device> devices;

    [System.Serializable]
    public struct Device {
        public string id;
        public string name;
    }

}

[System.Serializable]
public struct Package {

	public string id;
	public string data;

	public Package(string id, string data) {
		this.id = id;
		this.data = data;
	}
}