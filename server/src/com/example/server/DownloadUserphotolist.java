package com.example.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

public class DownloadUserphotolist extends AsyncTask<String, Void, List<Data>> {

	private static final String DEBUG_TAG = "Network";
	public AsyncResponsetwo delegate=null;
    Jsonparser json = new Jsonparser();
	@Override
	protected List doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			  return json.downloadUsers(params[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		   e.printStackTrace();
		   return null;
		}
		
	}
	 
	 @Override
     protected void onPostExecute(List<Data> result) {
        
       if(result == null)
       {
    	   Log.i(DEBUG_TAG,"NULLLLLLL");
       }
       else
       {
    	   ArrayList<String> str1 = new ArrayList(); 
   		for(int i=0;i<result.size();i++)
   	    {
   	    	str1.add(result.get(i).user);
   	    }
   		for(int i=0;i<str1.size();i++)
   	    {
   	    	//System.out.println(str1.get(i));
   	    }
   		
   		delegate.processFinishtwo(result);
       }
    }
	 
}
