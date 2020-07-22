package com.ira.utils;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

	public static Date convertDate(java.util.Date date) {
		return new Date(date.getTime());

	}
	
	public static Date getDate(String date) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return   (Date) sdf.parse(date);
	}
	
	public static Date convertDate(String mydate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date1 = sdf.parse(mydate);

		return new Date(date1.getTime());

	}

	public static String GetDateStr(java.util.Date date) {
		return date.toString();

	}

}
