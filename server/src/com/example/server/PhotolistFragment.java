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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PhotolistFragment extends Fragment{
	public ListView list;
	public testinterface test;
	public MainActivity main;
	public String s_id;
	public ArrayList<String> str = new ArrayList();
	public View resultView;
	public ArrayList<Integer> in;
	public Bundle g = null;
	public ProgressBar pb;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		    Log.i("Photofragment","Creating new one");
			  resultView = inflater.inflate(R.layout.plist_layout, container, false);
			  list = (ListView) resultView.findViewById(R.id.listView1);
			 return resultView;
			 }
	
	@Override
	  public void onActivityCreated(Bundle b) {
	    super.onActivityCreated(b);
	   
	    if(b==null)
	    {
	    	 Log.i("PhotoFragment","yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
	    test = (testinterface) getActivity();
		 test.update();
		
	    }
	    else
		 {
	    
	    	 Log.i("PhotoFragment","zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
	    	 test = (testinterface) getActivity();
	    	 
			 str = b.getStringArrayList("str");
			 in=b.getIntegerArrayList("int");
			 if(str.size()>0)
			 updatephotolist(str,in);
			 Log.i("MainFragment","Retrive");
			
		 }
	    
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		
	}
	
	 @Override
		public void onSaveInstanceState(Bundle b)
		{
			super.onSaveInstanceState(b);
			
			b.putStringArrayList("str", str);
			b.putIntegerArrayList("int", in);
			Log.i("MainFragment","save");
		}
	 
	

	
	public void updatephotolist(ArrayList<String> str1,ArrayList<Integer> int_l)
	{
		Log.i("PhotoFragment","Received");
		
		
		
		System.out.println(str1.size());
		System.out.println(int_l.size());
		str = str1;
		in = int_l;
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_activated_1, str1);
	 list.setAdapter(adapter);
	 list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				 
			//	int id = in.get(position);
			//	String s_id = Integer.toString(id);
				test.notifyuserphotolist(in,position);
				Log.i("PhotoFragment","ID sent");
				
			}
			 
		});
	}
	
	public void setposition(int pos)
	{
		list.setItemChecked(pos, true);
	}
	 
}
