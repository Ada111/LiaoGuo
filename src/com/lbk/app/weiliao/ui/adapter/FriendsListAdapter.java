package com.lbk.app.weiliao.ui.adapter;

import java.util.List;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.bean.Friend;
import com.lbk.app.weiliao.utils.MyLog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsListAdapter extends BaseAdapter {
	
	private List<Friend> friends;
	private Context context;
	private LayoutInflater layoutInflater;
	
	public FriendsListAdapter(Context context, List<Friend> friends){
		this.context = context;
		this.friends = friends;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return friends.size();
	}

	@Override
	public Object getItem(int position) {
		return friends.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.friends_list_item, null);
			viewHolder = new ViewHolder();
			
			viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_head);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_friendName);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Friend friend = friends.get(position);
		viewHolder.tv_name.setText(friend.getName());
		
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, position+"  µ„Œ“∏…¬Ô£ø", Toast.LENGTH_SHORT).show();
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
	}

}
