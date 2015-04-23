package com.example.adapter;

import java.util.List;

import com.example.imuclub.R;
import com.example.model.MessageModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class MessageItemAdapter extends BaseAdapter{

	private List<MessageModel> mData;
	private Context context;
	
	public MessageItemAdapter(Activity context, List<MessageModel> mData) {
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
		View view=View.inflate(context, R.layout.list_message_item, null);
		
		TextView tv_message_operation = (TextView) view.findViewById(R.id.tv_message_operation);
		TextView tv_message_object = (TextView) view.findViewById(R.id.tv_message_object);
		TextView tv_update_time = (TextView) view.findViewById(R.id.tv_update_time);
		
		MessageModel[] message = new MessageModel[mData.size()];
		
		for (int i = 0; i <= position; i++) {
			message[position] = mData.get(position); // 依次获得列表项
			tv_message_operation.setText(message[position].getOperation());
			tv_message_object.setText(message[position].getObject());
			tv_update_time.setText(message[position].getUpdatetime());
		}
		
		return view;
	}

}
