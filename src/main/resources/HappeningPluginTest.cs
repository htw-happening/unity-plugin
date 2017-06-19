using UnityEngine;
using System;
using System.Collections.Generic;

public class HappeningPluginTest : MonoBehaviour {

	HappeningPlugin Plugin;

	void Awake() {
		print("Awake!");
		Plugin = new HappeningPlugin();
	}

	void OnEnable() {
		print("OnEnable!");
	}

	void Start() {
		print("Start!");
		Devices devices = Plugin.getDevices();
		foreach (Devices.Device device in devices.devices) {
			Plugin.Toast(device.id);
			Plugin.sendData(device.id, "HALLO!");
		}
	}

	void Update() {	}

}