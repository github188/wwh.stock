package com.bm.wanma.ui.activity;

import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.adapter.MyDynamicAdapter;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.PullToRefreshListView;
import com.bm.wanma.view.PullToRefreshListView.OnLoadListener;
import com.bm.wanma.view.PullToRefreshListView.OnRefreshListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author cm 动态 界面
 * 
 */
public class MyDynamicListActivity extends BaseActivity implements
		OnClickListener, OnRefreshListener, OnLoadListener {

	private ImageButton ib_back;
	private PullToRefreshListView mListView;
	private MyDynamicAdapter mAdapter;
	private ArrayList<MyDynamicListBean> dynamicListBeans, allDynamicListBeans;
	private MyDynamicListBean itemBean;
	private final String pageNum = "20";
	private int currentPage;
	private boolean isRefresh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_list);
		ib_back = (ImageButton) findViewById(R.id.activity_dynamic_back);
		ib_back.setOnClickListener(this);
		mListView = (PullToRefreshListView) findViewById(R.id.activity_dynamic_listview);
		mListView.setOnRefreshListener(this);
		mListView.setOnLoadListener(this);
		mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		allDynamicListBeans = new ArrayList<MyDynamicListBean>();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_dynamic_back:
			finish();
			break;

		default:
			break;
		}

	}

	@Override
	protected void getData() {
		// 默认加载第一页数据
		currentPage = 1;
		isRefresh = true;
		if (isNetConnection()) {
			GetDataPost.getInstance(this).getMyDynamicList(handler,
					String.valueOf(currentPage), pageNum);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (bundle != null) {
			dynamicListBeans = (ArrayList<MyDynamicListBean>) bundle
					.getSerializable(Protocol.DATA);
			if (isRefresh) {
				allDynamicListBeans.clear();
				allDynamicListBeans.addAll(dynamicListBeans);
				mListView.onRefreshComplete();
			} else {
				allDynamicListBeans.addAll(dynamicListBeans);
				mListView.onLoadComplete();
				if(allDynamicListBeans.size()==0){
					showToast("暂无数据");
				}
			}
			if (mAdapter == null) {
				mAdapter = new MyDynamicAdapter(MyDynamicListActivity.this,
						allDynamicListBeans);
				mListView.setAdapter(mAdapter);
				mListView.setOnItemClickListener(new OnItemClickListener() {
					// 单击进入详情
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (position <= allDynamicListBeans.size()) {
							itemBean = allDynamicListBeans.get(position - 1);
							if(!Tools.isEmptyString(itemBean.getAdUrl())){
							Intent in = new Intent();
							in.putExtra("releaseId", itemBean);
							// in.putExtra("releaseId",
							// itemBean.getPk_release());//新闻id
							in.setClass(MyDynamicListActivity.this,
									MyDynamicDetailActivity.class);
							startActivity(in);
							}
						}

					}
				});
			}
			mListView.setResultSize(dynamicListBeans.size());
			mAdapter.notifyDataSetChanged();

		}

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));
//		finish();
		mListView.onLoadComplete();
		mListView.onRefreshComplete();
		
	}

	@Override
	public void onLoad() {
		isRefresh = false;
		// 获取下一页数据
		currentPage++;
//		if (isNetConnection()) {
			GetDataPost.getInstance(this).getMyDynamicList(handler,
					String.valueOf(currentPage), pageNum);
//		}

	}

	@Override
	public void onRefresh() {
		isRefresh = true;
		currentPage = 1;
//		if (isNetConnection()) {
			GetDataPost.getInstance(this).getMyDynamicList(handler,
					String.valueOf(currentPage), pageNum);
//		}
	}

}
