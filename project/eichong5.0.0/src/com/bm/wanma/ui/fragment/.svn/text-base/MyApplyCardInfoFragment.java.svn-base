package com.bm.wanma.ui.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MyCardApplyListAdapter;
import com.bm.wanma.entity.MyCardApplyInfo;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;

/**
 * @author cm
 *  我的充点卡--申请信息
 */
public class MyApplyCardInfoFragment extends BaseFragment {

	private TextView tv_nodata;
	private ListView mList;
	private String userId;
	private ArrayList<MyCardApplyInfo> applyInfos ; 
	private MyCardApplyListAdapter maAdapter;
	private GetDataPost mgDataPost;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
		mgDataPost = GetDataPost.getInstance(getActivity());
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mFragment = inflater.inflate(
				R.layout.fragment_my_apply_cardinfo, container, false);
		tv_nodata = (TextView) mFragment.findViewById(R.id.fragment_my_apply_cardinfo_nodata);
		mList = (ListView) mFragment.findViewById(R.id.fragment_my_apply_cardinfo_listview);
		mgDataPost.getMyCardApplyListInfo(handler, userId);
		
		return mFragment;
	}
	
	
	public void notifyDataChange(){
			mgDataPost.getMyCardListInfo(handler, userId);
	}
	
	
	private void initData(ArrayList<MyCardApplyInfo> infos){
		
		maAdapter = new MyCardApplyListAdapter(getActivity(), infos);
		mList.setAdapter(maAdapter);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		
		if(bundle != null){
			applyInfos = (ArrayList<MyCardApplyInfo>) bundle.getSerializable(Protocol.DATA);
			if(applyInfos != null && applyInfos.size()>0){
				initData(applyInfos);
			}else {
				tv_nodata.setVisibility(View.VISIBLE);
			}
			
		}

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));
	}

	
	
}
