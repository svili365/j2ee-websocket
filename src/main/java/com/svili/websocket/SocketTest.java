package com.svili.websocket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * websocket服务端推送消息测试代码.
 * <ul>
 * 容器启动时开启线程任务，每隔5秒向客户端发送消息。
 *
 */
public class SocketTest extends HttpServlet {

	private static final long serialVersionUID = -7725924501352471504L;

	@Override
	public void init() throws ServletException {
		super.init();
		Thread thread = new Thread(new NotifyThread());
		thread.start();
	}

	private class NotifyThread implements Runnable {

		public void run() {
			while (true) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				notifyTest();
			}

		}

		/**服务端向客户端推送消息*/
		public void notifyTest() {
			String principal = "1";
			String type = "radio";
			JSONObject data = new JSONObject();
			data.put("title", "test web socket");
			data.put("content", "Have you recieve this msg?--this msg from server.");

			WebSocketSubject subject = WebSocketSubject.Holder.getSubject(principal);
			subject.notify(type, data.toJSONString());
		}
	}

}
