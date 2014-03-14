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
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainFragment extends Fragment{
	  MainActivity main;
	ArrayList<String> str = new ArrayList();
	ArrayList<Integer> int_l = new ArrayList();
	public ArrayAdapter<String> adapter;
	ListView list;
	public OnUserSelectedListener item;
	public Bundle savedState;
	public ProgressBar pb;
	
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
       
	 if(b!=null)
	 {
		 str = b.getStringArrayList("str");
		 int_l=b.getIntegerArrayList("int");
		 updatelist(str,int_l);
		 Log.i("MainFragment","Retrive");
	 }
   
	}
	 
	 @Override
		public void onSaveInstanceState(Bundle b)
		{
			super.onSaveInstanceState(b);
			
			b.putStringArrayList("str", str);
			b.putIntegerArrayList("int", int_l);
			Log.i("MainFragment","save");
		}
	 
	 
	 public void updatelist(ArrayList<String> str1,ArrayList<Integer> in)
	 {
			
		 str = str1;
		 int_l=in;
		 
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
			        android.R.layout.simple_list_item_activated_1, str);
		 list.setAdapter(adapter);
		 
		 list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				 
				
				System.out.println(position);
				int id = int_l.get(position);
				item.notify(id);
				
			}
			 
		});
	 }
		
	}

