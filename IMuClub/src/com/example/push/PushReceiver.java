package com.example.push;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.imuclub.IMuClubActivity;
import com.example.imuclub.R;
import com.example.model.TaskModel;

import cn.bmob.push.PushConstants;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class PushReceiver extends BroadcastReceiver {

	private TaskModel task = null;
	private String message = "";
	private String subject = "", time = "", content = "";

	// 接受器，接收推送过来的信息
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {

			String msg = intent
					.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);

			JSONTokener jsontokener = new JSONTokener(msg);// JSON数据解析器，获取整个JSON的字符串，MainActivity
															// Push过来的JSONObject
			try {
				JSONObject jsonobject = (JSONObject) jsontokener.nextValue();// 定义一个JSONObject

				subject = jsonobject.getString("subject");
				time = jsonobject.getString("time");
				content = jsonobject.getString("content");

				task = new TaskModel();
				task.setTheme(subject);
				task.setDeadline(time);
				task.setTask(content);

				// Log.d("输出", subject+time+content);
				message = subject + time + content;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			NotificationManager nm = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			Notification n = new Notification();
			n.icon = R.drawable.app_icon;
			n.tickerText = "收到一条消息";
			n.when = System.currentTimeMillis();

			Bundle bundle = new Bundle();
			 bundle.putSerializable("Task", task);
			 Intent I = new Intent(context,IMuClubActivity.class);
			 I.putExtras(bundle);
			 PendingIntent pi = PendingIntent.getActivity(context, 0, I, 0);
			 n.setLatestEventInfo(context, "消息", message, pi);
			n.defaults |= Notification.DEFAULT_SOUND;
			n.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify(1, n);
		}
	}

}
