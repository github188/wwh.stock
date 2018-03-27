package com.bm.wanma.ui.fragment;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MyNewsSystemAdapter;
import com.bm.wanma.entity.MyNewsSystemBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.activity.MyNewsSystemDetailActivity;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.view.MyDetailListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 系统消息
 * @author cm
 *
 */
public class MyNewsSystemFragment extends BaseFragment {

	private MyDetailListView mListView;
	private String userId;
	private ArrayList<MyNewsSystemBean> myNewsSystemListBean;
	private MyNewsSystemBean itemBean;
	private MyNewsSystemAdapter mAdapter;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View bespokeOrderFragment = inflater.inflate(
				R.layout.fragment_mynews_system, container, false);
		mListView = (MyDetailListView) bespokeOrderFragment.findViewById(R.id.fragment_mynews_system_listview);
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).getMyNewsSystemList(handler, userId);
		}
		
		return bespokeOrderFragment;
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (bundle != null) {
			myNewsSystemListBean = (ArrayList<MyNewsSystemBean>) bundle
					.getSerializable(Protocol.DATA);
			mAdapter = new MyNewsSystemAdapter(getActivity(), myNewsSystemListBean);
			mListView.setAdapter(mAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {
				//单击单个消息进入详情
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					itemBean = myNewsSystemListBean.get(position);
					//itemBean.getId();
					Intent in = new Intent();
					in.setClass(getActivity(), MyNewsSystemDetailActivity.class);
					in.putExtra("itemBean", itemBean);
					startActivity(in);
					
				}
			});

		}
	}


	@Override
	public void onFaile(String sign, Bundle bundle) {
		
		
	}


	
}
