package com.example.server;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
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
	ArrayList<Integer> int_l = new ArrayList();
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
	  public void onActivityCreated(Bundle b) {
	    super.onActivityCreated(b);
       
	 
    if(savedState == null && b != null )
    {
    	 Bundle d = b.getBundle("key");
    	 savedState = d;
    	 Log.i("Fragment","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh ");
    }
    if(savedState!=null)
    {
    	
    
        str = savedState.getStringArrayList("list");
        int_l = savedState.getIntegerArrayList("id");
        updatelist(str);
        Log.i("Fragment","working");
    }
    
    if(savedState == null && b == null)
    {
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
	}
	 
	 
	
	 public Bundle SaveState() {
	        
		    
	        Log.i("Fragment","savetate called");
	        savedState = new Bundle();
	        savedState.putStringArrayList("list", str);
	        savedState.putIntegerArrayList("id", int_l);
	        return savedState;
	       
	      }
	 @Override
	    public void onDestroyView() {
	        super.onDestroyView();
	       
	      
	       SaveState(); /* vstup defined here for sure */
	      
	    }
	 
	 @Override
		public void onSaveInstanceState(Bundle b)
		{
			super.onSaveInstanceState(b);
			
			Bundle f = SaveState();
			b.putBundle("key", f);
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
				 
				
				System.out.println(position);
				int id = int_l.get(position);
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
	    	int_l.add(output.get(i).id);
	    	
	    }
		
		
		updatelist(str);
		
	}
}

