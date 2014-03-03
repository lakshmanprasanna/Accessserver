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


public class MainActivity extends Activity implements AsyncResponse,OnUserSelectedListener,testinterface{
	private static String TAG = "MainActivity";
	List<Data> m_dat = new ArrayList();
	String s_id;
	DownloadUsers us = new DownloadUsers();
	public ArrayList<String> str =new ArrayList();
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
		us.delegate = this;
		 ConnectivityManager connMgr = (ConnectivityManager) 
		            getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		        if (networkInfo != null && networkInfo.isConnected()) {
		        	
		        	us.execute("http://bismarck.sdsu.edu/photoserver/userlist");
		            Log.i(TAG,"success");
		        } else {
		           Log.i(TAG,"Fail");
		        }

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void processFinish(List<Data> output) {
		
		Log.i(TAG,"Received");
		m_dat = output;
		for(int i=0;i<m_dat.size();i++)
	    {
	    	str.add(m_dat.get(i).user);
	    }
		
		MainFragment Frag = (MainFragment)
                getFragmentManager().findFragmentByTag("MainFragment");
		Frag.receiveupdate(str);
		
		
	}

	@Override
	public void notify(int pos) {
		
	 int id = m_dat.get(pos).id;
	 Bundle b = new Bundle();
	  s_id = Integer.toString(id);
	 Log.i(TAG,s_id);
	
	 FragmentManager fragments = getFragmentManager(); 
	 fragments.enableDebugLogging(true);
	 FragmentTransaction fragmentTransaction = 
	fragments.beginTransaction();
	 fragmentTransaction.replace(R.id.mainactivity, photofragment,"Photo");
	 fragmentTransaction.addToBackStack("Optional");
	 fragmentTransaction.commit();
	
	
	 
	    
	}
	
	public void updatetext(String str)
	{
		 PhotolistFragment pFrag = (PhotolistFragment)
	             getFragmentManager().findFragmentByTag("Photo");
		 if(pFrag != null)
			{
				System.out.println("Something is wrong");
			}
		 pFrag.updatetext(str);
	}

	@Override
	public void update() {
		
		updatetext(s_id);
	}
	

}
