package dk.andsen.asqlitemanager;

import dk.andsen.utils.Utils;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class Database {
	public boolean isDatabase = false;
	private SQLiteDatabase _db = null; 
	
	/**
	 * Open a existing database at the given path
	 * @param dbPath Path to the database
	 */
	public Database(String dbPath, Context cont) {
		try {
			_db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
			isDatabase = true;
		} catch (Exception e) {
			// not a database
			isDatabase = false;
		}
	}
	
	public void close() {
		_db.close();
	}

	public String[] getTables() {
		String sql ="select name from sqlite_master where type = 'table'";
		Cursor res = _db.rawQuery(sql, null);
		int recs = res.getCount();
		String[] tables = new String[recs];
		int i = 0;
		Utils.logD("Views: " + recs);
		while(res.moveToNext()) {
			tables[i] = res.getString(0);
			i++;
		}
		res.close();
		return tables;
	}

	public String[] getViews() {
		String sql ="select name from sqlite_master where type = 'view'";
		Cursor res = _db.rawQuery(sql, null);
		int recs = res.getCount();
		String[] views = new String[recs];
		int i = 0;
		Utils.logD("Views: " + recs);
		while(res.moveToNext()) {
			views[i] = res.getString(0);
			i++;
		}
		res.close();
		return views;
	}

	public String[] getIndex() {
		String sql ="select name from sqlite_master where type = 'index'";
		Cursor res = _db.rawQuery(sql, null);
		int recs = res.getCount();
		String[] index = new String[recs];
		int i = 0;
		Utils.logD("Index: " + recs);
		while(res.moveToNext()) {
			index[i] = res.getString(0);
			i++;
		}
		res.close();
		return index;
	}

	public String[] getFields(String table) {
		String sql = "select * from " + table + " limit 1";
		Cursor res = _db.rawQuery(sql, null);
		String[] fields = null;
		if (res.moveToFirst()) {
			fields = res.getColumnNames();
			Bundle dd = res.getExtras();
			Utils.logD("getExtras: " + dd.toString());
		}
		return fields;
	}

}
