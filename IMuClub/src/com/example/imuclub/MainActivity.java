package com.example.imuclub;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.model.UserInfor;

public class MainActivity extends Activity {

	private LinearLayout ll_loading_logo;
	private LinearLayout ll_loginlayout;
	private Button btn_login;

	private EditText et_login_user; // “账号”编辑框
	private EditText et_login_pin; // “密码”编辑框

	private Button btn_register;// 注册

	// push
	private UserInfor user;
	private String APPID = "5d04bd96d646a589a78979f7b509f4fe";// 服务器特有码
	private String InstallationId = "";
	private BmobQuery<UserInfor> peoplequery;
	private ArrayList<UserInfor> PeopleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 去掉状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		// logo布局
		ll_loading_logo = (LinearLayout) findViewById(R.id.ll_loading_logo);

		// login布局
		ll_loginlayout = (LinearLayout) findViewById(R.id.ll_loginlayout);
		// 登录按钮
		btn_login = (Button) findViewById(R.id.btn_login);

		// 注册
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 注册
				Intent intent = new Intent(MainActivity.this,
						RegisterActivity.class);
				startActivity(intent);

			}
		});

		// 获得账号编辑框ID
		// tv_tip_user = (TextView) findViewById(R.id.tv_tip_user);
		et_login_user = (EditText) findViewById(R.id.et_login_user);

		// 获得密码编辑框ID
		// tv_tip_pin = (TextView) findViewById(R.id.tv_tip_pin);
		et_login_pin = (EditText) findViewById(R.id.et_login_pin);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				ll_loading_logo.setVisibility(View.INVISIBLE);
				ll_loginlayout.setVisibility(View.VISIBLE);
			}
		}, 3000);

		// btn_login.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		//
		// if (check()) {
		// Intent intent = new Intent();
		// intent.setClass(MainActivity.this, IMuClubActivity.class);
		// intent.putExtra("username1", mUser); // 传入账号信息
		// startActivity(intent);
		// MainActivity.this.finish();
		//
		// } else {
		// return;
		// }
		// }
		//
		// });

		// push
		Bmob.initialize(this, APPID);// 在app启动第一个activity进行连接到服务器
		InstallationId = BmobInstallation.getInstallationId(this);// 获取登录的机子的id

		user = BmobUser.getCurrentUser(MainActivity.this, UserInfor.class);// 获取缓存的登陆者，不需要进行再次输入登陆者信息
		if (user != null)// 如果当前的登陆者信息不为空则直接登录不需要进行输入
		{
			user.setInstallationId(InstallationId);
			user.update(this, user.getObjectId(), new UpdateListener() {// 获取当前登录的用户的手机id，并进行修改进入user

						@Override
						public void onSuccess() {
							android.util.Log.d("输出", "修改成功");

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							android.util.Log.d("输出", "修改失败");

						}
					});

			PeopleList = new ArrayList<UserInfor>();
			peoplequery = new BmobQuery<UserInfor>();
			peoplequery.addWhereEqualTo("club", user.getClub());
			peoplequery.findObjects(MainActivity.this,
					new FindListener<UserInfor>() {

						@Override
						public void onSuccess(List<UserInfor> list) {
							for (UserInfor people : list) {
								PeopleList.add(people);
							}
							//android.util.Log.d("输出人数", String.valueOf(PeopleList.size()));

						}

						
						// 已从网上获取到与登陆者相同社团的人员信息，存放在PeopleList里面
						public void onError(int arg0, String arg1) {

						}
					});

			android.util.Log.d("输出人数", String.valueOf(PeopleList.size()));
			
			Intent intenta = new Intent(MainActivity.this,
					IMuClubActivity.class);
			startActivity(intenta);
			finish();
		} else {
			
			user = new UserInfor();
			
			btn_login.setOnClickListener(new OnClickListener() {// 登录按钮

						@Override
						public void onClick(View v) {
							user.setUsername(et_login_user.getText().toString()
									.trim());
							user.setPassword(et_login_pin.getText().toString()
									.trim());
							user.login(MainActivity.this, new SaveListener() {

								
								public void onSuccess() {

									Intent i = new Intent(MainActivity.this,
											IMuClubActivity.class);
									startActivity(i);
									Toast.makeText(MainActivity.this, "登陆成功",
											Toast.LENGTH_SHORT).show();
									finish();
								}

								@Override
								public void onFailure(int arg0, String arg1) {
									Toast.makeText(MainActivity.this, "登陆失败",
											Toast.LENGTH_SHORT).show();
								}
							});

						}
					});
		}

		// 初始化一些user数据
		CreatDefalutUser();

	}

	// 初始化一些user数据
	private void CreatDefalutUser() {

		// String user2 = "20132100101";
		// String pass2 = "xiaomu";

	}

}
