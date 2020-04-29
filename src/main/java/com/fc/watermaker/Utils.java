package com.fc.watermaker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @program: watermaker
 * @description:
 * @author: fangqing.fan#hotmail.com
 * @create: 2020-04-29 09:22
 **/

public class Utils {



    //30天有效期
    public static String getExpiryDate(){
        String DATEFORMAT = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(DATEFORMAT);

        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,30);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        //System.out.println(df.format(date));

        String txt = "有效期至 " + df.format(date) + " 过期作废";
        return txt;
    }


}
