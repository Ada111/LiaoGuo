package com.lbk.app.weiliao.ui.chat;

import java.util.ArrayList;
import java.util.List;

import com.lbk.app.weiliao.bean.ChatMsgEntity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class MessageSQLService {

	private ManageDatabase dbmanger;

	public static MessageSQLService instance;

	/**
	 * 单例
	 * 
	 * @return instance
	 */
	public static MessageSQLService getInstance(Context context) {
		if (instance == null) {
			instance = new MessageSQLService(context);
		}
		return instance;
	}
	
	

	public MessageSQLService(Context context) {

		dbmanger = new ManageDatabase(context);
	}

	
	// 保存
	public void save(ChatMsgEntity de) {

		SQLiteDatabase database = dbmanger.getWritableDatabase();
		database.execSQL(
				"insert into message(TtmType,TtmTuID,TtmToUserId,TtmContent,TtmTime,isRead,isReplyLocation) values(?,?,?,?,?,?,?)",
				new Object[] { de.getTtmType(), de.getTtmTuID(),
						de.getTtmToUserId(), de.getTtmContent(), de.getTtmTime(),
						de.getIsRead(),de.getIsReplyLocation()});
		database.close();
	}
	


	/**
	 * 查询聊天记录
	 * 
	 * @return
	 */
	public List<ChatMsgEntity> getdatas(int TtmTuID, int TtmToUserId) {
		List<ChatMsgEntity> de = new ArrayList<ChatMsgEntity>();
		SQLiteDatabase database = dbmanger.getReadableDatabase();

		Cursor cursor = database
				.rawQuery(
						"select * from message where (TtmTuID = ? and TtmToUserId = ?) or (TtmToUserId = ? and TtmTuID = ?)  order by _id",
						new String[] { String.valueOf(TtmTuID),
								String.valueOf(TtmToUserId),
								String.valueOf(TtmTuID),
								String.valueOf(TtmToUserId)});
		while (cursor.moveToNext()) {
			de.add(new ChatMsgEntity(cursor.getInt(1), cursor.getInt(2), cursor
					.getInt(3), cursor.getString(4), cursor.getString(5),cursor.getInt(6),cursor.getInt(7)));

		}
		cursor.close();
		database.close();
		return de;
	}
	
	
	public List<ChatMsgEntity> getdata2(int TtmTuID) {
		List<ChatMsgEntity> de = new ArrayList<ChatMsgEntity>();
		SQLiteDatabase database = dbmanger.getWritableDatabase();

		Cursor cursor = database
				.rawQuery(
						"select * from message where TtmTuID = ? ",
						new String[] {String.valueOf(TtmTuID)});
		while (cursor.moveToNext()) {
			de.add(new ChatMsgEntity(cursor.getInt(1), cursor.getInt(2), cursor
					.getInt(3), cursor.getString(4), cursor.getString(5),cursor.getInt(6),cursor.getInt(7)));

		}
		cursor.close();
		database.close();
		return de;
	}
	
	
	public void updateIsRead(int type,String TtmTuID,String TtmToUserId) {
		
		SQLiteDatabase database = dbmanger.getWritableDatabase();
		database.execSQL("update message set isRead = ?  where TtmTuID = ? and TtmToUserId = ?",
					new String[]{String.valueOf(type),TtmTuID,TtmToUserId});
		database.close();
	}
	
	public void updateIsReplyLocation(int isReplyLocation,int _id) {
		
		SQLiteDatabase database = dbmanger.getWritableDatabase();
		database.execSQL("update message set isReplyLocation = ?  where _id = ? ",
					new Integer[]{isReplyLocation,_id});
		database.close();
	}
	
	
	public void updateContent(String TtmContent,int _id) {
		
		SQLiteDatabase database = dbmanger.getWritableDatabase();
		database.execSQL("update message set TtmContent = ?  where _id = ? ",
					new String[]{TtmContent,String.valueOf(_id)});
		database.close();
	}
	
	
	
	public List<ChatMsgEntity> findIsRead(int TtmTuID, int TtmToUserId) {
		
		List<ChatMsgEntity> de = new ArrayList<ChatMsgEntity>();
		SQLiteDatabase database = dbmanger.getWritableDatabase();

		Cursor cursor = database.rawQuery(
				"select * from message where isRead = 1 and (TtmTuID = ? and TtmToUserId = ?) or (TtmToUserId = ? and TtmTuID = ?)",
				new String[] { String.valueOf(TtmTuID),
						String.valueOf(TtmToUserId),
						String.valueOf(TtmTuID),
						String.valueOf(TtmToUserId)});
		while (cursor.moveToNext()) {
			de.add(new ChatMsgEntity(cursor.getInt(1), cursor.getInt(2), cursor
					.getInt(3), cursor.getString(4), cursor.getString(5),cursor.getInt(6),cursor.getInt(7)));

		}
		cursor.close();
		database.close();
		return de;
		
	}
	
	
	/**
	 * 查询聊天记录
	 * 
	 * @return
	 */
	public List<ChatMsgEntity> getdataToHe(int TtmTuID, int TtmToUserId,int type) {
		List<ChatMsgEntity> de = new ArrayList<ChatMsgEntity>();
		SQLiteDatabase database = dbmanger.getWritableDatabase();

		Cursor cursor = database.rawQuery(
				"select * from message where TtmTuID = ? and TtmToUserId = ? and isRead = ?",
				new String[] { String.valueOf(TtmTuID),
						String.valueOf(TtmToUserId),
						String.valueOf(type)});
		while (cursor.moveToNext()) {
			de.add(new ChatMsgEntity(cursor.getInt(1), cursor.getInt(2), cursor
					.getInt(3), cursor.getString(4), cursor.getString(5),cursor.getInt(6),cursor.getInt(7)));

		}
		cursor.close();
		database.close();
		return de;
	}

}
