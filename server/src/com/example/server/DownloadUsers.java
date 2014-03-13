package com.example.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class DownloadUsers extends AsyncTask<String, Integer, List<Data>>{

	private static final String DEBUG_TAG = "Network";
	private ProgressBar pb;
	private int progress=0;
	public AsyncResponse delegate=null;
    Jsonparser json = new Jsonparser();
    
   
    
    @Override
	protected void onPreExecute() {
       super.onPreExecute();
	    progress = 0;
	   // pb.setVisibility(View.VISIBLE);
	    
	}
    
    
	@Override
	protected List doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		while (progress < 100) {

	        progress += 1;

	        publishProgress(progress);
	   }
		try {
			  return json.downloadUsers(params[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		   e.printStackTrace();
		   return null;
		}
		
	}
	@Override
	protected void onProgressUpdate(Integer... values) {

	/*--- show download progress on main UI thread---*/
	  //  pb.setProgress(values[0]);
	    super.onProgressUpdate(values);
	}
	
	 
	 @Override
     protected void onPostExecute(List<Data> result) {
        
        Log.i(DEBUG_TAG,"sent");
	    delegate.processFinish(result);
    }
	 
	}

