package com.lbk.app.weiliao.ui.adapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.bean.ChatMsgEntity;
import com.lbk.app.weiliao.ui.chat.MessageSQLService;
import com.lbk.app.weiliao.ui.chat.PersonnelLocation;
import com.lbk.app.weiliao.ui.chat.TalkMessageImageView;
import com.lbk.app.weiliao.ui.chat.UserInfomation;
import com.lbk.app.weiliao.utils.ClippingPicture;
import com.lbk.app.weiliao.utils.ClippingSounds;
import com.lbk.app.weiliao.utils.MyLog;
import com.lbk.app.weiliao.utils.MyUtils;
import com.lbk.app.weiliao.utils.SmileyParser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatMsgAdapter extends BaseAdapter {

	private Context ctx;

	private List<ChatMsgEntity> entitys;
	
	private ChatMsgEntity entity;

	private TextView tvName;
	private TextView tvDate;
	private TextView tvText;
	private TextView time;
	private ImageView userImage;
	private Button voiceBtn;
	private Button locationBtn;

	private Bitmap bmpDefaultPics;

	private View view;

	private ImageView talkImage;

	private SmileyParser parser;

	private ImageView talkImages;
	
	public static boolean isClick = false;
	
	private List<ChatMsgEntity> messageList;
	
	private FinalBitmap fbitmp;


	public ChatMsgAdapter(Context context, List<ChatMsgEntity> entitys,String TFuid) {
		ctx = context;
		this.entitys = entitys;
		messageList = MessageSQLService.getInstance(context).getdatas(
				Integer.valueOf(UserInfomation.getUserID(ctx)),
				Integer.valueOf(TFuid));
		
		fbitmp = FinalBitmap.create(context);//初始化FinalBitmap模块
		fbitmp.configLoadingImage(R.drawable.ic_launcher);

	}

	@Override
	public int getCount() {
		return entitys.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		entity = entitys.get(position);

		int itemLayout = entity.getLayoutID();
		
		if("".equals(entity.getText())){
			view = LayoutInflater.from(ctx).inflate(R.layout.list_say_null, null);
			return view;
		}
		

		
		switch(itemLayout){
		case R.layout.list_say_me_item:
			view = LayoutInflater.from(ctx).inflate(itemLayout, null);
			tvText = (TextView) view.findViewById(R.id.messagedetail_row_text);
			SmileyParser.init(ctx);
			parser = SmileyParser.getInstance();
			tvText.setText(parser.addSmileySpans(entity.getTtmContent()));
			
			
			break;
		case R.layout.list_say_he_item:
			view = LayoutInflater.from(ctx).inflate(itemLayout, null);
			tvText = (TextView) view.findViewById(R.id.messagedetail_row_text);
			SmileyParser.init(ctx);
			parser = SmileyParser.getInstance();
			tvText.setText(parser.addSmileySpans(entity.getTtmContent()));
			
			break;	
		case R.layout.list_say_me_image:
			
			view = LayoutInflater.from(ctx).inflate(itemLayout, null);
			talkImage = (ImageView) view.findViewById(R.id.messagegedetail_image);
			
//			bmpDefaultPics = BitmapFactory.decodeFile(ClippingPicture.TALK_FILES2+entity.getTtmContent());
			bmpDefaultPics = MyUtils.revitionImageSize(new File(ClippingPicture.TALK_FILES2+entity.getTtmContent()), 100);
			talkImage.setImageBitmap(bmpDefaultPics);
			
			talkImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if("".equals(messageList.get(position).getTtmContent())){
						Toast.makeText(ctx, "图片不存在或已删除", 300).show();
					}else{
						Intent intent = new Intent(ctx,TalkMessageImageView.class);
						intent.putExtra("name", messageList.get(position).getTtmContent());
						ctx.startActivity(intent);
					}

				}
			});
			
			break;
		case R.layout.list_say_he_image:
			
			view = LayoutInflater.from(ctx).inflate(itemLayout, null);
			talkImage = (ImageView)view.findViewById(R.id.messagegedetail_images);
			
			if(entity.getTtmContent().startsWith("http:")){
				MyLog.i("afinal 装载图片 --------》 "+entity.getTtmContent());
				//bitmap加载就这一行代码，display还有其他重载，详情查看源码
			    fbitmp.display(talkImage, entity.getTtmContent());
			}else {
				bmpDefaultPics = BitmapFactory.decodeFile(ClippingPicture.TALK_FILES2+entity.getTtmContent());
				talkImage.setImageBitmap(bmpDefaultPics);
//				talkImage.setBackground(new BitmapDrawable(bmpDefaultPics));
//				talkImage.setBackgroundDrawable(new BitmapDrawable(bmpDefaultPics));
			}
			
			talkImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if("".equals(messageList.get(position).getTtmContent())){
						Toast.makeText(ctx, "图片不存在或已删除", 300).show();
					}else{
						Intent intent = new Intent(ctx, TalkMessageImageView.class);
						intent.putExtra("name", messageList.get(position).getTtmContent());
						ctx.startActivity(intent);
					}

				}
			});
			
			break;
		case R.layout.list_say_me_voice:
			
			view = LayoutInflater.from(ctx).inflate(itemLayout, null);
			
			voiceBtn = (Button)view.findViewById(R.id.talkmessage_voice);
			
			voiceBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final MediaPlayer mediaPlayer;
					try{
						
						mediaPlayer = new MediaPlayer();
						mediaPlayer.setDataSource(ClippingSounds.TALKSOUND_FILE+messageList.get(position).getTtmContent());
						mediaPlayer.prepare();
						mediaPlayer.start();
						
						
						mediaPlayer
								.setOnCompletionListener(new OnCompletionListener() {
									@Override
									public void onCompletion(MediaPlayer mp) {
										mediaPlayer.release();
									}
								});
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			});
			
			break;
		case R.layout.list_say_he_voice:
			
			view = LayoutInflater.from(ctx).inflate(itemLayout, null);
			
			voiceBtn = (Button)view.findViewById(R.id.talkmessage_voice);
			
			voiceBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final MediaPlayer mediaPlayer;
					try{
						mediaPlayer = new MediaPlayer();
						mediaPlayer.setDataSource(ClippingSounds.TALKSOUND_FILE+messageList.get(position).getTtmContent());
						mediaPlayer.prepare();
						mediaPlayer.start();
						
						mediaPlayer
								.setOnCompletionListener(new OnCompletionListener() {
									@Override
									public void onCompletion(MediaPlayer mp) {
										mediaPlayer.release();
									}
								});
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			});
			
			break;
		case R.layout.list_say_me_location:
			
			view = LayoutInflater.from(ctx).inflate(itemLayout, null);
			
			locationBtn = (Button)view.findViewById(R.id.messagegedetail_location);
			
			final String[] location = entity.getTtmContent().split(",");
			
			locationBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intentss = new Intent(ctx,PersonnelLocation.class);
					intentss.putExtra("Lon", location[1]);
					intentss.putExtra("Lat", location[0]);
					ctx.startActivity(intentss);
				}
			});
			
			break;
		case R.layout.list_say_he_location:
			
			view = LayoutInflater.from(ctx).inflate(itemLayout, null);
			
			locationBtn = (Button)view.findViewById(R.id.messagegedetail_location);
			
			final String[] locationsx = entity.getTtmContent().split(",");
			
			locationBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intentss = new Intent(ctx,PersonnelLocation.class);
					intentss.putExtra("Lon", locationsx[1]);
					intentss.putExtra("Lat", locationsx[0]);
					ctx.startActivity(intentss);
				}
			});
			
			break;
			
		}
		
		time = (TextView)view.findViewById(R.id.talk_time);
		time.setText(entity.getTtmTime());
		
		userImage = (ImageView)view.findViewById(R.id.messagegedetail_rov_icon);

		return view;
	}

}
