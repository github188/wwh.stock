package com.bm.wanma.utils;

import java.util.Comparator;

import com.bm.wanma.entity.PileHead;

public class ComparatorGunHead implements Comparator {
	
	private int flag = 0;

	public ComparatorGunHead() {
	}

	@Override
	public int compare(Object lhs, Object rhs) {
		
		PileHead bean1 = (PileHead) lhs;
		PileHead bean2 = (PileHead) rhs;
		String state1 = bean1.getPileHeadState();
		String state2 = bean2.getPileHeadState();
		flag = Integer.valueOf(state1).compareTo(
				Integer.valueOf(state2));
		return flag;
	}

}
