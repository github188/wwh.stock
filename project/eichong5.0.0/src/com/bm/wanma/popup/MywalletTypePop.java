package com.bm.wanma.popup;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.popup.SearchPop.SearchCallBack;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

public class MywalletTypePop extends PopupWindow implements OnItemClickListener {

	ArrayList<String> list;
	ArrayAdapter<String> myAdapter;
	Activity activity;
	SearchCallBack callBack;

	public MywalletTypePop(Activity activity, ArrayList<String> list, SearchCallBack callBack) {
		this.list = list;
		this.callBack = callBack;
		this.activity = activity;
		ListView lv = new ListView(activity);
		lv.setDivider(activity.getResources().getDrawable(R.color.common_white));
		lv.setDividerHeight(3);
		lv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		myAdapter = new ArrayAdapter<String>(activity, R.layout.mywallet_type_list_item, R.id.mywallet_list_item_text, this.list);
		lv.setAdapter(myAdapter);
		lv.setOnItemClickListener(this);
		setFocusable(true);
		setContentView(lv);
		setHeight(LayoutParams.WRAP_CONTENT);
		setOutsideTouchable(true);
		setBackgroundDrawable(activity.getResources().getDrawable(R.color.common_gray));
	}

	public void show(View v) {
		setWidth(v.getWidth());
		if (list != null && list.size() > 0) {
			System.out.println("========");
			showAsDropDown(v);
		}
	}

	/* 这是数据 */
	public void setList(ArrayList<String> list) {
		this.list = list;
		myAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		callBack.callBack(position);
		if (isShowing()) {
			dismiss();
		}
	}

}
