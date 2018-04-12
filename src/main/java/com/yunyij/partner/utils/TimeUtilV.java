package com.yunyij.partner.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtilV {
	
	public static Date getYesterDay() {
		Calendar todayStart = Calendar.getInstance();  
		todayStart.add(Calendar.DATE,-1); 
		return todayStart.getTime();
	}
	
	public static Calendar getCalendar(Date date) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		return todayStart;
	}
	
	public static Date getDateStartTime(Date date) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);  
		todayStart.set(Calendar.MINUTE, 0);  
		todayStart.set(Calendar.SECOND, 0);  
		todayStart.set(Calendar.MILLISECOND, 0);  
		return todayStart.getTime();  
	}
	
	public static Date getDateEndTime(Date date) {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
		todayEnd.set(Calendar.MINUTE, 59);  
		todayEnd.set(Calendar.SECOND, 59);  
		todayEnd.set(Calendar.MILLISECOND, 999);  
		return todayEnd.getTime();  
	}

	public static Date getTodayStartTime() {  
		Calendar todayStart = Calendar.getInstance();  
		todayStart.set(Calendar.HOUR_OF_DAY, 0);  
		todayStart.set(Calendar.MINUTE, 0);  
		todayStart.set(Calendar.SECOND, 0);  
		todayStart.set(Calendar.MILLISECOND, 0);  
		return todayStart.getTime();  
	}  

	public static Date getTodyEndTime() {  
		Calendar todayEnd = Calendar.getInstance();  
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
		todayEnd.set(Calendar.MINUTE, 59);  
		todayEnd.set(Calendar.SECOND, 59);  
		todayEnd.set(Calendar.MILLISECOND, 999);  
		return todayEnd.getTime();  
	} 
	
	//将时间转换为时间戳
	public static Long dateToStamp(String s) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		Date date = simpleDateFormat.parse(s);
		Long ts = date.getTime();
		return ts;
	}
	
	//当前时间戳
	public static String dateToString() throws ParseException{
	SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
	String date = df.format(new Date());
	return date;
	}
}
