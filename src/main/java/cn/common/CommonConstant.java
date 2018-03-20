package cn.common;

public class CommonConstant {	
	/**文件状态*/
	public static enum fileState {deleted, normal};	
	/**为根目录*/
	public static final String IS_ROOT_FOLDER = "1";
	/**非根目录*/
	public static final String NOT_ROOT_FOLDER = "0";			
	/**日期格式*/
	public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";	
	/**每天执行任务的开始时间*/
	public static final int C_SCHEDULE_HOUR = 23;
	/**每天执行任务的结束时间*/
	public static final int C_SCHEDULE_HOUR2 = 24;
	/**超过TIME天的回收信息会被清除*/
	public static final int TIME = 7;
}
