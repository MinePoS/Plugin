package net.minepos.plugin.websocket;

import com.google.inject.Inject;
import net.minepos.plugin.core.storage.yaml.MFile;
import org.bukkit.Bukkit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

/**
 * ------------------------------
 * Copyright (c) ThatGamerBlue 2018
 * https://www.thatgamerblue.com
 * Project: MinePoS
 * ------------------------------
 */
public class WSClient extends WebSocketClient {
	
	@Inject private MFile mFile;
	private JSONParser jsonParser = new JSONParser();
	
	@Inject
	public WSClient(String serverUri) throws URISyntaxException {
		super(new URI(serverUri));
	}
	
	@Override
	public void onOpen(ServerHandshake handshakedata) {
	
	}
	
	@Override
	public void onMessage(String message) {
		try {
			JSONObject outer = (JSONObject) jsonParser.parse(message);
			if (!outer.containsKey("type")) {
				// invalid message, discard silently
				return;
			}
			if (!(outer.get("type") instanceof String)) {
				// invalid message, discard silently
				return;
			}
			switch ((String) outer.get("type")) {
				case "auth-fragment":
					handleAuthFragment(outer);
					break;
				case "donation":
					handleDonation(outer);
					break;
				default:
					break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onClose(int code, String reason, boolean remote) {
	
	}
	
	@Override
	public void onError(Exception ex) {
	
	}
	
	private void handleAuthFragment(JSONObject outer) {
		JSONObject response = new JSONObject();
		if (!outer.containsKey("stage")) {
			// invalid message, discard silently
			return;
		}
		if (!(outer.get("stage") instanceof Integer)) {
			// you get the point
			return;
		}
		int stage = (int) outer.get("stage");
		if (stage == 1) {
			response.put("type", "auth-fragment");
			response.put("stage", 1);
			response.put("server-name", mFile.getFileConfiguration("config").getString("server-name"));
			response.put("api-key", mFile.getFileConfiguration("config").getString("API-KEY"));
			send(response.toJSONString());
			return;
		}
		if (stage == 2) {
			if (!outer.containsKey("state")) {
				return;
			}
			if (!(outer.get("state") instanceof Integer)) {
				return;
			}
			int state = (int) outer.get("state");
			if(state == 0) {
				// invalid api key
				return;
			}
			if(state == 1) {
				response.put("type", "auth-fragment");
				response.put("stage", 2);
				response.put("state", 1);
				send(response.toJSONString());
			}
			if(state == 2) {
				// server error
				return;
			}
		}
	}
	
	private void handleDonation(JSONObject outer) {
		JSONObject response = new JSONObject();
		if(!outer.containsKey("commands")) {
			response.put("type", "donation");
			response.put("state", 0);
			response.put("message", "No commands sent");
			send(response.toJSONString());
			return;
		}
		if(!(outer.get("commands") instanceof JSONArray)) {
			response.put("type", "donation");
			response.put("state", 0);
			response.put("message", "Commands object not an array");
			send(response.toJSONString());
			return;
		}
		JSONArray commands = (JSONArray) outer.get("commands");
		Iterator iter = commands.iterator();
		while(iter.hasNext()) {
			Object obj = iter.next();
			if(!(obj instanceof String)) {
				continue;
			}
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), (String) obj);
		}
		response.put("type", "donation");
		response.put("state", 1);
		send(response.toJSONString());
	}
	
}
