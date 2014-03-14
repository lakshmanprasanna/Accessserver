package com.example.server;

import java.util.ArrayList;
import java.util.List;

import com.example.server.Dtatabasehelper.FeedEntry;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class async_getphotolist extends AsyncTask<Integer, Void, List<Data>> {
	private Context context;
	public offlineAsyncResponse_photo del;
	public async_getphotolist(Context c)
	{
		this.context=c;
	}

	@Override
	protected List<Data> doInBackground(Integer... params) {
		
		int id = params[0];
		return get_photolist(id);
	}
	
	public List<Data> get_photolist(int id)
	{
		try
		{
		 Database db = new Database(this.context);
	   		SQLiteDatabase sqldb = db.getReadableDatabase();
	   		 
	   		  List<Data> list = new ArrayList();
	           String selectquery = " select * from " + FeedEntry.TABLE1_NAME + " where " + FeedEntry.COLUMN_USER_ID +" = " + id;
	       	   Cursor c1 = sqldb.rawQuery(selectquery, null);
	       	   int count = c1.getCount();
	       	   if(count>0)
	       	   {
	      	   c1.moveToFirst();
	      	 
	           while(count>0)
	      	   {
	      		  String u_name = c1.getString(c1.getColumnIndexOrThrow(FeedEntry.COLUMN_PHOTO_NAME));
	      		  int u_id = c1.getInt(c1.getColumnIndexOrThrow(FeedEntry.COLUMN_PHOTO_ID));
	      		  Data dat = new Data(u_id,u_name);
	      		  list.add(dat);
	      		  count = count-1;
	      		  c1.moveToNext();
	         	}
	           
	       	   }
	       	sqldb.close();
	       	   return list;	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	         
	}
	
	 @Override
     protected void onPostExecute(List<Data> result) {
         del.processFinish_offline_photo(result);

  }
}