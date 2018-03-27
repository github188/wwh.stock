package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MalfunctionAdapter;
import com.bm.wanma.entity.ImageCarouselBean;
import com.bm.wanma.entity.MalfunctionBean;
import com.bm.wanma.entity.TextCarouselBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.Tools;
public class MalfunctionActivity extends BaseActivity implements OnClickListener{
	private ImageView iv_malfunction_close;
	private ListView lv_mal_list;
	private ArrayList<MalfunctionBean> malfunctionBeans = new ArrayList<MalfunctionBean>();
	private TextView tv_malfunction_title,tv_malfunction_title_t,tv_malfunction_content;
	private TextCarouselBean textCarouselBean;
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_malfunction);
		textCarouselBean = (TextCarouselBean) getIntent().getSerializableExtra("malfunction");
		initView();
		initData();
    }

    

	@Override
    protected void getData() {
		
    }

	protected void initView() {
		iv_malfunction_close = (ImageView) findViewById(R.id.malfunction_back);
		iv_malfunction_close.setOnClickListener(this);
		lv_mal_list = (ListView) findViewById(R.id.mal_list);
		tv_malfunction_title = (TextView) findViewById(R.id.malfunction_title);
		
		tv_malfunction_title_t = (TextView) findViewById(R.id.malfunction_title_t);
		tv_malfunction_content = (TextView) findViewById(R.id.malfunction_content);
		
		
	}
	private void initData() {
		cancelPD();
		GetDataPost.getInstance(getActivity()).getMyMalfunctionList(handler,textCarouselBean.getPkMeiId());
		 if (!Tools.isEmptyString(textCarouselBean.getMeiType())&&textCarouselBean.getMeiType().equals("0")
         		||textCarouselBean.getMeiType().equals("1")) {						
				tv_malfunction_title.setText("故障通告");
			}else {
				tv_malfunction_title.setText("充电点新增通告");
			}
		 tv_malfunction_title_t.setText(Tools.parseDate(textCarouselBean.getMeiBeginTime() ,"yyyy/MM/dd HH:mm", "MM.dd HH:mm")+"  "+textCarouselBean.getMeiName());
		 tv_malfunction_content.setText(""+textCarouselBean.getMeiContent());
		

	}
	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		if(bundle != null ){
			malfunctionBeans = (ArrayList<MalfunctionBean>) bundle.getSerializable(Protocol.DATA);
			MalfunctionAdapter adapter = new MalfunctionAdapter(this, malfunctionBeans);
			lv_mal_list.setAdapter(adapter);
			
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.malfunction_back:
			finish();
			break;

		}
	}
}
