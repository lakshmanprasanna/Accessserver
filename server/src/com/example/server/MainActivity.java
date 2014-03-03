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


public class MainActivity extends Activity implements OnUserSelectedListener,testinterface{
	private static String TAG = "MainActivity";
	String s_id;
    public PhotolistFragment photofragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 
	    photofragment = new PhotolistFragment();
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
		 setContentView(R.layout.activity_main);
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
		 if(pFrag != null)
			{
				System.out.println("Something is wrong");
			}
		 pFrag.updatetext(s);
	}

	@Override
	public void update() {
		updatetext(s_id);
	}

}
