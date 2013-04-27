package com.lbk.app.weiliao.ui;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.ui.qrcode.CaptureActivityPortrait;
import com.lbk.app.weiliao.ui.views.MenuItem;
import com.lbk.app.weiliao.ui.views.PopupMenu;
import com.lbk.app.weiliao.ui.views.PopupMenu.OnItemSelectedListener;
import com.lbk.app.weiliao.utils.MyLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainTabTimelineFragment extends Fragment implements OnItemSelectedListener {
	
	private final static int SEARCH_NUMBER = 0;
    private final static int SAOYISAO = 1;
    private final static int TONGXUNLU = 2;
	
	@Override
	public void onAttach(Activity activity) {
//		MyLog.i("--> MainTabTimelineFragment.onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
//		MyLog.i("--> MainTabTimelineFragment.onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.main_tab_timeline, container, false);
		
		RelativeLayout rl_add_friend = (RelativeLayout) rootView.findViewById(R.id.rl_add_friend);
		rl_add_friend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Create Instance
		        PopupMenu menu = new PopupMenu(getActivity());
		        menu.setHeaderTitle("添加朋友");
		        // Set Listener
		        menu.setOnItemSelectedListener(MainTabTimelineFragment.this);
		        // Add Menu (Android menu like style)
		        menu.add(SEARCH_NUMBER, R.string.add_friend_search_number).setIcon(
		                getResources().getDrawable(R.drawable.v2_ic_add_friends));
		        menu.add(SAOYISAO, R.string.add_friend_saoyisao).setIcon(
		                getResources().getDrawable(R.drawable.v2_ic_add_friends));
		        menu.add(TONGXUNLU, R.string.add_friend_tongxunl).setIcon(
		                getResources().getDrawable(R.drawable.v2_ic_add_friends));
		        menu.show(v);
			}
		});
		
		
//		MyLog.i("--> MainTabTimelineFragment.onCreateView");
		return rootView;
	}

	@Override
	public void onItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case SEARCH_NUMBER:
	        	Toast.makeText(getActivity(), "搜号码", Toast.LENGTH_SHORT).show();
	            break;
	        case SAOYISAO:
	        	Toast.makeText(getActivity(), "扫一扫", Toast.LENGTH_SHORT).show();
	        	//打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(getActivity(),CaptureActivityPortrait.class);
				startActivityForResult(openCameraIntent, 0);
	            break;
	
	        case TONGXUNLU:
	        	Toast.makeText(getActivity(), "从手机通讯录列表添加", Toast.LENGTH_SHORT).show();
	            break;
        }
		
	}
}
