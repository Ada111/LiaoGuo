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
	
	//�ٶ�MapAPI�Ĺ�����
	public BMapManager mBMapMan = null;
	
	// ��ȨKey
	// TODO: ����������Key,
	// �����ַ��http://dev.baidu.com/wiki/static/imap/key/
	public String mStrKey = "CC785368D20134344C5863CF259A6939E9F60314";
	boolean m_bKeyRight = true;	// ��ȨKey��ȷ����֤ͨ��
	
	// �����¼���������������ͨ�������������Ȩ��֤�����
	public static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			Log.d("MyGeneralListener", "onGetNetworkState error is "+ iError);
			Toast.makeText(BMapApiDemoApp.mDemoApp.getApplicationContext(), "���������������",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			Log.d("MyGeneralListener", "onGetPermissionState error is "+ iError);
			if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
				// ��ȨKey����
				Toast.makeText(BMapApiDemoApp.mDemoApp.getApplicationContext(), 
						"����BMapApiDemoApp.java�ļ�������ȷ����ȨKey��",
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
		
		JPushInterface.setDebugMode(true); 	//���ÿ�����־,����ʱ��ر���־
        JPushInterface.init(this);     		// ��ʼ�� JPush
        
        // Bmob
        Bmob.initialize(this, "e42f5da6b5c8af2a3d31e1dbb8b2f20b");	// ����������
        
        // Parse
//        String YOUR_APPLICATION_ID = "R3nAXUFhTmjQ7GvgQAw93qUXKnWucWZ3dX9AVchM";
//		String YOUR_CLIENT_KEY = "GjIa3JjKI4NZHkOvN6uc69zt2aJ8SX8wErhmvLyx";
//
//		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
	}
	@Override
	//��������app���˳�֮ǰ����mapadpi��destroy()�����������ظ���ʼ��������ʱ������
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onTerminate();
	}

}
