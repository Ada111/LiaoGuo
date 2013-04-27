package com.lbk.app.weiliao.utils;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ClippingSounds {
	
	 public static final String TALKSOUND_FILE = "/sdcard/Test/ppt/";
     
     public static String talkSoundName;
     
     public static String saveSounds(){
    	 
    	 File myCaptureFile;
		try{
 			
			myCaptureFile = new File(TALKSOUND_FILE);
 			if (!myCaptureFile.exists())
 				myCaptureFile.mkdirs();
 			
    	 }catch(Exception e){
    		 e.printStackTrace();
    		 return null;
    	 }
    	 return TALKSOUND_FILE+saveTalkSoundsFileNames();
     }
     
     public static String saveSounds2(){
    	 
    	 File myCaptureFile;
 		try{
  			
 			myCaptureFile = new File(TALKSOUND_FILE);
  			if (!myCaptureFile.exists())
  				myCaptureFile.mkdirs();
  			
     	 }catch(Exception e){
     		 e.printStackTrace();
     		 return null;
     	 }
     	 return TALKSOUND_FILE+saveTalkSoundsFileNames();

     }
     
     
     
	public static String saveTalkSoundsFileNames() {
		
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		talkSoundName = dateFormat.format(date)+ "_sound"+ ".mp3";
		return talkSoundName;
	}

}
