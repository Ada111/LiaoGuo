package com.lbk.app.weiliao.push;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.bean.ChatMsgEntity;
import com.lbk.app.weiliao.ui.ChatActivity;
import com.lbk.app.weiliao.ui.adapter.ChatMsgAdapter;
import com.lbk.app.weiliao.ui.chat.MessageSQLService;
import com.lbk.app.weiliao.ui.chat.UserInfomation;
import com.lbk.app.weiliao.utils.ClippingSounds;
import com.lbk.app.weiliao.utils.DateUtil;
import com.lbk.app.weiliao.utils.MyLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
//	private static final String TAG = "MyReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		MyLog.i("onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            MyLog.i("接收Registration Id : " + regId);
            //send the Registration Id to your server...
        }else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())){
        	String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        	MyLog.i("接收UnRegistration Id : " + regId);
          //send the UnRegistration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	MyLog.i("接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	
        	showMessage(context, bundle.getString(JPushInterface.EXTRA_MESSAGE));
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
        	MyLog.i("接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            MyLog.i("接收到推送下来的通知的ID: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
        	MyLog.i("用户点击打开了通知");
            
        	//打开自定义的Activity
//        	Intent i = new Intent(context, TestActivity.class);
//        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	context.startActivity(i);
        	
        } else {
        	MyLog.i("Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	private void showMessage(Context context, String msg){
		
		String perfix = "#IMG#";	// 图片
		String perfix_s = "#SOUND#";	// 声音
		// 判断是不是推送的图片地址
		if(msg.startsWith(perfix)){
			String newMsg = msg.substring(5);
			MyLog.i("截取后的图片地址  = "+newMsg);
			
			saveImgMsg(context, newMsg);
			
		}else if(msg.startsWith(perfix_s)){
			String newMsg = msg.substring(7);
			MyLog.i("截取后的声音地址 = "+newMsg);
			
			saveSoundMsg(context, newMsg);
		}else{
			String TFuid = "2";
			
			ChatMsgEntity d = new ChatMsgEntity(msg,
					R.layout.list_say_me_item,
					UserInfomation.getUserID(context),
					DateUtil.getCurrentTiem());
			
			d.setTtmContent(msg);
//		d.setLayoutID(R.layout.list_say_me_item);
			d.setLayoutID(R.layout.list_say_he_item);
			d.setTtmType(1);
//		d.setTtmTuID(Integer.valueOf(UserInfomation.getUserID(context)));
//		d.setTtmToUserId(Integer.valueOf(TFuid));
			d.setTtmTuID(Integer.valueOf(TFuid));
			d.setTtmToUserId(Integer.valueOf(UserInfomation.getUserID(context)));
			d.setTtmTime(DateUtil.getCurrentTiem());
			
			MessageSQLService.getInstance(context).save(d);
			if(ChatActivity.list != null){
				ChatActivity.list.add(d);
				
				ChatActivity.talkList.setAdapter(new ChatMsgAdapter(context, ChatActivity.list,
						TFuid));
				ChatActivity.talkList.setSelection(ChatActivity.list.size());
			}
			
		}
		
		
	}
	
	private void saveImgMsg(Context context, String imgurl){
		String TFuid = "2";
		
		ChatMsgEntity d = new ChatMsgEntity(imgurl,
				R.layout.list_say_he_image,
				UserInfomation.getUserID(context),
				DateUtil.getCurrentTiem());

		d.setTtmContent(imgurl);
//		d.setLayoutID(R.layout.list_say_me_item);
		d.setLayoutID(R.layout.list_say_he_image);
		d.setTtmType(2);
//		d.setTtmTuID(Integer.valueOf(UserInfomation.getUserID(context)));
//		d.setTtmToUserId(Integer.valueOf(TFuid));
		d.setTtmTuID(Integer.valueOf(TFuid));
		d.setTtmToUserId(Integer.valueOf(UserInfomation.getUserID(context)));
		d.setTtmTime(DateUtil.getCurrentTiem());

		MessageSQLService.getInstance(context).save(d);
		ChatActivity.list.add(d);

		ChatActivity.talkList.setAdapter(new ChatMsgAdapter(context, ChatActivity.list,
				TFuid));
		ChatActivity.talkList.setSelection(ChatActivity.list.size());
	}
	
	private void saveSoundMsg(Context context, String soundUrl){
		
		saveLocSoundFile(soundUrl, ClippingSounds.TALKSOUND_FILE+convertUrlToFileName(soundUrl));
		
		String TFuid = "2";
		
		ChatMsgEntity d = new ChatMsgEntity(soundUrl,
				R.layout.list_say_he_voice,
				UserInfomation.getUserID(context),
				DateUtil.getCurrentTiem());

		d.setTtmContent(convertUrlToFileName(soundUrl));
//		d.setLayoutID(R.layout.list_say_me_item);
		d.setLayoutID(R.layout.list_say_he_voice);
		d.setTtmType(3);
//		d.setTtmTuID(Integer.valueOf(UserInfomation.getUserID(context)));
//		d.setTtmToUserId(Integer.valueOf(TFuid));
		d.setTtmTuID(Integer.valueOf(TFuid));
		d.setTtmToUserId(Integer.valueOf(UserInfomation.getUserID(context)));
		d.setTtmTime(DateUtil.getCurrentTiem());

		MessageSQLService.getInstance(context).save(d);
		ChatActivity.list.add(d);

		ChatActivity.talkList.setAdapter(new ChatMsgAdapter(context, ChatActivity.list,
				TFuid));
		ChatActivity.talkList.setSelection(ChatActivity.list.size());
	}
	
	private void saveLocSoundFile(String downUrl, String cachUrl){
		FinalHttp fh = new FinalHttp();  
	    //调用download方法开始下载
//	    HttpHandler handler = fh.download("http://www.xxx.com/下载路径/xxx.apk", //这里是下载的路径
//	    true,//true:断点续传 false:不断点续传（全新下载）
//	    "/mnt/sdcard/testapk.apk", //这是保存到本地的路径
//	    new AjaxCallBack() {  
//	                @Override  
//	                public void onLoading(long count, long current) {  
////	                     textView.setText("下载进度："+current+"/"+count);  
//	                }  
//
//	                @Override  
//	                public void onSuccess(File t) {  
////	                    textView.setText(t==null?"null":t.getAbsoluteFile().toString());  
//	                }  
//
//	            });  
	    
	    // 参数说明，1.下载地址，2.target,3.true:断点续传 false:不断点续传（全新下载）,4
	    HttpHandler handler = fh.download(downUrl, cachUrl, true, new AjaxCallBack<File>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				// 失败
				super.onFailure(t, strMsg);
				MyLog.i("<< 下载声音文件失败 >>  "+strMsg);
			}

			@Override
			public void onLoading(long count, long current) {
				// 下载进度
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(File t) {
				// 成功
				super.onSuccess(t);
				MyLog.i("<< 下载声音文件完成 >>  "+t.getAbsoluteFile().toString());
			}
	    	
		});

	  //调用stop()方法停止下载
//		handler.stop();

	   
	}
	
	/**
     * 解析Url为保存的文件名
     * @param url 图片网络路径
     * @return 返回解析后的图片名
     */
    private String convertUrlToFileName(String url){
        String[] s = url.split("/");
        return s[s.length - 1];
    }
	

}
