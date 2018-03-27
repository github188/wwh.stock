package com.bm.wanma.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.umeng.analytics.MobclickAgent;

public class CustomPullLinearLayout extends LinearLayout implements OnClickListener{
	
	private LayoutInflater inflater;
	private View content;
	private TextView tv_dc,tv_ac,tv_park,tv_idle;
	private boolean isDc,isAc,isPark,isIdle;
	private boolean isDcChange,isAcChange,isParkChange,isIdleChange;
	//private int headerContentInitialHeight;
	private int headerContentHeight;
	private int startY,currentY,space;
	private static final int NONE = 0;
	private static final int PULLING = 1;
	private static final int RELEASE = 2;
	private int state;
	private CustomPullHiddenListener listener;
	private Context mContext;

	public CustomPullLinearLayout(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}
	
	
	public CustomPullLinearLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView(context);
	}


	public CustomPullLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView(context);
	}


	private void initView(Context context) {
		inflater = LayoutInflater.from(context);
		content = inflater.inflate(R.layout.custom_pull_linearlayou_content, null);
		tv_ac = (TextView) content.findViewById(R.id.pull_ac);
		tv_ac.setOnClickListener(this);
		tv_dc = (TextView) content.findViewById(R.id.pull_dc);
		tv_dc.setOnClickListener(this);
		tv_park = (TextView) content.findViewById(R.id.pull_free_park);
		tv_park.setOnClickListener(this);
		tv_idle = (TextView) content.findViewById(R.id.pull_idle);
		tv_idle.setOnClickListener(this);
		isAc = isAcChange = PreferencesUtil.getBooleanPreferences(mContext, "isAc", false);
		if(isAc){
			Drawable drawable = getResources().getDrawable(R.drawable.img_selected_ac);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
			tv_ac.setCompoundDrawables(null, drawable,null, null);
		}else {
			Drawable drawable = getResources().getDrawable(R.drawable.img_selecte_ac);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
			tv_ac.setCompoundDrawables(null, drawable,null, null);
		}
		isDc = isDcChange = PreferencesUtil.getBooleanPreferences(mContext, "isDc", false);
		if(isDc){
			Drawable drawable = getResources().getDrawable(R.drawable.img_selected_dc);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
			tv_dc.setCompoundDrawables(null, drawable,null, null);
		}else {
			Drawable drawable = getResources().getDrawable(R.drawable.img_selecte_dc);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
			tv_dc.setCompoundDrawables(null, drawable,null, null);
		}
		isPark = isParkChange = PreferencesUtil.getBooleanPreferences(mContext, "isPark", false);
		if(isPark){
			Drawable drawable = getResources().getDrawable(R.drawable.img_selected_park);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
			tv_park.setCompoundDrawables(null, drawable,null, null);
		}else {
			Drawable drawable = getResources().getDrawable(R.drawable.img_selecte_park);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
			tv_park.setCompoundDrawables(null, drawable,null, null);
		}
		isIdle = isIdleChange = PreferencesUtil.getBooleanPreferences(mContext, "isIdle", false);
		if(isIdle){
			Drawable drawable = getResources().getDrawable(R.drawable.img_selected_idle);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
			tv_idle.setCompoundDrawables(null, drawable,null, null);
		}else {
			Drawable drawable = getResources().getDrawable(R.drawable.img_selecte_idle);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
			tv_idle.setCompoundDrawables(null, drawable,null, null);
		}
		
		//headerContentInitialHeight = content.getPaddingTop();
		measureView(content);
		headerContentHeight = content.getMeasuredHeight();
		topPadding(-headerContentHeight);
		this.addView(content);
		state = PULLING;
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			MobclickAgent.onEvent(mContext, "chongdian_shaixianfa");
			startY = (int) event.getY();
			break;
			
		case MotionEvent.ACTION_MOVE:
			if(state == NONE){
				return false;
			}
			whenMove(event);
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			whenUp();
			break;
		default:
			break;
		}
		return true;
	}
	
	// 调整header的大小。其实调整的只是距离顶部的高度。
		private void topPadding(int topPadding) {
			content.setPadding(content.getPaddingLeft(), topPadding,
					content.getPaddingRight(), content.getPaddingBottom());
			content.invalidate();
		}
		// 用来计算header大小的
		private void measureView(View child) {
			ViewGroup.LayoutParams p = child.getLayoutParams();
			if (p == null) {
				p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
			int lpHeight = p.height;
			int childHeightSpec;
			if (lpHeight > 0) {
				childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
						MeasureSpec.EXACTLY);
			} else {
				childHeightSpec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
			}
			child.measure(childWidthSpec, childHeightSpec);
		}
		
		private void whenUp(){
			if(Math.abs(space)>20){
				switch (state) {
				case PULLING:
					handler.sendEmptyMessageDelayed(0, 10);
					break;
				case RELEASE:
					if(space<0){
					  handler.sendEmptyMessageDelayed(0, 10);
					}
					
					break;
				}	
			}
			
		}
		
		//根据手势状态，刷新content显示情况
		private void whenMove(MotionEvent event) {
			currentY = (int) event.getY();
			space = currentY - startY;
			LogUtil.i("cm_socket", "space"+space+"state"+state);
			//int space = Math.abs(currentY - startY);
			if(Math.abs(space)>20){
				switch (state) {
				case PULLING:
					if(space <= headerContentHeight){
						int topPadding = space - headerContentHeight;
						topPadding(topPadding);
					}else {
						topPadding(0);
					}
					break;
				case RELEASE:
					if(space<0){
						int topPadding = -space - headerContentHeight;
						if(topPadding<=0){
							topPadding(space);
						}else {
							topPadding(0);
						}
					}
					
					break;
				}	
			}
		}
		
		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 0) {
					// 如果pull,space>0
					if(space >0){
						if(space <= headerContentHeight){
							int topPadding = space - headerContentHeight;
							space +=20;
							topPadding(topPadding);
							state = NONE;
							handler.sendEmptyMessageDelayed(0, 10);
						}else {
							handler.removeCallbacksAndMessages(null);
							state = RELEASE;
							topPadding(0);
						}
					}else {
						if(-space <= headerContentHeight){
							//int topPadding = -space - headerContentHeight;
							space -=20;
							topPadding(space);
							state = NONE;
							handler.sendEmptyMessageDelayed(0, 10);
						}else {
							handler.removeCallbacksAndMessages(null);
							state = PULLING;
							topPadding(-headerContentHeight);
							if(listener != null ){
								if(isAc != isAcChange || isDc !=isDcChange || isPark != isParkChange 
										|| isIdle != isIdleChange){
									isAcChange = isAc;
									isDcChange = isDc;
									isParkChange = isPark;
									isIdleChange = isIdle;
									PreferencesUtil.setPreferences(mContext,"isDc", isDc);
									PreferencesUtil.setPreferences(mContext,"isAc", isAc);
									PreferencesUtil.setPreferences(mContext,"isIdle", isIdle);
									PreferencesUtil.setPreferences(mContext,"isPark", isPark);
									listener.onHidden();
								}
							}
						}
					}
				}
			};
		};
	
	public void setCustomPullHiddenListener(CustomPullHiddenListener l){
		this.listener = l;
	}
		
	public interface CustomPullHiddenListener{
		void onHidden();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pull_ac:
			MobclickAgent.onEvent(mContext, "chongdian_jiaoliu");
			if(isAc){
				Drawable drawable = getResources().getDrawable(R.drawable.img_selecte_ac);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
				tv_ac.setCompoundDrawables(null, drawable,null, null);
				isAc = false;
			}else {
				Drawable drawable = getResources().getDrawable(R.drawable.img_selected_ac);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
				tv_ac.setCompoundDrawables(null, drawable,null, null);
				isAc = true;
			}
			
			break;
		case R.id.pull_dc:
			MobclickAgent.onEvent(mContext, "chongdian_zhiliu");
			if(isDc){
				Drawable drawable = getResources().getDrawable(R.drawable.img_selecte_dc);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
				tv_dc.setCompoundDrawables(null, drawable,null, null);
				isDc = false;
			}else {
				Drawable drawable = getResources().getDrawable(R.drawable.img_selected_dc);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
				tv_dc.setCompoundDrawables(null, drawable,null, null);
				isDc = true;
			}
			
			break;
		case R.id.pull_free_park:
			MobclickAgent.onEvent(mContext, "chongdian_mianfeitingche");
			if(isPark){
				Drawable drawable = getResources().getDrawable(R.drawable.img_selecte_park);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
				tv_park.setCompoundDrawables(null, drawable,null, null);
				isPark = false;
			}else {
				Drawable drawable = getResources().getDrawable(R.drawable.img_selected_park);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
				tv_park.setCompoundDrawables(null, drawable,null, null);
				isPark = true;
			}
			
			break;
		case R.id.pull_idle:
			MobclickAgent.onEvent(mContext, "chongdian_kongxian");
			if(isIdle){
				Drawable drawable = getResources().getDrawable(R.drawable.img_selecte_idle);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
				tv_idle.setCompoundDrawables(null, drawable,null, null);
				isIdle = false;
			}else {
				Drawable drawable = getResources().getDrawable(R.drawable.img_selected_idle);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); 
				tv_idle.setCompoundDrawables(null, drawable,null, null);
				isIdle = true;
			}
			
			break;

		default:
			break;
		}
		
	}	
}
