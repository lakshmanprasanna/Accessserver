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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class PhotolistFragment extends Fragment implements AsyncResponsetwo{
	public ListView list;
	public testinterface test;
	public MainActivity main;
	public String s_id;
	public ArrayList<String> str = new ArrayList();
	public View resultView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		    Log.i("Photofragment","Creating new one");
			  resultView = inflater.inflate(R.layout.plist_layout, container, false);
			  list = (ListView) resultView.findViewById(R.id.listView1);		 
			 return resultView;
			 }
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    test = (testinterface) getActivity();
		 test.update();
		 
	    
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		 
		 ConnectivityManager connMgr = (ConnectivityManager) 
		           getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		        if (networkInfo != null && networkInfo.isConnected()) {
		        	String url = "http://bismarck.sdsu.edu/photoserver/userphotos/" + s_id;
		        	System.out.println(url);
		        	DownloadUserphotolist usl = new DownloadUserphotolist();
		        	usl.delegate=this;
		        	usl.execute(url);
		            Log.i("PhotoFragment","success");
		        } else {
		           Log.i("PhotoFragment","Fail");
		        }
	}
	
	public void updatetext(String str)
	{
		Log.i("LOL","LOLLLLLLLLLLLLLLLLLLLLLLLL");
	
		
		s_id = str;
   		/*for(int i=0;i<str.size();i++)
   	    {
   	    	System.out.println(str.get(i));
   	    }
   		

		*/
		
	}

	@Override
	public void processFinishtwo(List<Data> output) {
		
		Log.i("PhotoFragment","Received");
		for(int i=0;i<output.size();i++)
	    {
	    	str.add(output.get(i).user);
	    }
		for(int i=0;i<str.size();i++)
   	    {
   	    	System.out.println(str.get(i));
   	    }
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_1, str);
	 list.setAdapter(adapter);
		
}
	
	 @Override
	    public void onDestroyView() {
	        super.onDestroyView();
	       str.clear();
	      
	    }
	 
}
