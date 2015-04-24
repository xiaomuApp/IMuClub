package com.example.imuclub;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.adapter.PeopleItemAdapter;
import com.example.model.PeopleModel;
import com.example.model.UserInfor;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PeopleSelectActivity extends FragmentActivity {

	private Button btn_select_return;
	private PullToRefreshListView lv_select_list; // 下拉刷新列表

	private PeopleItemAdapter adapter; // 列表现适配器
	private List<PeopleModel> list = new ArrayList<PeopleModel>(); // 存放列表项内容
	
	private List<Integer> listPosition = new ArrayList<Integer>();// 选中的人多列表位置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_people_select);

		btn_select_return = (Button) findViewById(R.id.btn_select_return);
		lv_select_list = (PullToRefreshListView) findViewById(R.id.lv_select_list);
		
		listPosition = getIntent().getIntegerArrayListExtra("checkPeoplePosition");//冲编辑任务中获取已经选择的人
		

		btn_select_return.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 模拟数据
		
		System.out.println(">>>>>>>>>>>>>>>>>test PeopleList");
		List<UserInfor> peopleList = IMuClubActivity.PeopleList;
		if(peopleList!=null){
			for(UserInfor people:peopleList){
				System.out.println(">>>>>>>>>>>>>>>>>>>>"+people.getClub()+": 名字："+people.getUsername());
				PeopleModel peopleModel = new PeopleModel();
				peopleModel.setName(people.getUsername());
				peopleModel.setNickname(people.getClub());
				peopleModel.setPosition(people.getId());
				list.add(peopleModel);
			}
			if(listPosition!=null){
				System.out.println("从编辑任务中获得已选的人个数："+listPosition.size());
				for(int p: listPosition){
					list.get(p).setVisible(true);
					System.out.println("从编辑任务中获得已选的人："+list.get(p).getName());
				}
			}else{
				System.out.println("没有从编辑任务中获得已选的人");
			}
		}else{
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>no data >>>>>>.");
		}

		adapter = new PeopleItemAdapter(this, list);
		lv_select_list.setAdapter(adapter);

		// 列表项点击响应事件
		lv_select_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("点击的列表项"+position);
				if(listPosition.contains(position-1)){
					listPosition.remove(new Integer(position-1));
					list.get(position-1).setVisible(false);
					adapter.notifyDataSetChanged();
				}else{
					listPosition.add(position-1);
					list.get(position-1).setVisible(true);
					adapter.notifyDataSetChanged();
				}
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

						adapter = new PeopleItemAdapter(
								PeopleSelectActivity.this, list); // 重新添加适配器以更新
						lv_select_list.setAdapter(adapter);

						lv_select_list.onRefreshComplete();
					};
				}.execute();
			}
		});
		
		findViewById(R.id.btn_select_confirm).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putIntegerArrayList("checkedPeoplePosition", (ArrayList<Integer>) listPosition);
				System.out.println(">>>>>>>>>>>>>选中的位置有");
				for(int p:listPosition){
					System.out.println(">>>>>>>>>>>>>>>>>"+listPosition);
				}
				intent.putExtras(bundle);
				setResult(101, intent);
				finish();
				
			}
		});
	}
}
