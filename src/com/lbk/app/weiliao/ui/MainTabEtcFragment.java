package com.lbk.app.weiliao.ui;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.ui.qrcode.MyQRCodeActivity;
import com.lbk.app.weiliao.utils.MyLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainTabEtcFragment extends Fragment implements OnClickListener {
	
	@Override
	public void onAttach(Activity activity) {
//		MyLog.i("--> MainTabEtcFragment.onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
//		MyLog.i("--> MainTabEtcFragment.onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.main_tab_etc, container, false);
		
		RelativeLayout rl_erweima = (RelativeLayout) rootView.findViewById(R.id.rl_erweima);
		rl_erweima.setOnClickListener(this);
		
//		rootView.findViewById(R.id.。。。。)
		
		
		
//		MyLog.i("--> MainTabEtcFragment.onCreateView");
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.rl_erweima:
				// 二维码名片
//				Toast.makeText(getActivity(), "我的二维码名片", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getActivity(), MyQRCodeActivity.class));
				break;
	
			default:
				break;
		}
	}
}
