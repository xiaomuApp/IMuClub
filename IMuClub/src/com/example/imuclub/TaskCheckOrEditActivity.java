package com.example.imuclub;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SaveListener;

import com.example.model.TaskModel;
import com.example.model.UserInfor;


public class TaskCheckOrEditActivity extends Activity implements
		OnClickListener, OnTouchListener {

	//push
	private static TaskModel mTask = new TaskModel();
	private TaskModel mTask1;
	private String APPID = "5d04bd96d646a589a78979f7b509f4fe";
	private ArrayList<String> InstallationIdList;
	private ArrayList<UserInfor> peoplelist = new ArrayList<UserInfor>();//定义所选中的人员的信息list
	private ArrayList<Integer> peoplePosition = new ArrayList<Integer>(); //暂时存储被选人员的列表中的位置

	// 标题栏控件
	private Button btn_return; // 返回按钮
	private Button btn_edit; // 编辑按钮

	// 内容控件
	private TextView tv_check_title; // 标题
	private TextView tv_general_theme; // 全概栏主题
	private TextView tv_builder; // 创建者
	private EditText et_item_theme; // 主题
	private EditText et_item_deadline; // 截止时间
	private EditText et_item_context; // 任务内容
	private ImageView iv_iscompleted; // 是否完成标志
	private Button btn_declare; // 发布按钮
	private Button btn_delete; // 删除按钮

	// 程序变量
	private boolean IsEdit = false; // 标志是否处于编辑状态

	// 人员控件
	private ImageView iv_people01;
	private ImageView iv_people02;
	private ImageView iv_people03;
	private ImageView iv_people04;
	private ImageView iv_people05;
	private ImageView iv_people06;
	
	private TextView checkPeopleTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_check_or_edit);
		
		Bmob.initialize(this, APPID);
		BmobInstallation.getCurrentInstallation(this).save();
		BmobPush.startWork(this, APPID);
		

		// 初始化View
		btn_return = (Button) findViewById(R.id.btn_return); // 返回按钮
		btn_edit = (Button) findViewById(R.id.btn_edit); // 编辑按钮
		tv_check_title = (TextView) findViewById(R.id.tv_check_title); // 标题
		tv_general_theme = (TextView) findViewById(R.id.tv_general_theme); // 全概栏主题
		tv_builder = (TextView) findViewById(R.id.tv_builder); // 创建者
		et_item_theme = (EditText) findViewById(R.id.et_item_theme); // 主题
		et_item_deadline = (EditText) findViewById(R.id.et_item_deadline); // 截止时间
		et_item_context = (EditText) findViewById(R.id.et_item_context); // 活动内容
		iv_iscompleted = (ImageView) findViewById(R.id.iv_iscompleted); // 是否完成标志
		btn_declare = (Button) findViewById(R.id.btn_declare); // 发布按钮
		btn_delete = (Button) findViewById(R.id.btn_delete); // 删除按钮

		// 初始化人员控件
		iv_people01 = (ImageView) findViewById(R.id.iv_people01);
		iv_people02 = (ImageView) findViewById(R.id.iv_people02);
		iv_people03 = (ImageView) findViewById(R.id.iv_people03);
		iv_people04 = (ImageView) findViewById(R.id.iv_people04);
		iv_people05 = (ImageView) findViewById(R.id.iv_people05);
		iv_people06 = (ImageView) findViewById(R.id.iv_people06);

		// 获得并设置Activity内容
		Bundle bundle = getIntent().getExtras();
		tv_check_title.setText(bundle.getString("project_theme")); // 设置标题
		tv_general_theme.setText(bundle.getString("project_theme")); // 设置标题
		tv_builder.setText(bundle.getString("project_builder")); // 设置创建者
		et_item_theme.setText(bundle.getString("project_theme")); // 设置标题
		et_item_deadline.setText(bundle.getString("project_deadline")); // 设置截止时间
		et_item_context.setText(bundle.getString("project_task"));
		
		checkPeopleTV = (TextView) findViewById(R.id.checkPeopleTV);
		

		// 设置任务是否完成标志
		if (bundle.getBoolean("project_iscompleted"))
			iv_iscompleted.setBackgroundResource(R.drawable.item_completed);
		else
			iv_iscompleted.setBackgroundResource(R.drawable.item_notcompleted);

		// 判断是否需要显示“发布”,“编辑”，“删除”，“添加人员”按钮
		switch (bundle.getInt("project_tab")) {
		case 1: // "全部活动"单击Item
			btn_edit.setVisibility(View.GONE);
			btn_declare.setVisibility(View.GONE);
			btn_delete.setVisibility(View.GONE);
			// 没有头像的选框不显示（此处为模拟）
			iv_people03.setVisibility(View.GONE);
			iv_people04.setVisibility(View.GONE);
			iv_people05.setVisibility(View.GONE);
			iv_people06.setVisibility(View.GONE);
			break;
		case 2: // "我参与的"单击Item
			btn_edit.setVisibility(View.GONE);
			btn_declare.setVisibility(View.VISIBLE);
			btn_declare.setText("完成反馈");
			btn_delete.setVisibility(View.GONE);
			// 没有头像的选框不显示（此处为模拟）
			iv_people03.setVisibility(View.GONE);
			iv_people04.setVisibility(View.GONE);
			iv_people05.setVisibility(View.GONE);
			iv_people06.setVisibility(View.GONE);
			break;
		case 3: // "我创建的"单击Item
			btn_edit.setVisibility(View.VISIBLE);
			btn_declare.setVisibility(View.VISIBLE);
			btn_declare.setText("发布");
			btn_delete.setVisibility(View.VISIBLE);

			// 显示没有头像的选框（此处为模拟）
			iv_people03.setVisibility(View.VISIBLE);
			iv_people04.setVisibility(View.VISIBLE);
			iv_people05.setVisibility(View.VISIBLE);
			iv_people06.setVisibility(View.VISIBLE);

			// 处于编辑模式才设置监听事件
			iv_people01.setOnClickListener(this);
			iv_people02.setOnClickListener(this);
			iv_people03.setOnClickListener(this);
			iv_people04.setOnClickListener(this);
			iv_people05.setOnClickListener(this);
			iv_people06.setOnClickListener(this);
			break;
		case 4: // "创建"点击
			// 设置标题
			tv_check_title.setText("创建活动");

			// "分配人员"区为空
			iv_people01.setImageResource(R.drawable.item_add_people);
			iv_people02.setImageResource(R.drawable.item_add_people);

			// 显示没有头像的选框（此处为模拟）
			iv_people03.setVisibility(View.VISIBLE);
			iv_people04.setVisibility(View.VISIBLE);
			iv_people05.setVisibility(View.VISIBLE);
			iv_people06.setVisibility(View.VISIBLE);

			// 将Activity设置为编辑状态
			et_item_theme.setEnabled(true);
			et_item_deadline.setEnabled(true);
			et_item_context.setEnabled(true);
			btn_edit.setBackgroundResource(R.drawable.title_save);

			// 设置按钮文字
			btn_declare.setText("完成");
			btn_delete.setText("取消");

			// 处于编辑模式才设置监听事件
			iv_people01.setOnClickListener(this);
			iv_people02.setOnClickListener(this);
			iv_people03.setOnClickListener(this);
			iv_people04.setOnClickListener(this);
			iv_people05.setOnClickListener(this);
			iv_people06.setOnClickListener(this);
			break;
		}
		// 设置点击监听事件
		btn_return.setOnClickListener(this);
		btn_edit.setOnClickListener(this);
		btn_declare.setOnClickListener(this);
		btn_delete.setOnClickListener(this);

		// 设置接触事件
		btn_return.setOnTouchListener(this);
		btn_edit.setOnTouchListener(this);
		btn_declare.setOnTouchListener(this);
		btn_delete.setOnTouchListener(this);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			switch (v.getId()) {
			case R.id.btn_return:
				btn_return.setBackgroundResource(R.drawable.title_return_down);
				break;
			case R.id.btn_edit:
				btn_edit.setBackgroundResource(R.drawable.title_edit_down);
				break;
			case R.id.btn_declare:
				btn_declare.setBackgroundResource(R.drawable.btn_declare_down);
				break;
			case R.id.btn_delete:
				btn_delete.setBackgroundResource(R.drawable.btn_declare_down);
				break;
			}
		} else if (action == MotionEvent.ACTION_UP) {
			switch (v.getId()) {
			case R.id.btn_return:
				btn_return.setBackgroundResource(R.drawable.title_return);
				break;
			case R.id.btn_edit:
				btn_edit.setBackgroundResource(R.drawable.title_edit);
				break;
			case R.id.btn_declare:
				btn_declare.setBackgroundResource(R.drawable.btn_declare);
				break;
			case R.id.btn_delete:
				btn_delete.setBackgroundResource(R.drawable.btn_declare);
				break;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_return:
			finish();
			break;
		case R.id.btn_edit:
			// 设置可编辑性
			if (IsEdit == false) {
				et_item_theme.setEnabled(true);
				et_item_deadline.setEnabled(true);
				et_item_context.setEnabled(true);
				btn_edit.setBackgroundResource(R.drawable.title_save);
				IsEdit = true;
			} else {
				et_item_theme.setEnabled(false);
				et_item_deadline.setEnabled(false);
				et_item_context.setEnabled(false);
				btn_edit.setBackgroundResource(R.drawable.title_edit);

				// 同步标题
				tv_check_title.setText(et_item_theme.getText());
				tv_general_theme.setText(et_item_theme.getText());
				IsEdit = false;
			}

			// 设置主题

			tv_general_theme.setText(et_item_theme.getText().toString().trim());

			setInTask();

			break;
		case R.id.iv_people01:
		case R.id.iv_people02:
		case R.id.iv_people03:
		case R.id.iv_people04:
		case R.id.iv_people05:
		case R.id.iv_people06:
			Intent intent = new Intent();
			intent.setClassName("com.example.imuclub",
					"com.example.imuclub.PeopleSelectActivity");
			intent.putIntegerArrayListExtra("checkPeoplePosition", peoplePosition);
			startActivityForResult(intent, 0);
			break;
		case R.id.btn_declare:
			Button button = (Button) v;
			if (button.getText() == "完成反馈") {
				String text = "项目\"" + tv_check_title.getText() + "\"反馈成功";
				Toast.makeText(this, text, Toast.LENGTH_LONG).show();
				finish();
			} else if (button.getText() == "发布") {
				String text = "项目\"" + tv_check_title.getText() + "\"发布成功";
				Toast.makeText(this, text, Toast.LENGTH_LONG).show();

				String subject = et_item_theme.getText().toString();
				String time = et_item_deadline.getText().toString();
				String content = et_item_context.getText().toString();

				if (subject.equals("") || content.equals("")) {
					Toast.makeText(TaskCheckOrEditActivity.this, "主题内容不能为空",
							Toast.LENGTH_SHORT).show();
				}

				else {
					InstallationIdList=new ArrayList<String>();
					
					mTask1 = new TaskModel();
					mTask1.setTheme(et_item_theme.getText().toString().trim());
					mTask1.setBuilder(tv_builder.getText().toString().trim());
					mTask1.setDeadline(et_item_deadline.getText().toString()
							.trim());
					mTask1.setTask(et_item_context.getText().toString().trim());
					mTask1.setIscomplete(false);
					mTask1.setIsdeclare(false);
					mTask1.setShow(true);

					BmobPushManager push = new BmobPushManager(
							TaskCheckOrEditActivity.this);
					BmobQuery<BmobInstallation> query = new BmobQuery<BmobInstallation>();// 查询
					
					//此处获取到所选中的人员的信息列表存放在peoplelist
					Log.d("peoplelist", String.valueOf(peoplelist.size()));
					
					for(UserInfor people:peoplelist)
					{
						System.out.println("发布任务给"+people.getUsername()+"id:"+people.getId()+"installationId:"+people.getInstallationId());
						mTask1.setStudentId(people.getId());
						InstallationIdList.add(people.getInstallationId());
						mTask1.save(TaskCheckOrEditActivity.this, new SaveListener() {
							
							@Override
							public void onSuccess() {
								
								
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								
								
							}
						});
					}
					
					Log.d("手机id", String.valueOf(InstallationIdList.size()));
					query.addWhereContainedIn("installationId", InstallationIdList);
					JSONObject object = new JSONObject();
					try {
						object.put("subject", mTask1.getTheme());
						object.put("time", mTask1.getDeadline());
						object.put("content", mTask1.getTask());
					} catch (JSONException e) {
						e.printStackTrace();
					}

					push.setQuery(query);
					push.pushMessage(object);
					
				}
				;

				finish();
			} else if (button.getText() == "完成") {

				setInTask();

				finish();
			}
			break;
		case R.id.btn_delete:
			finish();
			break;
		}

	}

	// 等到编辑好的数据
	private void setInTask() {
		mTask.setTheme(et_item_theme.getText().toString().trim());
		mTask.setBuilder(tv_builder.getText().toString().trim());
		mTask.setDeadline(et_item_deadline.getText().toString().trim());
		mTask.setTask(et_item_context.getText().toString().trim());
		mTask.setIscomplete(false);
		mTask.setIsdeclare(false);
		mTask.setShow(true);
	}

	public static TaskModel getmTask() {
		return mTask;
	}

	public static void setmTask(TaskModel mTask) {
		TaskCheckOrEditActivity.mTask = mTask;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
		case 101:
			peoplePosition = data.getIntegerArrayListExtra("checkedPeoplePosition");
			if(peoplePosition != null){
				checkPeopleTV.setText("");
				peoplelist.clear();
				for(int p:peoplePosition){
					UserInfor people = IMuClubActivity.PeopleList.get(p);
					peoplelist.add(people);
					checkPeopleTV.append(people.getUsername()+" ");
					System.out.println(people.getUsername()+">>>>>>>>>>>>>>>>.");
				}
				System.out.println("获得的总人数为："+peoplelist.size());
			}else{
				System.out.println(">>>>>>>>>>>>>>>>>>>>>no people position");
			}
			break;
		}
	}
	
}
