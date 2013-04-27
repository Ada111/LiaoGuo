package com.lbk.app.weiliao.ui.adapter;

import com.lbk.app.weiliao.ui.MainTabChatFragment;
import com.lbk.app.weiliao.ui.MainTabEtcFragment;
import com.lbk.app.weiliao.ui.MainTabFreindsFragment;
import com.lbk.app.weiliao.ui.MainTabTimelineFragment;
import com.lbk.app.weiliao.ui.views.TitleProvider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainFragmentAdapter extends FragmentPagerAdapter implements TitleProvider {
	
	// 主界面FragmentCount总数
	private static final int FragmentCount = 4;
	
	protected static final String[] CONTENT = new String[] { "通讯录", "微聊", "朋友们", "设置"};

	public MainFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
			case 0:
				
				return new MainTabFreindsFragment();
			case 1:
				
				return new MainTabChatFragment();
			case 2:
	
				return new MainTabTimelineFragment();
			case 3:
	
				return new MainTabEtcFragment();
	
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return FragmentCount;
	}

	@Override
	public String getTitle(int position) {
		// TODO Auto-generated method stub
		return CONTENT[position % CONTENT.length];
	}

}
