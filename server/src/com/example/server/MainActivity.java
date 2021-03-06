package com.example.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.server.Dtatabasehelper.FeedEntry;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends Activity implements OnUserSelectedListener,testinterface,AsyncResponsetwo,AsyncResponse,AsyncResponse_image,Imageinterface,OfflineAsyncResponse,offlineAsyncResponse_photo,imagereader_response{
	private static String TAG = "MainActivity";
	String s_id,im_id,ss_id;
    public PhotolistFragment photofragment,photofrag,photofrag1;
    public Imagefragment image,imagefrag;
    public MainFragment fragment,mainfrag;
    public ProgressBar pb;
    public List<Data> outp = new ArrayList();
    public ArrayList<Integer> in;
    public int pos,u_id;
    public Intent go;
    private static final int INTENT_EXAMPLE_REQUEST = 123;
    private Bitmap bp;
    private FileOutputStream fos;
    private ArrayList<String> name_user = new ArrayList();
	private ArrayList<Integer> id_user = new ArrayList();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_main);
		 
	    
		photofragment = new PhotolistFragment();
	    fragment = new MainFragment();
	    
	    pb = (ProgressBar)findViewById(R.id.progressBar_tab_land);
	    pb.setVisibility(View.INVISIBLE);
	    image = (Imagefragment) getFragmentManager().findFragmentById(R.id.fragment3);
	    
	    mainfrag = (MainFragment) getFragmentManager().findFragmentById(R.id.fragment1);
	    
	    photofrag = (PhotolistFragment) getFragmentManager().findFragmentById(R.id.fragment2);
	    
	   
		if(savedInstanceState == null)
		{
			
			if(isnetwork())
			{
	        	DownloadUsers us = new DownloadUsers();
	        	us.delegate=this;
	        	us.execute("http://bismarck.sdsu.edu/photoserver/userlist");
	        	 pb.setVisibility(View.VISIBLE);
	            Log.i("MainFragment","success");
	        } else {
	           Log.i("MainFragment","Fail");
	           pb.setVisibility(View.VISIBLE);
	           Toast.makeText(this, "you are not connected to internet!!! Application works on offline mode", Toast.LENGTH_SHORT).show();
	           async_getuserlist asy = new async_getuserlist(getBaseContext());
	           asy.del=this;
	           asy.execute();
	        }
			
		if(mainfrag == null || !mainfrag.isInLayout())
		{
			FragmentManager fragments = getFragmentManager(); 
			 FragmentTransaction fragmentTransaction = 
			fragments.beginTransaction();
			 fragments.enableDebugLogging(true);
			 fragmentTransaction.add(R.id.mainactivity,fragment,"MainFragment");
			// fragmentTransaction.addToBackStack("Fragment");
			 fragmentTransaction.commit();
		}
		else
		{
			
			    
		}
		
		if(image == null || !image.isInLayout())
		{
			
		}
		
		
		}

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	

	@Override
	public void notify(int ss_id) {
		
	 
    u_id =ss_id;
   
    String s_id = Integer.toString(ss_id);
    
    if(isnetwork())
    {
	        	String url = "http://bismarck.sdsu.edu/photoserver/userphotos/" + s_id;
	        	System.out.println(url);
	        	DownloadUserphotolist usl = new DownloadUserphotolist();
	        	usl.delegate=this;
	        	usl.execute(url);
	        	 pb.setVisibility(View.VISIBLE);
	            Log.i("PhotoFragment","success");
	        } else {
	           Log.i("PhotoFragment","Fail");
	           pb.setVisibility(View.VISIBLE);
	           async_getphotolist asy = new async_getphotolist(getBaseContext());
	           asy.del=this;
	           asy.execute(u_id);
	        }
	        
	        
    
    if(photofrag == null || !photofrag.isInLayout())
	{
        go = new Intent(this,PhotoActivity_holder.class);
    }
    if(image == null || !image.isInLayout())
	{
	}
    else
    {
    	Imagefragment im = (Imagefragment) getFragmentManager().findFragmentById(R.id.fragment3);
		im.clearview();
    }
	
    
}
	

	@Override
	public void notifyuserphotolist(ArrayList<Integer> in_l,int pos_l) {
		 pb.setVisibility(View.VISIBLE);
		 in = in_l;
		 pos = pos_l;
		 
		if(image == null || !image.isInLayout())
		{
			go = new Intent(this,ImageActivity_holder.class);
		        go.putExtra("list",in);
		        go.putExtra("pos", pos);
		        startActivity(go);
		        
		}
		else
		{
		
		     int i_id = in.get(pos);
		     String str_id = Integer.toString(i_id);
		     download(str_id);
		
		}
		
	}


	@Override
	public void processFinishtwo(List<Data> result) {
		
		
		try
		{
	    pb.setVisibility(View.INVISIBLE);
		ArrayList<String> str = new ArrayList();
		ArrayList<Integer> in = new ArrayList();
	    for(int i=0;i<result.size();i++)
	    {
	    	str.add(result.get(i).user);
	    	in.add(result.get(i).id);
	    }
	   
		
		if(photofrag == null || !photofrag.isInLayout())
		{
			
			
			
		    go.putExtra("str", str);
		    go.putExtra("in", in);
		    
		    startActivity(go);
		
		    
		    
		}
		else
		{
			PhotolistFragment photofrag2 = (PhotolistFragment) getFragmentManager().findFragmentById(R.id.fragment2);
			photofrag2.updatephotolist(str,in);
			
			
			
		}
		
		
        async_insert_photo asp = new async_insert_photo(getBaseContext());
		
		asp.execute(result,u_id);
		
		
		}
		catch(Exception e)
		{
			
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processFinish(List<Data> output) {
		try
		{
	    pb.setVisibility(View.INVISIBLE);
		ArrayList<String> str = new ArrayList();
		ArrayList<Integer> in = new ArrayList();
		for(int i=0;i<output.size();i++)
	    {
	    	str.add(output.get(i).user);
	    	in.add(output.get(i).id);
	    	
	    }
		
		
		
		if(mainfrag == null || !mainfrag.isInLayout())
		{
		
		MainFragment mf = (MainFragment) getFragmentManager().findFragmentByTag("MainFragment");	
	   		mf.updatelist(str,in);
		}
		else
		{
			MainFragment mf = (MainFragment) getFragmentManager().findFragmentById(R.id.fragment1);
			mf.updatelist(str,in);
		
	    
		}
		async_insert as = new async_insert(getBaseContext());
		   as.execute(output);
	
		}
		catch(Exception e)
		{
			
		}
	}

	@Override
	public void processFinish_image(Bitmap output) {
		
	   try
	   {
		if(image == null || !image.isInLayout())
		{
		       
		}
		else
		{
			Imagefragment im = (Imagefragment) getFragmentManager().findFragmentById(R.id.fragment3);
			im.setImage(output);
			 pb.setVisibility(View.INVISIBLE);
			 Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
		}
	   }
	   catch(Exception e)
	   {
		   
	   }
		
	}

	@Override
	public void update_image() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStop()
	{
		super.onStop();
		if(pb.getVisibility() == View.VISIBLE)
		{
			pb.setVisibility(View.INVISIBLE);
		}
		
	}

	@Override
	public void nextImage() {
		
		
		if(pos>=in.size())
		{
			Toast.makeText(this, "you reached position 0 ", Toast.LENGTH_SHORT).show();
		}
		else
		{
			pos = pos+1;
			int i_id = in.get(pos);
			String str_id = Integer.toString(i_id);
			download(str_id);
			 photofrag.setposition(pos);
		}
		
	}

	
	@Override
	public void previousImage() {
		
		
		if(pos<=0)
		{
			Toast.makeText(this, "You reached position last", Toast.LENGTH_SHORT).show();
		}
		else
		{
	
			pos = pos-1;
			int i_id = in.get(pos);
			String str_id = Integer.toString(i_id);
			download(str_id);
			 photofrag.setposition(pos);
		}
		
	}
	
	public void download(String str_id)
	{
		ss_id=str_id;
		imagereader ima = new imagereader();
		ima.del=this;
		ima.execute(str_id);

	}
		 
	
	public boolean isnetwork()
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) { 
        	return true;
        } else {
           return false;
        }
	}

	@Override
	public void processFinish_offline(List<Data> output) {
		
		if(output == null || output.size() <=0)
		{
			Toast.makeText(this, "Information not available on database", Toast.LENGTH_SHORT).show();
			pb.setVisibility(View.INVISIBLE);
		}
		else
		{
		try
		{
			
	    pb.setVisibility(View.INVISIBLE);
		ArrayList<String> str = new ArrayList();
		ArrayList<Integer> in = new ArrayList();
		for(int i=0;i<output.size();i++)
	    {
	    	str.add(output.get(i).user);
	    	in.add(output.get(i).id);
	    	
	    }
		
		
		
		if(mainfrag == null || !mainfrag.isInLayout())
		{
		
		MainFragment mf = (MainFragment) getFragmentManager().findFragmentByTag("MainFragment");	
	   		mf.updatelist(str,in);
		}
		else
		{
			MainFragment mf = (MainFragment) getFragmentManager().findFragmentById(R.id.fragment1);
			mf.updatelist(str,in);
		
	    
		}
	
		}
		catch(Exception e)
		{
			
		}
		}	
	}

	@Override
	public void processFinish_offline_photo(List<Data> result) {
		
		if(result==null || result.size()<=0)
		{
			Toast.makeText(this, "Information not available on database", Toast.LENGTH_SHORT).show();
			 pb.setVisibility(View.INVISIBLE);
		}
		else
		{
		
		try
		{
	    pb.setVisibility(View.INVISIBLE);
		ArrayList<String> str_photo = new ArrayList();
		ArrayList<Integer> in_photo = new ArrayList();
	   
		for(int i=0;i<result.size();i++)
	    {
	    	str_photo.add(result.get(i).user);
	    	in_photo.add(result.get(i).id);
	    }
	   
		
		if(photofrag == null || !photofrag.isInLayout())
		{
			
			
			
		    go.putExtra("str", str_photo);
		    go.putExtra("in", in_photo);
		    
		    startActivity(go);
		
		    
		    
		}
		else
		{
			PhotolistFragment photofrag2 = (PhotolistFragment) getFragmentManager().findFragmentById(R.id.fragment2);
			photofrag2.updatephotolist(str_photo,in_photo);
			
			
			
		}
		
	
		}
		catch(Exception e)
		{
			
		}
	}
  }

	@Override
	public void processFinish_reader(Bitmap output) {
		
		if(output!=null)
		{
			pb.setVisibility(View.INVISIBLE);
			Imagefragment im = (Imagefragment) getFragmentManager().findFragmentById(R.id.fragment3);
			im.setImage(output);
		}
		else
		{
		if(isnetwork())
		 {
       	System.out.println(ss_id);
       	Imagedownloader usl = new Imagedownloader();
       	usl.delegate=this;
       	usl.execute(ss_id);
       	 pb.setVisibility(View.VISIBLE);
           Log.i(TAG,"success");
       } else {
          Log.i("ImageFragment","Fail");
          Toast.makeText(this, "Information not found on database", Toast.LENGTH_SHORT).show();
          Imagefragment im = (Imagefragment) getFragmentManager().findFragmentById(R.id.fragment3);
			im.clearview();
			pb.setVisibility(View.INVISIBLE);
       }
	}
		
	}
		
}

	



