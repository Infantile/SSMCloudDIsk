package cn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.common.CommonConstant;

public class DateUtils { 
	
	/**
	 * 获取当前系统时间(格式：yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String getSystemTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		return df.format(new Date());
	}
}
