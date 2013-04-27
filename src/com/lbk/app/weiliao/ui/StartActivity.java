package com.lbk.app.weiliao.ui;

import com.lbk.app.weiliao.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
//		Bmob.initialize(this, "e42f5da6b5c8af2a3d31e1dbb8b2f20b");	// Âþ³¤µÄÔÐÓý
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				Intent intent = new Intent (StartActivity.this, LoginActivity.class);			
				startActivity(intent);			
				StartActivity.this.finish();
			}
		}, 2000);
	}
	
}
