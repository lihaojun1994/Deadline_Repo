package com.example.deadline.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBAdapter {

	private static final String DB_NAME = "deadline.db";
	private static final String DB_TABLE = "schedule_info";
	private static final int DB_VERSION = 1;

	public static final String KEY_BUILDUPTIME = "buildUpTime";
	public static final String KEY_TITLE = "title";
	public static final String KEY_TEXT = "text";
	public static final String KEY_TYPE = "type";
	public static final String KEY_ISIMPORTANT = "isImportant";
	public static final String KEY_REMINDTIME = "remindTime";
	public static final String KEY_DEADLINETIME = "deadlineTime";

	private SQLiteDatabase db;
	private final Context context;
	private DBOpenHelper dbOpenHelper;

	public DBAdapter(Context _context) {
		context = _context;
	}

	/** Close the database */
	public void close() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

	/** Open the database */
	public void open() throws SQLiteException {
		dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
		try {
			db = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbOpenHelper.getReadableDatabase();
		}
	}

	public long insert(ScheduleData scheduleData) {
		ContentValues newValues = new ContentValues();

		newValues.put(KEY_BUILDUPTIME, scheduleData.getBuildUpTime());
		newValues.put(KEY_TITLE, scheduleData.getTitle());
		newValues.put(KEY_TEXT, scheduleData.getText());
		newValues.put(KEY_TYPE, scheduleData.getType());
		newValues.put(KEY_ISIMPORTANT, scheduleData.getIsImportant());
		newValues.put(KEY_REMINDTIME, scheduleData.getRemindTime());
		newValues.put(KEY_DEADLINETIME, scheduleData.getDeadlineTime());

		return db.insert(DB_TABLE, null, newValues);
	}

	public ScheduleData[] queryAllData() {
		Cursor results = db.query(DB_TABLE, new String[] { KEY_BUILDUPTIME,
				KEY_TITLE, KEY_TEXT, KEY_TYPE, KEY_ISIMPORTANT, KEY_REMINDTIME,
				KEY_DEADLINETIME }, null, null, null, null, null);
		return ConvertToScheduleData(results);
	}

	public ScheduleData[] queryOneData(String buildUpTime) {
		Cursor results = db.query(DB_TABLE, new String[] { KEY_BUILDUPTIME,
				KEY_TITLE, KEY_TEXT, KEY_TYPE, KEY_ISIMPORTANT, KEY_REMINDTIME,
				KEY_DEADLINETIME }, KEY_BUILDUPTIME + "=" + buildUpTime, null,
				null, null, null);
		return ConvertToScheduleData(results);
	}
//	public ScheduleData[] queryMultipleData() {
//		Cursor results = db.query(DB_TABLE, new String[] { KEY_BUILDUPTIME,
//				KEY_TITLE, KEY_TEXT, KEY_TYPE, KEY_ISIMPORTANT, KEY_REMINDTIME,
//				KEY_DEADLINETIME }, KEY_BUILDUPTIME + "=" + buildUpTime, null,
//				null, null, null);
//		return ConvertToScheduleData(results);
//	}
	

	private ScheduleData[] ConvertToScheduleData(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		ScheduleData[] scheduleDatas = new ScheduleData[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			scheduleDatas[i] = new ScheduleData();
			scheduleDatas[i].setBuildUpTime(cursor.getString(cursor
					.getColumnIndex(KEY_BUILDUPTIME)));
			scheduleDatas[i].setTitle(cursor.getString(cursor
					.getColumnIndex(KEY_TITLE)));
			scheduleDatas[i].setText(cursor.getString(cursor
					.getColumnIndex(KEY_TEXT)));
			scheduleDatas[i].setType(cursor.getString(cursor
					.getColumnIndex(KEY_TYPE)));
			scheduleDatas[i].setIsImportant(cursor.getString(cursor
					.getColumnIndex(KEY_ISIMPORTANT)));
			scheduleDatas[i].setRemindTime(cursor.getString(cursor
					.getColumnIndex(KEY_REMINDTIME)));
			scheduleDatas[i].setDeadlineTime(cursor.getString(cursor
					.getColumnIndex(KEY_DEADLINETIME)));
			cursor.moveToNext();
		}
		return scheduleDatas;
	}

	public long deleteAllData() {
		return db.delete(DB_TABLE, null, null);
	}

	public long deleteOneData(String buildUpTime) {
		return db.delete(DB_TABLE, KEY_BUILDUPTIME + " = " + "'"+buildUpTime + "'", null);
	}

	public long updateOneData(String buildUpTime, ScheduleData scheduleData) {
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_BUILDUPTIME, scheduleData.getBuildUpTime());
		updateValues.put(KEY_TITLE, scheduleData.getTitle());
		updateValues.put(KEY_TEXT, scheduleData.getText());
		updateValues.put(KEY_TYPE, scheduleData.getType());
		updateValues.put(KEY_ISIMPORTANT, scheduleData.getIsImportant());
		updateValues.put(KEY_REMINDTIME, scheduleData.getRemindTime());
		updateValues.put(KEY_DEADLINETIME, scheduleData.getDeadlineTime());

		return db.update(DB_TABLE, updateValues, KEY_BUILDUPTIME + "="
				+ buildUpTime, null);
	}
	
	public int getCount() {
		Cursor cursor = db.query(DB_TABLE, new String[] { "count(*)" }, null,
				null, null, null, null);
		if (cursor.moveToNext()) {
			return cursor.getInt(0);
		}
		return 0;
	}

	/** 静态Helper类，用于建立、更新和打开数据库 */
	private static class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		private static final String DB_CREATE = "create table " + 
			    DB_TABLE + " ( " + KEY_BUILDUPTIME
				+ " varchar(20), " + KEY_TITLE + " varchar(20), "
				+ KEY_TEXT + " varchar(200), " + KEY_TYPE + " varchar(20),"
				+ KEY_ISIMPORTANT + " varchar(20), " + KEY_REMINDTIME
				+ " varchar(20)," + KEY_DEADLINETIME + " varchar(20))";

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
				int _newVersion) {
			_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(_db);
		}
	}
}