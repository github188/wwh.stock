package com.bm.wanma.view;

import com.bm.wanma.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.EditText;

/**
 * @author cm
 * 自定义输入框
 */
public class ScanInputEditText extends EditText {
		
	    private static final int defaultContMargin = 5;
	    private static final int defaultSplitLineWidth = 3;

	    private int borderColor = 0xFFCCCCCC;
	    private float borderWidth = 5;
	    private float borderRadius = 3;

	    private int passwordLength = 6;
	    private int passwordColor = 0xFFCCCCCC;
	    private float passwordWidth = 8;
	    private float passwordRadius = 3;

	    private Paint passwordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    private int textLength;
	    private String zNum;
	    private char[] values;
	    
	    private OnKeybordInputFinish callBack;
	    RectF rect = null;
	public ScanInputEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics dm = getResources().getDisplayMetrics();
        borderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, borderWidth, dm);
        borderRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, borderRadius, dm);
        passwordLength = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordLength, dm);
        passwordWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordWidth, dm);
        passwordRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordRadius, dm);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ScanInputEditText, 0, 0);
        borderColor = a.getColor(R.styleable.ScanInputEditText_pivBorderColor, borderColor);
        borderWidth = a.getDimension(R.styleable.ScanInputEditText_pivBorderWidth, borderWidth);
        borderRadius = a.getDimension(R.styleable.ScanInputEditText_pivBorderRadius, borderRadius);
        passwordLength = a.getInt(R.styleable.ScanInputEditText_pivPasswordLength, passwordLength);
        passwordColor = a.getColor(R.styleable.ScanInputEditText_pivPasswordColor, passwordColor);
        passwordWidth = a.getDimension(R.styleable.ScanInputEditText_pivPasswordWidth, passwordWidth);
        passwordRadius = a.getDimension(R.styleable.ScanInputEditText_pivPasswordRadius, passwordRadius);
        a.recycle();

        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        passwordPaint.setStrokeWidth(passwordWidth);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColor);
	}


	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		float[] radii = { 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f };
		float[] radiileft = { 12f, 12f, 0f, 0f, 0f, 0f, 12f, 12f };
		float[] radiiright = { 0f, 0f, 12f, 12f, 12f, 12f, 0f, 0f };
		Path path = new Path();

		Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		rectPaint.setColor(Color.parseColor("#A0A0A0"));
		rectPaint.setStrokeWidth(2);
		rectPaint.setStyle(Paint.Style.STROKE);

		int myWidth = width * 23 / 24;
		int padding = width * 1 / 48;
		int passwordframelength = myWidth / 6;

		for (int i = 0; i < passwordLength; i++) {
			rect = new RectF(padding + passwordframelength * i, 4, padding
					+ passwordframelength * (i + 1), passwordframelength);
			if (i != 0 && i != passwordLength - 1) {
				path.addRoundRect(rect, radii, Direction.CCW);
			} else if (i == 0) { 
				path.addRoundRect(rect, radiileft, Direction.CCW);
			} else if (i == passwordLength - 1) {
				path.addRoundRect(rect, radiiright, Direction.CCW);
			}
			canvas.drawPath(path, rectPaint);
		}
        
        // 数字
        float half = myWidth / passwordLength /2;
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG); 
        textPaint.setStrokeWidth(2);  
        textPaint.setTextSize(half);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        FontMetricsInt fontMetrics = textPaint.getFontMetricsInt(); 
        
        for(int i = 0; i < textLength; i++) {
        	rect = new RectF(padding+passwordframelength*i, 0,padding+passwordframelength*(i+1), passwordframelength);
            int baseline = (int) ((rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2);
            canvas.drawText(String.valueOf(values[i]), rect.centerX(), baseline, textPaint);  
           
        }
        
        
    }
	
	 @Override
	    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
	        super.onTextChanged(text, start, lengthBefore, lengthAfter);
	        zNum = text.toString();
	        this.textLength = zNum.length();
	        values= text.toString().toCharArray();
	        invalidate();
	        if(textLength == 6){
	        	callBack.onInputFinish(zNum);
	        }
	    }

		public int getBorderColor() {
	        return borderColor;
	    }

	    public void setBorderColor(int borderColor) {
	        this.borderColor = borderColor;
	        borderPaint.setColor(borderColor);
	        invalidate();
	    }

	    public float getBorderWidth() {
	        return borderWidth;
	    }

	    public void setBorderWidth(float borderWidth) {
	        this.borderWidth = borderWidth;
	        borderPaint.setStrokeWidth(borderWidth);
	        invalidate();
	    }

	    public float getBorderRadius() {
	        return borderRadius;
	    }

	    public void setBorderRadius(float borderRadius) {
	        this.borderRadius = borderRadius;
	        invalidate();
	    }

	    public int getPasswordLength() {
	        return passwordLength;
	    }

	    public void setPasswordLength(int passwordLength) {
	        this.passwordLength = passwordLength;
	        invalidate();
	    }

	    public int getPasswordColor() {
	        return passwordColor;
	    }

	    public void setPasswordColor(int passwordColor) {
	        this.passwordColor = passwordColor;
	        passwordPaint.setColor(passwordColor);
	        invalidate();
	    }

	    public float getPasswordWidth() {
	        return passwordWidth;
	    }

	    public void setPasswordWidth(float passwordWidth) {
	        this.passwordWidth = passwordWidth;
	        passwordPaint.setStrokeWidth(passwordWidth);
	        invalidate();
	    }

	    public float getPasswordRadius() {
	        return passwordRadius;
	    }

	    public void setPasswordRadius(float passwordRadius) {
	        this.passwordRadius = passwordRadius;
	        invalidate();
	    }
	
	    public void setOnkeybordListener(OnKeybordInputFinish l){
	    	callBack = l;
	    }

	   public interface OnKeybordInputFinish{
		   void onInputFinish(String str);
	   }
}
