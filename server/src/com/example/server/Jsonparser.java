package com.example.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;
import android.util.Log;

public class Jsonparser {
	public String DEBUG_TAG = "JSON";
	
	public List<Data> downloadUsers(String myurl) throws IOException
	{
		
		InputStream is = null;
		int len=50;
		try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d(DEBUG_TAG, "The response is: " + response);
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        List contentAsString = readJsonStream(is);
	        return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
		
	}
	
	public List<Data> readJsonStream(InputStream in) throws IOException {
	     JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
	     try {
	       return readMessagesArray(reader);
	     }
	      finally {
	       reader.close();
	     }
	
	  }
	
	 public List<Data> readMessagesArray(JsonReader reader) throws IOException {
	     List<Data> messages = new ArrayList();

	     reader.beginArray();
	     while (reader.hasNext()) {
	       messages.add(readMessage(reader));
	     }
	     reader.endArray();
	     return messages;
	   }
	 
	 public Data readMessage(JsonReader reader) throws IOException {
	     int id = 0;
	     String user = null;
	     reader.beginObject();
	     while (reader.hasNext()) {
	       String name = reader.nextName();
	       if (name.equals("name")) {
	         user = reader.nextString();
	       } else if (name.equals("id")) {
	         id = reader.nextInt();
	       } else {
	         reader.skipValue();
	       }
	     }
	     reader.endObject();
	     Data dat = new Data(id,user);
	     return dat;
	   }

}
