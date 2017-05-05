package org.hsweb.printer.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by apple on 15/12/15.
 */
public class DateUtil {

    public static final String yyyyMM = "yyyyMM";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String HHmm = "HHmm";
    public static final String HHmmss = "HHmmss";


    public static final String yyyy_MM = "yyyy-MM";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_zh = "yyyy年MM月dd日";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String HH_mm = "HH:mm";
    public static final String HH_mm_ss = "HH:mm:ss";

    /**
     * 时间区间 数据结构, 用于活动时间判断
     */
    public static class Section implements Comparable<Section>{
        private Date start;
        private Date end;

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public void setStart(Long start) {
            this.start = new Date(start);
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }
        public void setEnd(Long end) {
            this.end = new Date(end);
        }

        /**
         * 比较开始时间,
         * 开始时间相同比较结束时间
         * @param section
         * @return
         */
        @Override
        public int compareTo(Section section) {
            if (this.getStart().getTime() == section.getStart().getTime() && this.getEnd().getTime() == section.getEnd().getTime()){
                return 0;
            }
            if (this.getStart().getTime() == section.getStart().getTime()){
                return (int)(section.getEnd().getTime() - this.getEnd().getTime());
            }else{
                return (int)(this.getStart().getTime() - section.getStart().getTime());
            }
        }
    }


    public static String timeToString(Long tLong,String format) {
        if(tLong==null){
            return "";
        }
        return timeToString(new Date(tLong),format);
    }
    /**
     * 格式化日期
     * @return
     */
    public static String timeToString(Date date,String format) {
        String rString="";
        if (null!=date) {
            DateFormat dateFormat=new SimpleDateFormat(null==format?yyyy_MM_dd_HH_mm_ss:format);
            rString=dateFormat.format(date);
        }
        return rString;
    }
    /**
     * 格式化日期    yyyyMM
     * @param date
     * @return
     */
    public static String timeToString_yyyyMM(Date date)
    {
        return timeToString(date,yyyyMM);
    }
    /**
     * 格式化日期   yyyyMMdd
     * @param date
     * @return
     */
    public static String timeToString_yyyyMMdd(Date date)
    {
        return timeToString(date,yyyyMMdd);
    }
    /**
     * 格式化日期   yyyyMMddHHmm
     * @param date
     * @return
     */
    public static String timeToString_yyyyMMddHHmm(Date date)
    {
        return timeToString(date,yyyyMMddHHmm);
    }
    /**
     * 格式化日期   yyyyMMddHHmmss
     * @param date
     * @return
     */
    public static String timeToString_yyyyMMddHHmmss(Date date)
    {
        return timeToString(date,yyyyMMddHHmmss);
    }

    /**
     * 格式化日期   yyyyMMddHHmmssSSS
     * @param date
     * @return
     */
    public static String timeToString_yyyyMMddHHmmssSSS(Date date)
    {
        return timeToString(date,yyyyMMddHHmmssSSS);
    }
    /**
     * 格式化日期   HHmm
     * @param date
     * @return
     */
    public static String timeToString_HHmm(Date date)
    {
        return timeToString(date,HHmm);
    }
    /**
     * 格式化日期 HHmmss
     * @param date
     * @return
     */
    public static String timeToString_HHmmss(Date date)
    {
        return timeToString(date,HHmmss);
    }
    /**
     * 格式化日期   yyyy-MM
     * @param date
     * @return
     */
    public static String timeToString_yyyy_MM(Date date)
    {
        return timeToString(date,yyyy_MM);
    }
    /**
     * 格式化日期  yyyy-MM-dd
     * @param date
     * @return
     */
    public static String timeToString_yyyy_MM_dd(Date date)
    {
        return timeToString(date,yyyy_MM_dd);
    }
    /**
     * 格式化日期	yyyy-MM-dd HH:mm
     * @param date
     * @return
     */
    public static String timeToString_yyyy_MM_dd_HH_mm(Date date) {
        return timeToString(date,yyyy_MM_dd_HH_mm);
    }
    /**
     * 格式化日期	yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String timeToString_yyyy_MM_dd_HH_mm_ss(Date date) {
        return timeToString(date,yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 通过数据库毫秒时间戳获取到格式化字符串
     * @param millisecond
     * @return
     */
    public static String millisecondToString_yyyy_MM_dd_HH_mm_ss(Long millisecond) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisecond);

        return formatter.format(c.getTime());
    }

    public static String millisecondToString_yyyy_MM_dd(Long millisecond) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisecond);

        return formatter.format(c.getTime());
    }

    public static String millisecondToString_HH_mm(Long millisecond){
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisecond);

        return formatter.format(c.getTime());
    }

    /**
     * 格式化日期   HH:mm
     * @param date
     * @return
     */
    public static String timeToString_HH_mm(Date date)
    {
        return timeToString(date,HH_mm);
    }
    /**
     * 格式化日期  HH:mm:ss
     * @param date
     * @return
     */
    public static String timeToString_HH_mm_ss(Date date)
    {
        return timeToString(date,HH_mm_ss);
    }

    /**
     * 格式化日期
     * yyyyMM
     * @param timestamp
     * @return
     */
    public static String timeToString_yyyyMM(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),yyyyMM);
    }
    /**
     * 格式化日期   yyyyMMdd
     * @param timestamp
     * @return
     */
    public static String timeToString_yyyyMMdd(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),yyyyMMdd);
    }
    /**
     * 格式化日期   yyyyMMddHHmmss
     * @param timestamp
     * @return
     */
    public static String timeToString_yyyyMMddHHmmss(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),yyyyMMddHHmmss);
    }
    /**
     * 格式化日期   HHmm
     * @param timestamp
     * @return
     */
    public static String timeToString_HHmm(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),HHmm);
    }
    /**
     * 格式化日期 HHmmss
     * @param timestamp
     * @return
     */
    public static String timeToString_HHmmss(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),HHmmss);
    }
    /**
     * 格式化日期   yyyy-MM
     * @param timestamp
     * @return
     */
    public static String timeToString_yyyy_MM(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),yyyy_MM);
    }
    /**
     * 格式化日期  yyyy-MM-dd
     * @param timestamp
     * @return
     */
    public static String timeToString_yyyy_MM_dd(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),yyyy_MM_dd);
    }
    /**
     * 格式化日期	yyyy-MM-dd HH:mm:ss
     * @param timestamp
     * @return
     */
    public static String timeToString_yyyy_MM_dd_HH_mm_ss(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),yyyy_MM_dd_HH_mm_ss);
    }
    /**
     * 格式化日期   HH:mm
     * @param timestamp
     * @return
     */
    public static String timeToString_HH_mm(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),HH_mm);
    }
    /**
     * 格式化日期  HH:mm:ss
     * @param timestamp
     * @return
     */
    public static String timeToString_HH_mm_ss(java.security.Timestamp timestamp )
    {
        if(null==timestamp){
            return "";
        }
        return timeToString(timestamp.getTimestamp(),HH_mm_ss);
    }
    /**
     * 格式化日期   yyyy-MM
     * @return
     */
    public static String timeToString_yyyy_MM(Long tlong)
    {
        return timeToString(tlong,yyyy_MM);
    }
    /**
     * 格式化日期  yyyy-MM-dd
     * @return
     */
    public static String timeToString_yyyy_MM_dd(Long tlong)
    {
        return timeToString(tlong,yyyy_MM_dd);
    }
    /**
     * 格式化日期	yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String timeToString_yyyy_MM_dd_HH_mm_ss(Long tlong)
    {
        return timeToString(tlong,yyyy_MM_dd_HH_mm_ss);
    }
    /**
     * 格式化日期   HH:mm
     * @return
     */
    public static String timeToString_HH_mm(Long tlong)
    {
        return timeToString(tlong,HH_mm);
    }
    /**
     * 格式化日期  HH:mm:ss
     * @return
     */
    public static String timeToString_HH_mm_ss(Long tlong)
    {

        return timeToString(tlong,HH_mm_ss);
    }

    /**
     * 格式化日期 yyyy年MM月dd日
     * @param tlong
     * @return
     */
    public static String timeToString_yyyy_MM_dd_zh(Long tlong) {
        return timeToString(tlong, yyyy_MM_dd_zh);
    }

    /**
     * 字符串转时间
     * @param dateStr 时间字符串
     * @return
     */
    public static Date stringToTime(String dateStr)
    {

        return stringToTime(dateStr,"yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 字符串转时间
     * @param dateStr 时间字符串
     * @param format 字符串格式  例  yyyy-MM-dd
     * @return
     */
    public static Date stringToTime(String dateStr,String format)
    {
        if(dateStr==null||"".equals(dateStr)){
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * java.security.Timestamp 转Date
     * @param timestamp
     * @return
     */
    public static java.sql.Date timestampToDate(java.security.Timestamp timestamp)
    {
        if(null==timestamp)
        {
            return null;
        }
        return new java.sql.Date(timestamp.getTimestamp().getTime());
    }
    /**
     * java.sql.Timestamp 转Date
     * @param timestamp
     * @return
     */
    public static java.sql.Date timestampToDate(java.sql.Timestamp timestamp)
    {
        if(null==timestamp)
        {
            return null;
        }
        return new java.sql.Date(timestamp.getTime());
    }

    /**
     * Date 转    java.sql.Timestamp
     * @param date
     * @return
     */
    public static java.sql.Timestamp dateToSqlTimestamp(Date date)
    {
        if(null==date)
        {
            return null;
        }
        return new java.sql.Timestamp(date.getTime());
    }
	/*public static java.security.Timestamp datetampToSecurityTimestamp(java.util.Date date)
	{
		if(null==date)
		{
			return null;
		}
		return new java.security.Timestamp(date, null);
	}*/

    /**
     * 日期调控
     * @param date 日期
     * @param year 数字为正表示增加多少年 为负减少多少年   0为不改变
     * @param month 数字为正表示增加多少月 为负减少多少月   0为不改变
     * @param day 数字为正表示增加多少日 为负减少多少日   0为不改变
     * @param hour 数字为正表示增加多少小时 为负减少多少小时   0为不改变
     * @param minute 数字为正表示增加多分钟 为负减少多少分钟   0为不改变
     * @param second 数字为正表示增加多少秒 为负减少多少秒   0为不改变
     * @return
     */
    public static Date dateControl(Date date,int year,int month, int day,int hour,int minute,int second)
    {
        if (null==date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, day);
        calendar.add(Calendar.HOUR, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date dateControl(Date date, int day)
    {
        if (null==date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }
    /**
     * 日期调控
     * @param date 日期
     * @param year 数字为正表示增加多少年 为负减少多少年   0为不改变
     * @param month 数字为正表示增加多少月 为负减少多少月   0为不改变
     * @param day 数字为正表示增加多少日 为负减少多少日   0为不改变
     * @return
     */
    public static Date dateControlDate(Date date,int year,int month, int day) {
        return dateControl(date,year,month,day,0,0,0);
    }
    /**
     * 日期调控
     * @param date 日期
     * @param hour 数字为正表示增加多少小时 为负减少多少小时   0为不改变
     * @param minute 数字为正表示增加多分钟 为负减少多少分钟   0为不改变
     * @param second 数字为正表示增加多少秒 为负减少多少秒   0为不改变
     * @return
     */
    public static Date dateControlTime(Date date,int hour,int minute, int second) {
        return dateControl(date,0,0,0,hour,minute,second);
    }

    public static void main(String[] args) {
        Date d = DateUtil.dateControlTime(new Date(),0,Integer.valueOf("10"),0);
        System.out.println(DateUtil.timeToString_yyyy_MM_dd_HH_mm_ss(d));
    }

    /**
     * 功能描述：得到两个日期之间的相差天数
     *
     * @param date
     *            java.util.Date 日期
     * @param date1
     *            java.util.Date 日期
     * @return 返回相减后的日期
     */
    public static int dateControl(Date date, Date date1) {
        return dateControl(date.getTime(),date1.getTime());
    }
    public static int dateControl(long date,long date1) {
        return (int) ((date - date1) / (24 * 3600 * 1000));
    }


    /**
     * 判断是否是当天
     * @param
     * @return
     */
    public static boolean isToday(Date date)
    {
        String dateStr = timeToString_yyyy_MM_dd(date);
        String nowTime = timeToString_yyyy_MM_dd(new Date());
        Date requireDate = stringToTime(dateStr+" "+"00:00:00");
        Date nowDate = stringToTime(nowTime+" "+"00:00:00");
        int flag = dateControl(requireDate,nowDate);
        if(flag==0)
        {
            return true;
        }else
        {
            return false;
        }
    }

    /**
     * 是否是明天
     *
     * @param date
     * @return
     */
    public static boolean isTomorrow(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return isSameDay(date,calendar.getTime());
    }

    /**
     * 判断是否是同一天
     * @param
     * @return
     */
    public static boolean isSameDay(Date date1,Date date2)
    {
        String dateStr = timeToString_yyyy_MM_dd(date1);
        String nowTime = timeToString_yyyy_MM_dd(date2);
        Date requireDate = stringToTime(dateStr+" "+"00:00:00");
        Date nowDate = stringToTime(nowTime+" "+"00:00:00");
        int flag = dateControl(requireDate,nowDate);
        if(flag==0)
        {
            return true;
        }else
        {
            return false;
        }
    }
    /**
     * 计算两个日期相隔的天数
     * @return
     */
    public static int dateControl(String starTtime, String endTime) {
        Date firstDate = stringToTime(starTtime);
        Date secondDate = stringToTime(endTime);
        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
        return nDay;
    }

    /**
     * 取得指定月份的第一天
     * String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static Date getMonthBeginToDate(int year,int month) {
        return stringToTime((year<10?"0"+year:year)+"-"+(month<10?"0"+month:month)+ "-01","yyyy-MM-dd");
    }
    /**
     * 取得指定日期的第一天
     */
    public static Date getMonthBeginToDate(Date date)
    {
        return stringToTime(getMonthBeginToString(date),"yyyy-MM-dd");
    }
    /**
     * 取得指定月份的第一天
     * String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static String getMonthBeginToString(int year,int month) {
        return (year<10?"0"+year:year)+"-"+(month<10?"0"+month:month)+ "-01";
    }
    /**
     * 取得指定时间的该月份的第一天
     * @param date
     * @return
     */
    public static String getMonthBeginToString(Date date)
    {
        return timeToString_yyyy_MM(date) + "-01";
    }

    /**
     * 取得指定日期的最后一天
     * @return
     */
    public static Date getMonthEndToDate(int year,int month) {

        return stringToTime(getMonthEndToString(year, month),"yyyy-MM-dd");
    }
    /**
     * 取得指定日期的最后一天
     * @param date
     * @return
     */
    public static Date getMonthEndToDate(Date date)
    {
        return stringToTime(getMonthEndToString(date),"yyyy-MM-dd");
    }
    /**
     * 取得指定月份的最后一天
     * String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String getMonthEndToString(int year,int month) {
        Date da=stringToTime((year<10?"0"+year:year)+"-"+(month<10?"0"+month:month)+"-01");
        return getMonthEndToString(da);
    }
    /**
     * 取得指定日期的当月的最后一天
     * @param date
     * @return
     */
    public static String getMonthEndToString(Date date)
    {
        String rString="";
        if(null!=date)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, 1);
            calendar.add(Calendar.MONTH, +1);
            calendar.add(Calendar.DATE, -1);
            rString=timeToString_yyyy_MM_dd(calendar.getTime());
        }
        return rString;
    }


    /**
     * 判断当前时间是否在指定日期内
     * @param isMinute(是否带时分秒)
     * @return
     */
    public static boolean dateisNowBetweenDates(String startTime, String endTime,boolean isMinute){
        return dateisNowBetweenDates(new Date(), startTime, endTime, isMinute);
    }
    /**
     * 判断指定时间是否在指定日期内
     * @param isMinute(是否带时分秒)
     * @return
     */
    public static boolean dateisNowBetweenDates(String data,String startTime, String endTime,boolean isMinute){
        return dateisNowBetweenDates(stringToTime(data), startTime, endTime, isMinute);
    }
    /**
     * 判断指定时间是否在指定日期内

     * @return
     */
    public static boolean dateisNowBetweenDates(Date data,String startTime, String endTime,boolean isMinute){
        Date sT = null;// 起始时间
        Date eT = null;// 终止时间
        if(isMinute){
            sT = stringToTime(startTime+" 00:00:01");
            eT = stringToTime(endTime+" 23:59:59");
        }else{
            sT = stringToTime(startTime);
            eT = stringToTime(endTime);
        }
        Calendar scalendar = Calendar.getInstance();
        scalendar.setTime(sT);// 起始时间
        Calendar ecalendar = Calendar.getInstance();
        ecalendar.setTime(eT);// 终止时间
        Calendar calendarnow = Calendar.getInstance();
        calendarnow.setTime(data);
        return calendarnow.after(scalendar) && calendarnow.before(ecalendar);
    }


    /**
     * 判断日期是否在指定日期之后  (date是否在date2之后)
     * @param date
     * @param date2
     * @return
     */
    public static boolean dateisAfter(String date,String date2){
        Date sT = stringToTime(date);
        Date eT=stringToTime(date2);
        return sT.after(eT);
    }

    /**
     * 判断日期是否在指定日期之前 (date是否在date2之前)
     * @param date
     * @return
     */
    public static boolean dateisBefore(String date,String date2){
        Date sT = stringToTime(date);
        Date eT = stringToTime(date2);
        return sT.before(eT);
    }

    /**
     * 获取某一天开始的时间
     * @param date
     * @return
     */
    public static Date getDateBeginDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某一天结束的时间
     * @param date
     * @return
     */
    public static Date getDateEndDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取某一时刻的plusDate
     *
     * @param date
     * @param plusDay
     * @return
     */
    public static Date getPlusDate(Date date, Integer plusDay){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) + plusDay);
        return calendar.getTime();
    }

    /**
     * 获取某一天后plus天的开始时刻
     * @param date
     * @param plusDay
     * @return
     */
    public static Date getDateBeginDate(Date date, Integer plusDay){
        return getDateBeginDate(getPlusDate(date,plusDay));
    }

    /**
     * 获取某一天后plus天的结束时刻
     *
     * @param date
     * @param plusDay
     * @return
     */
    public static Date getDateEndDate(Date date, Integer plusDay){
        return getDateEndDate(getPlusDate(date,plusDay));
    }

    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if(date != null){
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekOfDays[w];
    }

    public static int getWeekValOfDate(Date date) {
        int[] weekOfDays = {0, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        if(date != null){
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 得到本月的第一天
     * @return
     */
    public static String getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));

        return timeToString(calendar.getTime(),"yyyy-MM-dd");
    }

    /**
     *  得到本月的最后一天
     * @return
     */
    public static String getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return timeToString(calendar.getTime(),"yyyy-MM-dd");
    }


    /**
     * 得到某年某月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return timeToString(cal.getTime(),"yyyy-MM-dd");
    }

    /**
     * 得到某年某月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return timeToString(cal.getTime(),"yyyy-MM-dd");
    }


    /**
     * 判断时间段A和时间段B有没重合的时间
     * @param sectionA
     * @param sectionB
     * @return
     */
    public static Boolean isSectionOverlop(Section sectionA, Section sectionB){

        return (sectionA.getStart().getTime() >= sectionB.getStart().getTime() && sectionA.getStart().getTime() <= sectionB.getEnd().getTime()) ||
                sectionA.getStart().getTime() <= sectionB.getStart().getTime() && sectionA.getEnd().getTime() > sectionB.getStart().getTime();

    }


    /**
     * 获取 两个 时间段的重叠时间段
     * @return
     */
    public static Section getOverlopSection(Section sectionA, Section sectionB){
        Section sectionRes = new Section();

        if (isSectionOverlop(sectionA, sectionB)){
            sectionRes.setStart(sectionA.getStart().getTime()>=sectionB.getStart().getTime() ?
                    sectionA.getStart().getTime() : sectionB.getStart().getTime());
            sectionRes.setEnd(sectionA.getEnd().getTime() <= sectionB.getEnd().getTime() ?
                    sectionA.getEnd().getTime() : sectionB.getEnd().getTime());
            return sectionRes;
        }
        return null;
    }

    public static Date parseDate(String date,String format){
        DateFormat dateFormat=new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取最近7天日期,含查询当天
     *
     * @param queryDate
     * @return
     */
    public static List<Date> getLastSeventDays(Date queryDate){
        List<Date> dates = new ArrayList<>();
        for(int i = -6; i <= 0; ++i){
            Date beginDate = DateUtil.getPlusDate(queryDate,i);
            dates.add(beginDate);
        }
        return dates;
    }
}
