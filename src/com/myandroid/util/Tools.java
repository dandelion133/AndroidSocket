package com.myandroid.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import com.myandroid.activity.ChatActivity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class Tools {
	// 协议命令
	public static final int CMD_SENDMSG = 13;// 发送信息
	public static final int PORT_SEND = 2426;// 发送端口
	public static final int PORT_RECEIVE = 2425;// 接收端口
	// 消息命令
	public static final int SHOW = 8000;// 显示消息

	private Context mContext;
	
	private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Tools.SHOW:
					Toast.makeText(mContext, (String) msg.obj,Toast.LENGTH_SHORT).show();
					break;
				
				}
			}
		};
	
	public Tools(Context context) {
		mContext = context;

	}

	// 发送消息
	public void sendMsg(Msg msg) {
		(new UdpSend(msg)).start();
	}

	// 发送消息线程
	class UdpSend extends Thread {
		Msg msg = null;

		UdpSend(Msg msg) {
			this.msg = msg;
		}

		public void run() {
			try {
				byte[] data = Tools.toByteArray(msg);
				DatagramSocket ds = new DatagramSocket(Tools.PORT_SEND);
				DatagramPacket packet = new DatagramPacket(data, data.length,
						InetAddress.getByName(msg.getReceiveUserIp()),//指定接收方
						Tools.PORT_RECEIVE);
				packet.setData(data);
				ds.send(packet);
				ds.close();
				// Tools.out("发送广播通知上线");
			} catch (Exception e) {
			}

		}
	}

	// 接收消息
	public void receiveMsg() {
		new UdpReceive().start();
	}

	// 接收消息线程
	class UdpReceive extends Thread {
		Msg msg = null;

		UdpReceive() {
		}

		public void run() {
			// 消息循环
			while (true) {
				try {
					DatagramSocket ds = new DatagramSocket(Tools.PORT_RECEIVE);
					byte[] data = new byte[1024 * 4];
					DatagramPacket dp = new DatagramPacket(data, data.length);
					dp.setData(data);
					ds.receive(dp);
					byte[] data2 = new byte[dp.getLength()];
					System.arraycopy(data, 0, data2, 0, data2.length);// 得到接收的数据
					Msg msg = (Msg) Tools.toObject(data2);
					ds.close();
					
					//Log.e
					Tips(Tools.SHOW, msg.getSendUser()+""+msg.getSendUserIp() + " 发来消息！" + msg.getBody());
					
				} catch (Exception e) {
				}
			}

		}
	}


	
	



	// 得到广播ip, 192.168.0.255之类的格式
	public static String getBroadCastIP() {
		String ip = getLocalHostIp().substring(0,getLocalHostIp().lastIndexOf(".") + 1) + "255";
		
		Log.e("ip", "本机ip " + getLocalHostIp() + "-广播ip： " + ip);
		
		return ip;
	}

	// 获取本机IP
	public static String getLocalHostIp() {
		String ipaddress = "";
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			// 遍历所用的网络接口
			while (en.hasMoreElements()) {
				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
				Enumeration<InetAddress> inet = nif.getInetAddresses();
				// 遍历每一个接口绑定的所有ip
				while (inet.hasMoreElements()) {
					InetAddress ip = inet.nextElement();
					if (!ip.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ip.getHostAddress())) {
						return ipaddress = ip.getHostAddress();
					}
				}

			}
		} catch (SocketException e) {
			System.out.print("获取IP失败");
			e.printStackTrace();
		}
		return ipaddress;

	}

	// 对象封装成消息
	public static byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	// 消息解析成对象
	public static Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}



	// Tips-Handler
	public  void Tips(int cmd, Object str) {
		Message m = new Message();
		m.what = cmd;
		m.obj = str;
		handler.sendMessage(m);
	}
 

}
