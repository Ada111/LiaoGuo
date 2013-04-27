package com.lbk.app.weiliao.ui.chat;

import net.tsz.afinal.FinalBitmap;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.utils.ClippingPicture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class TalkMessageImageView extends Activity implements OnClickListener {

	private ImageView image;
	private Button backButton;

	private String name;
	private int type;

	private Bitmap bmpDefaultPics;

	private Button bigButton;
	private Button smallButton;
	private Button tuenLeftButton;
	private Button tuenRightButton;

	private float scaleWidth=1; 
	private float scaleHeight=1;
	private int screenWidth;
	private int screenHeight; 
	private int id=0; 
	
	private float leftRote;
	private float rightTote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.talkmessage_imageview);

		image = (ImageView) findViewById(R.id.talkmessage_imageview);
		backButton = (Button) findViewById(R.id.talkmessageimage_backbtn);
		backButton.setOnClickListener(this);

		bigButton = (Button) findViewById(R.id.imageview_bigbtn);
		smallButton = (Button) findViewById(R.id.imageview_smallbtn);
		tuenLeftButton = (Button) findViewById(R.id.imageview_turnleft);
		tuenRightButton = (Button) findViewById(R.id.imageview_turnright);
		bigButton.setOnClickListener(this);
		smallButton.setOnClickListener(this);
		tuenLeftButton.setOnClickListener(this);
		tuenRightButton.setOnClickListener(this);

		name = this.getIntent().getStringExtra("name");
		
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		
		if(name.startsWith("http:")){
			FinalBitmap fbitmp = FinalBitmap.create(this);//³õÊ¼»¯FinalBitmapÄ£¿é
			fbitmp.configLoadingImage(R.drawable.ic_launcher);
			fbitmp.display(image, name);
		}else {
			bmpDefaultPics = BitmapFactory.decodeFile(
					ClippingPicture.TALK_FILES2 + name);
			image.setImageBitmap(bmpDefaultPics);
		}


	}  

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.talkmessageimage_backbtn:
			TalkMessageImageView.this.finish();
			break;
		case R.id.imageview_bigbtn:
			resizeBigImage(bmpDefaultPics,image,bigButton);
			break;
		case R.id.imageview_smallbtn:
			resizeSmallImage(bmpDefaultPics,image,smallButton);
			break;
		case R.id.imageview_turnleft:
			leftRote = leftRote+45;
			turnLeftBitmap(bmpDefaultPics,image,leftRote);
			
			break;
		case R.id.imageview_turnright:
			rightTote = rightTote-45;
			turnRightBitmap(bmpDefaultPics,image,rightTote);
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			TalkMessageImageView.this.finish();
		}

		return super.onKeyDown(keyCode, event);
	}


	public void resizeSmallImage(Bitmap bmp,ImageView mImageView,Button btn) {

		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		
		double scale = 0.8;
		
		scaleWidth = (float) (scaleWidth * scale);
		scaleHeight = (float) (scaleHeight * scale);

		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth,bmpHeight, matrix, true);

		mImageView.setImageBitmap(resizeBmp);

		if (scaleWidth * scale * bmpWidth < bmpWidth
				|| scaleHeight * scale * bmpHeight < bmpHeight) {
			btn.setClickable(false);
		}else{
			bigButton.setClickable(true);
		}
	}

	public void resizeBigImage(Bitmap bmp,ImageView mImageView,Button btn) {

		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		double scale = 1.25;
		scaleWidth = (float) (scaleWidth * scale);
		scaleHeight = (float) (scaleHeight * scale);

		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth,bmpHeight, matrix, true);

		mImageView.setImageBitmap(resizeBmp);

		if (scaleWidth * scale * bmpWidth > screenWidth
				|| scaleHeight * scale * bmpHeight > screenHeight) {
			btn.setClickable(false);
		}else{
			smallButton.setClickable(true);
		}
	}
	
	
	private void turnLeftBitmap(Bitmap bitmapOrg ,ImageView mImageView,float rote){
		
		Matrix matrix = new Matrix();  
		matrix.postRotate(rote);  
		
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,  
					width, height, matrix, true);  
		
		mImageView.setImageBitmap(resizedBitmap);
		
	}
	
	private void turnRightBitmap(Bitmap bitmapOrg ,ImageView mImageView,float rote){
		
		Matrix matrix = new Matrix();  
		matrix.postRotate(rote);  
		
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,  
					width, height, matrix, true); 
		
		mImageView.setImageBitmap(resizedBitmap);
	}
	

}
