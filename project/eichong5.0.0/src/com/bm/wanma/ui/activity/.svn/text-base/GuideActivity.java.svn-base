package com.bm.wanma.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bm.wanma.R;
import com.bm.wanma.adapter.CommonViewPagerAdapter;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.GetResourceUtil;
import com.bm.wanma.utils.IntentUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ProjectApplication;
import com.umeng.analytics.MobclickAgent;
import com.viewpagerindicator.CirclePageIndicator;


import java.util.ArrayList;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DbUpdateListener;
public class GuideActivity extends BaseActivity implements DbUpdateListener {
   private  CommonViewPagerAdapter mAdapter;
   private  ViewPager mPager;
   private  CirclePageIndicator mIndicator;
   private FinalDb finalDb;
   private boolean isUpgrate = false;
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//定义全屏参数
        int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
		setContentView(R.layout.activity_guide);
		mPager = (ViewPager)findViewById(R.id.activity_guide_vp);
		mIndicator = (CirclePageIndicator)findViewById(R.id.activity_guide_indicator);
		finalDb = FinalDb.create(this,Protocol.DATABASE_NAME,true,Protocol.dbNumer,this);
		initView();
		PreferencesUtil.setPreferences(getActivity(), "provincecode", "330000");
		PreferencesUtil.setPreferences(getActivity(), "citycode", "330100");
		if(!isUpgrate){
			new Thread(new Runnable() {
				@Override
				public void run() {
					finalDb.creatTable(GuideActivity.this,"t_m_area");
				}
			}).start();
		}else {
			new Thread(new Runnable() {
				@Override
				public void run() {
					/*finalDb.dropTable(CityUpdateTimeBean.class);
					finalDb.dropTable(MapModePileBean.class);
					finalDb.dropTable(MapModeStationBean.class);*/
					finalDb.dropDb();
					finalDb.creatTable(GuideActivity.this,"t_m_area");
				}
			}).start();
			
		}
    }

    @Override
    protected void getData() {

    }
@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(mContext);
	}
@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
	}
    @SuppressWarnings("deprecation")
	protected void initView() {
        ArrayList<View> list = new ArrayList<View>();
        for(int i=0 ; i<4 ; i++){
            View view = getLayoutInflater().inflate(R.layout.activity_guide_item, null);
            ImageView tempIv = (ImageView) view.findViewById(R.id.guide_item_iv);
            switch (i){
                case 0:
                    tempIv.setBackgroundDrawable(GetResourceUtil.getDrawable(R.drawable.guide_one));
                    break;
                case 1:
                    tempIv.setBackgroundDrawable(GetResourceUtil.getDrawable(R.drawable.guide_two));
                    break;
                case 2:
                    tempIv.setBackgroundDrawable(GetResourceUtil.getDrawable(R.drawable.guide_three));
                    break;
                case 3:
                    tempIv.setBackgroundDrawable(GetResourceUtil.getDrawable(R.drawable.guide_four));
                    break;
            }
            if(i == 3){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	isConceal = true;
                    	//ProjectApplication.setGuideType(true);// 让其以后不进入登录页
                    	
                        IntentUtil.startIntent(GuideActivity.this, HomeActivity.class);//方便调试，先放开点击
                        finish();
                    }
                });
            }
       /*     tempTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtil.startIntent(GuideActivity.this, HomeActivity.class);
                    finish();
                }
            });*/ 
            
            list.add(view);
        }
        mAdapter = new CommonViewPagerAdapter(list);
        mPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mPager);
    }

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		isUpgrate = true;
		
	}
}
