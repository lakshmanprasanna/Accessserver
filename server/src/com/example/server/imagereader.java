package com.example.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

public class imagereader extends AsyncTask<String,Void,Bitmap> {
	private FileInputStream streamIn;
	public imagereader_response del;
	@Override
	protected Bitmap doInBackground(String... params) {
		
		String str_id = params[0];
		File sdCard = Environment.getExternalStorageDirectory();

		File directory = new File (sdCard.getAbsolutePath() + "/proj2");

		File file = new File(directory, str_id+".jpg"); //or any other format supported

		try
		{
		 streamIn = new FileInputStream(file);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}

		try
		{
		  Bitmap bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image
		streamIn.close();
		return bitmap;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	 @Override
     protected void onPostExecute(Bitmap result) {
		 
		 del.processFinish_reader(result);
	 }
	
}
