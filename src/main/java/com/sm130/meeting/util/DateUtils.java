package com.sm130.meeting.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date String2Date(String time){
        Date date = new Date(Integer.parseInt(time.substring(0, 4))-1900, Integer.parseInt(time.substring(5, 7))-1, Integer.parseInt(time.substring(8, 10)));
        return date;
    }
    public static String date2String(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "年" + month + "月" + day + "日";
    }
}
