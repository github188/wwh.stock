package com.bm.wanma.ui.fragment;

import com.bm.wanma.R;
import com.bm.wanma.ui.activity.ApplyBuildPileActivity;
import com.bm.wanma.ui.activity.CarRepairActivity;
import com.bm.wanma.ui.activity.BackEmergencyCallActivity;
import com.bm.wanma.ui.activity.EmergencyCallSortActivity;
import com.bm.wanma.ui.activity.InstructionActivity;
import com.bm.wanma.ui.activity.SharePileActivity;
import com.bm.wanma.ui.scan.CaptureActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 小助手界面
 * @author cm
 *
 */
public class MyHelperFragment extends BaseFragment implements OnClickListener{
	private RelativeLayout rl_share,rl_apply,rl_carpair;
	private RelativeLayout rl_emergency,rl_instruction;
	private RelativeLayout rl_wash_car,rl_open_door;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myHelperFragment = inflater.inflate(
				R.layout.fragment_myhelper, container, false);
		initView(myHelperFragment);
		
		return myHelperFragment;
	}

   private void initView(View mainView){
	   rl_share = (RelativeLayout) mainView.findViewById(R.id.fragment_myhelper_share);
	   rl_share.setOnClickListener(this);
	   rl_apply = (RelativeLayout) mainView.findViewById(R.id.fragment_myhelper_apply);
	   rl_apply.setOnClickListener(this);
	   rl_carpair = (RelativeLayout) mainView.findViewById(R.id.fragment_myhelper_carpair);
	   rl_carpair.setOnClickListener(this);
	   rl_emergency = (RelativeLayout) mainView.findViewById(R.id.fragment_myhelper_emergency);
	   rl_emergency.setOnClickListener(this);
	   rl_instruction = (RelativeLayout) mainView.findViewById(R.id.fragment_myhelper_instruction);
	   rl_instruction.setOnClickListener(this);
	   
	   rl_wash_car = (RelativeLayout) mainView.findViewById(R.id.fragment_myhelper_car);
	   rl_wash_car.setOnClickListener(this);
	   rl_open_door = (RelativeLayout) mainView.findViewById(R.id.fragment_myhelper_door);
	   rl_open_door.setOnClickListener(this);
	   
   }


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_myhelper_share:
			//分享电桩
			Intent shareIn = new Intent();
			shareIn.setClass(getActivity(), SharePileActivity.class);
			getActivity().startActivity(shareIn);
			
			break;
		case R.id.fragment_myhelper_apply:
			//申请建电桩
			Intent ICIn = new Intent();
			ICIn.setClass(getActivity(), ApplyBuildPileActivity.class);
			getActivity().startActivity(ICIn);
			
			break;
		case R.id.fragment_myhelper_carpair:
			//车辆维修
			Intent carRIn = new Intent();
			carRIn.setClass(getActivity(), CarRepairActivity.class);
			getActivity().startActivity(carRIn);
			
			break;
		case R.id.fragment_myhelper_emergency:
			//急救电话
			Intent emIn = new Intent();
			//emIn.setClass(getActivity(), BackEmergencyCallActivity.class);
			emIn.setClass(getActivity(), EmergencyCallSortActivity.class);
			getActivity().startActivity(emIn);
			
			break;
		case R.id.fragment_myhelper_instruction:
			//操作说明
			Intent insIn = new Intent();
			insIn.setClass(getActivity(),InstructionActivity.class);
			getActivity().startActivity(insIn);
			
			break;

		case R.id.fragment_myhelper_car:
			//扫描洗车
			Intent carIn = new Intent();
			carIn.setClass(getActivity(),CaptureActivity.class);
			getActivity().startActivity(carIn);
			
			break;
		case R.id.fragment_myhelper_door:
			//扫码开门
			Intent doorIn = new Intent();
			doorIn.setClass(getActivity(),CaptureActivity.class);
			getActivity().startActivity(doorIn);
			
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub

	}



}
