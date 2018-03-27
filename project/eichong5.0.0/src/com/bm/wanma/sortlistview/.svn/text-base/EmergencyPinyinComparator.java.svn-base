package com.bm.wanma.sortlistview;

import java.util.Comparator;


public class EmergencyPinyinComparator implements Comparator<EmergencyTelSortModel> {

	public int compare(EmergencyTelSortModel o1, EmergencyTelSortModel o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
