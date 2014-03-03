package com.example.server;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainFragment extends Fragment{
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
	    }
	 
	 public void updatelist(ArrayList<String> str)
	 {
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
			        android.R.layout.simple_list_item_1, str);
		 list.setAdapter(adapter);
		 
		 list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				 
				String pos = Integer.toString(position);
				Toast.makeText(getActivity(), pos, Toast.LENGTH_SHORT);
				Log.i("Adapter",pos);
				item.notify(position);
				
			}
			 
		});
	 }
}

