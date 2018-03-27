package com.bm.wanma.ui.activity;

import net.tsz.afinal.FinalDb;

import com.bm.wanma.R;
import com.bm.wanma.entity.AreaBean;
import com.bm.wanma.entity.CityBean;
import com.bm.wanma.entity.InvoiceRecordDetailBean;
import com.bm.wanma.entity.ProvinceBean;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.Tools;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cm
 * 开票记录详情
 */
public class MyInvoiceRecordDetailActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_back,ib_close;
	private TextView tv_title,tv_status,tv_time,tv_content,tv_pay_name,tv_pay_money;
	private TextView tv_taitou_tag,tv_taitou,tv_amount;
	private TextView tv_tax,tv_comp_addr,tv_comp_phone,tv_bank,tv_bank_acc;
	private TextView tv_recipient,tv_recipient_phone,tv_area,tv_address,tv_purnum;
	private ImageView iv_pay_logo;
	private String iId;
	private InvoiceRecordDetailBean bean;
	private FinalDb finalDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_record_detail);
		finalDb = FinalDb.create(this,Protocol.DATABASE_NAME, true,Protocol.dbNumer,null);
		initView();
		bean = (InvoiceRecordDetailBean) getIntent().getSerializableExtra("InvoiceRecordDetailBean");
		iId = getIntent().getStringExtra("iId");
		if(bean != null){
			handleRenderView(bean);
		}
		
	}
	
	private void initView(){
		ib_back = (ImageButton) findViewById(R.id.invoice_back);
		ib_back.setOnClickListener(this);
		ib_close = (ImageButton) findViewById(R.id.invoice_close);
		ib_close.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.common_invoice_title);
		tv_title.setText("开票记录详情");
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
		
		tv_pay_name = (TextView) findViewById(R.id.invoice_pay_name);
		tv_pay_money = (TextView) findViewById(R.id.invoice_pay_money);
		iv_pay_logo = (ImageView) findViewById(R.id.invoice_pay_logo);
	}
	
	private void handleRenderView(InvoiceRecordDetailBean b){
		//0受理中 1处理完成 2未支付邮费
		if("0".equals(b.getStatus())){
			tv_status.setText("受理中");
		}else if("1".equals(b.getStatus())){
			tv_status.setText("处理完成");
		}else if("3".equals(b.getStatus())){
			tv_status.setText("拒绝开票");
		}
		if("2".equals(bean.getPayMod())){
			//1-支付宝支付，2-微信支付
			tv_pay_name.setText("微信支付");
			iv_pay_logo.setImageDrawable(getResources().getDrawable(R.drawable.img_weixin));
		}else if("1".equals(bean.getPayMod())){
			//1-支付宝支付，2-微信支付
			tv_pay_name.setText("支付宝");
			iv_pay_logo.setImageDrawable(getResources().getDrawable(R.drawable.img_zhifubao));
		}else {
			iv_pay_logo.setVisibility(View.GONE);
			tv_pay_name.setText("快递货到付款");
		}
		tv_pay_money.setText(Tools.formatMoney(b.getfAmount(), 2, "¥"));
		
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
			consumeIn.putExtra("iId", iId);
			startActivity(consumeIn);
			break;

		default:
			break;
		}
		
	}
	@Override
	protected void getData() {
		
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));

	}

	private SpannableStringBuilder getOrangeString(String str){
		   int index = str.indexOf("条");
	       SpannableStringBuilder style=new SpannableStringBuilder(str);     
	       style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.common_orange)),1,index,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);      
		return style;
	}

}
