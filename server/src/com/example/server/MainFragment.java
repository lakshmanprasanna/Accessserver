package com.example.server;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainFragment extends Fragment implements AsyncResponse{
	  MainActivity main;
	List<Data> dat = new ArrayList();
	ArrayList<String> str = new ArrayList();
	public ArrayAdapter<String> adapter;
	ListView list;
	public OnUserSelectedListener item;
	public Bundle savedState;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
			 View resultView = inflater.inflate(R.layout.mainfragment, container, false);
			 list = (ListView) resultView.findViewById(R.id.listView1);
			 return resultView;
			 }
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            item = (OnUserSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
       
	 if(savedInstanceState != null && savedState == null)
	 {
        savedState = savedInstanceState.getBundle("list");
        Log.i("Fragment","SavedInstanceState called first ");
	 }
    if(savedState != null)
    {
        str = savedState.getStringArrayList("list");
        updatelist(str);
        Log.i("Fragment","SavedState called first ");
    }
    
   // item = (OnUserSelectedListener) getActivity();
	// item.updateone();
    
    
    ConnectivityManager connMgr = (ConnectivityManager) 
            getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        	DownloadUsers us = new DownloadUsers();
        	us.delegate=this;
        	us.execute("http://bismarck.sdsu.edu/photoserver/userlist");
            Log.i("MainFragment","success");
        } else {
           Log.i("MainFragment","Fail");
        }
	 
	}
	 
	 public void receiveupdate(ArrayList<String> m_dat)
	 {
		 
		 str = m_dat;
		 updatelist(str);
   }
	 
	 @Override
	    public void onSaveInstanceState(Bundle icicle) {
	        super.onSaveInstanceState(icicle);
	        
	        Log.i("Fragment","SavedInstanceState called");
	        
	        icicle.putBundle("list", savedState != null ? savedState : SaveState());
	       
	      }
	
	 public Bundle SaveState() {
	        
		    Bundle b = new Bundle();
	        Log.i("Fragment","savetate called");
	        
	        b.putStringArrayList("list", str);
	        return b;
	       
	      }
	 @Override
	    public void onDestroyView() {
	        super.onDestroyView();
	       
	        savedState = SaveState(); /* vstup defined here for sure */
	        str.clear();
	    }
	 
	 public void updatelist(ArrayList<String> str1)
	 {
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
			        android.R.layout.simple_list_item_1, str1);
		 list.setAdapter(adapter);
		 
		 list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				 
				int id = dat.get(position).id;
				String s_id = Integer.toString(id);
				Log.i("Adapter",s_id);
				item.notify(s_id);
				
			}
			 
		});
	 }

	@Override
	public void processFinish(List<Data> output) {
		
		dat = output;
		
		Log.i("MainFragment","Received");
		for(int i=0;i<output.size();i++)
	    {
	    	str.add(output.get(i).user);
	    }
		for(int i=0;i<str.size();i++)
   	    {
   	    	System.out.println(str.get(i));
   	    }
		
		updatelist(str);
		
	}
}

