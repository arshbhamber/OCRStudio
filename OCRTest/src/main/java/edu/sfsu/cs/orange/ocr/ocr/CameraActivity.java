package edu.sfsu.cs.orange.ocr.ocr;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import edu.sfsu.cs.orange.ocr.R;

@SuppressWarnings("deprecation")
public class CameraActivity extends Activity implements SurfaceHolder.Callback {
	TextView testView;

	 private static final int MIN_FRAME_WIDTH = 50; // originally 240
	  private static final int MIN_FRAME_HEIGHT = 20; // originally 240
	  private static final int MAX_FRAME_WIDTH = 800; // originally 480
	  private static final int MAX_FRAME_HEIGHT = 600; // originally 360
	  
	  private static final int MIN_PREVIEW_PIXELS = 470 * 320; // normal screen
	  private static final int MAX_PREVIEW_PIXELS = 800 * 600; // more than large/HD screen
	int no_of_pics=0;
	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	ViewFinder viewFinder;
	Rect frame;
	Point size;
	PictureCallback rawCallback;
	ShutterCallback shutterCallback;
	PictureCallback jpegCallback;
	String path1,path2;

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		AssetManager assets = getResources().getAssets();
		new OCRinit(assets).downloadEngine();
		Display display = getWindowManager().getDefaultDisplay();
		size = new Point();
		display.getSize(size);
		final int width = size.x;
		final int height = size.y;
		frame = new Rect(width/2-50, height/2-100, width/2+50, height/2+100);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		surfaceHolder = surfaceView.getHolder();

		viewFinder = (ViewFinder)findViewById(R.id.ViewFinder);
		surfaceHolder.addCallback(this);

		viewFinder.setFrame(frame);
		
		viewFinder.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction()){
				
				case MotionEvent.ACTION_MOVE:
					int x,y;
					
					
					if((int)event.getX()>width/2)
						x = (int)event.getX()-width/2;
					else
						x = width/2-(int)event.getX();
//					if((int)event.getY()>height/2)
//						y = (int)event.getY()-height/2;
//					else
//						y = height/2-(int)event.getY();
					y = (int)(1.66 * x);
					Log.e("motion", x+"  "+y);
					frame.set(width/2-x, height/2-y, width/2+x, height/2+y);
				
					viewFinder.setFrame(frame);
					
				}
				
				return false;
			}
		});
		
		// deprecated setting, but required on Android versions prior to 3.0
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		jpegCallback = new PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {
				FileOutputStream outStream = null;
				if(no_of_pics==0){

					path1 = String.format(Environment.getExternalStoragePublicDirectory(
				            Environment.DIRECTORY_PICTURES)+"/front%d.jpg", System.currentTimeMillis());
					try {
						outStream = new FileOutputStream(path1);
						outStream.write(data);
						outStream.close();
						Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
					}
					Toast.makeText(getApplicationContext(), "Picture Saved", 2000).show();
					refreshCamera();
//					no_of_pics ++;
				
				}	if(no_of_pics==1){

					path2 = String.format(Environment.getExternalStoragePublicDirectory(
				            Environment.DIRECTORY_PICTURES)+"/back%d.jpg", System.currentTimeMillis());
				try {
					outStream = new FileOutputStream(path2);
					outStream.write(data);
					outStream.close();
					Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
				}
				Toast.makeText(getApplicationContext(), "Picture Saved", 2000).show();
				refreshCamera();
				
				}
				no_of_pics ++;
				if(no_of_pics==2){
					
					Intent i = new Intent(CameraActivity.this,DataExtraction.class);
					i.putExtra("front", path1);
					i.putExtra("back", path2);
					i.putExtra("frame", frame);
					i.putExtra("point", size);
					
				startActivity(i);
				}
				
			}
		};
	}

	public void captureImage(View v) throws IOException, InterruptedException {
		//take the picture
		camera.autoFocus(new AutoFocusCallback() {
			
			public void onAutoFocus(boolean success, Camera camera) {
				// TODO Auto-generated method stub
				if(success)
					camera.takePicture(null, null, jpegCallback);
			}
		});
		
//		Thread.sleep(5000);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		no_of_pics = 0;
		
	}

	public void refreshCamera() {
		if (surfaceHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			camera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here
		// start preview with new settings
		try {
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (Exception e) {

		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		refreshCamera();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// open the camera
			camera = Camera.open();
		} catch (RuntimeException e) {
			// check for exceptions
			System.err.println(e);
			return;
		}
		Camera.Parameters param;
		param = camera.getParameters();

		// modify parameter
		camera.setDisplayOrientation(90);
		param.setPreviewSize(2100, 1500);
//		param.setPictureSize(1260, 768);
		param.setPictureSize(2100, 1500);
//		param.setPictureSize(640, 480);
		param.setPictureFormat(ImageFormat.JPEG);
		param.setJpegQuality(100);
//		param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//		camera.setParameters(param);
		 try {
			// The Surface has been created, now tell the camera where to draw
			// the preview.
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (Exception e) {
			// check for exceptions
			System.err.println(e);
			return;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// stop preview and release camera
		camera.stopPreview();
		camera.release();
		camera = null;
	}
	
	public void gallery(View v){
		
		
		Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		
		startActivityForResult(i, 0);
		
		
		
		
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 0){
		
				
				Uri imageuri = data.getData();
				path1 = getRealPathFromURI(this, imageuri);
				
				
			
			
			Intent i = new Intent(CameraActivity.this,DataExtraction.class);
			i.putExtra("front", path1);
//			i.putExtra("byte", data);
			
			startActivity(i);
			
			
		}
		
	}
	public String getRealPathFromURI(Context context, Uri contentUri) {
		  Cursor cursor = null;
		  try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
		}
	
	  private Point findBestPreviewSizeValue(Camera.Parameters parameters, Point screenResolution) {

		    // Sort by size, descending
		    List<Camera.Size> supportedPreviewSizes = new ArrayList<Camera.Size>(parameters.getSupportedPreviewSizes());
		    Collections.sort(supportedPreviewSizes, new Comparator<Camera.Size>() {
		      @Override
		      public int compare(Camera.Size a, Camera.Size b) {
		        int aPixels = a.height * a.width;
		        int bPixels = b.height * b.width;
		        if (bPixels < aPixels) {
		          return -1;
		        }
		        if (bPixels > aPixels) {
		          return 1;
		        }
		        return 0;
		      }
		    });

//		    if (Log.isLoggable(TAG, Log.INFO)) {
//		      StringBuilder previewSizesString = new StringBuilder();
//		      for (Camera.Size supportedPreviewSize : supportedPreviewSizes) {
//		        previewSizesString.append(supportedPreviewSize.width).append('x')
//		        .append(supportedPreviewSize.height).append(' ');
//		      }
//		      Log.i(TAG, "Supported preview sizes: " + previewSizesString);
//		    }

		    Point bestSize = null;
		    float screenAspectRatio = (float) screenResolution.x / (float) screenResolution.y;

		    float diff = Float.POSITIVE_INFINITY;
		    for (Camera.Size supportedPreviewSize : supportedPreviewSizes) {
		      int realWidth = supportedPreviewSize.width;
		      int realHeight = supportedPreviewSize.height;
		      int pixels = realWidth * realHeight;
		      if (pixels < MIN_PREVIEW_PIXELS || pixels > MAX_PREVIEW_PIXELS) {
		        continue;
		      }
		      boolean isCandidatePortrait = realWidth < realHeight;
		      int maybeFlippedWidth = isCandidatePortrait ? realHeight : realWidth;
		      int maybeFlippedHeight = isCandidatePortrait ? realWidth : realHeight;
		      if (maybeFlippedWidth == screenResolution.x && maybeFlippedHeight == screenResolution.y) {
		        Point exactPoint = new Point(realWidth, realHeight);
//		        Log.i(TAG, "Found preview size exactly matching screen size: " + exactPoint);
		        return exactPoint;
		      }
		      float aspectRatio = (float) maybeFlippedWidth / (float) maybeFlippedHeight;
		      float newDiff = Math.abs(aspectRatio - screenAspectRatio);
		      if (newDiff < diff) {
		        bestSize = new Point(realWidth, realHeight);
		        diff = newDiff;
		      }
		    }

		    if (bestSize == null) {
		      Camera.Size defaultSize = parameters.getPreviewSize();
		      bestSize = new Point(defaultSize.width, defaultSize.height);
//		      Log.i(TAG, "No suitable preview sizes, using default: " + bestSize);
		    }

//		    Log.i(TAG, "Found best approximate preview size: " + bestSize);
		    return bestSize;
		  }

}