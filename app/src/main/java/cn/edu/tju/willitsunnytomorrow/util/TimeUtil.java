package cn.edu.tju.willitsunnytomorrow.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ouyangbin on 2016/3/7.
 */
public class TimeUtil {

    private static String days[] = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    private static GregorianCalendar mCalendar = new GregorianCalendar();

    public static String getDate() {
        return (mCalendar.get(Calendar.MONTH) + 1) + "." + mCalendar.get(Calendar.DATE) + "/" + days[(mCalendar.get(Calendar.DAY_OF_WEEK) + 5) % 7];
    }


}
