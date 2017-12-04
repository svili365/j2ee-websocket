package com.svili.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/***
 * Web Scoket服务.
 * <ul>
 * URL expression as
 * <li>not use SSL , ws://host/{yourProjectName}/websocket/{principal}
 * <li>if use SSL , wss://host/{yourProjectName}/websocket/{principal}
 * <li>观察者模式
 * 
 * 
 * @author svili
 *
 */
@ServerEndpoint(value = "/websocket/{principal}")
public class DefaultWebSocket {

	/** Web Socket连接建立成功的回调方法 */
	@OnOpen
	public void onOpen(@PathParam("principal") String principal, Session session) {

		// create observer
		WebSocketObserver observer = new WebSocketObserver(session);
		// get subject
		WebSocketSubject subject = WebSocketSubject.Holder.getSubject(principal);
		// register observer into subject
		subject.addObserver(observer);
	}

	/** 服务端收到客户端发来的消息 */
	@OnMessage
	public void onMessage(@PathParam("principal") String principal, String message, Session session) {

		// message EX:{"type":"","data":""}
		JSONObject json = JSON.parseObject(message);
		String type = json.getString("type");
		String data = json.getString("data");

		// 对消息的处理,根据义务自行定义。
		// 这里以打印消息为例
		String log = "receive msg from client,principal : " + principal + ", type : " + type + ", data : " + data;
		System.out.println(log);
	}

	@OnClose
	public void onClose(@PathParam("principal") String principal, Session session) {

		// get subject
		WebSocketSubject subject = WebSocketSubject.Holder.getSubject(principal);

		// get observer
		WebSocketObserver observer = new WebSocketObserver(session);
		// delete observer from subject
		subject.deleteObserver(observer);

		// close session and close Web Socket connection
		try {
			if (session.isOpen()) {
				session.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("close web socket session error.", e);
		}

	}

	@OnError
	public void onError(@PathParam("principal") String principal, Session session, Throwable error) {
		throw new RuntimeException("web socket error.", error);
	}

}
