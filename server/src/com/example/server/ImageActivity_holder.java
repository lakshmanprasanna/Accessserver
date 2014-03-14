package com.example.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ImageActivity_holder extends Activity implements Imageinterface,AsyncResponse_image,imagereader_response{

	private Intent go; 
	private String s_id;
	private int pos,id;
	private ArrayList<Integer> in;
	private Bitmap image;
	private boolean flag=false;
	private ProgressBar pb;
	private Imagefragment im;
	public FileOutputStream  fos;
	private Bitmap bitmap;
	private String ss_id;
	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_image_activity_holder);
		pb = (ProgressBar)findViewById(R.id.progressBar_image);
		pb.setVisibility(View.INVISIBLE);
		if(b!=null)
		{

			 image = b.getParcelable("im");
			 in = b.getIntegerArrayList("list");
			 pos = b.getInt("pos");
			 flag=true;
		}
		
		else
		{
	
		 go =getIntent();
		 in = go.getExtras().getIntegerArrayList("list");
		 pos = go.getExtras().getInt("pos");
		 id = in.get(pos);
		 s_id = Integer.toString(id);
		 download(s_id);		
		FragmentManager fragments = getFragmentManager(); 
		 FragmentTransaction fragmentTransaction = 
		fragments.beginTransaction();
		 fragments.enableDebugLogging(true);
		 Imagefragment im_f = new Imagefragment();
		 fragmentTransaction.add(R.id.imageactivity_holder,im_f,"Image");
		// fragmentTransaction.addToBackStack("Fragment");
		 fragmentTransaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_activity_holder, menu);
		return true;
	}

	@Override
	public void update_image() {
        if(flag)
        {
		im = (Imagefragment) getFragmentManager().findFragmentByTag("Image");
		im.setImage(image);
		flag=false;
        }
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle b)
	{
		super.onSaveInstanceState(b);
		
		b.putParcelable("im",image);
		b.putIntegerArrayList("list", in);
		b.putInt("pos", pos);
	}



	@Override
	public void processFinish_image(Bitmap output) {
		
		image = output;
		Imagefragment im = (Imagefragment) getFragmentManager().findFragmentByTag("Image");
		im.setImage(output);
		pb.setVisibility(View.INVISIBLE);
		Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void nextImage() {
		
		
		if(pos>=in.size())
		{
			Toast.makeText(this, "you reached position 0 ", Toast.LENGTH_SHORT).show();;
		}
		else
		{
			im = (Imagefragment) getFragmentManager().findFragmentByTag("Image");
			im.clearview();
			pos = pos+1;
			int i_id = in.get(pos);
			String str_id = Integer.toString(i_id);
			download(str_id);
			
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
			im = (Imagefragment) getFragmentManager().findFragmentByTag("Image");
			im.clearview();
			pos = pos-1;
			int i_id = in.get(pos);
			String str_id = Integer.toString(i_id);
			download(str_id);
			
			
		}
		
	}
	
	
	public void download(String str_id)
	{	
		ss_id=str_id;
		imagereader ima = new imagereader();
		ima.del=this;
		ima.execute(str_id);
	}

	@Override
	public void processFinish_reader(Bitmap output) {
		
		if(output!=null)
		{
			image=output;
			Imagefragment im = (Imagefragment) getFragmentManager().findFragmentByTag("Image");
			im.setImage(output);
			pb.setVisibility(View.INVISIBLE);
		}
		else
		{
			pb.setVisibility(View.VISIBLE);
			 ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		        if (networkInfo != null && networkInfo.isConnected()) {
		        	Imagedownloader usl = new Imagedownloader();
		        	usl.delegate=this;
		        	usl.execute(ss_id);
		 
		            Log.i("imholder","success");
		        } else {
		           
		        	Toast.makeText(this, "Information not available on database", Toast.LENGTH_SHORT).show();
		        	pb.setVisibility(View.INVISIBLE);
		        }
		}
		
	}
	

}
