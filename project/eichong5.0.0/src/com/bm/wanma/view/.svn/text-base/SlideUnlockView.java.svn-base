package com.bm.wanma.view;
import com.bm.wanma.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Path.Direction;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SlideUnlockView extends View {
	public static boolean visity = false;
	/**
	 * 滑块当前的状态
	 */
	public int currentState;
	/**
	 * 未解锁
	 */
	public static final int STATE_LOCK = 1;
	/**
	 * 解锁
	 */
	public static final int STATE_UNLOCK = 2;
	/**
	 * 正在拖拽
	 */
	public static final int STATE_MOVING = 3;

	/**
	 * 上一次x的值
	 */
	public float location = 0;
	private static final String TAG = "SlideUnlockView";
	/**
	 * 滑动解锁的背景图片
	 */
	private Bitmap slideUnlockBackground;
	/**
	 * 滑块的图片
	 */
	private Bitmap slideUnlockBlock;
	/**
	 * 滑动解锁背景的宽度
	 */
	private int  BackgoundWidth ;
	private int  Backgoundheight = 160;
	/**
	 * 滑块宽高
	 */
	private int blockWidth;
	private int blockHeight;
	/**
	 * 手指在滑块的x，y值
	 */
	private float x;
	private float y;
	/**
	 * 手指在按下时，是否按到了滑块上
	 */
	private boolean downOnBlock;
	/**
	 * 通过handler来控制滑块在未解锁的时候，平缓的滑动到左端
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				// 如果x还大于0，就人为的设置缓慢移动到最左端，每次移动距离设置为背景宽的/100
				if (x > 0) {
					x = x - BackgoundWidth * 1.0f / 20;
					// 刷新界面
					postInvalidate();
					// 设置继续移动
					handler.sendEmptyMessageDelayed(0, 10);
				} else {
					handler.removeCallbacksAndMessages(null);
					currentState = STATE_LOCK;
					Log.i(TAG, "state---lock.....");
				}
			}
		};
	};
	/**
	 * 解锁的监听
	 */
	private OnUnLockListener onUnLockListener;

	/**
	 * 自定义View的构造方法
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SlideUnlockView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 默认滑动解锁为未解锁状态
		currentState = STATE_LOCK;
		// 命名空间
		String namespace = "http://schemas.android.com/apk/res/com.bm.wanma";

		// 取出自定义属性中背景图片
		int slideUnlockBackgroundResource = attrs.getAttributeResourceValue(
				namespace, "slideUnlockBackgroundResource", -1);
		// 取出自定义属性中滑块图片
		int slideUnlockBlockResource = attrs.getAttributeResourceValue(
				namespace, "slideUnlockBlockResource", -1);

		// 取出自定义属性中当前状态
		// 如果解锁状态是true，说明已经解锁
		/**
		 * 当取出自定义属性的背景时，设置背景
		 */
		setSlideUnlockBackground(slideUnlockBackgroundResource);
		/**
		 * 当取出自定义属性的滑块时，设置滑块的图片
		 */
		setSlideUnlockBlock(slideUnlockBlockResource);
		/**
		 * 执行onDraw方法，进行界面绘制
		 */
		postInvalidate();
	}

	public SlideUnlockView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideUnlockView(Context context) {
		this(context, null);
	}
	int i = 0;
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
    	//去锯齿
    	paint.setAntiAlias(true);
    	paint.setStrokeWidth(4);
    	
    	//-----------------设置填充风格后绘制------------------
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setColor(getResources().getColor(R.color.common_orange));
    	RectF re2 = new RectF(12, 12, getWidth()-32, getHeight()-12+4);
    	//绘制圆角矩形
    	canvas.drawRoundRect(re2, blockWidth, blockWidth, paint);
    	BackgoundWidth = getWidth()-44;
		/**
		 * 判断当前状态
		 */
		switch (currentState) {
		// 如果是未解锁，就将滑块绘制到最左端
		case STATE_LOCK:
			canvas.drawBitmap(slideUnlockBlock, 12+2, 12+2, null);
			break;
		// 已解锁，计算出
		case STATE_UNLOCK:
//			int unlockX = BackgoundWidth - blockWidth;
//			//绘制悲剧色
//			paint.setColor(Color.parseColor("#ff7d00"));
//			paint.setStyle(Paint.Style.FILL);
//			RectF re6 = new RectF(6,6, getWidth(), getHeight()-6);
//	    	canvas.drawRoundRect(re6, blockWidth, blockWidth, paint);
//	    	//
//			canvas.drawBitmap(slideUnlockBlock, unlockX, 0, null);
			
//			break;
		case STATE_MOVING:
			
			if(x < blockWidth/2){
				onUnLockListener.onHidden(false);
			}else {
				onUnLockListener.onHidden(true);
			}
			if (x < 0) {
				x = 0;
			} else if (x > BackgoundWidth - blockWidth) {
				x = BackgoundWidth - blockWidth-6;
			}
			paint.setColor(Color.parseColor("#ff7d00"));
			paint.setStyle(Paint.Style.FILL);
			RectF re3 = new RectF(6+12,6+12, x+blockWidth-6-20, getHeight()-12-2);
	    	//绘制圆角矩形
	    	canvas.drawRoundRect(re3, blockWidth, blockWidth, paint);
			if(!visity){
				canvas.drawBitmap(slideUnlockBackground, 12+2, 12+2, null);
			}
			canvas.drawBitmap(slideUnlockBlock, x+12+2, 12+2, null);
			break;
		default:
			break;
		}
	}

	public void setSlideUnlockBackground(int slideUnlockBackgroundResource) {
		Log.i(TAG, "setSlideUnlockBackground.....");
		slideUnlockBackground = compressBitmapToGivenWidthAndHeight(BitmapFactory.decodeResource(getResources(),
				slideUnlockBackgroundResource), Backgoundheight, Backgoundheight);
		// 获取背景图的宽和高
	}

	public void setSlideUnlockBlock(int slideUnlockBlockResource) {
		Log.i(TAG, "setSlideUnlockBlock.....");

		slideUnlockBlock = compressBitmapToGivenWidthAndHeight(BitmapFactory.decodeResource(getResources(),
				slideUnlockBlockResource), Backgoundheight, Backgoundheight);
		// 获取滑块的宽和高
		blockWidth = slideUnlockBlock.getWidth();
		blockHeight = slideUnlockBlock.getHeight();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		BackgoundWidth = MeasureSpec.getSize(widthMeasureSpec);
		Log.i(TAG, "onMeauser.....");
		setMeasuredDimension(BackgoundWidth+24,
				slideUnlockBlock.getHeight()+24);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		// 当手指按下的时候，判断手指按下的位置是否在滑块上边
		case MotionEvent.ACTION_DOWN:

			if (currentState != STATE_MOVING) {
				// 判断一下，如果当前正在移动，则不执行触摸操作
				// 获取相对于背景的左上角的x，y值
				x = event.getX();
				y = event.getY();
				location = x;
				// 先计算出滑块的中心点的x，y坐标
				float blockCenterX = blockWidth * 1.0f / 2;
				float blockCenterY = blockHeight * 1.0f / 2;
				downOnBlock = isDownOnBlock(blockCenterX, x, blockCenterY, y);
				Log.i(TAG, "down......................");
				// 调用onDraw方法
				postInvalidate();

			}
			break;
		case MotionEvent.ACTION_MOVE:
			// 如果手指确定按在滑块上，就视为开始拖拽滑块
			if (downOnBlock) {
				// 获取相对于背景的左上角的x，y值
				x = event.getX();
				y = event.getY();
				if (x < BackgoundWidth - blockWidth) {
					visity = false;
				} else {
					visity = true;
				}
				currentState = STATE_MOVING;
				// 调用onDraw方法
				postInvalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			if (currentState == STATE_MOVING) {
				// 当手指抬起的时候，应该是让滑块归位的
				// 说明未解锁
				if ((x < BackgoundWidth - blockWidth-10) || !visity) {
					handler.sendEmptyMessageDelayed(0, 10);
					// 通过回调设置已解锁
					//onUnLockListener.setUnLocked(false);
				} else {
					if(visity){
						currentState = STATE_UNLOCK;
						// 通过回调设置未解锁
						onUnLockListener.setUnLocked();
					}
				}
				downOnBlock = false;
				// 调用onDraw方法
				postInvalidate();

			}
			break;

		default:
			break;
		}
		return true;
	}


	/**
	 * 计算手指是否是落在了滑块上(默认是按照滑块在未解锁的初始位置来计算的)
	 */
	public boolean isDownOnBlock(float x1, float x2, float y1, float y2) {
		float sqrt = FloatMath.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2)
				+ Math.abs(y1 - y2) * Math.abs(y1 - y2));
		if (sqrt <= blockWidth / 2) {
			return true;
		}
		return false;
	}

	/**
	 * 设置解锁监听
	 * 
	 * @param onUnLockListener
	 */
	public void setOnUnLockListener(OnUnLockListener onUnLockListener) {
		this.onUnLockListener = onUnLockListener;
	}

	// 定义一个解锁的监听
	public interface OnUnLockListener {
		//public void setUnLocked(boolean lock);
		public void setUnLocked();
		public void onHidden(boolean hidden);
	}

	/**
	 * 重置一下滑动索的状态，保证下次能够正常使用
	 */
	public void reset() {
		currentState = STATE_LOCK;
		onUnLockListener.onHidden(false);
		handler.sendEmptyMessageDelayed(0, 10);
		//postInvalidate();
	}
	
	//判断手指是否在滑块的背景区域移动
		public boolean isOnBackground(int x,int y){
			if(x<=slideUnlockBackground.getWidth()&&y<=slideUnlockBackground.getHeight()){
				return true;
			}
			return false;
		}

	public static Bitmap compressBitmapToGivenWidthAndHeight(Bitmap bitmap,
			double newWidth, double newHeight) {

		// 获取这个图片的宽和高
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 压缩Bitmap
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) width,
				(int) height, matrix, true);

		return newBitmap;
	}
}
