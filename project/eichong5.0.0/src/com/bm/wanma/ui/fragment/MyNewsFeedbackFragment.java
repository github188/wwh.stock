package com.bm.wanma.ui.fragment;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MyNewsFeedbackAdapter;
import com.bm.wanma.entity.MyNewsFeedbackBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.view.MyDetailListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 我的反馈
 * @author cm
 *
 */
public class MyNewsFeedbackFragment extends BaseFragment {

	private MyDetailListView mListView;
	private TextView tv_nodata;
	private ArrayList<MyNewsFeedbackBean> myNewsFeedbackListBean;
	private MyNewsFeedbackAdapter mAdapter;
	private String userId;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View feedbackFragment = inflater.inflate(
				R.layout.fragment_mynews_feedback, container, false);
		mListView = (MyDetailListView) feedbackFragment.findViewById(R.id.fragment_mynews_feedback_listview);
		tv_nodata = (TextView) feedbackFragment.findViewById(R.id.fragment_mynews_feedback_nodata);
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).getMyNewsFeedback(handler, userId);
		}
		return feedbackFragment;
	}

 


	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (bundle != null) {
			myNewsFeedbackListBean = (ArrayList<MyNewsFeedbackBean>) bundle
				.getSerializable(Protocol.DATA);
			if(myNewsFeedbackListBean != null && myNewsFeedbackListBean.size()>0){
				tv_nodata.setVisibility(View.GONE);
				mAdapter = new MyNewsFeedbackAdapter(getActivity(), myNewsFeedbackListBean);
				mListView.setAdapter(mAdapter);
			}
		}
	}


	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

    

}
