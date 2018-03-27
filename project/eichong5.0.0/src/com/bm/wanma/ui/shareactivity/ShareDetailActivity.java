package com.bm.wanma.ui.shareactivity;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bm.wanma.R;
import com.bm.wanma.dialog.TipDrawGunDialog;
import com.bm.wanma.entity.BanlanceBean;
import com.bm.wanma.entity.ChargeIntegralBean;
import com.bm.wanma.entity.IntegralEventBean;
import com.bm.wanma.entity.ShareIntegralBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.activity.BaseActivity;
import com.bm.wanma.ui.activity.ChargeFinishActivity;
import com.bm.wanma.ui.activity.IntegralDetailActivity;
import com.bm.wanma.ui.activity.LoginActivity;
import com.bm.wanma.ui.activity.MyCouponActivity;
import com.bm.wanma.ummodel.Defaultcontent;
import com.bm.wanma.ummodel.StyleUtil;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;
import com.umeng.analytics.MobclickAgent;
import com.umeng.social.tool.UMImageMark;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;

import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UrlUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by wangfei on 16/11/10.
 */
public class ShareDetailActivity extends BaseActivity implements OnClickListener{
//    private ListView listView;
//    public ArrayList<String> styles = new ArrayList<String>();
    private SHARE_MEDIA share_media;
    private UMImage imageurl,imagelocal;
    private UMVideo video;
    private UMusic music;
    private UMEmoji emoji;
    private UMWeb web;
    private File file;
    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    
    
    private TextView tv_payment,tv_payment_money,tv_server_money,tv_electric_money;
	private TextView tv_coupon_money,tv_duration,tv_power;
	private TextView tv_complete;
	private TextView tv_charge_detail_integral_vv,tv_integral,tv_coupon,tv_lottery,tv_text_introduce;
	private RelativeLayout rl_integral,rl_coupon,rl_lottery;
	private LinearLayout ll_but;
	private Intent getIn;
	private float power,servicemoney,electricMoney,paymentmoney,coupon,reduce;
	private String duration,pkUserId;
	private TipDrawGunDialog mDialog;
	private ImageButton	iv_charge_close,bt_umeng_title_fenxiang;
	private Button bt_charge_finish_share;
	private String  provincecode, citycode;
	private String pkuserId,money,oid;
	private boolean isShare = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.share_detail);
        platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.QQ.toSnsPlatform());
        share_media = platforms.get(0).mPlatform;
        pkuserId = PreferencesUtil.getStringPreferences(this,"pkUserinfo");
        initMedia();

        mPageName = "ChargeFinishActivity";
		getIn = getIntent();
		oid = getIn.getStringExtra("order");
		electricMoney = getIn.getFloatExtra("electricMoney", 0);
		pkUserId = PreferencesUtil.getStringPreferences(this, "pkUserinfo");
		if(isNetConnection()){
			GetDataPost.getInstance(this).getBalance(handler, pkUserId);
		}
        
        
        provincecode = PreferencesUtil.getStringPreferences(this,"provincecode");
		citycode = PreferencesUtil.getStringPreferences(this, "citycode");
//		Toast.makeText(this, "  "+provincecode+"   "+citycode , 0).show();
		if (Tools.isEmptyString(provincecode)||Tools.isEmptyString(citycode)) {
			provincecode = "330000";
			citycode = "330100";
		}
		
		InitView();
        findViewById(R.id.umeng_back).setVisibility(View.VISIBLE);
        findViewById(R.id.umeng_back).setOnClickListener(this);
        findViewById(R.id.rl_umeng_pengyou).setOnClickListener(this);
        findViewById(R.id.rl_umeng_qq).setOnClickListener(this);
        findViewById(R.id.rl_umeng_weixin).setOnClickListener(this);
        bt_charge_finish_share = (Button) findViewById(R.id.charge_finish_share);
        bt_charge_finish_share.setOnClickListener(this);
        if (PreferencesUtil.getBooleanPreferences(this, "fenxiang", false)) {
        	bt_charge_finish_share.setVisibility(View.GONE);
		}
        
    }
    private void InitView() {

    	findViewById(R.id.umeng_back).setVisibility(View.VISIBLE);
        findViewById(R.id.umeng_back).setOnClickListener(this);
        findViewById(R.id.rl_umeng_pengyou).setOnClickListener(this);
        findViewById(R.id.rl_umeng_qq).setOnClickListener(this);
        findViewById(R.id.rl_umeng_weixin).setOnClickListener(this);
        bt_charge_finish_share = (Button) findViewById(R.id.charge_finish_share);
        bt_charge_finish_share.setOnClickListener(this);
        bt_umeng_title_fenxiang = (ImageButton) findViewById(R.id.umeng_title_fenxiang);
        bt_umeng_title_fenxiang.setOnClickListener(this);
        
        tv_payment = (TextView)findViewById(R.id.charge_finish_payment);
		tv_payment_money = (TextView)findViewById(R.id.charge_finish_payment_money);
		tv_electric_money = (TextView)findViewById(R.id.charge_detail_elctric_money);
		tv_server_money = (TextView) findViewById(R.id.charge_detail_service_money);
		tv_coupon_money = (TextView) findViewById(R.id.charge_detail_coupon_money);
		tv_duration = (TextView) findViewById(R.id.charge_detail_duration);
		tv_power = (TextView) findViewById(R.id.charge_detail_power);
//		tv_charge_detail_state = (TextView) findViewById(R.id.charge_detail_integral);


		tv_complete = (TextView) findViewById(R.id.charge_finish_complete);
		tv_complete.setOnClickListener(this);
		initValue();
		mDialog = new TipDrawGunDialog(this);
		mDialog.setCancelable(false);
		mDialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
		mDialog.show();
		
		
		tv_charge_detail_integral_vv = (TextView) findViewById(R.id.charge_detail_integral_vv);//上
		tv_integral = (TextView) findViewById(R.id.tv_integral);//积分
		tv_coupon = (TextView) findViewById(R.id.tv_coupon);//优惠劵
		tv_lottery = (TextView) findViewById(R.id.tv_lottery);//抽奖机会
		tv_text_introduce = (TextView) findViewById(R.id.text_introduce);//下
		
		
		rl_integral = (RelativeLayout) findViewById(R.id.rl_integral);//积分按钮
		rl_integral.setOnClickListener(this);
		rl_coupon = (RelativeLayout) findViewById(R.id.rl_coupon);//优惠劵按钮
		rl_coupon.setOnClickListener(this);
		rl_lottery = (RelativeLayout) findViewById(R.id.rl_lottery);//抽奖按钮
		rl_lottery.setOnClickListener(this);
		
		ll_but = (LinearLayout) findViewById(R.id.ll_butt);//三个按钮
		GetDataPost.getInstance(getActivity()).getIntegralEvent(handler, pkUserId, provincecode, citycode, "5");
	}
    
	private void initValue(){
		DecimalFormat dotformat = new DecimalFormat(",##0.00");
		DecimalFormat dotformat1 = new DecimalFormat(",###.##");
		LogUtil.i("cm_socket", "duration_finish"+getIn.getLongExtra("duration", 0));
		duration = TimeUtil.getCutDown5(getIn.getLongExtra("duration", 0));
		power = getIn.getFloatExtra("power", 0);
		electricMoney = getIn.getFloatExtra("electricMoney", 0);
		servicemoney = getIn.getFloatExtra("serviceMoney", 0);
		coupon = getIn.getFloatExtra("coupon", 0);
		//first = getIn.getIntExtra("isFirst", 0);
		reduce = getIn.getFloatExtra("reduce", 0);
		paymentmoney = electricMoney+servicemoney-reduce;
		if(paymentmoney <=0){
			tv_payment.setText("充电完成，已免单");
		}
		tv_payment_money.setText(dotformat.format(paymentmoney) + " 元");
		tv_electric_money.setText(dotformat.format(electricMoney) + " 元");//电费
		
		tv_server_money.setText(dotformat.format(servicemoney)+ " 元");
		tv_coupon_money.setText(dotformat1.format(reduce)+ " 元");
		tv_duration.setText(duration);
		tv_power.setText(power + " kWh");	
		GetDataPost.getInstance(this).getChargeIntegral(handler, pkuserId,provincecode, citycode,String.valueOf(paymentmoney),oid);
//		tv_charge_detail_state.setText("已完成");
	}
	private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
        	isShare = true;
        	findViewById(R.id.ll_fenxiang).setVisibility(View.GONE);
        	GetDataPost.getInstance(ShareDetailActivity.this).getShareIntegral(handler, pkuserId,provincecode, citycode,oid);
//            Toast.makeText(ShareDetailActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareDetailActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareDetailActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };

    private void initMedia(){
        UMImageMark umImageMark = new UMImageMark();
        umImageMark.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        umImageMark.setMarkBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_map_head2));
        imageurl = new UMImage(this,Defaultcontent.imageurl);
        imageurl.setThumb(new UMImage(this, R.drawable.bg_map_head2));
       // imagelocal = new UMImage(this,R.drawable.logo,umImageMark);
        imagelocal = new UMImage(this,R.drawable.bg_map_head2);
        imagelocal.setThumb(new UMImage(this, R.drawable.bg_map_head2));
        music = new UMusic(Defaultcontent.musicurl);
        video = new UMVideo(Defaultcontent.videourl);
        web = new UMWeb(Defaultcontent.url);
        web.setTitle("给你推荐充电app");
        web.setThumb(new UMImage(this, R.drawable.bg_map_head2));
        web.setDescription("站点多，充电快，还有优惠劵，听说还经常送停车劵");
        music.setTitle("给你推荐充电app");
        music.setThumb(new UMImage(this, R.drawable.bg_map_head2));
        music.setDescription("站点多，充电快，还有优惠劵，听说还经常送停车劵");
        music.setmTargetUrl(Defaultcontent.url);
        video.setThumb(new UMImage(this,R.drawable.bg_map_head2));
        video.setTitle("给你推荐充电app");
        video.setDescription("站点多，充电快，还有优惠劵，听说还经常送停车劵");
        emoji = new UMEmoji(this,"http://img5.imgtn.bdimg.com/it/u=2749190246,3857616763&fm=21&gp=0.jpg");
        emoji.setThumb(new UMImage(this, R.drawable.bg_map_head2));
        file = new File(this.getFilesDir()+"test.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (SocializeUtils.File2byte(file).length<=0){
            String content = "U-share分享";
            byte[] contentInBytes = content.getBytes();
            try {
                FileOutputStream fop = new FileOutputStream(file);
                fop.write(contentInBytes);
                fop.flush();
                fop.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
    
//    rl_integral = (RelativeLayout) findViewById(R.id.rl_integral);//积分按钮
//	rl_integral.setOnClickListener(this);
//	rl_coupon = (RelativeLayout) findViewById(R.id.rl_coupon);//优惠劵按钮
//	rl_coupon.setOnClickListener(this);
//	rl_lottery = (RelativeLayout) findViewById(R.id.rl_lottery);//抽奖按钮
//	rl_lottery.setOnClickListener(this);
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.umeng_back:
			finish();
			break;
		case R.id.rl_integral:
			//积分
			Intent IntegralIn = new Intent();
			IntegralIn.setClass(getActivity(), IntegralDetailActivity.class);
			startActivity(IntegralIn);
			finish();
			break;
		case R.id.rl_coupon:
			//优惠券
			Intent couponIn = new Intent();
			couponIn.setClass(getActivity(), MyCouponActivity.class);
			couponIn.putExtra("source_inteface", "myperson_coupon");
			startActivity(couponIn);
			PreferencesUtil.setPreferences(getActivity(), "isCoupon", false);
			finish();
			break;
		case R.id.rl_lottery:
			//抽奖
			
//			finish();
			break;
		case R.id.charge_finish_share:
		case R.id.umeng_title_fenxiang:
			if (isShare) {
				Toast.makeText(ShareDetailActivity.this,"已经分享过了，不能再分享了",Toast.LENGTH_LONG).show();
				break;
			}
			//分享按钮
			if (findViewById(R.id.ll_fenxiang).getVisibility()==View.VISIBLE) {
				findViewById(R.id.ll_fenxiang).setVisibility(View.GONE);
			}else {				
				findViewById(R.id.ll_fenxiang).setVisibility(View.VISIBLE);
			}
			break;
		case R.id.charge_finish_complete:
			//完成
			finish();
			break;
		case R.id.rl_umeng_qq:
			//完成
			share_media = platforms.get(2).mPlatform;
			new ShareAction(ShareDetailActivity.this)
            .withText(Defaultcontent.text)
             .withMedia(web)
             .setPlatform(share_media)
             .setCallback(shareListener).share();
//			finish();
			break;
		case R.id.rl_umeng_pengyou:
			//完成
			share_media = platforms.get(1).mPlatform;
			new ShareAction(ShareDetailActivity.this)
            .withText(Defaultcontent.text)
             .withMedia(web)
             .setPlatform(share_media)
             .setCallback(shareListener).share();
//			finish();
			break;
		case R.id.rl_umeng_weixin:
			//完成
			share_media = platforms.get(0).mPlatform;
			new ShareAction(ShareDetailActivity.this)
            .withText(Defaultcontent.text)
             .withMedia(web)
             .setPlatform(share_media)
             .setCallback(shareListener).share();
//			finish();
			break;
		default:
			break;
		}
	}
	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSuccess(String sign, Bundle bundle) {

		if (bundle!=null) {			
			if (sign.equals(Protocol.INTEGRAL_CHARGE)) {
				ChargeIntegralBean chargeIntegralBean = (ChargeIntegralBean) bundle.getSerializable(Protocol.DATA);
				if (chargeIntegralBean!=null) {

					if (Tools.isEmptyString(chargeIntegralBean.getIntegralValue())||chargeIntegralBean.getIntegralValue().equals("0")) {
						rl_integral.setVisibility(View.GONE);
						chargeIntegralBean.setIntegralValue("0");
					}else {
						ll_but.setVisibility(View.VISIBLE);
						tv_text_introduce.setVisibility(View.VISIBLE);
						tv_charge_detail_integral_vv.setVisibility(View.VISIBLE);
						rl_integral.setVisibility(View.VISIBLE);
						tv_integral.setText(""+chargeIntegralBean.getIntegralValue());
					}
					if (Tools.isEmptyString(chargeIntegralBean.getCouponCount())||chargeIntegralBean.getCouponCount().equals("0")) {
						rl_coupon.setVisibility(View.GONE);
						chargeIntegralBean.setCouponCount("0");
					}else {
						ll_but.setVisibility(View.VISIBLE);
						tv_text_introduce.setVisibility(View.VISIBLE);
						tv_charge_detail_integral_vv.setVisibility(View.VISIBLE);
						rl_coupon.setVisibility(View.VISIBLE);
						tv_coupon.setText(""+chargeIntegralBean.getCouponCount());
					}
					if (Tools.isEmptyString(chargeIntegralBean.getChoiceCount())||chargeIntegralBean.getChoiceCount().equals("0")) {
						rl_lottery.setVisibility(View.GONE);
						chargeIntegralBean.setChoiceCount("0");
					}else {
						ll_but.setVisibility(View.VISIBLE);
						tv_text_introduce.setVisibility(View.VISIBLE);
						tv_charge_detail_integral_vv.setVisibility(View.VISIBLE);
						rl_lottery.setVisibility(View.VISIBLE);
						tv_lottery.setText(""+chargeIntegralBean.getChoiceCount());
					}
					if (chargeIntegralBean.getChoiceCount().equals("0")
							&&chargeIntegralBean.getCouponCount().equals("0")
							&&chargeIntegralBean.getIntegralValue().equals("0")
							) {
						ll_but.setVisibility(View.GONE);
						tv_text_introduce.setVisibility(View.GONE);
						tv_charge_detail_integral_vv.setVisibility(View.GONE);
					}
				}else {
					ll_but.setVisibility(View.GONE);
					tv_text_introduce.setVisibility(View.GONE);
					tv_charge_detail_integral_vv.setVisibility(View.GONE);
				}

			}else if (sign.equals(Protocol.BANLANCE)) {
				BanlanceBean mBanlanceBean = (BanlanceBean) bundle.getSerializable(Protocol.DATA);
				if(mBanlanceBean != null){
					PreferencesUtil.setPreferences(this, "usinAccountbalance", mBanlanceBean.getUserAB());
				}
			}else if (sign.equals(Protocol.SHARE_INTEGRAL)) {
				ShareIntegralBean shareIntegralBean = (ShareIntegralBean) bundle.getSerializable(Protocol.DATA);
				if(shareIntegralBean != null){
					finish();
				}
			}else if (sign.equals(Protocol.INTEGRAL_EVENT)) {
				if (bundle != null) {
					ArrayList<IntegralEventBean> integralEventBeans = (ArrayList<IntegralEventBean>) bundle.getSerializable(Protocol.DATA);
				if (integralEventBeans.size()>0) {
					IntegralEventBean integralEventBean = integralEventBeans.get(0);
					if(integralEventBean!= null){
						if (!Tools.isEmptyString(integralEventBean.getHasshared())&&integralEventBean.getHasshared().equals("0")) {
							bt_charge_finish_share.setVisibility(View.VISIBLE);
							bt_umeng_title_fenxiang.setVisibility(View.VISIBLE);							
						}else {
							bt_umeng_title_fenxiang.setVisibility(View.VISIBLE);
							bt_charge_finish_share.setVisibility(View.GONE);	
						}
					}
				}else {
					bt_charge_finish_share.setVisibility(View.GONE);
					bt_umeng_title_fenxiang.setVisibility(View.GONE);
				}
				}else {
					bt_charge_finish_share.setVisibility(View.GONE);
					bt_umeng_title_fenxiang.setVisibility(View.GONE);
				}
			}
		}
		
	}
	@Override
	public void onFaile(String sign, Bundle bundle) {
		if (sign.equals(Protocol.SHARE_INTEGRAL)) {
			finish();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(mContext);
		MobclickAgent.onPageStart(mPageName);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
		MobclickAgent.onPageEnd(mPageName);
	}

}
