package com.zjhcsoft.struc.test;

	import java.util.Date;
import java.util.GregorianCalendar;
public class longtest {





		public static void main(String[] args) {
			  long sd=1431584375821L;  
		        Date dat=new Date(sd);  
		        GregorianCalendar gc = new GregorianCalendar();   
		        gc.setTime(dat);  
		        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		        String sb=format.format(gc.getTime());  
		        System.out.print(sb);
		        
				Date date =new Date();
				String sj=Long.toString(date.getTime());
		        System.out.println( sj);  
		}

	}

