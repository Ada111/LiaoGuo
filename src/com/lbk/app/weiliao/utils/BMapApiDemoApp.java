package com.lbk.app.weiliao.utils;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.Bmob;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.*;
import com.baidu.mapapi.map.MKEvent;

public class BMapApiDemoApp extends Application {
	
	static BMapApiDemoApp mDemoApp;
	
	//百度MapAPI的管理类
	public BMapManager mBMapMan = null;
	
	// 授权Key
	// TODO: 请输入您的Key,
	// 申请地址：http://dev.baidu.com/wiki/static/imap/key/
	public String mStrKey = "CC785368D20134344C5863CF259A6939E9F60314";
	boolean m_bKeyRight = true;	// 授权Key正确，验证通过
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			Log.d("MyGeneralListener", "onGetNetworkState error is "+ iError);
			Toast.makeText(BMapApiDemoApp.mDemoApp.getApplicationContext(), "您的网络出错啦！",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			Log.d("MyGeneralListener", "onGetPermissionState error is "+ iError);
			if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(BMapApiDemoApp.mDemoApp.getApplicationContext(), 
						"请在BMapApiDemoApp.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
				BMapApiDemoApp.mDemoApp.m_bKeyRight = false;
			}
		}
		
	}
	
	@Override
    public void onCreate() {
		mDemoApp = this;
		mBMapMan = new BMapManager(this);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		super.onCreate();
		
		JPushInterface.setDebugMode(true); 	//设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        
        // Bmob
        Bmob.initialize(this, "e42f5da6b5c8af2a3d31e1dbb8b2f20b");	// 漫长的孕育
        
        // Parse
//        String YOUR_APPLICATION_ID = "R3nAXUFhTmjQ7GvgQAw93qUXKnWucWZ3dX9AVchM";
//		String YOUR_CLIENT_KEY = "GjIa3JjKI4NZHkOvN6uc69zt2aJ8SX8wErhmvLyx";
//
//		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
	}
	@Override
	//建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onTerminate();
	}

}
