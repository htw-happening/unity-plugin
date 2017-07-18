using UnityEngine;
using System;
using System.Collections.Generic;
using System.Timers;

public class HappeningPluginExample : MonoBehaviour {

	HappeningPlugin plugin;
	Timer timer;

	void Awake() {
	}

	void OnEnable() {
	}

	void Start() {
		plugin = new HappeningPlugin();
		sendBroadcastMessage();
		InvokeRepeating("sendBroadcastMessage", 0, 2.5f);
	}

	void Update() {
	}

	public void sendBroadcastMessage() {
		print("Broadcasting...");
		HappeningClients clients = plugin.getClients();
		foreach (HappeningClients.HappeningClient client in clients.clients) {
			print(client.uuid);
			plugin.sendData(client, "HALLO!");
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

	void onMessageReceived(String json) {
		Package pkg = JsonUtility.FromJson<Package>(json);
		plugin.toast(pkg.data + " from " + pkg.source.uuid);
	}

}