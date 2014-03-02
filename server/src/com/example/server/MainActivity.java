package com.example.server;

import java.util.ArrayList;
import java.util.List;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;


public class MainActivity extends FragmentActivity implements AsyncResponse{
	private static String TAG = "MainActivity";
	
	List<Data> m_dat = new ArrayList();
	DownloadUsers us = new DownloadUsers();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		ArrayList<String> str =new ArrayList();
		Log.i(TAG,"Received");
		m_dat = output;
		for(int i=0;i<m_dat.size();i++)
	    {
	    	str.add(m_dat.get(i).user);
	    }
		for(int i=0;i<str.size();i++)
	    {
	    	System.out.println(str.get(i));
	    }
		
		//Bundle b = new Bundle();
	//	b.putStringArrayList("name", str);
	//	MainFragment frag =new MainFragment();
//		frag.setArguments(b);
		//System.out.println(str.get(0));
		
	}
	
	 

}
