package com.example.imuclub;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.adapter.ItemAdapter;
import com.example.model.TaskModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class AllProjectFragment extends Fragment {

	private PullToRefreshListView lv_allproject; // 下拉刷新列表
	private ItemAdapter adapter; // 列表现适配器
	
	private List<TaskModel> list = new ArrayList<TaskModel>(); // 存放列表项内容
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.tab_allproject, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		lv_allproject = (PullToRefreshListView) getActivity().findViewById(
				R.id.lv_allproject);

		//模拟从网络读取数据
		getDataFromIntent();

		// 列表项点击响应事件
		lv_allproject.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClassName("com.example.imuclub",
						"com.example.imuclub.TaskCheckOrEditActivity");
				intent.putExtra("project_theme", list.get(position - 1)
						.getTheme());      //传入活动主题
				intent.putExtra("project_deadline", list.get(position - 1)
						.getDeadline());   //传入截止日期
				intent.putExtra("project_builder", list.get(position-1)
						.getBuilder());    //传入活动创建者
				intent.putExtra("project_task", list.get(position-1)
						.getTask());     //传入活动
				intent.putExtra("project_iscompleted", list.get(position - 1)
						.isIscomplete());      //传入是否完成标志
				intent.putExtra("project_tab", 1);      //传入Activity标志
				startActivity(intent);
			}
		});
		
		// 给更新ListView监听器
		lv_allproject.setOnRefreshListener(new OnRefreshListener<ListView>() {
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

						adapter = new ItemAdapter(getActivity(), list); // 重新添加适配器以更新
						lv_allproject.setAdapter(adapter);

						lv_allproject.onRefreshComplete();
					};
				}.execute();
			}
		});
	}

	private void getDataFromIntent() {
		
		
		//初始化
		list = new ArrayList<TaskModel>();

		// 模仿数据读取
		TaskModel taskModel01 = new TaskModel();
		taskModel01.setTheme("迎新晚会");
		taskModel01.setDeadline("2014/10/17");
		taskModel01.setBuilder("计算机学院学生会");
		taskModel01.setTask("筹划迎新晚会");
		taskModel01.setIsdeclare(true);
		taskModel01.setIscomplete(true);
		list.add(taskModel01);
		
		TaskModel taskModel02 = new TaskModel();
		taskModel02.setTheme("女生节");
		taskModel02.setDeadline("2015/3/2");
		taskModel02.setBuilder("外国语言文化学院学生会");
		taskModel02.setTask("制作女生节宣传海报");
		taskModel02.setIsdeclare(true);
		taskModel02.setIscomplete(true);
		list.add(taskModel02);

		TaskModel taskModel03 = new TaskModel();
		taskModel03.setTheme("阳光体育");
		taskModel03.setDeadline("2015/4/15");
		taskModel03.setBuilder("计算机学院学生会");
		taskModel03.setTask("举行阳光体育活动");
		taskModel03.setIsdeclare(true);
		taskModel03.setIscomplete(false);
		list.add(taskModel03);
		
		TaskModel taskModel04 = new TaskModel();
		taskModel04.setTheme("换届晚会");
		taskModel04.setBuilder("旅游管理学院团委");
		taskModel04.setDeadline("2015/6/20");
		taskModel04.setTask("大厅现场布置");
		taskModel04.setIsdeclare(true);
		taskModel04.setIscomplete(false);
		list.add(taskModel04);
		
		// 模仿数据读取
		TaskModel taskModel05 = new TaskModel();
		taskModel05.setTheme("女生节");
		taskModel05.setDeadline("2015/3/2");
		taskModel05.setBuilder("周楚鹏");
		taskModel05.setTask("为众位女生准备礼物");
		taskModel05.setIsdeclare(true);
		taskModel05.setIscomplete(true);
		list.add(taskModel05);
		
		if (TaskCheckOrEditActivity.getmTask() != null && TaskCheckOrEditActivity.getmTask().isShow() ) {
			list.add(TaskCheckOrEditActivity.getmTask());
		}
			//得到网上的数据
		if (IMuClubActivity.getmTask() != null && TaskCheckOrEditActivity.getmTask().isShow() ) {
			list.add(TaskCheckOrEditActivity.getmTask());
		}
		
		adapter = new ItemAdapter(getActivity(), list);
		lv_allproject.setAdapter(adapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		 getDataFromIntent();
		adapter = new ItemAdapter(getActivity(), list); // 重新添加适配器以更新
		lv_allproject.setAdapter(adapter);
		lv_allproject.onRefreshComplete();
	}
}
