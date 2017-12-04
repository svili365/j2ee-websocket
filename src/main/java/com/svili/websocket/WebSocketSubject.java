package com.svili.websocket;

import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

/***
 * Web Socket 主题(被观察者)
 *
 * @author svili
 *
 */
public class WebSocketSubject extends Observable {

	/** subject键值 */
	private String principal;

	private WebSocketSubject(String principal) {
		this.principal = principal;
	}

	public String getPrincipal() {
		return principal;
	}

	public void notify(String type, String data) {
		super.setChanged();
		JSONObject json = new JSONObject();
		json.put("type", type);
		json.put("data", data);
		super.notifyObservers(json.toJSONString());
	}

	public static class Holder {
		private static ConcurrentHashMap<String, WebSocketSubject> subjects = new ConcurrentHashMap<>();

		public static WebSocketSubject getSubject(String principal) {
			if (subjects.containsKey(principal)) {
				return subjects.get(principal);
			}

			WebSocketSubject subject = new WebSocketSubject(principal);
			subjects.put(principal, subject);
			return subject;
		}
	}

}
