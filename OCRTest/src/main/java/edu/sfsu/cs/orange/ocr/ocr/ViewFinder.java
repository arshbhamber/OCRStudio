package edu.sfsu.cs.orange.ocr.ocr;

import java.util.Iterator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ViewFinder extends View {

	Rect frame;
	Paint paint;
	Mapping mapping;
	int frame_width = 5;
	public ViewFinder(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		frame = new Rect();
		mapping = new Mapping();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setColor(Color.argb(200, 0, 0, 0));
		paint.setStyle(Paint.Style.FILL);
		
		canvas.drawRect(frame.left, 0, canvas.getWidth(), frame.top, paint);
		canvas.drawRect(0, 0, frame.left, frame.bottom, paint);
		canvas.drawRect(0, frame.bottom, frame.right, canvas.getHeight(), paint);
		canvas.drawRect(frame.right, frame.top, canvas.getWidth(), canvas.getHeight(), paint);
		paint.setColor(Color.argb(255, 255, 255, 255));
		canvas.drawRect(frame.left-frame_width, frame.top-frame_width, frame.right+frame_width,frame.top, paint);
		canvas.drawRect(frame.left-frame_width, frame.top-frame_width, frame.left,frame.bottom+frame_width, paint);
		canvas.drawRect(frame.left-frame_width, frame.bottom, frame.right+frame_width,frame.bottom+frame_width, paint);
		canvas.drawRect(frame.right  , frame.top-frame_width, frame.right+frame_width,frame.bottom, paint);

		Iterator<RectF> iterator = mapping.areamap.get("Maharashtra").iterator();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.BLACK);
		while(iterator.hasNext()){
			RectF rf = iterator.next();
			Rect r = new Rect((int)frame.left*(int)(1+rf.left),(int)frame.top*(int)(1+rf.top),(int)frame.right*(int)(1-rf.right),(int)frame.bottom*(int)(1-rf.bottom));
			canvas.drawRect(r, paint);
			Log.e("Drawing rectangles", VIEW_LOG_TAG);
			
		}
		
//		canvas.draw
//		canvas.drawRect(frame.left-10, frame.top-10, frame.left, frame.top, paint);
//		canvas.drawRect(frame.right, frame.top-10, frame.right+10, frame.top, paint);
//		canvas.drawRect(frame.left-10, frame.bottom+10, frame.left, frame.bottom, paint);
//		canvas.drawRect(frame.right, frame.bottom, frame.right+10, frame.bottom+10, paint);
		
		
		
	}
	
	public void setFrame(Rect rect){
		
		frame = rect;
		invalidate();
		
	}
	

}
