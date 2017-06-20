using UnityEngine;
using System;
using System.Collections.Generic;

public class HappeningPluginExample : MonoBehaviour {

	HappeningPlugin Plugin;

	void Awake() {
	}

	void OnEnable() {
	}

	void Start() {
		Plugin = new HappeningPlugin();
		sendBroadcastMessage();
	}

	void Update() {
	}

	public void sendBroadcastMessage() {
		print("Broadcasting...");
		HappeningClients clients = Plugin.getClients();
		foreach (HappeningClients.HappeningClient client in clients.clients) {
			print(client.uuid);
			Plugin.sendData(client, "HALLO!");
		}
	}

	// Callbacks

	void onClientAdded(String json) {
		HappeningClients.HappeningClient client = JsonUtility.FromJson<HappeningClients.HappeningClient>(json);
		print("onClientAdded: " + client);
	}

	void onClientUpdated(String json) {
		HappeningClients.HappeningClient client = JsonUtility.FromJson<HappeningClients.HappeningClient>(json);
		print("onClientUpdated: " + client);
	}

	void onClientRemoved(String json) {
		HappeningClients.HappeningClient client = JsonUtility.FromJson<HappeningClients.HappeningClient>(json);
		print("onClientRemoved: " + client);
	}

	void onParcelQueued(String json) {
		HappeningClients.HappeningClient client = JsonUtility.FromJson<HappeningClients.HappeningClient>(json);
		print("onParcelQueued: " + client);
	}

	void onMessageReceived(String json) {
		Package pkg = JsonUtility.FromJson<Package>(json);
		Plugin.Toast(pkg.data + " from " + pkg.source.uuid);
	}

}