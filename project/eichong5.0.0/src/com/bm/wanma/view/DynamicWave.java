
package com.bm.wanma.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class DynamicWave extends View {

    //private static final int WAVE_PAINT_COLOR = 0xff7d00;
    // y = Asin(wx+b)+h
    private static final float STRETCH_FACTOR_A = 20;
    private static final int OFFSET_Y = 0;
 // 第一条水波移动速度
    private static final int TRANSLATE_X_SPEED_ONE = 10;
 // 第二条水波移动速度
    private static final int TRANSLATE_X_SPEED_TWO = 6;
    private float mCycleFactorW;

    private int mTotalWidth, mTotalHeight;
    private float[] mYPositions;
    private float[] mResetOneYPositions;
    private float[] mResetTwoYPositions;
    private int mXOffsetSpeedOne;
    private int mXOffsetSpeedTwo;
    private int mXOneOffset ;
    private int mXTwoOffset;
    private int currentY = 0;
    private int perY = 0;
    private Shader mShader;
    private boolean isWaving;
    private Paint mWavePaint;
    private DrawFilter mDrawFilter;

    public DynamicWave(Context context, AttributeSet attrs) {
        super(context, attrs);
     // 将dp转化为px，用于控制不同分辨率上移动速度基本一致
        mXOffsetSpeedOne = dipToPx(context, TRANSLATE_X_SPEED_ONE);
        mXOffsetSpeedTwo = dipToPx(context, TRANSLATE_X_SPEED_TWO);

        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStyle(Style.FILL);
      //  mWavePaint.setColor(Color.parseColor("#ff6e00"));
       /* Shader mShader = new LinearGradient(0,0,getWidth(),getHeight(),new int[] 
        		{Color.parseColor("#ffcc00"),Color.parseColor("#ff6f00"),Color.parseColor("#ff3300")},null,Shader.TileMode.MIRROR); */
       
       // mWavePaint.setAlpha(50);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }
    
    
    Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		if(msg.what == 0){
    			postInvalidate();
    			if(currentY <= mTotalHeight){
    				currentY += perY;
    			}else {
    				currentY = 0;
    			}
    			sendEmptyMessageDelayed(0, 200);
    		}
    	};
    	
    };    
    public void setCurrentY(int y){
    	perY = y;
    }
    public void startAnimation(){
    	if(!isWaving){
    		isWaving = true;
    		handler.sendEmptyMessageDelayed(0, 10);
    	}
    }
    public void stopAnimation(){
    	isWaving = false;
    	handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 从canvas层面去除绘制时锯齿
        canvas.setDrawFilter(mDrawFilter);
        resetPositonY();
        for (int i = 0; i < mTotalWidth; i++) {
        	
             mShader = new LinearGradient(i, mTotalHeight - mResetOneYPositions[i] - currentY, i,
                     mTotalHeight,new int[] 
        		{Color.parseColor("#ffcc00"),Color.parseColor("#ff6f00"),Color.parseColor("#ff3300")},null,Shader.TileMode.MIRROR);
             mWavePaint.setShader(mShader);
             // 减currentY只是为了控制波纹绘制的y的在屏幕的位置，然后动态改变这个变量，从而形成波纹上升下降效果
             // 绘制第一条水波纹
            canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - currentY, i,
                    mTotalHeight,
                    mWavePaint);

            // 绘制第二条水波纹
            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - currentY, i,
                    mTotalHeight,
                    mWavePaint);
        }

        // 改变两条波纹的移动点
        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;

     // 如果已经移动到结尾处，则重头记录
        if (mXOneOffset >= mTotalWidth) {
            mXOneOffset = 0;
        }
        if (mXTwoOffset > mTotalWidth) {
            mXTwoOffset = 0;
        }
    }

    private void resetPositonY() {
    	 // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0,
                yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        // 用于保存原始波纹的y值ֵ
        mYPositions = new float[mTotalWidth];
     // 用于保存波纹一的y值ֵ
        mResetOneYPositions = new float[mTotalWidth];
     // 用于保存波纹二的y值ֵ
        mResetTwoYPositions = new float[mTotalWidth];

     // 将周期定为view总宽度
       // mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);
        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

        // 根据view总宽度得出所有对应的y值ֵ
        for (int i = 0; i < mTotalWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }
    
    
    
     public int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

     public float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }


}
