package com.example.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Imagedownloader extends AsyncTask<String, Integer, Bitmap> {
	
	public AsyncResponse_image delegate=null;
	//private ProgressBar pb;
	private int progress;
	private String id;
	private FileOutputStream fos;
	
	
	@Override
	protected void onPreExecute() {
       super.onPreExecute();
	    progress = 0;
	   // pb.setVisibility(View.VISIBLE);
	    
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		
		
	   try
	   {
		return downloadBitmap(params[0]);
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
		   return null;
	   }
	  
	   
	
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {

	/*--- show download progress on main UI thread---*/
//	    pb.setProgress(values[0]);
	    super.onProgressUpdate(values);
	}
	
	public Bitmap downloadBitmap(String str_id) throws IOException
	{
		InputStream is = null;
		try {
			id = str_id;
			String myurl = "http://bismarck.sdsu.edu/photoserver/photo/" + str_id;
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d("ImageDownloader", "The response is: " + response);
	        is = conn.getInputStream();
	        final Bitmap bitmap = BitmapFactory.decodeStream(is);
	        // Convert the InputStream into a string
	        return bitmap;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
		
		
		
		
	}
	 @Override
     protected void onPostExecute(Bitmap result) {
        
       if(result == null)
       {
    	   Log.i("ImageDownloader","NULLLLLLL");
       }
       else
       {
    	   delegate.processFinish_image(result);
    	   storeImage(result);
       }
    }
	 
	 public void storeImage(Bitmap bitmap)
		{
			
			String filename = id+".jpg";
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
			File wallpaperDirectory = new File("/sdcard/Proj2/");
			wallpaperDirectory.mkdirs();
			File file = new File(wallpaperDirectory,filename);
		    try {
		        file.createNewFile();
		    } catch (IOException e) {
		        e.printStackTrace();
		        System.out.println("exception 1");
		    }
		    
		    try {
		       fos = new FileOutputStream(file);
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		        System.out.println("exception 2");
		    }
		    
		    try {
		        
		    	fos.write(bytes.toByteArray());
		        fos.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

	
	

}
