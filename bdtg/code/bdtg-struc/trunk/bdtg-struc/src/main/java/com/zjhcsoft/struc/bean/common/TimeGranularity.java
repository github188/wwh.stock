package com.zjhcsoft.struc.bean.common;

public enum TimeGranularity {
	DAY,HOUR;
	
	public static void main(String[] args) {
		TimeGranularity timeGranularity = TimeGranularity.DAY;
		System.out.println(timeGranularity.equals(TimeGranularity.DAY));
	}
}
