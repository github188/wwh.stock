package com.bm.wanma.ui.activity;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import net.tsz.afinal.FinalDb;

import com.alipay.sdk.app.PayTask;
import com.bm.wanma.R;
import com.bm.wanma.alipay.PayResult;
import com.bm.wanma.dialog.MyAlertDialog;
import com.bm.wanma.entity.AreaBean;
import com.bm.wanma.entity.CityBean;
import com.bm.wanma.entity.InvoiceConfigBean;
import com.bm.wanma.entity.InvoiceInfoDetailBean;
import com.bm.wanma.entity.ProvinceBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.RegularExpressionUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.ContainsEmojiEditText;
import com.bm.wanma.view.wheelcity.OnWheelChangedListener;
import com.bm.wanma.view.wheelcity.OnWheelScrollListener;
import com.bm.wanma.view.wheelcity.WheelView;
import com.bm.wanma.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.bm.wanma.view.wheelcity.adapters.AreaArrayWheelAdapter;
import com.bm.wanma.view.wheelcity.adapters.CityArrayWheelAdapter;
import com.bm.wanma.weixin.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cm
 * 填写发票详情  个人
 */
public class InvoicePersonalActivity extends BaseActivity implements OnClickListener {
	
	private ImageButton ib_back,ib_close;
	private TextView tv_title,tv_content,tv_invoice_money,tv_addr;
	private TextView tv_weixin_money,tv_alipay_money,tv_commit;
	private ContainsEmojiEditText et_company_title,et_name,et_phone,et_addr;
	private ImageView iv_weixin_select,iv_alipay_select,iv_daohu_select;
	private RelativeLayout rl_weixin,rl_alipay,rl_daofu;
	private FinalDb finalDb;
	private List<ProvinceBean> provinceList;
	private List<CityBean> cityList;
	private List<AreaBean> areaList;
	private List<InvoiceInfoDetailBean> infoList;
	private ProvinceBean provinceBean,currentP;
	private CityBean cityBean,currentC;
	private WheelView view_province;
	private WheelView view_city;
	private WheelView view_area;
	private InvoiceConfigBean configBean;
	private String pmoney,pRecords,payment,invoiceAmount,invoiceContent,uId,pcaAddr,province,city,county;
	private String companyName,recipients,recipientsPhone,recipientsAddr,orderId;
	//alipay
	private static final int SDK_PAY_FLAG = 1;
	private String aliPayInfo;
	private String WXpayInfo;
	//weixin
	private PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_personal);
		initView();
		initData();
		registerBoradcastReceiver();
	}
	
	private void initView(){
		tv_title = (TextView) findViewById(R.id.common_invoice_title);
		ib_back = (ImageButton) findViewById(R.id.invoice_back);
		ib_back.setOnClickListener(this);
		ib_close = (ImageButton) findViewById(R.id.invoice_close);
		ib_close.setOnClickListener(this);
		tv_content = (TextView) findViewById(R.id.invoice_company_tv_content);
		tv_invoice_money = (TextView) findViewById(R.id.invoice_company_tv_money);
		tv_addr = (TextView) findViewById(R.id.invoice_company_tv_code);
		tv_addr.setOnClickListener(this);
		tv_commit = (TextView) findViewById(R.id.invoice_commit);
		tv_commit.setOnClickListener(this);
		tv_weixin_money = (TextView) findViewById(R.id.invoice_pay_weix_money);
		tv_alipay_money = (TextView) findViewById(R.id.invoice_pay_alipay_money);
		et_company_title = (ContainsEmojiEditText) findViewById(R.id.invoice_company_et_taitou);
		et_name = (ContainsEmojiEditText) findViewById(R.id.invoice_company_et_name);
		et_phone = (ContainsEmojiEditText) findViewById(R.id.invoice_company_et_tel);
		et_addr = (ContainsEmojiEditText) findViewById(R.id.invoice_company_et_addr);
		iv_weixin_select = (ImageView) findViewById(R.id.invoice_pay_weix_select);
		iv_alipay_select = (ImageView) findViewById(R.id.invoice_pay_alipay_select);
		iv_daohu_select = (ImageView) findViewById(R.id.invoice_pay_daofu_select);
		rl_weixin = (RelativeLayout) findViewById(R.id.invoice_pay_weixin_rl);
		rl_weixin.setOnClickListener(this);
		rl_alipay = (RelativeLayout) findViewById(R.id.invoice_pay_alipay_rl);
		rl_alipay.setOnClickListener(this);
		rl_daofu = (RelativeLayout) findViewById(R.id.invoice_pay_daofu_rl);
		rl_daofu.setOnClickListener(this);
	}
	private void initData(){
		pmoney = getIntent().getStringExtra("pmoney");
		pRecords = getIntent().getStringExtra("pcode");
		payment = "2";
		uId = PreferencesUtil.getStringPreferences(this,"pkUserinfo");
		GetDataPost.getInstance(this).getInvoiceConfig(handler);
		finalDb = FinalDb.create(this,Protocol.DATABASE_NAME, true,Protocol.dbNumer,null);
		try {
			infoList = finalDb.findAllByWhere(InvoiceInfoDetailBean.class, "User_ID=\'"+uId +"\' and Type=2");
			if(infoList != null && infoList.size()>0){
				et_company_title.setText(infoList.get(0).getCompanyName());
				et_name.setText(infoList.get(0).getRecipient());
				et_phone.setText(infoList.get(0).getRecipientPhone());
				et_addr.setText(infoList.get(0).getRecipientAddr());
				tv_addr.setText(infoList.get(0).getPca());
				province = infoList.get(0).getPcode();
				city = infoList.get(0).getCcode();
				county = infoList.get(0).getAcode();
			}else {
				String phone = PreferencesUtil.getStringPreferences(this, "usinPhone");
				et_phone.setText(phone);
				handleAddr();
			}
		} catch (Exception e) {
			e.printStackTrace();
			String phone = PreferencesUtil.getStringPreferences(this, "usinPhone");
			et_phone.setText(phone);
			handleAddr();
		}
		req = new PayReq();
		msgApi.registerApp(Constants.APP_ID);
		
		tv_title.setText("填写发票详情");
		tv_invoice_money.setText("¥"+pmoney);
	}
	
	
	private void handleCommitInvoice(){
		companyName = et_company_title.getText().toString();
		recipients = et_name.getText().toString();
		recipientsPhone = et_phone.getText().toString();
		recipientsAddr = et_addr.getText().toString();
		pcaAddr = tv_addr.getText().toString();
		if(TextUtils.isEmpty(companyName)){
			showToast("请填写发票抬头");
			return;
		}
		if(TextUtils.isEmpty(recipients)){
			showToast("请填写收件人");
			return;
		}
		if(TextUtils.isEmpty(recipientsPhone)){
			showToast("请填写收件人电话");
			return;
		}
		if(!RegularExpressionUtil.isPhone(recipientsPhone) && !RegularExpressionUtil.isMobilephone(recipientsPhone)){
			showToast("收件人电话格式有误");
			return;
		}
		if(TextUtils.isEmpty(pcaAddr)){
			showToast("请选择所在地区");
			return;
		}
		if(TextUtils.isEmpty(recipientsAddr)){
			showToast("请填写收件人详细地址");
			return;
		}
		showPD("提交开票请求");
		GetDataPost.getInstance(this).commitInvoice(handler, uId, "0", invoiceContent, companyName, pmoney,
				null, null, null, null, null, payment, recipients, recipientsPhone, 
				province, city, county, recipientsAddr, pRecords,invoiceAmount,null);
	}
	
	private void handleAddr(){
		String p = PreferencesUtil.getStringPreferences(this, "province");
		String c= PreferencesUtil.getStringPreferences(this, "city");
		String distric = PreferencesUtil.getStringPreferences(this, "distric");
		county = PreferencesUtil.getStringPreferences(this, "adcode");
		pcaAddr = p + c + distric;
		try {
			areaList = finalDb.findAllByWhere(AreaBean.class, "AREA_ID="+county);
			province = areaList.get(0).getPROVINCE_ID();
			city = areaList.get(0).getCITY_ID();
		} catch (Exception e) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					finalDb.creatTable(InvoicePersonalActivity.this,"t_m_area");
				}
			}).start();
		}
		tv_addr.setText(pcaAddr);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
		saveDB();
	}
	
	private void saveDB(){
		companyName = et_company_title.getText().toString();
		recipients = et_name.getText().toString();
		recipientsPhone = et_phone.getText().toString();
		recipientsAddr = et_addr.getText().toString();
		InvoiceInfoDetailBean bean = new InvoiceInfoDetailBean();
		bean.setUser_ID(uId);
		bean.setType(2);
		bean.setCompanyName(companyName);
		bean.setRecipient(recipients);
		bean.setRecipientPhone(recipientsPhone);
		bean.setRecipientAddr(recipientsAddr);
		bean.setPca(pcaAddr);
		bean.setPcode(province);
		bean.setCcode(city);
		bean.setAcode(county);
		try {
			if(infoList != null && infoList.size()>0){
				finalDb.update(bean,"User_ID=\'"+uId +"\' and Type=2");
			}else {
				finalDb.save(bean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.invoice_back:
			finish();
			break;
		case R.id.invoice_close:
			sendBroadcast(new Intent("action.invoice.close"));
			finish();
			break;
		case R.id.invoice_company_tv_code:
			//选择省市区
			handleSelectAddress();
			break;
		case R.id.invoice_commit:
			//提交
			if(isNetConnection()){
				handleCommitInvoice();
			}else {
				showToast("网络不好，请稍后再试");
			}
			
			break;
		case R.id.invoice_pay_weixin_rl:
			payment = "2";
			iv_weixin_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_selected));
			iv_alipay_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			iv_daohu_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			break;
		case R.id.invoice_pay_alipay_rl:
			payment = "1";
			iv_weixin_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			iv_alipay_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_selected));
			iv_daohu_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			break;
		case R.id.invoice_pay_daofu_rl:
			payment = "4";
			iv_weixin_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			iv_alipay_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			iv_daohu_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_selected));
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}
	
	private void showInvoiceError(){
		Intent payErrorIn = new Intent(this,InvoiceErrorActivity.class);
		payErrorIn.putExtra("orderId", orderId);
		startActivity(payErrorIn);
		//sendBroadcast(new Intent("action.invoice.close"));
	}
	
	private void showInvoiceSuccess(){
		Intent paySuccesIn = new Intent(this,InvoiceSuccessActivity.class);
		startActivity(paySuccesIn);
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		if(bundle != null){
			if(sign.equals(Protocol.MY_INVOICE_CONFIG)){
				configBean = (InvoiceConfigBean) bundle.getSerializable(Protocol.DATA);
				if(configBean != null){
					invoiceAmount = configBean.getInvoiceAmount();
					invoiceContent = configBean.getInvoiceContent();
					if(invoiceAmount == null){
						invoiceAmount = "8.00";
					}
					if(invoiceContent == null){
						invoiceContent = "电费、充电服务费";
					}
					tv_content.setText(invoiceContent);
					tv_weixin_money.setText(Tools.formatMoney(invoiceAmount, 2, "¥"));
					tv_alipay_money.setText(Tools.formatMoney(invoiceAmount, 2, "¥"));
					
				}
			}else if(sign.equals(Protocol.COMMIT_INVOICE)){
				//提交订单成功，如果是微信或支付宝，调用付款
				Double tempId = (Double) bundle.getSerializable(Protocol.DATA);
				DecimalFormat format = new DecimalFormat("###");
				orderId = format.format(tempId);
//				invoiceAmount = "0.01";
				if("1".equals(payment)){//alipay
					StringBuilder sBuilder = new StringBuilder(uId);
					sBuilder.append("_").append("5").append("_").append(orderId);
					String userPhone = PreferencesUtil.getStringPreferences(this, "usinPhone"); 
					GetDataPost.getInstance(this).getAlipayInfo(handler, "快递费，邮费",sBuilder.toString(), invoiceAmount, userPhone);
				}else if("2".equals(payment)){//weixin
					 Double tempMoney = Double.valueOf(invoiceAmount)*100;
					 DecimalFormat df0 = new DecimalFormat("###");
					 //判断是否安装了微信app
					 
					 if(msgApi.isWXAppInstalled()){
						 if(isNetConnection()){
							    String userPhone = PreferencesUtil.getStringPreferences(this, "usinPhone"); 
							    showPD("正在获取微信支付信息");
								GetDataPost.getInstance(this).getWXPrepayInfo(handler,uId,"196.168.1.1", "快递费，邮费", ""+df0.format(tempMoney), userPhone, "APP","5",orderId);
						 }else {
							 showToast("请，网络不稳，请稍后再试");
							 showInvoiceError();
						 }
					 } else {
						 showToast("未安装微信客户端,请求失败");
						 showInvoiceError();
					   }
					
					
				}else if("4".equals(payment)){//到付
					//跳转支付成功界面
					showInvoiceSuccess();
				}
			}else if (Tools.judgeString(sign, Protocol.AliPayURL)) {//支付宝获取支付信息alipayInfo
					if(bundle != null){
						aliPayInfo =  (String) bundle.getSerializable(Protocol.DATA);
						callaliPay();
					}
			}else if (Tools.judgeString(sign, Protocol.WeiXinPayURL)) {//微信获取支付信息WXpayInfo
					if(bundle != null){
						WXpayInfo =  (String) bundle.getSerializable(Protocol.DATA);
						Map<String, String> xml = decodeXml(WXpayInfo);
						callWXpay(xml);
					}
				}
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
		if(sign.equals(Protocol.MY_INVOICE_CONFIG)){
			invoiceAmount = "8.00";
			invoiceContent = "电费、充电服务费";
			tv_content.setText(invoiceContent);
			tv_weixin_money.setText(Tools.formatMoney(invoiceAmount, 2, "¥"));
			tv_alipay_money.setText(Tools.formatMoney(invoiceAmount, 2, "¥"));
		}

	}
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction("action.invoice.close"); 
        myIntentFilter.addAction("com.bm.wanma.recharge_wx_ok"); 
        myIntentFilter.addAction("com.bm.wanma.recharge_wx_cancel"); 
        myIntentFilter.addAction("com.bm.wanma.recharge_wx_fail"); 
        myIntentFilter.addAction("com.bm.wanma.recharge_wx_err"); 
        myIntentFilter.addAction("com.bm.wanma.recharge_wx_err_auth"); 
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			  String action = intent.getAction();
			  if(action.equals("action.invoice.close")){
				  finish();
			  }else  if(action.equals("com.bm.wanma.recharge_wx_ok")){
				  //微信支付成功
				  showInvoiceSuccess();
			  }else if(action.equals("com.bm.wanma.recharge_wx_cancel")){
				  //微信支付失败
				  showInvoiceError();
			  }else if(action.equals("com.bm.wanma.recharge_wx_fail")){
				  //微信支付失败
				  showInvoiceError();
			  }else if(action.equals("com.bm.wanma.recharge_wx_err")){
				  //微信支付失败
				  showInvoiceError();
			  }else if(action.equals("com.bm.wanma.recharge_wx_err_auth")){
				  //微信支付失败
				  showInvoiceError();
			  }
		 }
	};
	
	
	//选择省市区
		private void handleSelectAddress(){
			View view = null;
			try {
				view = dialog();
			} catch (Exception e) {
				finalDb.creatTable(InvoicePersonalActivity.this,"t_m_area");
				view = dialog();
			}
			
			final MyAlertDialog dialog1 = new MyAlertDialog(
					InvoicePersonalActivity.this).builder().setTitle("请选择省市区")
					.setView(view)
					.setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
			dialog1.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(cityList.size()>0 && areaList.size()>0){
						pcaAddr = provinceList.get(view_province.getCurrentItem()).getPROVINCE_NAME()
								+ cityList.get(view_city.getCurrentItem()).getCITY_NAME()
								+ areaList.get(view_area.getCurrentItem()).getAREA_NAME();
						province = provinceList.get(view_province.getCurrentItem()).getPROVINCE_ID();
						city = cityList.get(view_city.getCurrentItem()).getCITY_ID();
						county = areaList.get(view_area.getCurrentItem()).getAREA_ID();
						
					}else if(cityList.size()>0 &&  areaList.size()==0){
						pcaAddr = provinceList.get(view_province.getCurrentItem()).getPROVINCE_NAME()
								+ cityList.get(view_city.getCurrentItem()).getCITY_NAME();
						
					}else {
						pcaAddr = provinceList.get(view_province.getCurrentItem()).getPROVINCE_NAME();
						
					}
					tv_addr.setText(""+pcaAddr);
					
				}
			});
			dialog1.show();
			
		}
		private View dialog() {
			View contentView = LayoutInflater.from(InvoicePersonalActivity.this).inflate(
					R.layout.wheelcity_cities_layout, null);
			//finalDb = FinalDb.create(ApplyCardActivity.this,Protocol.DATABASE_NAME);
			finalDb = FinalDb.create(getActivity(),Protocol.DATABASE_NAME, true,Protocol.dbNumer,null);
			provinceList = finalDb.findAll(ProvinceBean.class);
			//初始化值
			cityList = new ArrayList<CityBean>();
			areaList = new ArrayList<AreaBean>();
			//省选择
			 view_province = (WheelView) contentView
					.findViewById(R.id.wheelcity_country);
			view_province.setVisibleItems(3);
			view_province.setViewAdapter(new CountryAdapter(this));
			//城市选择
			 view_city = (WheelView) contentView
					.findViewById(R.id.wheelcity_city);
			// 地区选择
		    view_area = (WheelView) contentView
					.findViewById(R.id.wheelcity_ccity);
			provinceBean = provinceList.get(0);
			cityList = finalDb.findAllByWhere(CityBean.class,"PROVINCE_ID ="+provinceBean.getPROVINCE_ID());
			updateCities(view_city, cityList, 0);
			if(cityList.size()>0){
				cityBean = cityList.get(0);
				areaList = finalDb.findAllByWhere(AreaBean.class, "CITY_ID = "+cityBean.getCITY_ID());
				updatearea(view_area, areaList);
			}else {
				areaList.clear();
				updatearea(view_area,areaList);
			}
			
			view_province.addChangingListener(new OnWheelChangedListener() {
				public void onChanged(WheelView wheel, int oldValue, int newValue) {
					
				}
			});
			
			view_province.addScrollingListener(new OnWheelScrollListener() {
				@Override
				public void onScrollingStarted(WheelView wheel) {
					
				}
				
				@Override
				public void onScrollingFinished(WheelView wheel) {
					provinceBean = provinceList.get(view_province.getCurrentItem());
					if(provinceBean.equals(currentP)){
						return ;
					}
					currentP = provinceBean;
					cityList = finalDb.findAllByWhere(CityBean.class,"PROVINCE_ID ="+provinceBean.getPROVINCE_ID());
					updateCities(view_city, cityList, 0);
					if(cityList.size()>0){
						cityBean = cityList.get(0);
						areaList = finalDb.findAllByWhere(AreaBean.class, "CITY_ID = "+cityBean.getCITY_ID());
						updatearea(view_area, areaList);
					}else {
						areaList.clear();
						updatearea(view_area,areaList);
					}
					
				}
			});
			view_city.addScrollingListener(new OnWheelScrollListener() {
				
				@Override
				public void onScrollingStarted(WheelView wheel) {
				}
				@Override
				public void onScrollingFinished(WheelView wheel) {
					cityBean = cityList.get(view_city.getCurrentItem());
					if(cityBean.equals(currentC)){
						return ;
					}
					currentC = cityBean;
					
					areaList = finalDb.findAllByWhere(AreaBean.class, "CITY_ID = "+cityBean.getCITY_ID());
					updatearea(view_area, areaList);
				}
			});
			view_city.addChangingListener(new OnWheelChangedListener() {
				public void onChanged(WheelView wheel, int oldValue, int newValue) {
					
				}
			});

			view_area.addChangingListener(new OnWheelChangedListener() {
				public void onChanged(WheelView wheel, int oldValue, int newValue) {
					
				}
			});
			//初始化值
			view_province.setCurrentItem(0);// 设置北京

			return contentView;
		}
		
		/**
		 * Adapter for countries
		 */
		private class CountryAdapter extends AbstractWheelTextAdapter {
			// Countries names
			//private String countries[] = addressData.provinces;//AddressData.PROVINCES;

			/**
			 * Constructor
			 */
			protected CountryAdapter(Context context) {
				super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
				setItemTextResource(R.id.wheelcity_country_name);
				
			}

			@Override
			public View getItem(int index, View cachedView, ViewGroup parent) {
				View view = super.getItem(index, cachedView, parent);
				return view;
			}

			@Override
			public int getItemsCount() {
				return provinceList.size();
			}

			@Override
			protected CharSequence getItemText(int index) {
				String temp = provinceList.get(index).getPROVINCE_NAME();
				if(temp.length()>3){
					temp = temp.substring(0, 3)+"..";
				}
				return temp;
			}
		}
		
		/**
		 * Updates the city wheel
		 */
		private void updateCities(WheelView city, List<CityBean> cities, int index) {
			if(cities.size()>0){
				CityArrayWheelAdapter adapter = new CityArrayWheelAdapter(this,
						cities);
				adapter.setTextSize(18);
				city.setVisibility(View.VISIBLE);
				city.setViewAdapter(adapter);
				city.setCurrentItem(0);
			}else {
				city.setVisibility(View.GONE);
			}
			
		}
		/**
		 * Updates the area wheel
		 */
		private void updatearea(WheelView city, List<AreaBean> areas) {
			if(areas.size()>0){
				AreaArrayWheelAdapter adapter = new AreaArrayWheelAdapter(this,
						areas);
				adapter.setTextSize(18);
				city.setVisibility(View.VISIBLE);
				city.setViewAdapter(adapter);
				city.setCurrentItem(0);
			}else {
				city.setVisibility(View.GONE);
			}
			
		}
		
		@SuppressLint("HandlerLeak")
		private Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);
					// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						//充值成功
						showInvoiceSuccess();
					} else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(InvoicePersonalActivity.this, "支付结果确认中",
									Toast.LENGTH_SHORT).show();
							showInvoiceSuccess();
						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							/*Toast.makeText(InvoiceCompanyActivity.this, "支付失败",
									Toast.LENGTH_SHORT).show();*/
							showInvoiceError();
						}
					}
					break;
				}
			  }
			};
		};
		/**
		 * call alipay sdk pay. 调用SDK支付
		 */
		private void callaliPay() {
			
			Runnable payRunnable = new Runnable() {
				@Override
				public void run() {
					// 构造PayTask 对象
					PayTask alipay = new PayTask(InvoicePersonalActivity.this);
					// 调用支付接口，获取支付结果
					String result = alipay.pay(aliPayInfo);
					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			};

			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}	
		
		//微信支付调用
		private void callWXpay(Map<String,String> wxpayinfo){
				req.appId = wxpayinfo.get("appid");
				req.partnerId = wxpayinfo.get("partnerid");
				req.prepayId = wxpayinfo.get("prepayid");
				req.packageValue = "Sign=WXPay";
				req.nonceStr = wxpayinfo.get("noncestr");
				req.timeStamp = wxpayinfo.get("timestamp");
				req.sign = wxpayinfo.get("sign");
					//调用微信支付接口
				msgApi.registerApp(req.appId);
				msgApi.sendReq(req);
		} 
		
		
		public Map<String,String> decodeXml(String content) {
			try {
				Map<String, String> xml = new HashMap<String, String>();
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(new StringReader(content));
				int event = parser.getEventType();
				while (event != XmlPullParser.END_DOCUMENT) {

					String nodeName=parser.getName();
					switch (event) {
						case XmlPullParser.START_DOCUMENT:

							break;
						case XmlPullParser.START_TAG:

							if("xml".equals(nodeName)==false){
								//实例化student对象
								xml.put(nodeName,parser.nextText());
							}
							break;
						case XmlPullParser.END_TAG:
							break;
					}
					event = parser.next();
				}

				return xml;
			} catch (Exception e) {
				LogUtil.i("orion",e.toString());
			}
			return null;

		}
		
}
