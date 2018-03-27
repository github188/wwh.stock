package com.bm.wanma.popup;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.entity.BaseBean;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;

public class SearchPop extends PopupWindow implements OnItemClickListener {

	private Activity activity;
	private int position;
	private ArrayList<BaseBean> list;
	private SearchCallBack callBack;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> temp = new ArrayList<String>();

	public SearchPop(Activity activity, int position, ArrayList<BaseBean> list, SearchCallBack callBack) {
		this.activity = activity;
		this.position = position;
		this.list = list;
		this.callBack = callBack;
		ListView lv = new ListView(activity);
		lv.setDivider(activity.getResources().getDrawable(R.color.common_white));
		lv.setDividerHeight(3);
		lv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		adapter = new ArrayAdapter<String>(activity, R.layout.simple_list_item, R.id.text, temp);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		setFocusable(true);
		setContentView(lv);
		setHeight(LayoutParams.WRAP_CONTENT);
		setOutsideTouchable(true);
		//setBackgroundDrawable(activity.getResources().getDrawable(R.color.gray));
		//setBackgroundDrawable(activity.getResources().getDrawable(R.color.titlebarbg));
	}

	public void show(View view) {
		setWidth(view.getWidth());
		System.out.println("list==" + adapter.getCount());
		if (adapter.getCount() > 0) {
			showAsDropDown(view);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		callBack.callBack(this.position);
		if (isShowing()) {
			dismiss();
		}
	}
	public interface SelectCallBack {
		public void callBack(int position);
	}
	public interface SearchCallBack {
		public void callBack(int position);
	}

}
