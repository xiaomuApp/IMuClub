package com.example.imuclub;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

import com.example.model.TaskModel;
import com.example.model.UserInfor;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

@SuppressLint("ResourceAsColor")
public class IMuClubActivity extends FragmentActivity implements
		OnTouchListener, OnClickListener, OnPageChangeListener {
	//传入的任务
	private static TaskModel mTask;
	
	//标志是否进入选择状态
	public static boolean IsCheck = false;
	//标志是否全选
	public static boolean IsSelectAll = false;
	
	// 主界面控件
	private Button btn_menu; // 菜单按钮
	private Button btn_add; // 新建按钮
	private Button btn_sure;    //全选按钮
	private TextView tv_title; // 标题
	private ViewPager vp_myproject; // 我的项目滑动页
	private ViewPager vp_mytask; // 我的任务滑动页
	private ViewPager vp_peoplelist; // 人员列表滑动页
	
	private LinearLayout ll_title_bottom_select;    //底部选择区
	private Button btn_bottom_sure;         //底部确定按钮
	private Button btn_bottom_cancel;         //底部取消按钮

	// 主界面tab控件
	private LinearLayout ll_tab01; // 第一个tab
	private LinearLayout ll_tab02; // 第二个tab
	private LinearLayout ll_tab03; // 第三个tab

	private TextView tv_allproject; // 全部项目
	private TextView tv_takepartByme; // 我参加的
	private TextView tv_buildByme; // 我创建的
	private ImageView iv_tabline; // tab下滑块
	private TextView tv_username; // 我创建的

	// tab界面
	Fragment tab01;
	Fragment tab02;
	Fragment tab03;

	// 左侧菜单控件
	private Button ll_myproject; // 我的项目
	private Button ll_mytask; // 我的任务
	private Button ll_peoplelist; // 人员列表
	private Button ll_mymessage; // 我的消息
	private LinearLayout ll_exit_login;     //退出登录

	// 程序变量
	private SlidingMenu slidingmenu; // 滑动菜单
	private FragmentPagerAdapter mAdapter; // FragmentPager适配器
	private List<Fragment> mDatas; // 三个Fragment
	private int mScreen1_3; // 记录屏幕的三分之一长度
	private int mCurrentPageIndex; // 记录当前页面下标

	private static UserInfor username;

	
	// 操作标志
	private int selectItem = 0; // 0代表选择“我的项目”，1代表“我的任务”，2代表“人员列表”，3代表“我的消息”

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imuclub);
		// 获取登录账号的信息
		Intent intent = getIntent();
		mTask = (TaskModel) intent.getSerializableExtra("Task");
		if(mTask!=null){
			mTask.setShow(true);
		}
		// 获取数据
		username =BmobUser.getCurrentUser(IMuClubActivity.this, UserInfor.class);//获取当前登陆者的信息，姓名，学号
		
		initMenu(); // 初始化Menu
		initView(); // 初始化View
		initTabline(); // 初始化tabline

	}

	// 初始化Menu
	private void initMenu() {
		slidingmenu = new SlidingMenu(this);
		slidingmenu.setMode(SlidingMenu.LEFT); // 左边模式
		slidingmenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
		slidingmenu.setTouchModeAbove(SlidingMenu.LEFT); // 左边可触摸
		slidingmenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingmenu.setMenu(R.layout.slidingmenu);
	}

	// 初始化tabline
	private void initTabline() {
		iv_tabline = (ImageView) findViewById(R.id.iv_tabline); // tabline图形控件

		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_3 = outMetrics.widthPixels / 3;
		LayoutParams lp = iv_tabline.getLayoutParams();
		lp.width = mScreen1_3;
		iv_tabline.setLayoutParams(lp);
	}

	// 初始化View
	private void initView() {
		// 主界面控件
		btn_menu = (Button) findViewById(R.id.btn_menu); // 菜单按钮
		btn_add = (Button) findViewById(R.id.btn_add); // 添加按钮
		btn_sure = (Button) findViewById(R.id.btn_sure);      //全选按钮
		tv_title = (TextView) findViewById(R.id.tv_title); // 标题
		vp_myproject = (ViewPager) findViewById(R.id.vp_myproject); // 我的项目滑动页面
		vp_mytask = (ViewPager) findViewById(R.id.vp_mytask); // 我的任务滑动页
		vp_peoplelist = (ViewPager) findViewById(R.id.vp_peoplelist); // 人员列表滑动页

		ll_title_bottom_select = (LinearLayout) findViewById(R.id.ll_title_bottom_select);   //底部选择区
		btn_bottom_sure = (Button) findViewById(R.id.btn_bottom_sure);         //底部确定按钮
		btn_bottom_cancel = (Button) findViewById(R.id.btn_bottom_cancel);      //底部取消按钮
		
		// 主界面tab控件
		ll_tab01 = (LinearLayout) findViewById(R.id.ll_tab01);
		ll_tab02 = (LinearLayout) findViewById(R.id.ll_tab02);
		ll_tab03 = (LinearLayout) findViewById(R.id.ll_tab03);

		tv_allproject = (TextView) findViewById(R.id.tv_allproject); // 全部项目
		tv_takepartByme = (TextView) findViewById(R.id.tv_takepartByme); // 我参加的
		tv_buildByme = (TextView) findViewById(R.id.tv_buildByme); // 我创建的
		tv_username = (TextView) findViewById(R.id.tv_user_name); // 名字
		tv_username.setText(username.getUsername());
		iv_tabline = (ImageView) findViewById(R.id.iv_tabline); // tab下滑块

		// 左侧菜单控件
		ll_myproject = (Button) findViewById(R.id.ll_myproject); // 我的项目
		ll_mytask = (Button) findViewById(R.id.ll_mytask); // 我的任务
		ll_peoplelist = (Button) findViewById(R.id.ll_peoplelist); // 人员列表
		ll_mymessage = (Button) findViewById(R.id.ll_mymessage); // 我的消息
		ll_exit_login = (LinearLayout) findViewById(R.id.ll_exit_login);   //退出登录

		// 程序变量

		initFragment(); // 初始化Fragment

		// 添加页面滚动监听器
		vp_myproject.setOnPageChangeListener(this);
		vp_mytask.setOnPageChangeListener(this);
		vp_peoplelist.setOnPageChangeListener(this);

		// 设置按下监听器
		btn_menu.setOnTouchListener(this);
		btn_add.setOnTouchListener(this);
		btn_sure.setOnTouchListener(this);
		btn_bottom_sure.setOnTouchListener(this);
		btn_bottom_cancel.setOnTouchListener(this);
		ll_myproject.setOnTouchListener(this);
		ll_mytask.setOnTouchListener(this);
		ll_peoplelist.setOnTouchListener(this);
		ll_mymessage.setOnTouchListener(this);
		ll_exit_login.setOnTouchListener(this);

		// 设置点击监听器(菜单)
		btn_menu.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
		btn_bottom_sure.setOnClickListener(this);
		btn_bottom_cancel.setOnClickListener(this);
		ll_myproject.setOnClickListener(this);
		ll_mytask.setOnClickListener(this);
		ll_peoplelist.setOnClickListener(this);
		ll_mymessage.setOnClickListener(this);
		ll_exit_login.setOnClickListener(this);

		// 设置点击监听器(tab)
		ll_tab01.setOnClickListener(this);
		ll_tab02.setOnClickListener(this);
		ll_tab03.setOnClickListener(this);

	}

	// 根据不同选择初始化Fragment
	private void initFragment() {
		resetTextView(); // 将TextView颜色置为初始状态
		
		//如果在勾选状态，设置侧滑菜单无效化
		if (IsCheck) {
			slidingmenu.setSlidingEnabled(false);
			btn_menu.setEnabled(false);       //菜单按钮无效化
		}
		else {
			slidingmenu.setSlidingEnabled(true);
			btn_menu.setEnabled(true);
		}
	
		tv_allproject.setTextColor(Color.parseColor("#2680ef"));
		mCurrentPageIndex = 0; // 设置当前页面
		switch (selectItem) {
		case 0: // 我的项目
			mDatas = new ArrayList<Fragment>();
			tab01 = new AllProjectFragment();
			tab02 = new TakePartByMeFragment();
			tab03 = new BuildByMeFragment();
			mDatas.add(tab01);
			mDatas.add(tab02);
			mDatas.add(tab03);
			mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
				@Override
				public int getCount() {
					return mDatas.size();
				}

				@Override
				public Fragment getItem(int position) {
					return mDatas.get(position);
				}
			};
			vp_myproject.setAdapter(mAdapter);
			break;

		case 1:
			mDatas = new ArrayList<Fragment>();
			tab01 = new AllTaskFragment();
			tab02 = new CompletedTaskFragment();
			tab03 = new NotCompletedTaskFragment();
			mDatas.add(tab01);
			mDatas.add(tab02);
			mDatas.add(tab03);
			mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
				@Override
				public int getCount() {
					return mDatas.size();
				}

				@Override
				public Fragment getItem(int position) {
					return mDatas.get(position);
				}
			};
			vp_mytask.setAdapter(mAdapter);
			break;
		case 2:
			mDatas = new ArrayList<Fragment>();
			tab01 = new AllPeopleFragment();
			tab02 = new MinisterFragment();
			tab03 = new FreshManFragment();
			mDatas.add(tab01);
			mDatas.add(tab02);
			mDatas.add(tab03);
			mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
				@Override
				public int getCount() {
					return mDatas.size();
				}

				@Override
				public Fragment getItem(int position) {
					return mDatas.get(position);
				}
			};
			vp_peoplelist.setAdapter(mAdapter);
			break;
		}
	}

	// 让字体颜色恢复初始状态
	private void resetTextView() {
		tv_allproject.setTextColor(Color.parseColor("#000000"));
		tv_takepartByme.setTextColor(Color.parseColor("#000000"));
		tv_buildByme.setTextColor(Color.parseColor("#000000"));
	}

	// 接触事件响应
	@SuppressLint({ "ResourceAsColor", "ClickableViewAccessibility" })
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			switch (v.getId()) {
			case R.id.btn_menu:
				btn_menu.setBackgroundResource(R.drawable.menu_down);
				break;
			case R.id.btn_add:
				btn_add.setBackgroundResource(R.drawable.title_add_down);
				break;
			case R.id.btn_sure:
				btn_sure.setBackgroundResource(R.drawable.title_sure_down);
				break;
			case R.id.btn_bottom_sure:
				btn_bottom_sure.setBackgroundColor(Color.parseColor("#1b59a7"));
				break;
			case R.id.btn_bottom_cancel:
				btn_bottom_cancel.setBackgroundColor(Color.parseColor("#1b59a7"));
				break;
			case R.id.ll_myproject:
				ll_myproject.setBackgroundColor(Color.parseColor("#888888"));
				break;
			case R.id.ll_mytask:
				ll_mytask.setBackgroundColor(Color.parseColor("#888888"));
				break;
			case R.id.ll_peoplelist:
				ll_peoplelist.setBackgroundColor(Color.parseColor("#888888"));
				break;
			case R.id.ll_mymessage:
				ll_mymessage.setBackgroundColor(Color.parseColor("#888888"));
				break;
			case R.id.ll_exit_login:
				ll_exit_login.setBackgroundColor(Color.parseColor("#888888"));
				break;
			}
		} else if (action == MotionEvent.ACTION_UP) {
			switch (v.getId()) {
			case R.id.btn_menu:
				btn_menu.setBackgroundResource(R.drawable.menu_up);
				break;
			case R.id.btn_add:
				btn_add.setBackgroundResource(R.drawable.title_add);
				break;
			case R.id.btn_sure:
				btn_sure.setBackgroundResource(R.drawable.title_sure);
				break;
			case R.id.btn_bottom_sure:
				btn_bottom_sure.setBackgroundColor(Color.parseColor("#2680ef"));
				break;
			case R.id.btn_bottom_cancel:
				btn_bottom_cancel.setBackgroundColor(Color.parseColor("#2680ef"));
				break;
			case R.id.ll_myproject:
				ll_myproject.setBackgroundColor(Color.parseColor("#555555"));
				break;
			case R.id.ll_mytask:
				ll_mytask.setBackgroundColor(Color.parseColor("#555555"));
				break;
			case R.id.ll_peoplelist:
				ll_peoplelist.setBackgroundColor(Color.parseColor("#555555"));
				break;
			case R.id.ll_mymessage:
				ll_mymessage.setBackgroundColor(Color.parseColor("#555555"));
				break;
			case R.id.ll_exit_login:
				ll_exit_login.setBackgroundColor(Color.parseColor("#555555"));
				break;
			}
		}
		return false;
	}

	// 点击事件响应
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_menu: // 菜单按钮
			slidingmenu.toggle();
			break;
		case R.id.btn_add:
			Intent addintent = new Intent();
			addintent.setClassName("com.example.imuclub",
					"com.example.imuclub.TaskCheckOrEditActivity");
			addintent.putExtra("project_theme", "未编辑");
			addintent.putExtra("project_deadline", "未编辑");
			addintent.putExtra("project_builder", username.getUsername());//传入账号信息
			addintent.putExtra("project_task", "未编辑");
			addintent.putExtra("project_iscompleted", false);
			addintent.putExtra("project_tab", 4);
			startActivity(addintent);

			break;
		case R.id.btn_sure:
			//全选按钮
			IsSelectAll = true;
			initFragment();
			break;
		case R.id.btn_bottom_sure:
			//底部确定按钮
			IsCheck = false;
        	//设置标题栏按钮
        	btn_add.setVisibility(View.VISIBLE);
        	btn_sure.setVisibility(View.INVISIBLE);
        	ll_title_bottom_select.setVisibility(View.INVISIBLE);
        	initFragment();
			break;
		case R.id.btn_bottom_cancel:
			//底部取消按钮
			IsCheck = false;
        	//设置标题栏按钮
        	btn_add.setVisibility(View.VISIBLE);
        	btn_sure.setVisibility(View.INVISIBLE);
        	ll_title_bottom_select.setVisibility(View.INVISIBLE);
        	initFragment();
			break;
		case R.id.ll_myproject: // 菜单栏：社团活动
			slidingmenu.toggle();
			// 设置标题文字
			tv_title.setText(R.string.menu_userselect1);
			// 设置tab文字
			tv_allproject.setText(R.string.myproject_tab1);
			tv_takepartByme.setText(R.string.myproject_tab2);
			tv_buildByme.setText(R.string.myproject_tab3);
			// 显示添加按钮
			btn_add.setVisibility(View.VISIBLE);
			// 设置操作标志
			selectItem = 0;
			// 设置滑动页的可视性
			vp_myproject.setVisibility(View.VISIBLE);
			vp_mytask.setVisibility(View.GONE);
			vp_peoplelist.setVisibility(View.GONE);
			initFragment();
			break;
		case R.id.ll_mytask: // 菜单栏：我的任务
			slidingmenu.toggle();
			// 设置标题文字
			tv_title.setText(R.string.menu_userselect2);
			// 设置tab文字
			tv_allproject.setText(R.string.mytask_tab1);
			tv_takepartByme.setText(R.string.mytask_tab2);
			tv_buildByme.setText(R.string.mytask_tab3);
			// 隐藏添加按钮
			btn_add.setVisibility(View.INVISIBLE);
			// 设置操作标志
			selectItem = 1;
			// 设置滑动页的可视性
			vp_myproject.setVisibility(View.GONE);
			vp_mytask.setVisibility(View.VISIBLE);
			vp_peoplelist.setVisibility(View.GONE);
			initFragment();
			break;
		case R.id.ll_peoplelist: // 菜单栏：人员列表
			slidingmenu.toggle();
			// 设置标题文字
			tv_title.setText(R.string.menu_userselect3);
			// 设置tab文字
			tv_allproject.setText(R.string.peoplelist_tab1);
			tv_takepartByme.setText(R.string.peoplelist_tab2);
			tv_buildByme.setText(R.string.peoplelist_tab3);
			// 显示添加按钮
			btn_add.setVisibility(View.VISIBLE);
			// 设置操作标志
			selectItem = 2;
			// 设置滑动页的可视性
			vp_myproject.setVisibility(View.GONE);
			vp_mytask.setVisibility(View.GONE);
			vp_peoplelist.setVisibility(View.VISIBLE);
			initFragment();
			break;
		case R.id.ll_mymessage: // 菜单栏：我的消息
			slidingmenu.toggle();
			Intent messageIntent = new Intent();
			messageIntent.setClassName("com.example.imuclub",
					"com.example.imuclub.MyMessageActivity");
			startActivity(messageIntent);
			break;
		case R.id.ll_tab01:
			resetTextView(); // 将TextView颜色置为初始状态
			tv_allproject.setTextColor(Color.parseColor("#2680ef"));
			mCurrentPageIndex = 0;

			LinearLayout.LayoutParams lp01 = (android.widget.LinearLayout.LayoutParams) iv_tabline
					.getLayoutParams();
			lp01.leftMargin = 0;
			iv_tabline.setLayoutParams(lp01);

			switch (selectItem) {
			case 0:
				vp_myproject.setCurrentItem(0);
				break;
			case 1:
				vp_mytask.setCurrentItem(0);
				break;
			case 2:
				vp_peoplelist.setCurrentItem(0);
				break;
			}
			break;
		case R.id.ll_tab02:
			resetTextView(); // 将TextView颜色置为初始状态
			tv_takepartByme.setTextColor(Color.parseColor("#2680ef"));
			mCurrentPageIndex = 1;

			LinearLayout.LayoutParams lp02 = (android.widget.LinearLayout.LayoutParams) iv_tabline
					.getLayoutParams();
			lp02.leftMargin = mScreen1_3;
			iv_tabline.setLayoutParams(lp02);

			switch (selectItem) {
			case 0:
				vp_myproject.setCurrentItem(1);
				break;
			case 1:
				vp_mytask.setCurrentItem(1);
				break;
			case 2:
				vp_peoplelist.setCurrentItem(1);
				break;
			}
			break;
		case R.id.ll_tab03:
			resetTextView(); // 将TextView颜色置为初始状态
			tv_buildByme.setTextColor(Color.parseColor("#2680ef"));
			mCurrentPageIndex = 2;

			LinearLayout.LayoutParams lp03 = (android.widget.LinearLayout.LayoutParams) iv_tabline
					.getLayoutParams();
			lp03.leftMargin = 2 * mScreen1_3;
			iv_tabline.setLayoutParams(lp03);

			switch (selectItem) {
			case 0:
				vp_myproject.setCurrentItem(2);
				break;
			case 1:
				vp_mytask.setCurrentItem(2);
				break;
			case 2:
				vp_peoplelist.setCurrentItem(2);
				break;
			}
			break;
		case R.id.ll_exit_login:
			BmobUser.logOut(this);
			BmobUser.getCurrentUser(IMuClubActivity.this, UserInfor.class);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int position) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPx) {
		// 设置tabline移动效果
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) iv_tabline
				.getLayoutParams();
		if (mCurrentPageIndex == 0 && position == 0) { // 0->1
			lp.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentPageIndex
					* mScreen1_3);
		} else if (mCurrentPageIndex == 1 && position == 0) { // 1->0
			lp.leftMargin = (int) ((positionOffset - 1) * mScreen1_3 + mCurrentPageIndex
					* mScreen1_3);
		} else if (mCurrentPageIndex == 1 && position == 1) { // 1->2
			lp.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentPageIndex
					* mScreen1_3);
		} else if (mCurrentPageIndex == 2 && position == 1) { // 2->1
			lp.leftMargin = (int) ((positionOffset - 1) * mScreen1_3 + mCurrentPageIndex
					* mScreen1_3);
		}

		iv_tabline.setLayoutParams(lp);
	}

	@Override
	public void onPageSelected(int position) {
		resetTextView(); // 将TextView颜色置为初始状态
		switch (position) {
		case 0:
			tv_allproject.setTextColor(Color.parseColor("#2680ef"));
			break;
		case 1:
			tv_takepartByme.setTextColor(Color.parseColor("#2680ef"));
			break;
		case 2:
			tv_buildByme.setTextColor(Color.parseColor("#2680ef"));
			break;
		}

		mCurrentPageIndex = position;

	}
	
	
	public static TaskModel getmTask() {
		return mTask;
	}

	public static void setmTask(TaskModel mTask) {
		IMuClubActivity.mTask = mTask;
	}

	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		if (selectItem ==0 || selectItem == 1){    //只有在“社团活动”和“我的任务”里才出现菜单栏
			menu.add(0, 1, 1, "搜索");
			menu.add(0, 2, 2, "删除");
		}
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if(item.getItemId() == 1){
            Toast.makeText(this, "你选的是搜索", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == 2){
        	IsCheck = true;
        	//设置标题栏按钮
        	btn_add.setVisibility(View.INVISIBLE);
        	btn_sure.setVisibility(View.VISIBLE);
        	ll_title_bottom_select.setVisibility(View.VISIBLE);
        	initFragment();
        }
        return true;
    }
}
