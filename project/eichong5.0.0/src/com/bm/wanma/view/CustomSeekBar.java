package com.bm.wanma.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * @author cm
 * 自定义seekbar，实现多段颜色显示
 */
public class CustomSeekBar extends SeekBar {

	private ArrayList<ProgressItem> mProgressItemsList;
	//定义圆角
	private float[] radiiL={40f,40f,0f,0f,0f,0f,40f,40f};
	private float[] radiiR={0f,0f,40f,40f,40f,40f,0f,0f};
	private float[] radii={0f,0f,0f,0f,0f,0f,0f,0f};
	private float[] radiiAll={40f,40f,40f,40f,40f,40f,40f,40f};
	
	public CustomSeekBar(Context context) {
		super(context);
	}

	public CustomSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public CustomSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void initData(ArrayList<ProgressItem> progressItemsList) {
		this.mProgressItemsList = progressItemsList;
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas canvas) {
		
		if (mProgressItemsList.size() > 0) {
			int progressBarWidth = getWidth();
			int progressBarHeight = getHeight();
			int thumboffset = getThumbOffset();
			int lastProgressX = 0;
			int progressItemWidth, progressItemRight;
			int size = mProgressItemsList.size();
			
			for (int i = 0; i < size; i++) {
				ProgressItem progressItem = mProgressItemsList.get(i);
				Paint progressPaint = new Paint();
				progressPaint.setAntiAlias(true);//去锯齿
				progressPaint.setColor(getResources().getColor(
						progressItem.color));

				progressItemWidth = (int) (progressItem.progressItemPercentage
						* progressBarWidth / 100);

				progressItemRight = lastProgressX + progressItemWidth;
				Path cmpath = new Path();
				RectF progressRect = new RectF();
				progressRect.set(lastProgressX, thumboffset / 2,
						progressItemRight, progressBarHeight - thumboffset / 2);
				if (i == mProgressItemsList.size() - 1
						&& progressItemRight != progressBarWidth) {
					progressItemRight = progressBarWidth;
				}
				
				if(i == 0 && size == 1){
					cmpath.addRoundRect(progressRect, radiiAll, Path.Direction.CW);
					canvas.drawPath(cmpath,progressPaint);
				}else if(i == 0 && size > 1){
					cmpath.addRoundRect(progressRect, radiiL, Path.Direction.CW);
					canvas.drawPath(cmpath,progressPaint);
				}else if(i == 1 && size > 2){
					cmpath.addRoundRect(progressRect, radii, Path.Direction.CW);
					canvas.drawPath(cmpath,progressPaint);
				}else {
					cmpath.addRoundRect(progressRect, radiiR, Path.Direction.CW);
					canvas.drawPath(cmpath,progressPaint);
				}
				
				lastProgressX = progressItemRight;
			}
			
			super.onDraw(canvas);
		}

	}

}
