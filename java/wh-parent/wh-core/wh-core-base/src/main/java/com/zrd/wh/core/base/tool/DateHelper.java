package com.zrd.wh.core.base.tool;

import com.sun.istack.internal.NotNull;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class DateHelper {

	/**
	 * 中文格式--年月
	 */
	private static final String ChinaYearMonthPatternString = "yyyy年MM月";

	/**
	 * 中文格式--年月日
	 */
	private static final String ChinaDatePatternString = "yyyy年MM月dd日";

	/**
	 * 中文格式--年月日 时分秒
	 */
	private static final String ChinaDateTimePatternString = "yyyy年MM月dd日 HH时mm分ss秒";

	/**
	 * 横线格式--年月
	 */
	private static final String StandardYearMonthPatternString = "yyyy-MM";

	/**
	 * 横线格式--年月日
	 */
	private static final String StandardDatePatternString = "yyyy-MM-dd";

	/**
	 * 横线格式--年月日 冒号格式--时分秒
	 */
	private static final String StandardDateTimePatternString = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 无分隔符格式--年月
	 */
	private static final String CompactYearMonthPatternString = "yyyyMM";

	/**
	 * 无分隔符格式--年月日
	 */
	private static final String CompactDatePatternString = "yyyyMMdd";

	/**
	 * 无分隔符格式--年月日时分秒
	 */
	private static final String CompactDateTimePatternString = "yyyyMMddHHmmss";

	/**
	 * 斜线格式日期--年月
	 */
	private static final String ObliqueLineYearMonthPatternString = "yyyy/MM";

	/**
	 * 斜线格式日期--年月日
	 */
	private static final String ObliqueLineDatePatternString = "yyyy/MM/dd";

	/**
	 * 斜线格式日期--年月日 时分秒
	 */
	private static final String ObliqueLineDateTimePatternString = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 标准年格式
	 */
	private static final String YearPatternString = "yyyy";


	private DateHelper() {
	}

	/**
	 * 获取当前系统时间格式化后的日期
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @see DatePattern
	 * @return 格式化后的日期数据
	 */
	public static Date getCurrentDate(@NotNull DatePattern pattern){
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		try {
			return sdf.parse(sdf.format(new Date()));
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前系统时间格式化后的日期字符串
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @see DatePattern
	 * @return 格式化后的字符串
	 */
	public static String getCurrentDateString(@NotNull DatePattern pattern){
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		return sdf.format(new Date());
	}

	/**
	 * 格式化输入的日期转换为字符串
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param date 用户自定义的日期
	 * @see DatePattern
	 * @return 格式化后的字符串
	 */
	public static String getDateStringByInputDate(@NotNull DatePattern pattern,@NotNull Date date){
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		return sdf.format(date);
	}

	/**
	 * 格式化输入的日期转换为新日期
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param date 用户自定义的日期
	 * @see DatePattern
	 * @return 格式化后的字符串
	 */
	public static Date getDateByInputDate(@NotNull DatePattern pattern,@NotNull Date date){
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		try {
			return sdf.parse(sdf.format(date));
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化输入的日期转换为新日期
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param dateString 用户自定义的日期字符串，该值的格式必须和 {@param pattern} 参数格式匹配
	 * @see DatePattern
	 * @return 格式化后的日期
	 */
	public static Date getDateByInputDateString(DatePattern pattern, @NotNull String dateString){
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		try {
			return sdf.parse(sdf.format(dateString));
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期格式
	 */
	public enum DatePattern {
		/**
		 * yyyy年MM月
		 */
		ChinaYearMonthPattern(ChinaYearMonthPatternString),
		/**
		 * yyyy年MM月dd日
		 */
		ChinaDatePattern(ChinaDatePatternString),
		/**
		 * yyyy年MM月dd日 HH时mm分ss秒
		 */
		ChinaDateTimePattern(ChinaDateTimePatternString),
		/**
		 * yyyy-MM
		 */
		StandardYearMonthPattern(StandardYearMonthPatternString),
		/**
		 * yyyy-MM-dd
		 */
		StandardDatePattern(StandardDatePatternString),
		/**
		 * yyyy-MM-dd HH:mm:ss
		 */
		StandardDateTimePattern(StandardDateTimePatternString),
		/**
		 * yyyyMM
		 */
		CompactYearMonthPattern(CompactYearMonthPatternString),
		/**
		 * yyyyMMdd
		 */
		CompactDatePattern(CompactDatePatternString),
		/**
		 * yyyyMMddHHmmss
		 */
		CompactDateTimePattern(CompactDateTimePatternString),
		/**
		 * yyyy/MM
		 */
		ObliqueLineYearMonthPattern(ObliqueLineYearMonthPatternString),
		/**
		 * yyyy/MM/dd
		 */
		ObliqueLineDatePattern(ObliqueLineDatePatternString),
		/**
		 * yyyy/MM/dd HH:mm:ss
		 */
		ObliqueLineDateTimePattern(ObliqueLineDateTimePatternString),
		/**
		 * yyyy
		 */
		YearPattern(YearPatternString);

		DatePattern(String pattern){
			this.pattern = pattern;
		}
		private String pattern;

		public String getPattern(){
			return pattern;
		}
	}

	/**
	 * 日期格式化类
	 */
	private enum SimpleDateFormatObject {
		ChinaYearMonth(ChinaYearMonthPatternString),
		ChinaDate(ChinaDatePatternString),
		ChinaDateTime(ChinaDateTimePatternString),
		StandardYearMonth(StandardYearMonthPatternString),
		StandardDate(StandardDatePatternString),
		StandardDateTime(StandardDateTimePatternString),
		CompactYearMonth(CompactYearMonthPatternString),
		CompactDate(CompactDatePatternString),
		CompactDateTime(CompactDateTimePatternString),
		ObliqueLineYearMonth(ObliqueLineYearMonthPatternString),
		ObliqueLineDate(ObliqueLineDatePatternString),
		ObliqueLineDateTime(ObliqueLineDateTimePatternString),
		Year(YearPatternString);

		private static Map<String, Object> dateTransYearMonth = new HashMap<>();
		static {
			dateTransYearMonth.put(ChinaYearMonthPatternString, ChinaDatePatternString);
			dateTransYearMonth.put(StandardYearMonthPatternString, StandardDatePatternString);
			dateTransYearMonth.put(CompactYearMonthPatternString, CompactDatePatternString);
			dateTransYearMonth.put(ObliqueLineYearMonthPatternString, ObliqueLineDatePatternString);
		}
		SimpleDateFormatObject(String pattern){
			this.pattern = pattern;
			this.thisSimpleDateFormat = new SimpleDateFormat(pattern);
		}

		private SimpleDateFormat thisSimpleDateFormat;

		private String pattern;
		
		public SimpleDateFormat getThisSimpleDateFormat() {
			return thisSimpleDateFormat;
		}

		/**
		 * 通过格式名称找对应的处理格式化处理器
		 * @param pattern 格式
		 * @return 格式化
		 */
		public static SimpleDateFormat getSimpleDateFormatByPattern(String pattern){
			SimpleDateFormatObject[] sdfos = SimpleDateFormatObject.class.getEnumConstants();
			SimpleDateFormat sdf = null;
			for (SimpleDateFormatObject enumConstant : sdfos) {
				if(enumConstant.pattern.equals(pattern)){
					sdf =  enumConstant.thisSimpleDateFormat;
				}
			}
			return sdf;
		}

		/**
		 * 通过格式名称找对应的处理格式化处理器
		 * @param pattern 格式
		 * @return 格式化
		 */
		public static SimpleDateFormat getSimpleDateFormatByYearMonthPattern(String pattern){
			SimpleDateFormatObject[] sdfos = SimpleDateFormatObject.class.getEnumConstants();
			SimpleDateFormat sdf = null;
			for (SimpleDateFormatObject enumConstant : sdfos) {
				if(enumConstant.pattern.equals(dateTransYearMonth.get(pattern))){
					sdf =  enumConstant.thisSimpleDateFormat;
				}
			}
			return sdf;
		}
	}

	/**
	 * 计算两个日期之间的间隔
	 * 
	 * @param beginDate
	 *            - 起始时间
	 * @param endDate
	 *            - 结束时间
	 * @return 日期间的间隔
	 */
	public static int getTimeDistance(@NotNull Date beginDate, @NotNull Date endDate) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		long beginTime = beginCalendar.getTime().getTime();
		long endTime = endCalendar.getTime().getTime();
		int betweenDays = (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));// 先算出两时间的毫秒数之差大于一天的天数

		endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);// 使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
		endCalendar.add(Calendar.DAY_OF_MONTH, -1);// 再使endCalendar减去1天
		if (beginCalendar.get(Calendar.DAY_OF_MONTH) == endCalendar.get(Calendar.DAY_OF_MONTH))// 比较两日期的DAY_OF_MONTH是否相等
			return betweenDays + 1; // 相等说明确实跨天了
		else
			return betweenDays + 0; // 不相等说明确实未跨天
	}

	/**
	 * 获取当月时间范围
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @return
	 */
	public static String getMonth(DatePattern pattern) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat format = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String first = format.format(c.getTime());
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return first + " and " + last;
	}

	/**
	 * 获取去年当月时间范围
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @return
	 */
	public static String getMonthLastYear(DatePattern pattern) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat format = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Calendar lastYearStart = Calendar.getInstance();
		lastYearStart.add(Calendar.YEAR, -1);
		lastYearStart.add(Calendar.MONTH, 0);
		lastYearStart.set(Calendar.DAY_OF_MONTH, 1);
		String firstDayLastYear = format.format(lastYearStart.getTime());
		Calendar lastYearEnd = Calendar.getInstance();
		lastYearEnd.add(Calendar.YEAR, -1);
		lastYearEnd.add(Calendar.MONTH, 0);
		lastYearEnd.set(Calendar.DAY_OF_MONTH, lastYearEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
		String endDayLastYear = format.format(lastYearEnd.getTime());
		return firstDayLastYear + " and " + endDayLastYear;
	}

	/**
	 * 获取某年某月时间范围
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardYearMonthPattern}
	 * @param date - 年月 格式必须为 {@param pattern} 的格式
	 * @return
	 */
	public static String getMonthAround(DatePattern pattern, @NotNull String date) {
		pattern = pattern == null ? DatePattern.StandardYearMonthPattern : pattern;
		DatePattern pattern2 = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat format = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		SimpleDateFormat format2 = SimpleDateFormatObject.getSimpleDateFormatByYearMonthPattern(pattern.pattern);
		Date monthDate = null;
		try {
			monthDate = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar lastYearStart = Calendar.getInstance();
		lastYearStart.setTime(monthDate);
		lastYearStart.set(Calendar.DAY_OF_MONTH, 1);
		String firstDayLastYear = format2.format(lastYearStart.getTime());
		Calendar lastYearEnd = Calendar.getInstance();
		lastYearEnd.setTime(monthDate);
		lastYearEnd.set(Calendar.DAY_OF_MONTH, lastYearEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
		String endDayLastYear = format2.format(lastYearEnd.getTime());
		return firstDayLastYear + " and " + endDayLastYear;
	}

	/**
	 * 获取某日去年的时间,如果是闰年二月29号,前一年自动变成28号
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param dateStr 开始日期，格式必须为 {@param pattern} 的格式
	 * @return
	 */
	public static String getDateDayLastYear(DatePattern pattern, @NotNull String dateStr) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat format = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Date date = null;
		String lastDay = null;
		try {
			date = format.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.YEAR, -1);
			cal.add(Calendar.MONTH, 0);
			lastDay = format.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lastDay;
	}

	/**
	 * 计算两个日期之间所包含的天数
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param startDate 开始日期，格式必须为 {@param pattern} 的格式
	 * @param endDate 结束日期，格式必须为 {@param pattern} 的格式
	 * @return
	 */
	public static int differentDays(DatePattern pattern, @NotNull String startDate, @NotNull String endDate) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat format = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Date date2 = null;
		Date date1 = null;
		int rInt = -1;
		try {
			date1 = format.parse(startDate);
			date2 = format.parse(endDate);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			int day1 = cal1.get(Calendar.DAY_OF_YEAR);
			int day2 = cal2.get(Calendar.DAY_OF_YEAR);

			int year1 = cal1.get(Calendar.YEAR);
			int year2 = cal2.get(Calendar.YEAR);
			if (year1 != year2) {
				int timeDistance = 0;
				for (int i = year1; i < year2; i++) {
					// 闰年
					if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
						timeDistance += 366;
					} else { // 不是闰年
						timeDistance += 365;
					}
				}
				rInt = timeDistance + (day2 - day1) + 1;
			} else { // 同年
				rInt = day2 - day1 + 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rInt;
	}

	/**
	 * 获取上个月的第一天
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @return
	 */
	public static String getLastMonthFirstDay(DatePattern pattern) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat format = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(calendar.getTime());
	}

	/**
	 * 获取上个月的最后一天
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @return
	 */
	public static String getLastMonthLastDay(DatePattern pattern) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat format = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Calendar calendar1 = Calendar.getInstance();
		int month = calendar1.get(Calendar.MONTH);
		calendar1.set(Calendar.MONTH, month - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(calendar1.getTime());
	}

	/**
	 * 获取某个时间的当周时间范围
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param date 转换日期，格式必须为 {@param pattern} 的格式
	 * @return
	 */
	public static String getWeek(DatePattern pattern, @NotNull String date) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Date time = null;
		try {
			time = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		return imptimeBegin + " and " + imptimeEnd;
	}

	/**
	 * 指定日期加上天数后的日期
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param num     - 增加的天数
	 * @param newDate 转换日期，格式必须为 {@param pattern} 的格式
	 * @return
	 */
	public static String plusDay(DatePattern pattern, @NotNull int num, @NotNull String newDate) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Date dt = null;
		try {
			dt = sdf.parse(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.DAY_OF_YEAR, num);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	/**
	 * 指定日期加上月数后的日期
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param num     - 增加的月数
	 * @param newDate 转换日期，格式必须为 {@param pattern} 的格式
	 * @return
	 */
	public static String plusMonth(DatePattern pattern, @NotNull int num, @NotNull String newDate) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Date dt = null;
		try {
			dt = sdf.parse(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MONTH, num);// 日期加3个月
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	/**
	 * 获取上个月的今天
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @return
	 */
	public static String getFirstDate(DatePattern pattern) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.MONTH, -1);
		Date dateFrom = cl.getTime();
		return sdf.format(dateFrom);
	}

	/**
	 * 获取某天的下一天
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param newDate 转换日期，格式必须为 {@param pattern} 的格式
	 * @return
	 */
	public static String getNextDate(DatePattern pattern, @NotNull String newDate) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Date dt = null;
		try {
			dt = sdf.parse(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.DATE, 1);// 日期加1天
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	/**
	 * 获取某天的前一天
	 * @param pattern 转换格式 默认格式为 {@see DatePattern.StandardDatePattern}
	 * @param newDate 转换日期，格式必须为 {@param pattern} 的格式
	 * @return
	 */
	public static String getBeforeDate(DatePattern pattern, @NotNull String newDate) {
		pattern = pattern == null ? DatePattern.StandardDatePattern : pattern;
		SimpleDateFormat sdf = SimpleDateFormatObject.getSimpleDateFormatByPattern(pattern.pattern);
		Date dt = null;
		try {
			dt = sdf.parse(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.DATE, -1);// 日期加1天
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	/**
	 * 获取当前时间的前一天
	 * @param datePattern 时间格式
	 * @see DatePattern
	 * @return
	 */
	public static String getBeforeByToday(DatePattern datePattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern.pattern);
		Calendar leftNow = Calendar.getInstance();
		leftNow.setTime(new Date());
		leftNow.add(Calendar.DATE, -1);// 日期加1天
		return sdf.format(leftNow.getTime());
	}

	public static void main(String[] args) {
		System.out.println(getDateByInputDate(null, new Date()));
	}
}
