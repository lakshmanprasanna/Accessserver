package com.example.server;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;

public class PhotoActivity_holder extends Activity implements testinterface {

	PhotolistFragment photofrag,photofragment;
	Imagefragment image;
	public ArrayList<String> str;
	public ArrayList<Integer> in;
	public Intent go;
	
	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_photo_activity_holder);
		 image = (Imagefragment) getFragmentManager().findFragmentById(R.id.fragment3);
		if(b!=null)
		{
			str = b.getStringArrayList("str");
			 in=b.getIntegerArrayList("int");
		}
		else
		{
		
		Intent myIntent = getIntent();
		
		 str = new ArrayList();
		in = new ArrayList();
			
				str = myIntent.getStringArrayListExtra("str");
				in = myIntent.getIntegerArrayListExtra("in");
				
				FragmentManager fragments = getFragmentManager(); 
				photofragment = new PhotolistFragment();
			   	 fragments.enableDebugLogging(true);
			   	 FragmentTransaction fragmentTransaction = 
			   	fragments.beginTransaction();
			   	 fragmentTransaction.add(R.id.photoactivity_holder, photofragment,"Photo");
			   	 fragmentTransaction.commit();
			   	 
		}
			 
			
			for(int i=0;i<str.size();i++)
		    {
		    	System.out.println(str.get(i));
		    	System.out.println(in.get(i));
		    }
			
		   	 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_activity_holder, menu);
		return true;
	}

	@Override
	public void update() {
		
		PhotolistFragment photofrag2 = (PhotolistFragment) getFragmentManager().findFragmentByTag("Photo");
		photofrag2.updatephotolist(str,in);
		
		Log.i("PhotoFragment","Receivewdwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
	}

	@Override
	public void notifyuserphotolist(ArrayList<Integer> in,int pos) {
		

        if(image == null || !image.isInLayout())
         {
        	go = new Intent(this,ImageActivity_holder.class);
        	go.putExtra("list", in);
        	go.putExtra("pos", pos);
        	startActivity(go);
          }


		
	}
	
	 @Override
		public void onSaveInstanceState(Bundle b)
		{
			super.onSaveInstanceState(b);
			
			b.putStringArrayList("str", str);
			b.putIntegerArrayList("int", in);
			Log.i("PhotoFragment","saveActivity");
		}

}
