package com.lbk.app.weiliao.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.BmobException;
import cn.bmob.BmobFile;
import cn.bmob.BmobObject;
import cn.bmob.SaveCallback;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.bean.ChatMsgEntity;
import com.lbk.app.weiliao.ui.adapter.ChatMsgAdapter;
import com.lbk.app.weiliao.ui.chat.MessageSQLService;
import com.lbk.app.weiliao.ui.chat.UserInfomation;
import com.lbk.app.weiliao.utils.BMapApiDemoApp;
import com.lbk.app.weiliao.utils.ClippingPicture;
import com.lbk.app.weiliao.utils.ClippingSounds;
import com.lbk.app.weiliao.utils.Config;
import com.lbk.app.weiliao.utils.DateUtil;
import com.lbk.app.weiliao.utils.MyLog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ChatActivity extends Activity implements OnClickListener {

	public static ListView talkList;
	private Button mBtnSend;
	private EditText mEditTextContent;

	private static String TFuid = "2";
	public static List<ChatMsgEntity> list = null;
	private double baiDu_Lon;
	private double baiDu_Lat;

	private TextView titleText;
	private Button backButton;
	private GridView expressionView;
	private List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
	private AlertDialog expressionDialog;
	private View mediaView;
	private ProgressBar progressBarBtn;
	private TextView progressBarText;
	private Button talkMessage_record;
	private Button talkMessage_voice;
	private ImageButton showMediaButton;
	private ImageButton cancelMediaButton;
	private AlertDialog voiceDialog;
	private MediaRecorder mediaRecorder;
	private ImageButton expressionButton;

	private Button cameraButton;
	private Button photoButotn;
	private Button locationButton;
	private Button requestLocationButton;
	private Button sendButton;
	private EditText edit;
	private List<ChatMsgEntity> messageList;
	private boolean Voice_Sel = false;
	private BMapApiDemoApp app;
	

	public static Integer[] mImageIds = { R.drawable.f000, R.drawable.f001,
			R.drawable.f002, R.drawable.f003, R.drawable.f004, R.drawable.f005,
			R.drawable.f006, R.drawable.f007, R.drawable.f008, R.drawable.f009,
			R.drawable.f010, R.drawable.f011, R.drawable.f012, R.drawable.f013,
			R.drawable.f014, R.drawable.f015, R.drawable.f016, R.drawable.f017,
			R.drawable.f018, R.drawable.f019, R.drawable.f020, R.drawable.f021,
			R.drawable.f022, R.drawable.f023, R.drawable.f024, R.drawable.f025,
			R.drawable.f026, R.drawable.f027, R.drawable.f028, R.drawable.f029,
			R.drawable.f030, R.drawable.f031, R.drawable.f032, R.drawable.f033,
			R.drawable.f034, R.drawable.f035, R.drawable.f036, R.drawable.f037,
			R.drawable.f038, R.drawable.f039, R.drawable.f040, R.drawable.f041,
			R.drawable.f042, R.drawable.f043, R.drawable.f044, R.drawable.f045,
			R.drawable.f046, R.drawable.f047, R.drawable.f048, R.drawable.f049,
			R.drawable.f050, R.drawable.f051, R.drawable.f052, R.drawable.f053,
			R.drawable.f054, R.drawable.f044, R.drawable.f056, R.drawable.f057,
			R.drawable.f058, R.drawable.f059, R.drawable.f060, R.drawable.f061,
			R.drawable.f062, R.drawable.f063, R.drawable.f064, R.drawable.f065,
			R.drawable.f066, R.drawable.f067, R.drawable.f068, R.drawable.f069,
			R.drawable.f070, R.drawable.f071, R.drawable.f072, R.drawable.f073 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.talkmessage);

		View view = LayoutInflater.from(this).inflate(
				R.layout.expressiondialog, null);
		expressionView = (GridView) view.findViewById(R.id.expression_gridview);
		for (int i = 0; i < mImageIds.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("IMAGE", mImageIds[i]);
			content.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, content,
				R.layout.expressiondialog_context, new String[] { "IMAGE" },
				new int[] { R.id.expressiondialog_image });
		expressionView.setAdapter(adapter);
		expressionView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int positon, long arg3) {

				setFace(positon + 1, mImageIds[positon]);
				expressionDialog.cancel();
			}

		});
		expressionDialog = new AlertDialog.Builder(this).create();
		expressionDialog.setView(view, 0, 0, 0, 0);

		talkList = (ListView) findViewById(R.id.talkmessage);

		mediaView = findViewById(R.id.talkmessage_media);

		progressBarBtn = (ProgressBar) findViewById(R.id.talkmessage_pro);
		progressBarText = (TextView) findViewById(R.id.talkmessage_protext);

		// friendName = this.getIntent().getStringExtra("name");

		titleText = (TextView) findViewById(R.id.talkmessage_title);
		titleText.setText("稻草人");

		talkList = (ListView) findViewById(R.id.talkmessage);

		backButton = (Button) findViewById(R.id.talkmessage_backbtn);
		backButton.setOnClickListener(this);

		talkMessage_voice = (Button) findViewById(R.id.talkmessage_voice);
		talkMessage_voice.setOnClickListener(this);
		talkMessage_record = (Button) findViewById(R.id.talk_message_record);
		ButtonListener b = new ButtonListener();
		talkMessage_record.setOnTouchListener(b);

		showMediaButton = (ImageButton) findViewById(R.id.talkmessage_showmedia);
		cancelMediaButton = (ImageButton) findViewById(R.id.talkmessage_cancelmedia);
		showMediaButton.setOnClickListener(this);
		cancelMediaButton.setOnClickListener(this);

		expressionButton = (ImageButton) findViewById(R.id.talkmessage_expression);
		expressionButton.setOnClickListener(this);

		cameraButton = (Button) findViewById(R.id.talkmessage_camera);
		photoButotn = (Button) findViewById(R.id.talkmessage_pic);
		locationButton = (Button) findViewById(R.id.talkmessage_location);
		requestLocationButton = (Button) findViewById(R.id.talkmessage_requestlocation);
		cameraButton.setOnClickListener(this);
		photoButotn.setOnClickListener(this);
		locationButton.setOnClickListener(this);
		requestLocationButton.setOnClickListener(this);

		edit = (EditText) findViewById(R.id.talkmessage_edit);
		sendButton = (Button) findViewById(R.id.talkmessage_send);
		sendButton.setOnClickListener(this);

		list = new ArrayList<ChatMsgEntity>();
		messageList = new ArrayList<ChatMsgEntity>();

		View voiceView = LayoutInflater.from(this).inflate(
				R.layout.record_voiceview, null);
		voiceDialog = new AlertDialog.Builder(this).setView(voiceView).create();

		// TFuid = this.getIntent().getStringExtra("TFuid");
		TFuid = "2";

		messageList = MessageSQLService.getInstance(this).getdatas(
				Integer.valueOf(UserInfomation.getUserID(this)),
				Integer.valueOf(TFuid));

		for (int i = 0; i < messageList.size(); i++) {

			switch (messageList.get(i).getTtmType()) {
			case 1:

				int id = Integer.valueOf(messageList.get(i).getTtmTuID());
				if (id == Integer.valueOf(TFuid)) {
					ChatMsgEntity other = new ChatMsgEntity(messageList.get(i)
							.getTtmContent(), R.layout.list_say_he_item, TFuid,
							messageList.get(i).getTtmTime());

					list.add(other);
					if (list.size() <= 0) {

					} else {
						talkList.setAdapter(new ChatMsgAdapter(
								ChatActivity.this, list, TFuid));
					}

				} else if (id == Integer.valueOf(UserInfomation
						.getUserID(ChatActivity.this))) {
					ChatMsgEntity other = new ChatMsgEntity(messageList.get(i)
							.getTtmContent(), R.layout.list_say_me_item,
							UserInfomation.getUserID(ChatActivity.this),
							messageList.get(i).getTtmTime());

					list.add(other);
					if (list.size() <= 0) {

					} else {
						talkList.setAdapter(new ChatMsgAdapter(
								ChatActivity.this, list, TFuid));
					}
				}

				break;
			case 2:
				int ids = Integer.valueOf(messageList.get(i).getTtmTuID());
				if (ids == Integer.valueOf(TFuid)) {
					ChatMsgEntity other = new ChatMsgEntity(messageList.get(i)
							.getTtmContent(), R.layout.list_say_he_image,
							TFuid, messageList.get(i).getTtmTime());
					list.add(other);
					if (list.size() <= 0) {

					} else {
						talkList.setAdapter(new ChatMsgAdapter(
								ChatActivity.this, list, TFuid));
					}

				} else if (ids == Integer.valueOf(UserInfomation
						.getUserID(ChatActivity.this))) {
					ChatMsgEntity other = new ChatMsgEntity(messageList.get(i)
							.getTtmContent(), R.layout.list_say_me_image,
							UserInfomation.getUserID(ChatActivity.this),
							messageList.get(i).getTtmTime());

					list.add(other);
					if (list.size() <= 0) {

					} else {
						talkList.setAdapter(new ChatMsgAdapter(
								ChatActivity.this, list, TFuid));
					}
				}
				break;
			case 3:
				int idsx = Integer.valueOf(messageList.get(i).getTtmTuID());
				if (idsx == Integer.valueOf(TFuid)) {
					ChatMsgEntity other = new ChatMsgEntity(messageList.get(i)
							.getTtmContent(), R.layout.list_say_he_voice,
							TFuid, messageList.get(i).getTtmTime());
					list.add(other);
					if (list.size() <= 0) {

					} else {
						talkList.setAdapter(new ChatMsgAdapter(
								ChatActivity.this, list, TFuid));
					}

				} else if (idsx == Integer.valueOf(UserInfomation
						.getUserID(ChatActivity.this))) {
					ChatMsgEntity other = new ChatMsgEntity(messageList.get(i)
							.getTtmContent(), R.layout.list_say_me_voice,
							UserInfomation.getUserID(ChatActivity.this),
							messageList.get(i).getTtmTime());

					list.add(other);
					if (list.size() <= 0) {

					} else {
						talkList.setAdapter(new ChatMsgAdapter(
								ChatActivity.this, list, TFuid));
					}
				}
				break;
			case 5:
				break;
			case 6:
				int idsxx = Integer.valueOf(messageList.get(i).getTtmTuID());
				if (idsxx == Integer.valueOf(TFuid)) {
					ChatMsgEntity other = new ChatMsgEntity(messageList.get(i)
							.getTtmContent(), R.layout.list_say_he_location,
							TFuid, messageList.get(i).getTtmTime());
					list.add(other);
					if (list.size() <= 0) {

					} else {
						talkList.setAdapter(new ChatMsgAdapter(this, list,
								TFuid));
					}

				} else if (idsxx == Integer.valueOf(UserInfomation
						.getUserID(this))) {
					ChatMsgEntity other = new ChatMsgEntity(messageList.get(i)
							.getTtmContent(), R.layout.list_say_me_location,
							UserInfomation.getUserID(this), messageList.get(i)
									.getTtmTime());

					list.add(other);
					if (list.size() <= 0) {

					} else {
						talkList.setAdapter(new ChatMsgAdapter(this, list,
								TFuid));
					}
				}
				break;

			}
		}

		talkList.setSelection(messageList.size());
		
		mLocationClient = new LocationClient(this);     //声明LocationClient类
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");//返回的定位结果包含地址信息
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);//禁止启用缓存定位
		option.setPoiNumber(5);	//最多返回POI个数	
		option.setPoiDistance(1000); //poi查询距离		
		option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息		
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener( mLocationListener );    //注册监听函数
	}
	
	LocationClient mLocationClient;
	private BDLocationListener mLocationListener = new BDLocationListener() {

//		public void onLocationChanged(BDLocation location) {
//			if (location != null) {
//
//				baiDu_Lon = location.getLongitude();
//				baiDu_Lat = location.getLatitude();
//
//				mHandler.sendEmptyMessage(11);
//
//			}
//		}

		// 接收异步返回的定位结果
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location != null) {

				MyLog.i("错误代号   = "+location.getLocType());
				baiDu_Lon = location.getLongitude();
				baiDu_Lat = location.getLatitude();
				mLocationClient.stop();
				mHandler.sendEmptyMessage(11);
			}
		}

		// 接收异步返回的POI查询结果
		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}
	};

	private void setFace(int faceTitle, int faceImg) {

		int start = edit.getSelectionStart();
		Spannable ss = edit.getText().insert(start, "[" + faceTitle + "]");
		Drawable d = getResources().getDrawable(faceImg);
		d.setBounds(0, 0, 30, 30);
		ImageSpan span = new ImageSpan(d, faceTitle + ".gif",
				ImageSpan.ALIGN_BASELINE);
		ss.setSpan(span, start, start + ("[" + faceTitle + "]").length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	class ButtonListener implements OnTouchListener {

		public boolean onTouch(View v, MotionEvent event) {

			if (v.getId() == R.id.talk_message_record) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					voiceDialog.show();
					record();
					break;
				case MotionEvent.ACTION_UP:
					mediaRecorder.stop();
					mediaRecorder.release();
					voiceDialog.cancel();

					mHandler.sendEmptyMessage(8);

					break;
				}

			}
			return false;
		}
	}

	private void record() {

		mediaRecorder = new MediaRecorder();
		try {
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			mediaRecorder.setOutputFile(ClippingSounds.saveSounds());
			mediaRecorder.prepare();
			mediaRecorder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// private void initView(){
	// mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
	// talkList = (ListView) findViewById(R.id.lv_chat);
	// mBtnSend = (Button) findViewById(R.id.btn_send);
	// mBtnSend.setOnClickListener(this);
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.talkmessage_backbtn:
			ChatActivity.this.finish();
			break;
		case R.id.talkmessage_showmedia:
			mediaView.setVisibility(View.VISIBLE);
			cancelMediaButton.setVisibility(View.VISIBLE);
			showMediaButton.setVisibility(View.GONE);
			break;
		case R.id.talkmessage_cancelmedia:
			mediaView.setVisibility(View.GONE);
			cancelMediaButton.setVisibility(View.GONE);
			showMediaButton.setVisibility(View.VISIBLE);
			break;

		case R.id.talkmessage_voice:
			Voice_Sel = !Voice_Sel;
			if (Voice_Sel) {
				talkMessage_voice
						.setBackgroundResource(R.drawable.chatting_setmode_msg_btn_normal);
				talkMessage_record.setVisibility(View.VISIBLE);
				edit.setVisibility(View.GONE);
				sendButton.setVisibility(View.GONE);
			} else {
				talkMessage_voice
						.setBackgroundResource(R.drawable.profile_icon_voice_normal);
				edit.setVisibility(View.VISIBLE);
				sendButton.setVisibility(View.VISIBLE);
				talkMessage_record.setVisibility(View.GONE);
			}
			break;

		case R.id.talkmessage_send:

			if (TextUtils.isEmpty(edit.getText())) {

			} else {

				ChatMsgEntity d = new ChatMsgEntity(edit.getText().toString(),
						R.layout.list_say_me_item,
						UserInfomation.getUserID(ChatActivity.this),
						DateUtil.getCurrentTiem());

				d.setTtmContent(edit.getText().toString());
				d.setLayoutID(R.layout.list_say_me_item);
//				d.setLayoutID(R.layout.list_say_he_item);
				d.setTtmType(1);
				d.setTtmTuID(Integer.valueOf(UserInfomation
						.getUserID(ChatActivity.this)));
				d.setTtmToUserId(Integer.valueOf(TFuid));
				d.setTtmTime(DateUtil.getCurrentTiem());

				MessageSQLService.getInstance(ChatActivity.this).save(d);
				list.add(d);

				talkList.setAdapter(new ChatMsgAdapter(ChatActivity.this, list,
						TFuid));
				talkList.setSelection(list.size());

				// mHandler.sendEmptyMessage(1);
				mHandler.sendEmptyMessage(2);
				
				// JPush 推送信息
				pushToMsg(edit.getText().toString());
				
			}

			break;
		case R.id.talkmessage_edit:
			break;
		case R.id.talkmessage_expression:
			expressionDialog.show();
			break;
		case R.id.talkmessage_camera:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, 1);
			break;
		case R.id.talkmessage_pic:
			Intent album = new Intent(Intent.ACTION_GET_CONTENT);
			album.setType("image/*");
			startActivityForResult(album, 2);
			break;
		case R.id.talkmessage_location:

			progressBarBtn.setVisibility(View.VISIBLE);
			progressBarText.setVisibility(View.VISIBLE);
			// mLocationManager.getLocation(this, 1);
			app = (BMapApiDemoApp) this.getApplication();
			if (app.mBMapMan == null) {
				app.mBMapMan = new BMapManager(getApplication());
				app.mBMapMan.init(app.mStrKey,
						new BMapApiDemoApp.MyGeneralListener());
			}
			app.mBMapMan.start();
			
			

			
			
			
			mLocationClient.start();
			MyLog.i("mLocationClient     "+mLocationClient+"      isStarted     "+mLocationClient.isStarted());
			// 发起定位请求。请求过程是异步的，定位结果在上面的监听函数onReceiveLocation中获取。
			if (mLocationClient != null && mLocationClient.isStarted())
				mLocationClient.requestLocation();
			else {
				Log.d("LocSDK3", "locClient is null or not started");
				mLocationClient.requestOfflineLocation();
			}
			
			

//			app.mBMapMan.getLocationManager().requestLocationUpdates(
//					mLocationListener);
//			app.mBMapMan.start();

			break;
		case R.id.talkmessage_requestlocation:

			break;
		}
	}

	// private void send() {
	// String contString = mEditTextContent.getText().toString();
	// if(TextUtils.isEmpty(contString)){
	//
	// }else{
	//
	// ChatMsgEntity d = new ChatMsgEntity(contString,
	// R.layout.list_say_me_item,
	// UserInfomation.getUserID(this), DateUtil.getCurrentTiem());
	//
	// d.setTtmContent(contString);
	// d.setLayoutID(R.layout.list_say_me_item);
	// d.setTtmType(1);
	// d.setTtmTuID(Integer.valueOf(UserInfomation.getUserID(this)));
	// d.setTtmToUserId(Integer.valueOf(TFuid));
	// d.setTtmTime(DateUtil.getCurrentTiem());
	//
	// MessageSQLService.getInstance(this).save(d);
	// list.add(d);
	//
	// talkList.setAdapter(new ChatMsgAdapter(this,list,TFuid));
	// talkList.setSelection(list.size());
	//
	// // mHandler.sendEmptyMessage(1);
	// mHandler.sendEmptyMessage(2);
	// }
	// }
	
	private void pushToMsg(String msg){
		
		JPushClient jpush = new JPushClient(Config.MASTERSECERT, Config.APPKEY);
		
		int sendNo = 1;
		String imei = "353995055947810";	// htc one x 
//		String imei = "357032043252149";	// htc g4
		//String imei = "012703009181350";	// sony ericsson 
		String msgTitle = "测试自定义信息标题";
		String msgContent = msg;
		
		MessageResult msgResult = jpush.sendCustomMessageWithImei(sendNo, imei, msgTitle, msgContent);
		
		if (null != msgResult) {
		    if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
		        MyLog.i("发送成功， sendNo=" + msgResult.getSendno());
		    } else {
		        MyLog.i("发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg());
		    }
		} else {
		    MyLog.i("无法获取数据");
		}
	}
	
//	private void showMessage(String msg){
//		
////		String TFuid = "2";
//		
//		ChatMsgEntity d = new ChatMsgEntity(msg,
//				R.layout.list_say_me_item,
//				UserInfomation.getUserID(ChatActivity.this),
//				DateUtil.getCurrentTiem());
//
//		d.setTtmContent(msg);
////		d.setLayoutID(R.layout.list_say_me_item);
//		d.setLayoutID(R.layout.list_say_he_item);
//		d.setTtmType(1);
//		d.setTtmTuID(Integer.valueOf(UserInfomation
//				.getUserID(ChatActivity.this)));
//		d.setTtmToUserId(Integer.valueOf(TFuid));
//		d.setTtmTime(DateUtil.getCurrentTiem());
//
//		MessageSQLService.getInstance(ChatActivity.this).save(d);
//		list.add(d);
//
//		talkList.setAdapter(new ChatMsgAdapter(ChatActivity.this, list,
//				TFuid));
//		talkList.setSelection(list.size());
//	}
	
	private Handler mHandler = new Handler() {

		private ChatMsgEntity d;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				break;
			case 2:
				progressBarBtn.setVisibility(View.GONE);
				progressBarText.setVisibility(View.GONE);
				edit.setText("");
				break;
			case 3:
				Toast.makeText(ChatActivity.this, "发送失败", 300).show();
				break;
			case 4:
				Toast.makeText(ChatActivity.this, "网络异常", 300).show();
				break;
			case 5:
				// 图片聊天
				d = new ChatMsgEntity(ClippingPicture.talkPicName,
						R.layout.list_say_me_image,
						UserInfomation.getUserID(ChatActivity.this),
						DateUtil.getCurrentTiem());

				
				d.setTtmContent(ClippingPicture.talkPicName);
				d.setLayoutID(R.layout.list_say_me_image);
				d.setTtmType(2);
				d.setTtmTuID(Integer.valueOf(UserInfomation
						.getUserID(ChatActivity.this)));
				d.setTtmToUserId(Integer.valueOf(TFuid));
				d.setTtmTime(DateUtil.getCurrentTiem());

				MessageSQLService.getInstance(ChatActivity.this).save(d);
				list.add(d);

				talkList.setAdapter(new ChatMsgAdapter(ChatActivity.this, list,
						TFuid));
				talkList.setSelection(list.size());

				// mHandler.sendEmptyMessage(6);
				mHandler.sendEmptyMessage(2);
				
				MyLog.i("------start------有聊天图片保存。");
				
				new Thread( new Runnable() {     
				    public void run() {     
				    	String tableName = "talk_img";
						try {
							BmobObject bmobObject = new BmobObject(tableName);
							final BmobFile bFile = new BmobFile(tableName, new File(ClippingPicture.TALK_FILES2+ClippingPicture.talkPicName));
							bFile.save();
							MyLog.i("------start------文件名："+ClippingPicture.talkPicName);
							bmobObject.put("imgName", ClippingPicture.talkPicName);
							bmobObject.put("imgUrl", bFile.getFileNameUrl());
							bmobObject.put("imgFile", bFile);
							bmobObject.saveInBackground(new SaveCallback() {
								@Override
								public void done(BmobException arg0) {
									if(arg0 != null){
										MyLog.i("保存聊天图片失败。");
									}else {
										MyLog.i("保存聊天图片成功。"+bFile.getFileNameUrl());
										pushToMsg("#IMG#"+bFile.getFileNameUrl());
									}
								}
							});
						} catch (BmobException e) {
							e.printStackTrace();
						}
						MyLog.i("-------end-----有聊天图片保存。");
				     }            
				}).start();
				
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				// 语音聊天
				d = new ChatMsgEntity(ClippingSounds.talkSoundName,
						R.layout.list_say_me_voice,
						UserInfomation.getUserID(ChatActivity.this),
						DateUtil.getCurrentTiem());

				d.setTtmContent(ClippingSounds.talkSoundName);
				d.setLayoutID(R.layout.list_say_me_voice);
				d.setTtmType(3);
				d.setTtmTuID(Integer.valueOf(UserInfomation
						.getUserID(ChatActivity.this)));
				d.setTtmToUserId(Integer.valueOf(TFuid));
				d.setTtmTime(DateUtil.getCurrentTiem());

				MessageSQLService.getInstance(ChatActivity.this).save(d);
				list.add(d);

				talkList.setAdapter(new ChatMsgAdapter(ChatActivity.this, list,
						TFuid));
				talkList.setSelection(list.size());

				// mHandler.sendEmptyMessage(9);
				mHandler.sendEmptyMessage(2);
				
				MyLog.i("------start------有聊天语音保存。");
				
				new Thread( new Runnable() {     
				    public void run() {     
				    	String tableName = "talk_sound";
						try {
							BmobObject bmobObject = new BmobObject(tableName);
							final BmobFile bFile = new BmobFile(tableName, new File(ClippingSounds.TALKSOUND_FILE+ClippingSounds.talkSoundName));
							bFile.save();
							MyLog.i("------start------文件名："+ClippingSounds.talkSoundName);
							bmobObject.put("soundName", ClippingSounds.talkSoundName);
							bmobObject.put("soundUrl", bFile.getFileNameUrl());
							bmobObject.put("soundFile", bFile);
							bmobObject.saveInBackground(new SaveCallback() {
								@Override
								public void done(BmobException arg0) {
									if(arg0 != null){
										MyLog.i("保存聊天语音失败。");
									}else {
										MyLog.i("保存聊天语音成功。"+bFile.getFileNameUrl());
										// JPush 推送信息
										pushToMsg("#SOUND#"+bFile.getFileNameUrl());
									}
								}
							});
						} catch (BmobException e) {
							e.printStackTrace();
						}
						MyLog.i("-------end-----有聊天语音保存。");
				     }            
				}).start();
				
				break;
			case 9:

				break;
			case 10:

				break;
			case 11:

				System.out.println(baiDu_Lat + "," + baiDu_Lon);

				d = new ChatMsgEntity(baiDu_Lat + "," + baiDu_Lon,
						R.layout.list_say_me_location,
						UserInfomation.getUserID(ChatActivity.this),
						DateUtil.getCurrentTiem());

				d.setTtmContent(baiDu_Lat + "," + baiDu_Lon);
				d.setLayoutID(R.layout.list_say_me_location);
				d.setTtmType(6);
				d.setTtmTuID(Integer.valueOf(UserInfomation
						.getUserID(ChatActivity.this)));
				d.setTtmToUserId(Integer.valueOf(TFuid));
				d.setTtmTime(DateUtil.getCurrentTiem());

				MessageSQLService.getInstance(ChatActivity.this).save(d);
				list.add(d);

				talkList.setAdapter(new ChatMsgAdapter(ChatActivity.this, list,
						TFuid));
				talkList.setSelection(list.size());

				// mHandler.sendEmptyMessage(12);
				mHandler.sendEmptyMessage(2);
				
				// JPush 推送信息
				pushToMsg(baiDu_Lat + "," + baiDu_Lon);

				break;
			case 12:

				break;
			case 13:

				break;

			}
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		ContentResolver resolver = getContentResolver();
		if (resultCode == 0)
			return;
		
		if (requestCode == 1) {
			Bitmap bitmap = (Bitmap) data.getExtras().get("data");
			Bitmap newBitmap = ClippingPicture.Resize(bitmap);
			ClippingPicture.saveTalkBitmap(newBitmap);
			
			mHandler.sendEmptyMessage(5);
			
		}else if (requestCode == 2) {

			Uri originalUri = data.getData();
			if (originalUri != null) {
				try {
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver,
							originalUri);
					Bitmap newBitmap = ClippingPicture.Resize(bitmap);
					ClippingPicture.saveTalkBitmap(newBitmap);
					mHandler.sendEmptyMessage(5);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		
		if (data == null)
			return;

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		BMapApiDemoApp app = (BMapApiDemoApp) this.getApplication();
		// 移除listener
//		app.mBMapMan.getLocationManager().removeUpdates(mLocationListener);
		app.mBMapMan.stop();
		super.onPause();
	}

}
