package com.example.imuclub;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MessageItemAdapter;
import com.example.model.MessageModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MyMessageActivity extends Activity {
	
	private PullToRefreshListView lv_message;
	private Button btn_return_to_main; // 返回按钮
	
	private MessageItemAdapter adapter; // 列表现适配器

	private List<MessageModel> list = new ArrayList<MessageModel>(); // 存放列表项内容
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mymessage);
		
		lv_message = (PullToRefreshListView) findViewById(R.id.lv_mymessage);
		btn_return_to_main = (Button) findViewById(R.id.btn_return_to_main); // 返回按钮
		
		//模拟网上读取数据
		getDataFromIntent();
		
		btn_return_to_main.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		// 给更新ListView监听器
		lv_message.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new AsyncTask<Void, Void, Void>() {
					// 模拟服务器读取时间
					@Override
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
								}
						return null;
						}
					// 执行事件
					protected void onPostExecute(Void result) {
						// 模仿数据读取
						
						adapter = new MessageItemAdapter(MyMessageActivity.this, list);
						lv_message.setAdapter(adapter);
						
						lv_message.onRefreshComplete();
						};
						}.execute();
					}
				});
	}
	
private void getDataFromIntent() {
		
		//初始化
		list = new ArrayList<MessageModel>();

		// 模仿数据读取
		MessageModel messageModel01 = new MessageModel();
		messageModel01.setOperation("添加了人员");
		messageModel01.setObject("黄炫");
		messageModel01.setUpdatetime("2015-3-1");
		list.add(messageModel01);
		
		MessageModel messageModel02 = new MessageModel();
		messageModel02.setOperation("创建了活动");
		messageModel02.setObject("女生节");
		messageModel02.setUpdatetime("2015-3-5");
		list.add(messageModel02);
		
		MessageModel messageModel03 = new MessageModel();
		messageModel03.setOperation("被分配了任务");
		messageModel03.setObject("换届晚会");
		messageModel03.setUpdatetime("2015-3-18");
		list.add(messageModel03);
		
		MessageModel messageModel04 = new MessageModel();
		messageModel04.setOperation("还有任务未完成");
		messageModel04.setObject("换届晚会");
		messageModel04.setUpdatetime("2015-3-18");
		list.add(messageModel04);
		
		adapter = new MessageItemAdapter(this, list);
		lv_message.setAdapter(adapter);
	}
}
