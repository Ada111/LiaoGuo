package com.lbk.app.weiliao.ui.adapter;

import java.util.List;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.bean.Chat;
import com.lbk.app.weiliao.bean.Friend;
import com.lbk.app.weiliao.ui.ChatActivity;
import com.lbk.app.weiliao.ui.LoginActivity;
import com.lbk.app.weiliao.ui.MainActivity3;
import com.lbk.app.weiliao.ui.adapter.FriendsListAdapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatListAdapter extends BaseAdapter {
	
	private List<Chat> chats;
	private Context context;
	private LayoutInflater layoutInflater;
	
	public ChatListAdapter(Context context, List<Chat> chats){
		this.chats = chats;
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chats.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return chats.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.chat_list_item, null);
			viewHolder = new ViewHolder();
			
			viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_head);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_friendName);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Chat chat = chats.get(position);
		viewHolder.tv_name.setText(chat.getFriendName());
		
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(context, "进入聊天界面", Toast.LENGTH_SHORT).show();
				context.startActivity(new Intent(context, ChatActivity.class));
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
	}

}
