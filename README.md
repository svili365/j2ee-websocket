# j2ee-websocket
[![JDK 1.7](https://img.shields.io/badge/JDK-1.7-green.svg "JDK 1.7")]()

a simple websocket demo for j2ee

## webSocket简介

WebSocket协议是基于TCP的一种新的网络协议。它实现了浏览器与服务器全双工(full-duplex)通信，**允许服务器主动发送信息给客户端**。

(1) 与 HTTP协议有着良好的兼容性。默认端口也是80和443。

(2) 协议标识符是ws（如果加密，则为wss）。

## 

项目可直接打包在tomcat容器中运行。

可直接集成Spring，不需要额外配置。

JQuery.websocket增加了消息的类型，将消息拆分为{"type":"","message":""}。
这样更灵活，可以根据业务类型，定义type，如：通知，公告，广播，发文等...

运行示例 --> run-examples.png 



