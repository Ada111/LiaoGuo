package com.lbk.app.weiliao.ui;

import java.util.ArrayList;
import java.util.List;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.bean.Chat;
import com.lbk.app.weiliao.bean.Friend;
import com.lbk.app.weiliao.ui.adapter.ChatListAdapter;
import com.lbk.app.weiliao.ui.adapter.FriendsListAdapter;
import com.lbk.app.weiliao.utils.MyLog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainTabChatFragment extends Fragment {

	@Override
	public void onAttach(Activity activity) {
//		MyLog.i("--> MainTabChatFragment.onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
//		MyLog.i("--> MainTabChatFragment.onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.main_tab_chat, container, false);
		ListView lv_friends = (ListView) rootView.findViewById(R.id.lv_chat);
		
		List<Chat> chats = new ArrayList<Chat>();
		
		Chat chat = new Chat();
		chat.setFriendName("稻草人");
		chat.setContent("欢迎您使用微聊，如果您在使用过程中有任何的问题或建议，记得给我发信反馈哦。");
		chats.add(chat);

		lv_friends.setAdapter(new ChatListAdapter(getActivity(), chats));
		
		
//		MyLog.i("--> MainTabChatFragment.onCreateView");
		return rootView;
	}
	
}
