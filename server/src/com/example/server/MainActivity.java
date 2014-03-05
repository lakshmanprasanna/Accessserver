package com.example.server;

import java.util.ArrayList;
import java.util.List;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;


public class MainActivity extends Activity implements OnUserSelectedListener,testinterface,Imageinterface{
	private static String TAG = "MainActivity";
	String s_id,im_id;
    public PhotolistFragment photofragment;
    public Imagefragment image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_main);
	    photofragment = new PhotolistFragment();
	    image = new Imagefragment();
		if(savedInstanceState == null)
		{
		FragmentManager fragments = getFragmentManager(); 
		 FragmentTransaction fragmentTransaction = 
		fragments.beginTransaction();
		 fragments.enableDebugLogging(true);
		 MainFragment fragment = new MainFragment();
		 fragmentTransaction.add(R.id.mainactivity,fragment,"MainFragment");
		// fragmentTransaction.addToBackStack("Fragment");
		 fragmentTransaction.commit();
		
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	

	@Override
	public void notify(String ss_id) {
		
	 
    s_id =ss_id;
	
	 FragmentManager fragments = getFragmentManager(); 
	 fragments.enableDebugLogging(true);
	 FragmentTransaction fragmentTransaction = 
	fragments.beginTransaction();
	 fragmentTransaction.replace(R.id.mainactivity, photofragment,"Photo");
	 fragmentTransaction.addToBackStack("Optional");
	 fragmentTransaction.commit();
	
	 
	    
	}
	
	public void updatetext(String s)
	{
		 PhotolistFragment pFrag = (PhotolistFragment)
	             getFragmentManager().findFragmentByTag("Photo");
		 try{
			 if(pFrag != null)
				{
				 pFrag.updatetext(s);
				}
		 }
		 catch(Exception e)
		 {
			 
		 }
		 
		
	}

	@Override
	public void update() {
		updatetext(s_id);
	}

	@Override
	public void notifyuserphotolist(String s_id) {
		
		im_id = s_id;
		FragmentManager fragments = getFragmentManager(); 
		 fragments.enableDebugLogging(true);
		 FragmentTransaction fragmentTransaction = 
		fragments.beginTransaction();
		 fragmentTransaction.replace(R.id.mainactivity, image,"image");
		 fragmentTransaction.addToBackStack("image");
		 fragmentTransaction.commit();
		
	}

	@Override
	public void update_image() {
		
		 Imagefragment imFrag = (Imagefragment)
	             getFragmentManager().findFragmentByTag("image");
		 try{
			 if(imFrag != null)
				{
				 imFrag.update_text(im_id);
				}
		 }
		 catch(Exception e)
		 {
			 
		 }
		 
		
	}


}
