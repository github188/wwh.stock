package com.bm.wanma.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MyInvoiceAdapter;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.entity.EnableInvoiceBean;
import com.bm.wanma.entity.EnableInvoiceSectionBean;
import com.bm.wanma.entity.InvoiceCheckHold;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class InvoiceActivity extends BaseActivity implements OnClickListener,OnCheckedChangeListener{
	
	private ImageButton ib_back,ib_close;
	private ListView listView;
	private ViewSwitcher viewSwitcher;
	private TextView tv_role,tv_check_total,tv_next;
	private RelativeLayout rl_bottom;
	private CheckBox checkBox;
	private String uId;
	private ArrayList<EnableInvoiceBean> rawdata;
	private ArrayList<EnableInvoiceSectionBean> data;
	private MyInvoiceAdapter maAdapter;
	private Map<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();
	private int checkNum;// 记录选中的条目数量
	private String money;
	private int isall = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinvoice_list);
		initView();
		money = "0.00";
		uId = PreferencesUtil.getStringPreferences(this,"pkUserinfo");
		if(isNetConnection()){
			GetDataPost.getInstance(this).getMyEnableInvoiceList(handler, uId);
		}
		registerBoradcastReceiver();
	}
	
	private void initView(){
		ib_back = (ImageButton) findViewById(R.id.invoice_back);
		ib_back.setOnClickListener(this);
		ib_close = (ImageButton) findViewById(R.id.invoice_close);
		ib_close.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.activity_invoice_listview);
		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewswitcher);
		rl_bottom = (RelativeLayout) findViewById(R.id.invoice_bottom);
		tv_role = (TextView) findViewById(R.id.invoice_role);
		tv_role.setOnClickListener(this);
		tv_check_total = (TextView) findViewById(R.id.invoice_total);
		tv_next = (TextView) findViewById(R.id.invoice_next);
		checkBox = (CheckBox) findViewById(R.id.invoice_checkbox);
		checkBox.setOnCheckedChangeListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.invoice_back:
		case R.id.invoice_close:
			finish();
			break;

		case R.id.invoice_next:
			//下一步
			if(Double.valueOf(money) == 0){
				showToast("开票金额需大于0");
				return;
			}
			StringBuilder pcode = new StringBuilder();
			for(int i=0;i<data.size();i++){
				if(0 == data.get(i).getType() && isCheckedMap.get(i)){
					pcode.append(data.get(i).getBillBean().getpId()).append(",");
				}
			}
			Intent typeIn = new Intent(InvoiceActivity.this,InvoiceTypeActivity.class);
			typeIn.putExtra("pcode",pcode.substring(0, pcode.length()-1));
			typeIn.putExtra("pmoney",money);
			startActivity(typeIn);
			
			break;
		case R.id.invoice_role:
			//规则
			Intent role = new Intent(InvoiceActivity.this,InvoiceRoleActivity.class);
			startActivity(role);
			break;

		default:
			break;
		}
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isall==0) {		
			money = "0";
			checkNum = 0;
			if(isChecked){
				for(int i=0;i<data.size();i++){
					if(0 == data.get(i).getType()){
						checkNum ++;
						money = Tools.addStr(money, data.get(i).getBillBean().getpMoney());
					}
				}
				tv_next.setOnClickListener(this);
				tv_next.setBackground(getResources().getDrawable(R.drawable.invoice_shape_corner_next_orange));
			}else {
				
				tv_next.setOnClickListener(null);
				tv_next.setBackground(getResources().getDrawable(R.drawable.invoice_shape_corner_next_gray));
			}
			String text = String.format(getResources().getString(R.string.invoice_total),checkNum,money);
			tv_check_total.setText(getRedString(text));
			isCheckedMap = maAdapter.setCheckAll(isChecked);
		}
	}
	
	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if(bundle != null){
			rawdata = (ArrayList<EnableInvoiceBean>) bundle.getSerializable(Protocol.DATA);
			if(rawdata != null && rawdata.size()>0){
				rl_bottom.setVisibility(View.VISIBLE);
				data = EnableInvoiceSectionBean.getData(rawdata);
				maAdapter = new MyInvoiceAdapter(this, data);
				listView.setAdapter(maAdapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@SuppressLint("NewApi")
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if(0 == data.get(position).getType()){
							InvoiceCheckHold hold = (InvoiceCheckHold) view.getTag();
							hold.cb.toggle();
							 // 调整选定条目  
			                if (hold.cb.isChecked()) {  
			                    checkNum++;  
			                    money = Tools.addStr(money, data.get(position).getBillBean().getpMoney());
			                    isCheckedMap = maAdapter.setCheckAt(position, true);
			                    if (rawdata.size()==checkNum) {
			                    	isall = 0;
			                    	checkBox.setChecked(true);
								}
			                } else {  
			                    checkNum--; 
			                    money = Tools.reduceStr(money, data.get(position).getBillBean().getpMoney());
			                    isCheckedMap = maAdapter.setCheckAt(position, false);
			                    if (checkBox.isChecked()) {
			                    	isall = 2;
			                    	checkBox.setChecked(false);
			                    	isall = 0;
								}
			                }  
			                if(checkNum>0){
			                	tv_next.setOnClickListener(InvoiceActivity.this);
			        			tv_next.setBackground(getResources().getDrawable(R.drawable.invoice_shape_corner_next_orange));
			                }else {
			                	tv_next.setOnClickListener(null);
			        			tv_next.setBackground(getResources().getDrawable(R.drawable.invoice_shape_corner_next_gray));
			                }
			                String text = String.format(getResources().getString(R.string.invoice_total),checkNum,money);
							tv_check_total.setText(getRedString(text));
						}
					}
				});
				String text = String.format(getResources().getString(R.string.invoice_total),0,"0");
				tv_check_total.setText(getRedString(text));
			}else {
				viewSwitcher.showNext();
			}
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}
	
	private SpannableStringBuilder getRedString(String str){
		   int index[] = new int[3];  
	       index[0] = str.indexOf("笔");  
	       index[1] = str.indexOf("共");  
	       index[2] = str.indexOf("元"); 
	       SpannableStringBuilder style=new SpannableStringBuilder(str);     
	       style.setSpan(new ForegroundColorSpan(Color.RED),2,index[0],Spannable.SPAN_EXCLUSIVE_INCLUSIVE);      
           style.setSpan(new ForegroundColorSpan(Color.RED),index[1]+1,index[2],Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return style;
	}
	
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction("action.invoice.close"); 
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			  String action = intent.getAction();
			  if(action.equals("action.invoice.close")){
				  finish();
			  }
		 }
	};

}
