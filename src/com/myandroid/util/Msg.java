package com.myandroid.util;

import java.io.Serializable;

public class Msg implements Serializable {
	private String sendUser;// 发送人 用户名
	private String sendUserIp;// 发送人ip
	private String receiveUser;// 接收人
	private String receiveUserIp;//接收人IP
	private int MsgType;// 消息类型
	private Object body;// 主体
	

	public Msg (){}
	public Msg(String sendUser, String sendUserIp, String receiveUser,
			String receiveIp, int msgType, Object body) {
		super();
		this.sendUser = sendUser;
		this.sendUserIp = sendUserIp;
		this.receiveUser = receiveUser;
		this.receiveUserIp = receiveIp;
		MsgType = msgType;
		this.body = body;
	}
	
	
	public String getReceiveUserIp() {
		return receiveUserIp;
	}
	public void setReceiveUserIp(String receiveUserIp) {
		this.receiveUserIp = receiveUserIp;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getSendUserIp() {
		return sendUserIp;
	}
	public void setSendUserIp(String sendUserIp) {
		this.sendUserIp = sendUserIp;
	}
	public String getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	public int getMsgType() {
		return MsgType;
	}
	public void setMsgType(int msgType) {
		MsgType = msgType;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}



	
}
