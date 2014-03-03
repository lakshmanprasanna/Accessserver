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


public class MainActivity extends Activity implements AsyncResponse,OnUserSelectedListener{
	private static String TAG = "MainActivity";
	List<Data> m_dat = new ArrayList();
	DownloadUsers us = new DownloadUsers();
	public ArrayList<String> str =new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(savedInstanceState == null)
		{
		FragmentManager fragments = getFragmentManager(); 
		 FragmentTransaction fragmentTransaction = 
		fragments.beginTransaction();
		 fragments.enableDebugLogging(true);
		 MainFragment fragment = new MainFragment();
		 fragmentTransaction.add(R.id.main,fragment,"Fragment");
		// fragmentTransaction.addToBackStack("Fragment");
		 fragmentTransaction.commit();
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
                getFragmentManager().findFragmentById(R.id.main);
		Frag.receiveupdate(str);
		
		
	}

	@Override
	public void notify(int pos) {
		
	 int id = m_dat.get(pos).id;
	 Bundle b = new Bundle();
	 String s_id = Integer.toString(id);
	 Log.i(TAG,s_id);
	
	 FragmentManager fragments = getFragmentManager(); 
	 FragmentTransaction fragmentTransaction = 
	fragments.beginTransaction();
	 PhotolistFragment fragment = new PhotolistFragment();
	 fragmentTransaction.replace(R.id.main, fragment,"optional");
	 fragmentTransaction.addToBackStack("Optional");
	 fragmentTransaction.commit();
	}
	

}
