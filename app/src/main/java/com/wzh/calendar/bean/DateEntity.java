package com.wzh.calendar.bean;

public class DateEntity {
	public long million ; //时间戳
	public String weekName ;  //周几
	public int weekNum ;  //一周中第几天，非中式
	public String date ; //日期
	public boolean isToday ;  //是否今天
	public String  day ;  //天
	public String luna ;  //阴历

	@Override
	public String toString() {
		return "DateEntity{" +
				"million=" + million +
				", weekName='" + weekName + '\'' +
				", weekNum=" + weekNum +
				", date='" + date + '\'' +
				", isToday=" + isToday +
				", day='" + day + '\'' +
				", luna='" + luna + '\'' +
				'}';
	}
}
