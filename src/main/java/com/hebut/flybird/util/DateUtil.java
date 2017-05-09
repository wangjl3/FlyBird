package com.hebut.flybird.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WangJL on 2017/5/5.
 */
public class DateUtil {
    public static final   String formatStr_yyyyMMddHHmmss ="yyyy-MM-dd HH:mm:ss";
    public static String format(Date date,String pattern){
        SimpleDateFormat  simpleDateFormat= new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
