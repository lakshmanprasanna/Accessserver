package com.example.server;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Imagefragment extends Fragment {
	
	private static String TAG = "IMAGEFRAGMENT";
	private Imageinterface test;
	private ImageView imview;
	private Bitmap ima;
	private View resultView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		
		    Log.i(TAG,"Creating new view");
			  resultView = inflater.inflate(R.layout.image_layout, container, false);
		//	  list = (ListView) resultView.findViewById(R.id.listView1);	
			  imview = (ImageView) resultView.findViewById(R.id.imageView1);
			        return resultView;
			 }
	
	@Override
	  public void onActivityCreated(Bundle b) {
	    super.onActivityCreated(b);
	     test = (Imageinterface) getActivity();
		 test.update_image(); 
		 
		 final GestureDetector gesture = new GestureDetector(getActivity(),
		            new GestureDetector.SimpleOnGestureListener() {

		                @Override
		                public boolean onDown(MotionEvent e) {
		                    return true;
		                }

		                @Override
		                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		                    float velocityY) {
		                    Log.i("ffffffffffff", "onFling has been called!");
		                    final int SWIPE_MIN_DISTANCE = 120;
		                    final int SWIPE_MAX_OFF_PATH = 250;
		                    final int SWIPE_THRESHOLD_VELOCITY = 200;
		                    try {
		                        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
		                            return false;
		                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
		                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		                            Log.i("ddddddddddddd", "Right to Left");
		                            
		                            test.nextImage();
		                            
		                        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
		                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		                            Log.i("gggggggggggggggggggg", "Left to Right");
		                            test.previousImage();
		                            
		                        }
		                    } catch (Exception e) {
		                        // nothing
		                    }
		                    return super.onFling(e1, e2, velocityX, velocityY);
		                }
		            });

		        resultView.setOnTouchListener(new View.OnTouchListener() {
		            @Override
		            public boolean onTouch(View v, MotionEvent event) {
		                return gesture.onTouchEvent(event);
		            }
		        });
		 
	}
	
	public void setImage(Bitmap image)
	{
		imview.setScaleType(ScaleType.CENTER_INSIDE);
		imview.setImageBitmap(image);
		ima = image;
	}
	
	public void clearview()
	{
		imview.setImageResource(-1);
		
	}

	
	
}
