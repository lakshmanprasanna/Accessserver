package com.example.server;

import java.util.ArrayList;
import java.util.List;

import com.example.server.Dtatabasehelper.FeedEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class async_insert_photo extends AsyncTask<Object, Void, Void> {
	
	private Context context;
	
	public async_insert_photo(Context c)
	{
		this.context=c;
	}

	@Override
	protected Void doInBackground(Object... params) {
		
		List<Data> data = (List<Data>) params[0];
		int id = (Integer) params[1];
		insert(data,id);
		
		return null;
	}
	
	public void insert(List<Data> output,int uid)
	{
		try
		{
		Database db = new Database(this.context);
		SQLiteDatabase sqldb = db.getWritableDatabase();
		ArrayList<String> str = new ArrayList();
		ArrayList<Integer> in = new ArrayList();
		for(int i=0;i<output.size();i++)
	    {
	    	str.add(output.get(i).user);
	    	in.add(output.get(i).id);
	    	
	    }
		for(int i=0;i<str.size();i++)
		{
			String name = str.get(i);
			int id = in.get(i);
			ContentValues values = new ContentValues();
			values.put(FeedEntry.COLUMN_USER_ID, uid);
			values.put(FeedEntry.COLUMN_PHOTO_NAME, name);
			values.put(FeedEntry.COLUMN_PHOTO_ID, id);
			long rowid = sqldb.insert(FeedEntry.TABLE1_NAME, null, values);
			System.out.println(rowid);
			System.out.println("fffffff");
		}
		sqldb.close();
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	}


}
