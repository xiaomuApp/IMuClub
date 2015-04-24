package com.example.imuclub;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.PeopleItemAdapter;
import com.example.model.PeopleModel;
import com.example.model.UserInfor;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AllPeopleFragment extends Fragment {

	private PullToRefreshListView lv_allpeople; // 下拉刷新列表
	private PeopleItemAdapter adapter; // 列表现适配器
	private Button btn_add; // 添加项目按钮

	private List<PeopleModel> list; // 存放列表项内容

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.tab_allpeople, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		//模拟从网络读取数据
		getDataFromIntent();
		
		btn_add = (Button) getActivity().findViewById(R.id.btn_add);


		btn_add.setOnClickListener(new OnClickListener() { // 点击添加按钮添加新的未编辑的列表项
			@Override
			public void onClick(View v) {
				PeopleModel peopleModel = new PeopleModel();
				peopleModel.setName("未编辑");
				peopleModel.setNickname("小木用户");
				peopleModel.setPosition("未编辑");
				list.add(peopleModel);
				adapter = new PeopleItemAdapter(getActivity(), list); // 重新添加适配器以更新
				lv_allpeople.setAdapter(adapter);
			}
		});

		// 列表项点击响应事件
		lv_allpeople.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}
		});

		// 给更新ListView监听器
		lv_allpeople.setOnRefreshListener(new OnRefreshListener<ListView>() {
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
						adapter = new PeopleItemAdapter(getActivity(), list);
						lv_allpeople.setAdapter(adapter);
						
						lv_allpeople.onRefreshComplete();
					};
				}.execute();
			}
		});
	}

	private void getDataFromIntent() {
		// 模仿数据读取
		list = new ArrayList<PeopleModel>();
		lv_allpeople = (PullToRefreshListView) getActivity().findViewById(
				R.id.lv_allpeople);
		System.out.println(">>>>>>>>>>>>>>>>>test PeopleList");
		List<UserInfor> peopleList = IMuClubActivity.PeopleList;
		if(peopleList!=null){
			for(UserInfor people:peopleList){
				System.out.println(">>>>>>>>>>>>>>>>>>>>"+people.getClub()+": 名字："+people.getUsername());
				PeopleModel peopleModel = new PeopleModel();
				peopleModel.setName(people.getUsername());
				peopleModel.setNickname(people.getClub());
				peopleModel.setPosition(people.getUpdatedAt());
				list.add(peopleModel);
			}
		}else{
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>no data >>>>>>.");
		}
		
		
		adapter = new PeopleItemAdapter(getActivity(), list); // 重新添加适配器以更新
		lv_allpeople.setAdapter(adapter);
	}
}
