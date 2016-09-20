package edu.sfsu.cs.orange.ocr.ocr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.googlecode.tesseract.android.TessBaseAPI;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import edu.sfsu.cs.orange.ocr.R;

public class DataExtraction extends Activity {

	
	  File myFile;// = new File("/sdcard/mysdfile.txt");
	CustomAdapter adapter;
	private ArrayList<String> key;
	private ArrayList<String> value;
	private ListView list_view;
	Bitmap image,image2;
	ImageView iv;
	Rect frame;
	Database db;
	private String lang = "eng";
	ArrayList<String> list;
	JSONObject result = new JSONObject();
	String path1;
//	byte[] data;
	Mapping mapping;
	static {
	    if (!OpenCVLoader.initDebug()) {
	        // Handle initialization error
	    }
	}
	
	 private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
	        @Override
	        public void onManagerConnected(int status) {
	            switch (status) {
	                case LoaderCallbackInterface.SUCCESS:
	                {
	                    Log.i("OpenCV", "OpenCV loaded successfully");
//	                    camera.enableView();
//	                    mOpenCvCameraView.setOnTouchListener(MainActivity.this);
	                } break;
	                default:
	                {
	                    super.onManagerConnected(status);
	                } break;
	            }
	        }
	    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("OpenCV", "Trying to load OpenCV library");
		 if (!OpenCVLoader.initDebug()) {
		        // Handle initialization error
		    }

		 db = new Database(this);
	     myFile = new File("/sdcard/mysdfile.txt");
		Toast.makeText(getApplicationContext(), "Started", 2000).show();
		setContentView(R.layout.datalist);
		 path1 = getIntent().getExtras().getString("front");
//		String path2 = getIntent().getExtras().getString("back");
		File f = new File(path1);
		Log.e("TextResult", "Loading file");
		image = BitmapFactory.decodeFile(f.getAbsolutePath());

//		processImage();
		
	
		mapping = new Mapping();
		key = new ArrayList<String>();
		value = new ArrayList<String>();
		list = new ArrayList<String>();

		list_view = (ListView) findViewById(R.id.listView1);
		adapter = new CustomAdapter(this,key,value);
		list_view.setAdapter(adapter);
//		runAsyncTask();

	}

	public void runAsyncTask() {

		Iterator<RectF> iterator = mapping.areamap.get("Maharashtra").iterator();
		while(iterator.hasNext()){
			RectF rect = iterator.next();
			new OCRAsyncTask().execute(Bitmap.createBitmap(image, (int) (image.getWidth()*rect.left), (int) (image.getHeight()*rect.top),  (int) (image.getWidth()*rect.right), (int) (image.getHeight()*rect.bottom)));
//			new OCRAsyncTask().execute(Bitmap.createScaledBitmap(Bitmap.createBitmap(image, (int) (image.getWidth()*rect.left), (int) (image.getHeight()*rect.top),  (int) (image.getWidth()*rect.right), (int) (image.getHeight()*rect.bottom)),image.getWidth(),image.getHeight(),false));
			
		}

	}
	
	  public void onResume()
	    {
	        super.onResume();
	        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
	    }


	public class OCRAsyncTask extends AsyncTask<Bitmap, Void, String> {


		float intensity;
		ProgressDialog dialog;
		private long timeRequired;
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	
	dialog = new ProgressDialog(DataExtraction.this);
	dialog.setMessage("processing");
	dialog.show();
}
		public OCRAsyncTask( ) {
			// TODO Auto-generated constructor stub
			
			
		}

		@Override
		protected String doInBackground(Bitmap... image) {
			// TODO Auto-generated method stub
			long start = System.currentTimeMillis();
			
//			Bitmap bitmap = createBlackAndWhite(image[0], 0.2f);

			String textResult = "";
			for(Bitmap bitmap:image){
			

//				bitmap = createBlackAndWhite(bitmap, intensity);
				Log.e("TextResult", "started scanning");
				TessBaseAPI baseApi = new TessBaseAPI();
				baseApi.setDebug(true);
				baseApi.init(Environment.getExternalStorageDirectory().toString() + "/SimpleAndroidOCR/", lang);
				baseApi.setImage(bitmap);
				textResult = textResult +"\n" +baseApi.getUTF8Text();
				timeRequired = System.currentTimeMillis() - start;
				Log.e("TextResult", "scanning finished");
				// Check for failure to recognize text
				if (textResult == null || textResult.equals("")) {
					return "";
			}
			
			baseApi.end();
			timeRequired = System.currentTimeMillis() - start;

		
			Log.e("TextResult", textResult);
			}
			return textResult;
		}

		@Override
		protected void onPostExecute(String textResult) {
			// TODO Auto-generated method stub
			super.onPostExecute(textResult);

			list.add(textResult);
			extract(list);
			dialog.dismiss();
			Log.e("length", ""+value.size());
			adapter.notifyDataSetChanged();
			
		}
		
	}
	public  Bitmap createBlackAndWhite(Bitmap src, float strength) {
		int width = src.getWidth();
		int height = src.getHeight();

		Bitmap bmOut = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);

		final float factor = 255f;
		final float redBri = 0.2126f;
		final float greenBri = 0.2126f;
		final float blueBri = 0.0722f;

		int length = width * height;
		int[] inpixels = new int[length];
		int[] oupixels = new int[length];

		src.getPixels(inpixels, 0, width, 0, 0, width, height);

		int point = 0;
		for (int pix : inpixels) {
			int R = (pix >> 16) & 0xFF;
			int G = (pix >> 8) & 0xFF;
			int B = pix & 0xFF;

			float lum = (redBri * R / factor) + (greenBri * G / factor)
					+ (blueBri * B / factor);

			if (lum > strength) {
				oupixels[point] = 0xFFFFFFFF;
			} else {
				oupixels[point] = 0xFF000000;
			}
			point++;
		}
		bmOut.setPixels(oupixels, 0, width, 0, 0, width, height);

		return bmOut;
	}

	
	public void extract(ArrayList<String> data){

		for (int i = 0; i < data.size(); i++) {

			String s = data.get(i);
			s = s.concat("\n");
			s = s.replaceAll("[^A-Za-z 0-9\n./]", "").toUpperCase();
//			s = s.replaceAll("(^| ).( |$)","");
//			s = s.replaceAll("(\n. )*","\n");
			s = s.replaceAll("( .\n)","\n");

			Iterator<MapObject> iterator = mapping.maplist.iterator();

			while (iterator.hasNext()) {
				MapObject map = iterator.next();
				Iterator<String> it = map.mappedValues.iterator();
				while (it.hasNext()) {
					String p = it.next();

					if (s.contains(p) && map.flag == false&&(s.substring(s.indexOf(p) + p.length(),s.indexOf("\n", s.indexOf(p))).length()>=map.min_size)) {
						if(map.index==-2){
							map.index = key.size();
							
						key.add(map.key);
						
//						mapping.maplist.get(index)
						String val = s.substring(s.indexOf(p) + p.length(),s.indexOf("\n", s.indexOf(p)));
						val = val.replaceAll("(^|^ ).( |$)","").trim();
						val = smartness(map.key, val);
						value.add(val);
						}if(map.size_limit){
							
							map.flag = true;
							
						}
						else{
							if(!map.flag){
							key.set(map.index, map.key);
							String val = s.substring(s.indexOf(p) + p.length(),s.indexOf("\n", s.indexOf(p)));
							val = val.replaceAll("(^|^ ).( |$)","");
							value.set(map.index, val);
							
							}
						}
					}	
				}			
			}	
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		this.finish();
	}
	
	public static Bitmap GrayscaleToBin(Bitmap bm2){
		
	 Bitmap bm;
	 bm=bm2.copy(Config.RGB_565, true);
	 final   int width = bm.getWidth();
	 final  int height = bm.getHeight();

	 int pixel1,pixel2,pixel3,pixel4,A,R;
	 int[]  pixels;
	 pixels = new int[width*height];
	 bm.getPixels(pixels,0,width,0,0,width,height);
	 int size=width*height;
	      int s=width/8;
	      int s2=s>>1;
	      double t=0.15;
	      double it=1.0-t;
	      int []integral= new int[size];
	      int []threshold=new int[size];
	      int i,j,diff,x1,y1,x2,y2,ind1,ind2,ind3;
	      int sum=0;
	      int ind=0;
	      while(ind<size)
	      {
	       sum+=pixels[ind] & 0xFF;
	       integral[ind]=sum;
	       ind+=width;
	      }
	   x1=0;
	   for(i=1;i<width;++i)       {
	       sum=0;
	       ind=i;
	       ind3=ind-s2;
	       if(i>s){x1=i-s;}
	       diff=i-x1;
	       for(j=0;j<height;++j)
	       {
	           sum+=pixels[ind] & 0xFF;
	           integral[ind]=integral[(int)(ind-1)]+sum;
	           ind+=width;
	           if(i<s2)continue;
	           if(j<s2)continue;
	           y1=(j<s ? 0 : j-s);
	           ind1=y1*width;
	           ind2=j*width;

	        if (((pixels[ind3]&0xFF)*(diff * (j - y1))) < ((integral[(int)(ind2 + i)] - integral[(int)(ind1 + i)] - integral[(int)(ind2 + x1)] + integral[(int)(ind1 + x1)])*it))
	        {
	            threshold[ind3] = 0x00;
	        } else {
	            threshold[ind3] = 0xFFFFFF;
	        }
	        ind3 += width;
	    }
	}

	y1 = 0;
	for( j = 0; j < height; ++j )
	{
	    i = 0;
	    y2 =height- 1;
	    if( j <height- s2 ) 
	    {
	        i = width - s2;
	        y2 = j + s2;
	    }

	    ind = j * width + i;
	    if( j > s2 ) y1 = j - s2;
	    ind1 = y1 * width;
	    ind2 = y2 * width;
	    diff = y2 - y1;
	    for( ; i < width; ++i, ++ind )
	    {

	        x1 = ( i < s2 ? 0 : i - s2);
	        x2 = i + s2;

	        // check the border
	        if (x2 >= width) x2 = width - 1;

	        if (((pixels[ind]&0xFF)*((x2 - x1) * diff)) < ((integral[(int)(ind2 + x2)] - integral[(int)(ind1 + x2)] - integral[(int)(ind2 + x1)] + integral[(int)(ind1 + x1)])*it))
	        {
	            threshold[ind] = 0x00;
	        } else {
	            threshold[ind] = 0xFFFFFF;
	        }
	    }
	}
	   /*-------------------------------
	    * --------------------------------------------*/
	   bm.setPixels(threshold,0,width,0,0,width,height);

	   return bm;
	 }
	
	private Bitmap convert(Bitmap bitmap, Config config) {
		
	    Bitmap convertedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
	    Canvas canvas = new Canvas(convertedBitmap);
	    Paint paint = new Paint();
	    paint.setColor(Color.BLACK);
	    canvas.drawBitmap(bitmap, 0, 0, paint);
	    return convertedBitmap;
	}
	
	
	
	private static Mat findLargestRectangle(Mat original_image) {
       
		Mat imgSource = original_image;

        Bitmap bmpOriOut = Bitmap.createBitmap(imgSource.cols(), imgSource.rows(), Config.ARGB_8888);

        Utils.matToBitmap(imgSource, bmpOriOut);

        try {
            bmpOriOut.compress(CompressFormat.JPEG, 100, new FileOutputStream("/sdcard/mediaAppPhotos/original.jpg"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //convert the image to black and white, commenting this wont crash
        Imgproc.cvtColor(imgSource, imgSource, Imgproc.COLOR_BGR2RGB);

        //convert the image to black and white does (8 bit), commenting this crashes
        Imgproc.Canny(imgSource, imgSource, 50, 50);

        //apply gaussian blur to smoothen lines of dots, commenting this crashes
        Imgproc.GaussianBlur(imgSource, imgSource, new Size(5, 5), 5);

        //find the contours
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(imgSource, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = -1;
        int maxAreaIdx = -1;        MatOfPoint temp_contour = contours.get(0); //the largest is at the index 0 for starting point
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        Mat largest_contour = contours.get(0);
        List<MatOfPoint> largest_contours = new ArrayList<MatOfPoint>();
        for (int idx = 0; idx < contours.size(); idx++) {
            temp_contour = contours.get(idx);
            double contourarea = Imgproc.contourArea(temp_contour);
            //compare this contour to the previous largest contour found
            if (contourarea > maxArea) {
                //check if this contour is a square
                MatOfPoint2f new_mat = new MatOfPoint2f( temp_contour.toArray() );
                int contourSize = (int)temp_contour.total();
                Imgproc.approxPolyDP(new_mat, approxCurve, contourSize*0.05, true);
                if (approxCurve.total() == 4) {
                    maxArea = contourarea;
                    maxAreaIdx = idx;
                    largest_contours.add(temp_contour);
                    largest_contour = temp_contour;
                }
            }
        }
        MatOfPoint temp_largest = largest_contours.get(largest_contours.size()-1);
        largest_contours = new ArrayList<MatOfPoint>();
        largest_contours.add(temp_largest);

        Imgproc.cvtColor(imgSource, imgSource, Imgproc.COLOR_BayerBG2RGB);
        Imgproc.drawContours(imgSource, largest_contours, -1, new Scalar(0, 255, 0), 1);

        //create the new image here using the largest detected square

        //Toast.makeText(getApplicationContext(), "Largest Contour: ", Toast.LENGTH_LONG).show();
        Bitmap bmpOut = Bitmap.createBitmap(imgSource.cols(), imgSource.rows(), Config.ARGB_8888);

        Utils.matToBitmap(imgSource, bmpOut);

        try {
            bmpOut.compress(CompressFormat.JPEG, 100, new FileOutputStream("/sdcard/mediaAppPhotos/bigrect.jpg"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return imgSource;
    }
	
	public Bitmap opencv(Bitmap image){
		
		Bitmap img;
		Mat mat = new Mat();
//		img = image.copy(Config.ARGB_8888, true);
		img = Bitmap.createScaledBitmap(image, 2100, 1500, false);
		Utils.bitmapToMat(img, mat);

    	Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5,5));
    	Mat temp = new Mat(); 
    	Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);
    	Imgproc.GaussianBlur(mat, mat, new org.opencv.core.Size(3,3),1);
    	Mat mat2 = mat;
         Core.addWeighted(mat2, 1.5, mat, -0.5, 0, mat);
    	Imgproc.resize(mat, temp, new Size(mat.cols()/8, mat.rows()/8));
    	Imgproc.morphologyEx(temp, temp, Imgproc.MORPH_CLOSE, kernel);
    	Imgproc.resize(temp, temp, new Size(mat.cols(), mat.rows()));

    	Core.divide(mat, temp, temp, 1, CvType.CV_32F); // temp will now have type CV_32F
    	Core.normalize(temp, mat, 0, 255, Core.NORM_MINMAX, CvType.CV_8U);
    	Imgproc.threshold(mat, mat, -1, 255,Imgproc.THRESH_BINARY_INV+Imgproc.THRESH_OTSU);
    	
    	
    	
    	Utils.matToBitmap(mat, img);
    	
		img = img.copy(Config.ARGB_8888, true);
    	return img;
		
		
		
		
	}
	
	public void processImage(){
		
//		image = Bitmap.createScaledBitmap(image, 1260, 768, false);
//		image = Bitmap.createBitmap(image, frame.left*image.getWidth()/x, frame.top*image.getHeight()/y,frame.right*image.getWidth()/x - frame.left*image.getWidth()/x,frame.bottom*image.getHeight()/y - frame.top*image.getHeight()/y);
		Log.e("TextResult", "Image processing");
		
		image = GrayscaleToBin(image);
		Log.e("TextResult", "Convert to argb_8888");
		image = image.copy(Config.ARGB_8888, true);
		
//		image = opencv(image);
	}
	
	public void listmethod(View v){
		
//		image = opencv(image);
		image = GrayscaleToBin(image);
		image = image.copy(Config.ARGB_8888, false);
		runAsyncTask();
	}
	
	public void saveSql(View v){
		for(int i=0;i<key.size();i++){
			try {
				result.put(key.get(i), value.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			db.insertDetails(result.toString(4), path1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();
		
		try {
          
//            myFile.createNewFile();
            FileOutputStream fOut = openFileOutput(myFile.getAbsolutePath(),MODE_APPEND);
            OutputStreamWriter myOutWriter = 
                                    new OutputStreamWriter(fOut);
            myOutWriter.append(result.toString(4));
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getBaseContext(),
                    "Done writing SD 'mysdfile.txt'",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
		
		Intent i = new Intent(this,DisplayData.class);
		startActivity(i);
	
	}
	
	
	public String smartness(String key,String val){
		
		String value = val;
		StringBuilder builder = new StringBuilder(val);
		if(key.equals("REG.DT")){
			
			switch(val.length()){
				
			case 10:
//				value = val.substring(0,1)+"/"+ val.substring(3,4)+"/"+val.substring(6,10);
				builder.setCharAt(2, '/');
				builder.setCharAt(5, '/');
				break;
			default:
			
			}	
		}
		else if(key.equals("MFG DATE")){
			
			switch(val.length()){
			
			case 10:
				builder.setCharAt(2, '/');
				builder.setCharAt(5, '/');
				break;
			case 7:
				builder.setCharAt(2, '/');
				break;
			case 6:
				builder.insert(2, '/');
				break;
			}
			
		}
else if(key.equals("REG UPTO")){
			
			switch(val.length()){
			
			case 10:
				builder.setCharAt(2, '/');
				builder.setCharAt(5, '/');
				break;
			case 7:
				builder.setCharAt(2, '/');
			
			}
			
		}
		else if(key.equals("REGN NO")){
			if(val.length()==10){
	
				builder.setCharAt(0, new Filter().digitToAlphabet(builder.charAt(0)));
				builder.setCharAt(1, new Filter().digitToAlphabet(builder.charAt(1)));
				builder.setCharAt(2, new Filter().AlphabetToDigit(builder.charAt(2)));
				builder.setCharAt(3, new Filter().AlphabetToDigit(builder.charAt(3)));
				builder.setCharAt(4, new Filter().digitToAlphabet(builder.charAt(4)));
				builder.setCharAt(5, new Filter().digitToAlphabet(builder.charAt(5)));
			
			}
			
		}
		else if(key.equals("CHASSIS NO.")){
//			builder.replace('O', 'D');
			
		}	
		return builder.toString();
		
		
		
	}
}
