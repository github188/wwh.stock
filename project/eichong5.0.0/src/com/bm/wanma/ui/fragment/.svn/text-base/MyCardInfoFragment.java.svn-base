package com.bm.wanma.ui.fragment;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.dialog.TipClearHistoryDialog;
import com.bm.wanma.dialog.TipImproveInfoDialog;
import com.bm.wanma.entity.MyCardInfo;
import com.bm.wanma.entity.UserInfoBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.activity.ApplyCardActivity;
import com.bm.wanma.ui.activity.MyIcCardActivity;
import com.bm.wanma.ui.activity.MyUserInfoActivity;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author cm
 *  我的充点卡--卡信息
 */
public class MyCardInfoFragment extends BaseFragment implements OnClickListener{

	private RelativeLayout rl_tcardInfo,rl_cardInfo;
	private TextView tv_tcard_num,tv_card_num,tv_tcard_status,tv_card_status;
	private TextView tv_tcard_guashi,tv_card_guashi,tv_common_apply,tv_nodata,tv_tcard_mark;
	private String userId,tcardoutNum,cardoutNum,cardoutNum_ucPayMode,tcardoutNum_ucPayMode;
	private ArrayList<MyCardInfo> myCardInfos;
	private boolean isApplyCard/*,isUserInfoComplete*/;
	private TipClearHistoryDialog mWarnDialog;
	private TipImproveInfoDialog mTipImproveInfoDialog;
	private UserInfoBean mUserInfoBean;
	private String isHasApplyCard;
	public boolean isPullData;
	public boolean isStatus = true;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
		//setRetainInstance(true); 设置后可以在横竖屏切换时不被重新创建
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View cardInfoFragment = inflater.inflate(
				R.layout.fragment_my_cardinfo, container, false);
		initView(cardInfoFragment);
		initData();
		return cardInfoFragment;
	}
	
	private void initView(View v){
		rl_tcardInfo = (RelativeLayout) v.findViewById(R.id.fragment_my_cardinfo_rl_tcard);
		rl_cardInfo = (RelativeLayout) v.findViewById(R.id.fragment_my_cardinfo_rl_card);
		tv_nodata = (TextView) v.findViewById(R.id.fragment_my_cardinfo_nodata);
		tv_tcard_num = (TextView) v.findViewById(R.id.tv_tcard_num);
		tv_tcard_status = (TextView) v.findViewById(R.id.tv_tcard_status);
		tv_tcard_guashi = (TextView) v.findViewById(R.id.tv_tcard_guashi);
		tv_tcard_mark = (TextView) v.findViewById(R.id.tv_tcard_markinfo);
		tv_card_num = (TextView) v.findViewById(R.id.tv_card_num);
		tv_card_status = (TextView) v.findViewById(R.id.tv_card_status);
		tv_card_guashi = (TextView) v.findViewById(R.id.tv_card_guashi);
		tv_common_apply = (TextView) v.findViewById(R.id.tv_card_apply);
	}
	//请求数据
	private void initData(){
		if(isNetConnection()){
			showPD("正在获取信息");
			GetDataPost.getInstance(getActivity()).getMyCardListInfo(handler, userId);
		}
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Activity.RESULT_FIRST_USER && resultCode == -1){
				getActivity().finish();
		}
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_card_apply:
			//申请电卡
			if(myCardInfos != null && myCardInfos.size()>0){
				if (isNetConnection()) {					
					showPD("获取数据中...");
					GetDataPost.getInstance(getActivity()).getUserInfo(handler, userId);
				}else {					
					showToast("网络不稳，稍后再试");
				}
			}else {
				handleApplyCardEvent();
			}
			break;
		case R.id.tv_tcard_guashi: 
			handleTcardGuashiEvent();
			break;
		case R.id.tv_card_guashi:
			//普通卡挂失或申请
			handlePcardGuashiEvent();
			break;
		default:
			break;
		}
		
	}
	//处理普通卡挂失 申请
	private void handlePcardGuashiEvent(){
		if(isApplyCard){
			//申请
			/*if("1".equals(isHasApplyCard)){
				showToast("申请中，不可重复申请");
			}else {
				Intent applyIn = new Intent();
				applyIn.setClass(getActivity(), ApplyCardActivity.class);
				startActivityForResult(applyIn, Activity.RESULT_FIRST_USER);
			}*/
			if(isNetConnection()){
				showPD("获取数据中...");
				GetDataPost.getInstance(getActivity()).getUserInfo(handler, userId);
			}else {
				showToast("网络不稳，稍后再试");
			}
			
		}else {
			//挂失
			if(mWarnDialog == null){
				mWarnDialog = new TipClearHistoryDialog(getActivity(), "挂失后，此卡不可再用！");
			}
			mWarnDialog.setCancelable(false);
			mWarnDialog.setOnNegativeListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mWarnDialog.dismiss();
				}
			});
			mWarnDialog.setOnPositiveListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mWarnDialog.dismiss();
					//普通卡挂失
					if(isNetConnection()){
						showPD("正在提交挂失请求");
						GetDataPost.getInstance(getActivity()).reportLossCard(handler, userId, cardoutNum,cardoutNum_ucPayMode);
					}else {
						showToast("网络不稳，请稍后再试");
					}
				}
			});
			if(!mWarnDialog.isShowing()){
				mWarnDialog.show();
			}
		}
	}
	
	//处理特殊卡挂失
	private void handleTcardGuashiEvent(){
		if(mWarnDialog == null){
			mWarnDialog = new TipClearHistoryDialog(getActivity(), "挂失后，此卡不可再用！");
		}
		mWarnDialog.setCancelable(false);
		mWarnDialog.setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mWarnDialog.dismiss();
			}
		});
		mWarnDialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mWarnDialog.dismiss();
				//特殊卡挂失
				if(isNetConnection()){
					GetDataPost.getInstance(getActivity()).reportLossCard(handler, userId, tcardoutNum,tcardoutNum_ucPayMode);
				}else {
					showToast("网络不稳，请稍后再试");
				}
			}
		});
		if(!mWarnDialog.isShowing()){
			mWarnDialog.show();
		}
	}
	
	//处理申请充电卡事件
	private void handleApplyCardEvent(){
//		if(isUserInfoComplete){
			if("1".equals(isHasApplyCard)){
				showToast("申请中，不可重复申请");
			}else {
				Intent applyIn = new Intent();
				applyIn.setClass(getActivity(), ApplyCardActivity.class);
				startActivityForResult(applyIn, Activity.RESULT_FIRST_USER);
			}
			
//		}else{
//			mTipImproveInfoDialog = new TipImproveInfoDialog(getActivity());
//			mTipImproveInfoDialog.setCancelable(false);
//			mTipImproveInfoDialog.setOnNegativeListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					mTipImproveInfoDialog.dismiss();
//				}
//			});
//			mTipImproveInfoDialog.setOnPositiveListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					mTipImproveInfoDialog.dismiss();
//					//去完善个人资料
//					Intent in = new Intent();
//					in.setClass(getActivity(), MyUserInfoActivity.class);
//					in.putExtra("userInfo", mUserInfoBean);
//					startActivity(in);
//					getActivity().finish();
//				}
//			});
//			if(!mTipImproveInfoDialog.isShowing()){
//				mTipImproveInfoDialog.show();
//			}
//		}
		
	}
	
	@SuppressLint("NewApi")
	private void handleSingleEvent(MyCardInfo event){
		//10普通充电卡，11特殊卡·储值充电卡，12特殊卡·信用充电卡
		if("10".equals(event.getPayMode())){
			rl_cardInfo.setVisibility(View.VISIBLE);
			cardoutNum = event.getOutNum();
			cardoutNum_ucPayMode =event.getPayMode();
			tv_card_num.setText(""+cardoutNum);
			tv_card_guashi.setOnClickListener(this);
			String status = event.getStatus();
			if("0".equals(status)){
				tv_card_status.setText("正常");
				tv_card_guashi.setText("挂失");
				isApplyCard = false ;
				tv_card_guashi.setBackground(getResources().getDrawable(R.drawable.common_orange_guashi_circle));
			}else{
				tv_card_status.setText("挂失");
				if (isStatus) {
//					tv_card_guashi.setText("申请电卡");
//					tv_card_guashi.setBackground(getResources().getDrawable(R.drawable.common_orange_guashi_rectangle));
					isApplyCard = true;
					tv_common_apply.setVisibility(View.VISIBLE);
					tv_common_apply.setOnClickListener(this);
//				}else {
				}
				tv_card_guashi.setVisibility(View.GONE);
			}
		}else if("11".equals(event.getPayMode())){
			tv_common_apply.setVisibility(View.VISIBLE);
			tv_common_apply.setOnClickListener(this);
			rl_tcardInfo.setVisibility(View.VISIBLE);
			tv_tcard_mark.setText("(储值电卡)");
			tcardoutNum = event.getOutNum();
			tcardoutNum_ucPayMode =event.getPayMode();
			tv_tcard_num.setText(""+tcardoutNum);
			String status = event.getStatus();
			if("0".equals(status)){
				tv_tcard_status.setText("正常");
				tv_tcard_guashi.setText("挂失");
				tv_tcard_guashi.setOnClickListener(this);
			}else {
				tv_tcard_status.setText("挂失");
				tv_tcard_guashi.setVisibility(View.GONE);
			}
		}else if("12".equals(event.getPayMode())){
			rl_tcardInfo.setVisibility(View.VISIBLE);
			tv_common_apply.setVisibility(View.VISIBLE);
			tv_common_apply.setOnClickListener(this);
			tv_tcard_mark.setText("(信用电卡)");
			tcardoutNum = event.getOutNum();
			tcardoutNum_ucPayMode =event.getPayMode();
			tv_tcard_num.setText(""+tcardoutNum);
			String status = event.getStatus();
			if("0".equals(status)){
				tv_tcard_status.setText("正常");
				tv_tcard_guashi.setText("挂失");
				tv_tcard_guashi.setOnClickListener(this);
			}else {
				tv_tcard_status.setText("挂失");
				tv_tcard_guashi.setVisibility(View.GONE);
			}
		}
	}
	
	//处理包含两种卡
	@SuppressLint("NewApi")
	private void handleCardEvent(ArrayList<MyCardInfo> list){
		MyCardInfo card1 = list.get(0);
		MyCardInfo card2 = list.get(1);
	
		if("10".equals(card1.getPayMode())){
			rl_cardInfo.setVisibility(View.VISIBLE);
			tv_card_guashi.setOnClickListener(this);
			cardoutNum = card1.getOutNum();
			cardoutNum_ucPayMode =card1.getPayMode();
			tv_card_num.setText(""+cardoutNum);
			tv_card_guashi.setOnClickListener(this);
			String status = card1.getStatus();
			if("0".equals(status)){
				tv_card_status.setText("正常");
				tv_card_guashi.setText("挂失");
				isApplyCard = false ;
				tv_card_guashi.setBackground(getResources().getDrawable(R.drawable.common_orange_guashi_circle));
				
			}else{
				tv_card_status.setText("挂失");
				if (isStatus) {
//					tv_card_guashi.setText("申请电卡");
//					tv_card_guashi.setBackground(getResources().getDrawable(R.drawable.common_orange_guashi_rectangle));
					isApplyCard = true;
					tv_common_apply.setVisibility(View.VISIBLE);
					tv_common_apply.setOnClickListener(this);
//				}else {
				}
				tv_card_guashi.setVisibility(View.GONE);
			}
		}else if("11".equals(card1.getPayMode())){
			rl_tcardInfo.setVisibility(View.VISIBLE);
			tv_tcard_guashi.setOnClickListener(this);
			tv_tcard_mark.setText("(储值电卡)");
			tcardoutNum = card1.getOutNum();
			tcardoutNum_ucPayMode =card1.getPayMode();
			tv_tcard_num.setText(""+tcardoutNum);
			String status = card1.getStatus();
			if("0".equals(status)){
				tv_tcard_status.setText("正常");
				tv_tcard_guashi.setText("挂失");
			}else {
				tv_tcard_status.setText("挂失");
				tv_tcard_guashi.setVisibility(View.GONE);
			}
		}else if("12".equals(card1.getPayMode())){
			rl_tcardInfo.setVisibility(View.VISIBLE);
			tv_tcard_mark.setText("（信用电卡）");
			tcardoutNum = card1.getOutNum();
			tcardoutNum_ucPayMode =card1.getPayMode();
			tv_tcard_num.setText(""+tcardoutNum);
			String status = card1.getStatus();
			if("0".equals(status)){
				tv_tcard_status.setText("正常");
				tv_tcard_guashi.setText("挂失");
				tv_tcard_guashi.setOnClickListener(this);
			}else {
				tv_tcard_status.setText("挂失");
				tv_tcard_guashi.setVisibility(View.GONE);
			}
		}
		
		if("10".equals(card2.getPayMode())){
			rl_cardInfo.setVisibility(View.VISIBLE);
			cardoutNum = card2.getOutNum();
			cardoutNum_ucPayMode =card2.getPayMode();
			tv_card_num.setText(""+cardoutNum);
			tv_card_guashi.setOnClickListener(this);
			String status = card2.getStatus();
			if("0".equals(status)){
				tv_card_status.setText("正常");
				tv_card_guashi.setText("挂失");
				isApplyCard = false ;
				tv_card_guashi.setBackground(getResources().getDrawable(R.drawable.common_orange_guashi_circle));
			}else{
				tv_card_status.setText("挂失");
				if (isStatus) {
//					tv_card_guashi.setText("申请电卡");
//					tv_card_guashi.setBackground(getResources().getDrawable(R.drawable.common_orange_guashi_rectangle));
					isApplyCard = true;
					tv_common_apply.setVisibility(View.VISIBLE);
					tv_common_apply.setOnClickListener(this);
//				}else {
				}
				tv_card_guashi.setVisibility(View.GONE);
			}
		}else if("11".equals(card2.getPayMode())){
			rl_tcardInfo.setVisibility(View.VISIBLE);
			tv_tcard_mark.setText("(储值电卡)");
			tcardoutNum = card2.getOutNum();
			tcardoutNum_ucPayMode =card2.getPayMode();
			tv_tcard_num.setText(""+tcardoutNum);
			
			String status = card2.getStatus();
			if("0".equals(status)){
				tv_tcard_status.setText("正常");
				tv_tcard_guashi.setText("挂失");
				tv_tcard_guashi.setOnClickListener(this);
			}else {
				tv_tcard_status.setText("挂失");
				tv_tcard_guashi.setVisibility(View.GONE);
			}
		}else if("12".equals(card2.getPayMode())){
			rl_tcardInfo.setVisibility(View.VISIBLE);
			tv_tcard_mark.setText("(信用电卡)");
			tcardoutNum = card2.getOutNum();
			tcardoutNum_ucPayMode =card2.getPayMode();
			tv_tcard_num.setText(""+tcardoutNum);
			String status = card2.getStatus();
			if("0".equals(status)){
				tv_tcard_status.setText("正常");
				tv_tcard_guashi.setText("挂失");
				tv_tcard_guashi.setOnClickListener(this);
			}else {
				tv_tcard_status.setText("挂失");
				tv_tcard_guashi.setVisibility(View.GONE);
			}
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		if (Tools.judgeString(sign, Protocol.GET_MY_CARDLIST)) {
			if (myCardInfos != null&&myCardInfos.size()>0) {
				myCardInfos.clear();
			}
			//获取我的充点卡列表
			myCardInfos = (ArrayList<MyCardInfo>) bundle
					.getSerializable(Protocol.DATA);
			if(myCardInfos != null && myCardInfos.size()>0){
				for (MyCardInfo cardInfo : myCardInfos) {
					if (cardInfo.getStatus().equals("0")&&cardInfo.getPayMode().equals("0")) {
						isStatus = false;
					}
				}
				int number = myCardInfos.size();
				switch (number) {
				case 1:
					MyCardInfo info = myCardInfos.get(0);
					handleSingleEvent(info);
					break;
				case 2:
					handleCardEvent(myCardInfos);
					break;
				default:
					break;
				}
				
			}else {
				GetDataPost.getInstance(getActivity()).getUserInfo(handler, userId);
			}
		} else if(Tools.judgeString(sign,Protocol.REPORTLOSS_CARD)){
			GetDataPost.getInstance(getActivity()).getMyCardListInfo(handler, userId);
			showToast("挂失成功");
			isPullData = true ;
		}else if(Tools.judgeString(sign,Protocol.GET_USER_INFO)){
			//获取个人资料
			mUserInfoBean = (UserInfoBean) bundle.getSerializable(Protocol.DATA);
//			isUserInfoComplete = checkUserInfo(mUserInfoBean);
			isHasApplyCard = mUserInfoBean.getApplyCard();
			
			if(myCardInfos != null && myCardInfos.size()>0){
				handleApplyCardEvent();
			}else {	
				tv_nodata.setVisibility(View.VISIBLE);
				if("1".equals(isHasApplyCard)){
					tv_nodata.setText("亲，你有一张电卡在申领中哦~");
				}else {
					tv_common_apply.setVisibility(View.VISIBLE);
					tv_common_apply.setOnClickListener(this);
				}
			}
			
		}
			
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
	}

//	private boolean checkUserInfo(UserInfoBean bean){
//		boolean isUserInfoComplete = false;
//		if(Tools.isEmptyString(bean.getUserRealName()) || Tools.isEmptyString(bean.getAddress())
//				|| Tools.isEmptyString(bean.getUserBrithy()) || Tools.isEmptyString(bean.getUserSex()) ||
//				Tools.isEmptyString(bean.getPlateNum()) || Tools.isEmptyString(bean.getUserCarType())){
//			isUserInfoComplete = false ;
//		}else {
//			isUserInfoComplete = true ;
//		}
//		return isUserInfoComplete;
//	} 
	
}
