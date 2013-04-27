package com.lbk.app.weiliao.ui;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.ui.adapter.MainFragmentAdapter;
import com.lbk.app.weiliao.ui.views.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity3 extends FragmentActivity {
	
	ViewPager viewPager;
	MainFragmentAdapter fragmentAdapter;
	
	private RelativeLayout rl_add_friend, rl_add_chat;
	

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main3);
		
		rl_add_chat = (RelativeLayout) findViewById(R.id.rl_add_chat);
		rl_add_friend = (RelativeLayout) findViewById(R.id.rl_add_friend);
		viewPager = (ViewPager) findViewById(R.id.tabpager);
		fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
		
		viewPager.setAdapter(fragmentAdapter);
		TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);
		indicator.setCurrentItem(1);
		
		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				showTopMenu(arg0);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		rl_add_chat.setOnClickListener(topMenuOnClickListener);
		rl_add_friend.setOnClickListener(topMenuOnClickListener);
	}
	
	private OnClickListener topMenuOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.rl_add_friend:
					Toast.makeText(MainActivity3.this, "添加朋友", Toast.LENGTH_SHORT).show();
					break;
				case R.id.rl_add_chat:
					Toast.makeText(MainActivity3.this, "添加会话", Toast.LENGTH_SHORT).show();
					break;
		
				default:
					break;
			}
		}
	};
	
	/**
	 * 显示相关菜单
	 * @param pageIndex
	 */
	private void showTopMenu(int pageIndex){
		switch (pageIndex) {
			case 0:
				rl_add_friend.setVisibility(View.VISIBLE);
				rl_add_chat.setVisibility(View.GONE);
				break;
			case 1:
				rl_add_friend.setVisibility(View.GONE);
				rl_add_chat.setVisibility(View.VISIBLE);
				break;
			case 2:
				rl_add_friend.setVisibility(View.GONE);
				rl_add_chat.setVisibility(View.GONE);
				break;
			case 3:
				rl_add_friend.setVisibility(View.GONE);
				rl_add_chat.setVisibility(View.GONE);
				break;
			default:
				break;
		}
	}
	
}
