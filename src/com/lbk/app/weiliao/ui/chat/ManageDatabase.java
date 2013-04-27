package com.lbk.app.weiliao.ui.chat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ManageDatabase extends  SQLiteOpenHelper{
	
	private static final int VERSION = 1;

	private static final String NAME = "msg";
	
	private String table = "CREATE TABLE IF NOT EXISTS message(" +
			"_id integer PRIMARY KEY," +
			"TtmType integer ," +
			"TtmTuID integer ," +
			"TtmToUserId integer ," +
			"TtmContent text ," +
			"TtmTime varchar(200) ," +
			"isRead integer ," +
			"isReplyLocation integer )";
	
	
	public ManageDatabase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, VERSION);
	}
	
	public ManageDatabase(Context context) {
		super(context, NAME ,null, VERSION);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(table);
		
	}
	
	
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	
}
