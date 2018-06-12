package com.wl.runzekeji.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Strings;

public class DateUtil {

	public static String formatDateOut(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	public static Date formatDateIn(String fDate,String format) {
		Date result=null;
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(!Strings.isNullOrEmpty(fDate)){
			try {
				result=sdf.parse(fDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}
	
	public static java.sql.Date formatDateInSql(String fDate,String format) throws Exception{
		Date resultTemp=null;
		java.sql.Date result=null;
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(!Strings.isNullOrEmpty(fDate)){
			resultTemp=sdf.parse(fDate);
			result=new java.sql.Date(resultTemp.getTime());
		}
		return result;
	}
}
