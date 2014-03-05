package com.example.server;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class Imagefragment extends Fragment implements AsyncResponse_image {
	
	public static String TAG = "IMAGEFRAGMENT";
	public Imageinterface test;
	public ImageView imview;
	public String im_id;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		    Log.i(TAG,"Creating new view");
			  View resultView = inflater.inflate(R.layout.image_layout, container, false);
		//	  list = (ListView) resultView.findViewById(R.id.listView1);	
			  imview = (ImageView) resultView.findViewById(R.id.imageView1);
			 return resultView;
			 }
	
	@Override
	  public void onActivityCreated(Bundle b) {
	    super.onActivityCreated(b);

	    test = (Imageinterface) getActivity();
		 test.update_image(); 
		 if(b !=null)
		 {
			 String srr= b.getString("key");
		     im_id = srr;
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
		        	String url = "http://bismarck.sdsu.edu/photoserver/photo/" + im_id;
		        	System.out.println(url);
		        	Imagedownloader usl = new Imagedownloader();
		        	usl.delegate=this;
		        	usl.execute(url);
		            Log.i(TAG,"success");
		        } else {
		           Log.i("ImageFragment","Fail");
		        }
		
		
	}
	
	public void update_text(String s)
	{
		im_id=s;
	}

	@Override
	public void processFinish_image(Bitmap output) {
		
		imview.setImageBitmap(output);
	}
	
	@Override
	public void onSaveInstanceState(Bundle b)
	{
		super.onSaveInstanceState(b);
		
		b.putString("key", im_id);
	}

}
