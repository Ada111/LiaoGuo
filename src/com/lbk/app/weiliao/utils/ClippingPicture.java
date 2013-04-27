package com.lbk.app.weiliao.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ClippingPicture {
	
	public static final String TALK_FILES = "/sdcard/Test/talk";
	public static final String TALK_FILES2 = "/sdcard/Test/talk/";
	
	public static String signinPicName = "";
	public static String talkPicName;
	
	
	public static void saveTalkBitmap(Bitmap bitmap) {
		
		File myCaptureFile = new File(TALK_FILES);
		if (!myCaptureFile.exists())
			myCaptureFile.mkdirs();
		
		File f = new File(myCaptureFile + "/" + setTalkFileNames());
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(f));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String setTalkFileNames() {
		
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		talkPicName = dateFormat.format(date)+ "_talk"+ ".jpg";
		return talkPicName;
	}
	
	

	public static Bitmap Resize(Bitmap bmp) {
		float scaleWidth = 1;
		float scaleHeight = 1;
		double scale = 0;

		// 获得图片的宽高
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		if (width <= 600 && height <= 600) {
			return bmp;
		}else if(width <= 800 && height <= 800){
			scale = 0.8;
		}else if(width <= 960 && height <= 800){
			scale = 0.7;
		}else if(width <= 1200 && height <= 1600){
			scale = 0.6;
		}else if(width > 1200 || height > 1200){
			scale = 0.5;
		}
		
		// 计算缩放比例
		scaleWidth = (float) (scaleWidth * scale);
		scaleHeight = (float) (scaleHeight * scale);
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix,
				true);
		return newbm;
	}
	
	
	
	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	
	
	public static Drawable bitmapToDrawable(Bitmap bmp){
		
		return new BitmapDrawable(bmp);
	}
	
	
	
    public static void deleteSDFile(String fileName) { 
        File file = new File(TALK_FILES + "//" + fileName); 
        if (file == null || !file.exists() || file.isDirectory()) {
        	
        }else{
        	file.delete();
        }
    } 
}
