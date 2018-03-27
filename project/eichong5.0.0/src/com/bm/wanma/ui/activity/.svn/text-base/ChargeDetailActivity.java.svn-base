package com.bm.wanma.ui.activity;
import java.math.BigDecimal;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.adapter.ChargeFlAdapter;
import com.bm.wanma.adapter.FunctionButtonAdapter;
import com.bm.wanma.entity.ChargeOrderDetailsBean;
import com.bm.wanma.entity.RateBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * @author cm
 * 充电订单详情
 */
public class ChargeDetailActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_back;
	private TextView tv_name,tv_start_time,tv_end_time,tv_addr;
	private TextView tv_duration,tv_electric_money,tv_service_money;
	private TextView tv_coupon_money,tv_payment_money,tv_order_code,tv_orderstate,tv_charge_detail_service_price;
	private ChargeOrderDetailsBean bean;
	private ArrayList<RateBean> details;
	private String order;
	private GridView gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charge_order_detail);
		order = getIntent().getStringExtra("ordernumber");
		initView();
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.common_orange);
        }
	}
	
	@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	private void initView(){
		ib_back = (ImageButton) findViewById(R.id.activity_charge_detail_back);
		ib_back.setOnClickListener(this);
		tv_duration = (TextView) findViewById(R.id.charge_detail_duration);//充电时长
		
		tv_start_time = (TextView) findViewById(R.id.charge_detail_start);//开始时间
		tv_end_time = (TextView) findViewById(R.id.charge_detail_end);//结束时间
		
		tv_name = (TextView) findViewById(R.id.charge_detail_name);//充电点
		tv_addr = (TextView) findViewById(R.id.charge_detail_addr);//地址
		
		tv_payment_money = (TextView) findViewById(R.id.charge_detail_payment_money);//费用
		
		gridView = (GridView)findViewById(R.id.custom_function);
		
		tv_electric_money = (TextView) findViewById(R.id.charge_detail_elctric_money);//电费金额		
		tv_service_money = (TextView) findViewById(R.id.charge_detail_service_money);//服务费金额 
		tv_coupon_money = (TextView) findViewById(R.id.charge_detail_coupon_money);//优惠卷抵扣		
		tv_orderstate = (TextView) findViewById(R.id.charge_detail_order_status);//订单状态
		tv_order_code = (TextView) findViewById(R.id.charge_detail_order);//订单编号
		tv_charge_detail_service_price = (TextView) findViewById(R.id.charge_detail_service_price);//订单编号
		if(!Tools.isEmptyString(order)){ 
			 GetDataPost.getInstance(getActivity()).getMyChargeOrder(handler, order);
		 }else {
			finish();
		}
	}
	
	private void initValue(ChargeOrderDetailsBean bean,ArrayList<RateBean> details){
		LogUtil.i("cm_netPost","--------------------------------- = " + bean);
		/* 编号 */
		String code = bean.getCode();
		/* 优惠金额 */
		String cpVal = bean.getCpVal();
		
		/* 电费 */
		String cgMn = bean.getCgMn();
		/* 服务费 */
		String svMn = bean.getSvMn();
		/*服务费单价*/
		String svd = bean.getSvd();
		/*电费总量*/
		String allqlty = bean.getAllQlty();
		/* 开始时间 */
		String st = bean.getSt();
		/* 结束时间 */
		String et = bean.getEt();
		/* 状态（订单状态 1：待支付 2：支付成功 3 充电完成，未支付 （1状态下需要支付）4：异常订单 5：临时结算）*/
		String sts = bean.getSts();
		/* 地址 */
		String addr = bean.getAddr();
		/* 总费用  */
		String fee = bean.getFee();
		if(Tools.isEmptyString(cpVal)){
			cpVal = "0";
		}
		if(!Tools.isEmptyString(svd)&&!Tools.isEmptyString(allqlty)){
			tv_charge_detail_service_price.setVisibility(View.VISIBLE);
			tv_charge_detail_service_price.setText(svd+"元/千瓦时x"+allqlty+"千瓦");//电费价格1
		}

		/*充电点*/
		String ptName = bean.getPtName();
		long start = TimeUtil.getTimestamp(st,"yyyy-MM-dd HH:mm:ss");
		long end = TimeUtil.getTimestamp(et,"yyyy-MM-dd HH:mm:ss");
		/* 充电时长*/
		long between = end - start;
		/* 费率 */
//		ArrayList<RateBean> details;
		
		tv_duration.setText(TimeUtil.getCutDown4(between));//充电时长
		
		tv_start_time.setText(TimeUtil.parseDate(st, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日  HH:mm:ss"));//开始时间
		tv_end_time.setText(TimeUtil.parseDate(et, "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日  HH:mm:ss"));//结束时间
		
		tv_name.setText(ptName);//充电点
		tv_addr.setText(addr);//地址
		
		tv_payment_money.setText(fee+"元");//费用
		
		tv_electric_money.setText(cgMn+"元");//电费金额	
//		LogUtil.i("cm_netPost"," = " + bean);
		if (details.size()>0) {			
			ChargeFlAdapter adapter = new ChargeFlAdapter(getActivity(), details);
			gridView.setAdapter(adapter);
		}
//		for (int i = 0; i < details.size(); i++) {
//			LogUtil.i("cm_netPost","----------details.get("+i+")----------------------- = " + details.get(i));
//			if(i==0){
//				tv_charge_detail_price1.setVisibility(View.VISIBLE);
//				tv_charge_detail_price1.setText(details.get(i).getFl()+"元/千瓦时x"+details.get(i).getQt()+"千瓦");//电费价格1
//			}
//			if(i==1){
//				tv_charge_detail_price2.setVisibility(View.VISIBLE);
//				tv_charge_detail_price2.setText(details.get(i).getFl()+"元/千瓦时x"+details.get(i).getQt()+"千瓦");//电费价格1
//			}
//			if(i==2){
//				tv_charge_detail_price3.setVisibility(View.VISIBLE);
//				tv_charge_detail_price3.setText(details.get(i).getFl()+"元/千瓦时x"+details.get(i).getQt()+"千瓦");//电费价格1
//			}
//			if(i==3){
//				tv_charge_detail_price4.setVisibility(View.VISIBLE);
//				tv_charge_detail_price4.setText(details.get(i).getFl()+"元/千瓦时x"+details.get(i).getQt()+"千瓦");//电费价格1
//			}
//		}

//		tv_service_price.setText();//服务费价格
		tv_service_money.setText(svMn+"元");//服务费金额 
		
		tv_coupon_money.setText("-"+cpVal+"元");//优惠卷抵扣	
		
		
		tv_order_code.setText(code);//订单编号
		
		
		/*订单状态 1：待支付 2：支付成功 3 完成操作 （1状态下需要支付  3完成未结算）4异常订单5.临时结算*/
		if("4".equals(sts)){
			tv_orderstate.setText("故障");
//			TextView tv_answer_questions = (TextView) findViewById(R.id.answer_questions);
//			tv_answer_questions.setVisibility(View.VISIBLE);
//			tv_answer_questions.setText("\n订单异常，暂不计费。\n如果有疑问请拨打客服电话：400-085-0006");
		}else if("5".equals(sts)){
			tv_orderstate.setText("临时结算");
//			TextView tv_answer_questions = (TextView) findViewById(R.id.answer_questions);
//			tv_answer_questions.setVisibility(View.VISIBLE);
		}else {
			tv_orderstate.setText("已完成");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_charge_detail_back:
			finish();
			break;

		default:
			break;
		}
		
	}

/*	private SpannableStringBuilder getRedString(String str){
		   int end = str.indexOf("元");
	       SpannableStringBuilder style = new SpannableStringBuilder(str);     
	       style.setSpan(new ForegroundColorSpan(Color.RED),0,end,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);      
		return style;
	}
*/
	@Override
	protected void getData() {
		
		
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if(sign.equals(Protocol.MYCHARGE_ORDER_DETAILS)){
			bean = (ChargeOrderDetailsBean) bundle.getSerializable(Protocol.DATA);
//			LogUtil.i("cm_netPost","--------------------------------- = " + Protocol.DATA);
			LogUtil.i("cm_netPost","--------------------------------- = " + bean);
			if(bean == null){
				finish();
			}
			details = bean.getDetails();
			initValue(bean,details);
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		finish();
	}


}
