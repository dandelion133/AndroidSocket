package com.myandroid.activity;


import com.myandroid.message.R;
import com.myandroid.util.Msg;
import com.myandroid.util.Tools;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.style.BulletSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity implements OnClickListener {
	// 参数
	private EditText chartMsg = null;
	private Button chartMsgSend = null;
	private Tools tools;
	public static ChatActivity activity = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		activity = this;
		setContentView(R.layout.activity_chart);		
		chartMsg = (EditText) findViewById(R.id.chart_msg);
		chartMsgSend = (Button) findViewById(R.id.chart_msg_send);
		chartMsgSend.setOnClickListener(this);
		tools = new Tools(this);
		tools.receiveMsg();
	}





	// 按钮点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chart_msg_send:
			// 发送信息按钮
			sendMsg();
			break;
		}
	}

	// 发送信息
	public void sendMsg() {
		String body = chartMsg.getText().toString();
		if (null == body || body.length() <= 0) {
			Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		chartMsg.setText("");
		
		Msg msg = new Msg(Build.MODEL,
				Tools.getLocalHostIp(), 
				"you",
				"192.168.1.111",
				Tools.CMD_SENDMSG, 
				body);
		
		
		Toast.makeText(this, "本地IP： "+Tools.getLocalHostIp(), 0).show();
		tools.sendMsg(msg);
	}

	
	
	

	

}
