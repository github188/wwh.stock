package com.bm.wanma.ui.activity;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import net.tsz.afinal.FinalDb;

import com.alipay.sdk.app.PayTask;
import com.bm.wanma.R;
import com.bm.wanma.alipay.PayResult;
import com.bm.wanma.entity.AreaBean;
import com.bm.wanma.entity.CityBean;
import com.bm.wanma.entity.InvoiceConfigBean;
import com.bm.wanma.entity.InvoiceRecordDetailBean;
import com.bm.wanma.entity.ProvinceBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cm
 * 开票记录详情-未支付
 */
public class MyInvoiceRecordDetailPayActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_back,ib_close;
	private TextView tv_title,tv_status,tv_time,tv_content;
	private TextView tv_taitou_tag,tv_taitou,tv_amount;
	private TextView tv_tax,tv_comp_addr,tv_comp_phone,tv_bank,tv_bank_acc;
	private TextView tv_recipient,tv_recipient_phone,tv_area,tv_address,tv_purnum;
	private ImageView iv_weixin_select,iv_alipay_select,iv_daohu_select;
	private RelativeLayout rl_weixin,rl_alipay,rl_daofu;
	private TextView tv_weixin_money,tv_alipay_money,tv_commit;
	private String uId,orderId,payment,fAmount,tempFamount;
	private InvoiceRecordDetailBean bean;
	private InvoiceConfigBean configBean;
	private FinalDb finalDb;
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
		setContentView(R.layout.activity_invoice_record_detail_pay);
		finalDb = FinalDb.create(this,Protocol.DATABASE_NAME, true,Protocol.dbNumer,null);
		initView();
		bean = (InvoiceRecordDetailBean) getIntent().getSerializableExtra("InvoiceRecordDetailBean");
		orderId = getIntent().getStringExtra("iId");
		GetDataPost.getInstance(this).getInvoiceConfig(handler);
		if(bean != null){
			handleRenderView(bean);
		}
		registerBoradcastReceiver();
	}
	
	private void initView(){
		ib_back = (ImageButton) findViewById(R.id.invoice_back);
		ib_back.setOnClickListener(this);
		ib_close = (ImageButton) findViewById(R.id.invoice_close);
		ib_close.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.common_invoice_title);
		
		tv_status = (TextView) findViewById(R.id.invoice_record_detail_status);
		tv_time = (TextView) findViewById(R.id.invoice_record_detail_time);
		tv_content = (TextView) findViewById(R.id.invoice_record_detail_content);
		tv_taitou_tag = (TextView) findViewById(R.id.invoice_record_detail_taitou_tag);
		tv_taitou = (TextView) findViewById(R.id.invoice_record_detail_taitou);
		tv_amount = (TextView) findViewById(R.id.invoice_record_detail_amount);
		
		tv_recipient = (TextView) findViewById(R.id.invoice_record_detail_recipient);
		tv_recipient_phone = (TextView) findViewById(R.id.invoice_record_detail_recipientp);
		tv_area = (TextView) findViewById(R.id.invoice_record_detail_area);
		tv_address = (TextView) findViewById(R.id.invoice_record_detail_recipientaddr);
		tv_purnum = (TextView) findViewById(R.id.invoice_record_detail_purnum);
		tv_purnum.setOnClickListener(this);
	
		rl_weixin = (RelativeLayout) findViewById(R.id.invoice_pay_weixin_rl);
		rl_weixin.setOnClickListener(this);
		rl_alipay = (RelativeLayout) findViewById(R.id.invoice_pay_alipay_rl);
		rl_alipay.setOnClickListener(this);
		rl_daofu = (RelativeLayout) findViewById(R.id.invoice_pay_daofu_rl);
		rl_daofu.setOnClickListener(this);
		iv_weixin_select = (ImageView) findViewById(R.id.invoice_pay_weix_select);
		iv_alipay_select = (ImageView) findViewById(R.id.invoice_pay_alipay_select);
		iv_daohu_select = (ImageView) findViewById(R.id.invoice_pay_daofu_select);
		tv_commit = (TextView) findViewById(R.id.invoice_commit);
		tv_commit.setOnClickListener(this);
		tv_weixin_money = (TextView) findViewById(R.id.invoice_pay_weix_money);
		tv_alipay_money = (TextView) findViewById(R.id.invoice_pay_alipay_money);
	}
	
	private void handleRenderView(InvoiceRecordDetailBean b){
		payment = "2";
		uId = PreferencesUtil.getStringPreferences(this,"pkUserinfo");
		tv_title.setText("开票记录详情");
		tv_status.setText("未支付(请选择支付方式、再次提交)");
		tv_time.setText(Tools.parseDate(b.getTime(), "yyyy-MM-dd HH:mm:SS", "yyyy年MM月dd日"));
		tv_content.setText(b.getContent()+"");
		tv_taitou.setText(""+b.getCompName());
		tv_amount.setText(Tools.formatMoney(b.getAmount(), 2, "¥"));
		
		tv_recipient.setText(""+b.getRecipient());
		tv_recipient_phone.setText(""+b.getRecPhone());
		tv_address.setText(""+b.getConAddr());
		
		if("0".equals(b.getType())){
			//0-个人开票 ，1-公司开票
			tv_taitou_tag.setText("发票抬头");
			findViewById(R.id.invoice_record_detail_ll_tax).setVisibility(View.GONE);
			findViewById(R.id.invoice_record_detail_ll_companyaddr).setVisibility(View.GONE);
			findViewById(R.id.invoice_record_detail_ll_companyphone).setVisibility(View.GONE);
			findViewById(R.id.invoice_record_detail_ll_bank).setVisibility(View.GONE);
			findViewById(R.id.invoice_record_detail_ll_bankacc).setVisibility(View.GONE);
		}else if("1".equals(b.getType())){
			tv_taitou_tag.setText("公司抬头");
			tv_tax = (TextView) findViewById(R.id.invoice_record_detail_tax);
			tv_tax.setText(""+b.getTaxNum());
			tv_comp_addr = (TextView) findViewById(R.id.invoice_record_detail_companyaddr);
			tv_comp_addr.setText(""+b.getCompAddr());
			tv_comp_phone = (TextView) findViewById(R.id.invoice_record_detail_companyphone);
			tv_comp_phone.setText(""+b.getCompPhone());
			tv_bank = (TextView) findViewById(R.id.invoice_record_detail_bank);
			tv_bank.setText(""+b.getBankName());
			tv_bank_acc = (TextView) findViewById(R.id.invoice_record_detail_bankacc);
			tv_bank_acc.setText(""+b.getBankAcc());
		}
		
		try {
			String p_name = finalDb.findAllByWhere(ProvinceBean.class, "PROVINCE_ID=" + b.getpCode()).get(0).getPROVINCE_NAME();
			String c_name = finalDb.findAllByWhere(CityBean.class, "CITY_ID=" + b.getcCode()).get(0).getCITY_NAME();
			String a_name = finalDb.findAllByWhere(AreaBean.class, "AREA_ID=" + b.getaCode()).get(0).getAREA_NAME();
			tv_area.setText(p_name+"  "+c_name+"  "+a_name);
		} catch (Exception e) {
			
		}
		String amount = String.format(getResources().getString(R.string.invoice_detail_consume), b.getPurNum());
		tv_purnum.setText(getOrangeString(amount));
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
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
		case R.id.invoice_record_detail_purnum:
			//消费记录详情
			Intent consumeIn = new Intent(this,MyInvoiceRecordConsumeActivity.class);
			consumeIn.putExtra("iId", orderId);
			startActivity(consumeIn);
			break;
		case R.id.invoice_pay_weixin_rl:
			payment = "2";
			tempFamount = fAmount;
			iv_weixin_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_selected));
			iv_alipay_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			iv_daohu_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			break;
		case R.id.invoice_pay_alipay_rl:
			payment = "1";
			tempFamount = fAmount;
			iv_weixin_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			iv_alipay_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_selected));
			iv_daohu_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			break;
		case R.id.invoice_pay_daofu_rl:
			payment = "4";
			tempFamount = "0.00";
			iv_weixin_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			iv_alipay_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_select));
			iv_daohu_select.setImageDrawable(getResources().getDrawable(R.drawable.btn_invoice_pay_selected));
			break;
		case R.id.invoice_commit:
			//提交支付
			if(isNetConnection()){
				handleCommitInvoice();
			}else {
				showToast("网络不好，请稍后再试");
			}
			
			break;

		default:
			break;
		}
		
	}
	
 
	private void handleCommitInvoice(){
		showPD("提交开票请求");
		GetDataPost.getInstance(this).commitInvoice(handler, uId,bean.getType(), bean.getContent(), bean.getCompName(), bean.getAmount(),
				bean.getTaxNum(), bean.getCompAddr(), bean.getCompPhone(), bean.getBankName(), bean.getBankAcc(), payment, bean.getRecipient(),
				bean.getRecPhone(), bean.getpCode(), bean.getcCode(), bean.getaCode(), bean.getConAddr(),"",tempFamount,orderId);
	}
	
	@Override
	protected void getData() {
		
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		if(sign.equals(Protocol.COMMIT_INVOICE)){
			//提交订单成功，如果是微信或支付宝，调用付款
//			fAmount = "0.01";
			if("1".equals(payment)){//alipay
				StringBuilder sBuilder = new StringBuilder(uId);
				sBuilder.append("_").append("5").append("_").append(orderId);
				String userPhone = PreferencesUtil.getStringPreferences(this, "usinPhone"); 
				GetDataPost.getInstance(this).getAlipayInfo(handler, "快递费，邮费",sBuilder.toString(),fAmount, userPhone);
			}else if("2".equals(payment)){//weixin
				Double tempMoney = Double.valueOf(fAmount)*100;
				DecimalFormat df0 = new DecimalFormat("###");
				 //判断是否安装了微信app
				 if(msgApi.isWXAppInstalled()){
					 if(isNetConnection()){
						    String userPhone = PreferencesUtil.getStringPreferences(this, "usinPhone"); 
						 	//wxpaydg =  ProgressDialog.show(InvoiceCompanyActivity.this, getString(R.string.wx_tip), getString(R.string.getting_prepayid));
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
				/*if(wxpaydg != null){
					wxpaydg.dismiss();
				}*/
				if(bundle != null){
					WXpayInfo =  (String) bundle.getSerializable(Protocol.DATA);
					Map<String, String> xml = decodeXml(WXpayInfo);
					req = new PayReq();
					msgApi.registerApp(Constants.APP_ID);
					callWXpay(xml);
				}
		}else if(sign.equals(Protocol.MY_INVOICE_CONFIG)){
				configBean = (InvoiceConfigBean) bundle.getSerializable(Protocol.DATA);
				if(configBean != null){
					fAmount = configBean.getInvoiceAmount();
					if(fAmount == null){
						fAmount = "8.00";
					}
					tempFamount = fAmount;
					tv_weixin_money.setText(Tools.formatMoney(fAmount, 2, "¥"));
					tv_alipay_money.setText(Tools.formatMoney(fAmount, 2, "¥"));
				}else {
					fAmount = "8.00";
					tempFamount = fAmount;
					tv_weixin_money.setText(Tools.formatMoney(fAmount, 2, "¥"));
					tv_alipay_money.setText(Tools.formatMoney(fAmount, 2, "¥"));
				}
			}
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
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
		if(sign.equals(Protocol.MY_INVOICE_CONFIG)){
				fAmount = "8.00";
				tv_weixin_money.setText(Tools.formatMoney(fAmount, 2, "¥"));
				tv_alipay_money.setText(Tools.formatMoney(fAmount, 2, "¥"));
		}

	}

	private SpannableStringBuilder getOrangeString(String str){
		   int index = str.indexOf("条");
	       SpannableStringBuilder style=new SpannableStringBuilder(str);     
	       style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_orange)),1,index,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);      
		return style;
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
						Toast.makeText(MyInvoiceRecordDetailPayActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();
						showInvoiceSuccess();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(MyInvoiceRecordDetailPayActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();
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
				PayTask alipay = new PayTask(MyInvoiceRecordDetailPayActivity.this);
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
			  }else if(action.equals("com.bm.wanma.recharge_wx_ok")){
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
}
