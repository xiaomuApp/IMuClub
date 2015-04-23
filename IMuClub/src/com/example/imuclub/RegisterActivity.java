package com.example.imuclub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

import com.example.model.UserInfor;

public class RegisterActivity extends Activity {

	private Button btn_register_return,btn_register;
	private EditText name, password, id, club;
	private UserInfor user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		btn_register_return = (Button) findViewById(R.id.btn_register_return);
		btn_register_return.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				register(v);
				finish();
			}
		});
		
		name = (EditText) findViewById(R.id.et_name);
		password = (EditText) findViewById(R.id.et_pin);
		id = (EditText) findViewById(R.id.et_num);
		club = (EditText) findViewById(R.id.et_club);

	}

	// 用户注册
	public void register(View view) {
		user = new UserInfor();
		user.setUsername(name.getText().toString());
		user.setPassword(password.getText().toString());
		user.setId(id.getText().toString());
		user.setClub(club.getText().toString());
		user.signUp(this, new SaveListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(RegisterActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
				password.setText("");
			}
		});
	}
}
