package com.example.server;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class PhotolistFragment extends Fragment{
	public EditText text;
	public testinterface test;
	public MainActivity main;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
			 View resultView = inflater.inflate(R.layout.plist_layout, container, false);
			 text = (EditText) resultView.findViewById(R.id.editText1);
			 
			 
			 return resultView;
			 }
	
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		test = (testinterface) getActivity();
		 test.update();
	}
	
	public void updatetext(String a)
	{

		text.setText(a);
		Log.i("Photo",a);
		
	}


}
