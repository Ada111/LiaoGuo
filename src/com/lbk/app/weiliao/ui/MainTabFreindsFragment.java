package com.lbk.app.weiliao.ui;

import java.util.ArrayList;
import java.util.List;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.bean.Friend;
import com.lbk.app.weiliao.ui.adapter.FriendsListAdapter;
import com.lbk.app.weiliao.ui.adapter.MyContactsAdapter;
import com.lbk.app.weiliao.ui.views.StickyListHeadersListView;
import com.lbk.app.weiliao.utils.MyLog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainTabFreindsFragment extends Fragment implements OnScrollListener {
	
	private int firstVisible;
	private static final String KEY_LIST_POSITION = "KEY_LIST_POSITION";
	private MyContactsAdapter adapter;

	@Override
	public void onAttach(Activity activity) {
//		MyLog.i("--> MainTabFreindsFragment.onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
//		MyLog.i("--> MainTabFreindsFragment.onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.main_tab_freinds, container,
				false);
		ListView lv_friends = (ListView) rootView.findViewById(R.id.lv_friends);
		RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.rl_addFriend);
		
		rl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "ÃÌº”≈Û”—", Toast.LENGTH_SHORT).show();
			}
		});

		List<Friend> friends = new ArrayList<Friend>();
		
		for (int i = 0; i < 20; i++) {
			Friend friend = new Friend();
			friend.setName(i+"µæ≤›»À"+i);
			friends.add(friend);
		}

		lv_friends.setAdapter(new FriendsListAdapter(getActivity(), friends));
		
		
//		StickyListHeadersListView stickyList = (StickyListHeadersListView) rootView.findViewById(R.id.list);
//		stickyList.setOnScrollListener(this);
////		stickyList.setOnItemClickListener(this);
////		stickyList.setOnHeaderClickListener(this);
//
//		if (savedInstanceState != null) {
//			firstVisible = savedInstanceState.getInt(KEY_LIST_POSITION);
//		}
//
//		//stickyList.addHeaderView(getLayoutInflater().inflate(R.layout.list_header, null));
//		//stickyList.addFooterView(getLayoutInflater().inflate(R.layout.list_footer, null));
//		stickyList.setEmptyView(rootView.findViewById(R.id.empty));
//		adapter = new MyContactsAdapter(getActivity(), friends);
//		stickyList.setAdapter(adapter);
//		stickyList.setSelection(firstVisible);
//
//		stickyList.setDrawingListUnderStickyHeader(true);

//		new Handler().postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
////				adapter.clearAll();
//				adapter.notifyDataSetChanged();
//				Toast.makeText(getActivity(), "notifyDataSetChanged", Toast.LENGTH_SHORT).show();
//			}
//		}, 5000);
		

		return rootView;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisible = firstVisibleItem;
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	
}
