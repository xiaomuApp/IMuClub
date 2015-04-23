package com.example.imuclub;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.PeopleItemAdapter;
import com.example.model.PeopleModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PeopleSelectActivity extends FragmentActivity {

	private Button btn_select_return;
	private PullToRefreshListView lv_select_list; // 下拉刷新列表

	private PeopleItemAdapter adapter; // 列表现适配器
	private List<PeopleModel> list = new ArrayList<PeopleModel>(); // 存放列表项内容

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_people_select);

		btn_select_return = (Button) findViewById(R.id.btn_select_return);
		lv_select_list = (PullToRefreshListView) findViewById(R.id.lv_select_list);

		btn_select_return.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 模拟数据
		PeopleModel peopleModel01 = new PeopleModel();
		peopleModel01.setName("池学辉");
		peopleModel01.setNickname("安卓组组长");
		peopleModel01.setPosition("部长");
		list.add(peopleModel01);

		PeopleModel peopleModel02 = new PeopleModel();
		peopleModel02.setName("龙宇文");
		peopleModel02.setNickname("安卓组组员");
		peopleModel02.setPosition("干事");
		list.add(peopleModel02);

		PeopleModel peopleModel03 = new PeopleModel();
		peopleModel03.setName("冼立志");
		peopleModel03.setNickname("安卓组组员");
		peopleModel03.setPosition("干事");
		list.add(peopleModel03);
		
		PeopleModel peopleModel04 = new PeopleModel();
		peopleModel04.setName("吴伟峰");
		peopleModel04.setNickname("安卓组组员");
		peopleModel04.setPosition("干事");
		list.add(peopleModel04);
		
		PeopleModel peopleModel05 = new PeopleModel();
		peopleModel05.setName("黄炫");
		peopleModel05.setNickname("安卓组组员");
		peopleModel05.setPosition("干事");
		list.add(peopleModel05);
		
		PeopleModel peopleModel06 = new PeopleModel();
		peopleModel06.setName("周楚鹏");
		peopleModel06.setNickname("UI组长");
		peopleModel06.setPosition("部长");
		list.add(peopleModel06);

		adapter = new PeopleItemAdapter(this, list);
		lv_select_list.setAdapter(adapter);

		// 列表项点击响应事件
		lv_select_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				finish();
			}
		});

		// 给更新ListView监听器
		lv_select_list.setOnRefreshListener(new OnRefreshListener<ListView>() {
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
						PeopleModel peopleModel01 = new PeopleModel();
						peopleModel01.setName("周楚鹏");
						peopleModel01.setNickname("捡肥皂的屌丝");
						peopleModel01.setPosition("部长");
						list.add(peopleModel01);

						PeopleModel peopleModel02 = new PeopleModel();
						peopleModel02.setName("何倩");
						peopleModel02.setNickname("下一站、再见");
						peopleModel02.setPosition("干事");
						list.add(peopleModel02);

						adapter = new PeopleItemAdapter(
								PeopleSelectActivity.this, list); // 重新添加适配器以更新
						lv_select_list.setAdapter(adapter);

						lv_select_list.onRefreshComplete();
					};
				}.execute();
			}
		});
	}
}
