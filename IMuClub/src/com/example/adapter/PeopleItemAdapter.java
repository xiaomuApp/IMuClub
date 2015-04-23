package com.example.adapter;

import java.util.List;

import com.example.imuclub.R;
import com.example.model.PeopleModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class PeopleItemAdapter extends BaseAdapter{

	private List<PeopleModel> mData;
	private Context context;
	
	public PeopleItemAdapter(FragmentActivity context, List<PeopleModel> mData) {
		this.context=context;
		this.mData=mData;
	}

	//决定列表Item显示的个数
	@Override
	public int getCount() {
		return mData.size();
	}

	//根据position获得对应的Item数据
	@Override
	public Object getItem(int position) {
		return mData.get(position);
		}
	
	//根据position获得对应的ItemID
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	//创建列表item视图
	@SuppressLint({ "ViewHolder", "ResourceAsColor" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=View.inflate(context, R.layout.list_people_item, null);
		PeopleModel []friend=new PeopleModel[mData.size()];
		
		TextView tv_people_name = (TextView) view.findViewById(R.id.tv_people_name);
		Button btn_user_name = (Button) view.findViewById(R.id.btn_user_name);
		TextView tv_people_position = (TextView) view.findViewById(R.id.tv_people_position);

		
		for (int i=0;i<=position;i++){
			friend[position]=mData.get(position);      //依次获得列表项
			tv_people_name.setText(friend[position].getName());
			btn_user_name.setText(friend[position].getNickname());
			tv_people_position.setText(friend[position].getPosition());
			
		}
		return view;
	}

}
