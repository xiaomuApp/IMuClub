package com.example.imuclub;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.ItemAdapter;
import com.example.model.TaskModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NotCompletedTaskFragment extends Fragment {

	private PullToRefreshListView lv_notcompletedtask; // 下拉刷新列表
	private ItemAdapter adapter; // 列表现适配器

	private List<TaskModel> list = new ArrayList<TaskModel>(); // 存放列表项内容
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater
				.inflate(R.layout.tab_notcompletedtask, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		lv_notcompletedtask = (PullToRefreshListView) getActivity().findViewById(
				R.id.lv_notcompletedtask);

		//模拟从网络读取数据
		getDataFromIntent();

		// 列表项点击响应事件
		lv_notcompletedtask.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClassName("com.example.imuclub",
						"com.example.imuclub.TaskCheckOrEditActivity");
				intent.putExtra("project_theme", list.get(position - 1)
						.getTheme());
				intent.putExtra("project_deadline", list.get(position - 1)
						.getDeadline());
				intent.putExtra("project_builder", list.get(position - 1)
						.getBuilder());
				intent.putExtra("project_task", list.get(position-1)
						.getTask());
				intent.putExtra("project_iscompleted", list.get(position - 1)
						.isIscomplete());
				intent.putExtra("project_tab", 2);
				startActivity(intent);
			}
		});

		// 给更新ListView监听器
		lv_notcompletedtask.setOnRefreshListener(new OnRefreshListener<ListView>() {
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
						lv_notcompletedtask.setAdapter(adapter);

						lv_notcompletedtask.onRefreshComplete();
					};
				}.execute();
			}
		});
	}

	private void getDataFromIntent() {
		
		//初始化
		list = new ArrayList<TaskModel>();
		
		// 模仿数据读取
		TaskModel taskModel = new TaskModel();
		taskModel.setTheme("阳光体育");
		taskModel.setDeadline("2015/4/15");
		taskModel.setBuilder("计算机学院学生会");
		taskModel.setTask("举行阳光体育活动");
		taskModel.setIsdeclare(true);
		taskModel.setIscomplete(false);
		list.add(taskModel);

		adapter = new ItemAdapter(getActivity(), list); // 重新添加适配器以更新
		lv_notcompletedtask.setAdapter(adapter);
	}
}