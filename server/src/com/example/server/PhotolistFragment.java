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
import android.widget.Toast;

public class PhotolistFragment extends Fragment implements AsyncResponsetwo{
	public ListView list;
	public testinterface test;
	public MainActivity main;
	public String s_id;
	public ArrayList<String> str = new ArrayList();
	public View resultView;
	public List<Data> dat;
	public Bundle g = null;
	
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

	    test = (testinterface) getActivity();
		 test.update();
		 if(b!= null && g == null)
		 {
			 Bundle dd = b.getBundle("key");
			 g = dd;
			 Log.i("ffffffffffffffff","rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		 }
		 if(g!=null)
		 {
			 Log.i("ffffffffffffffff","eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			 s_id = g.getString("key");
			 
			 System.out.println(s_id);
		 }
		 
	    
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
		
	}

	@Override
	public void processFinishtwo(List<Data> output) {
		dat = output;
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
	 list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				 
				int id = dat.get(position).id;
				String s_id = Integer.toString(id);
				Log.i("Adapter",s_id);
				test.notifyuserphotolist(s_id);
				Log.i("PhotoFragment","ID sent");
			}
			 
		});
	 
		
}
	
	 @Override
	    public void onDestroyView() {
	        super.onDestroyView();
	       str.clear();
	       
	     savestate();
	      
	    }
	 
	 @Override
		public void onSaveInstanceState(Bundle b)
		{
			super.onSaveInstanceState(b);
			
			Bundle f = savestate();
			b.putBundle("key", f);
		}
	 
	 public Bundle savestate()
	 {
		 g = new Bundle();
		 g.putString("key", s_id);
		 return g;
	 }
	 
}
