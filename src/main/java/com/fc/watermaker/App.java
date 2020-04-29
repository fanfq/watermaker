package com.fc.watermaker;

import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @program: watermaker
 * @description:
 * @author: fangqing.fan#hotmail.com
 * @create: 2020-04-28 17:39
 **/

public class App {

    public static void main(String[] args){

        TextWaterMaker mSingleText = new TextWaterMaker();
//        String src  = "/Users/fred/Desktop/1.jpg";
//        String out = "/Users/fred/Desktop/";

        String src = args[0];
        String word = args[1]+" 他用无效";
//        String word = args[1];

        File file = new File(src);

        String filePath = file.getPath();
        String fileName = file.getName();
        String out = filePath.replace(fileName,"");

        String[] fix = fileName.split("\\.");
        //System.out.println(fix.length);
        String prefix_ = fix[0]+"_"+word+"_"+Utils.getExpiryDate()+"_"+System.currentTimeMillis()/1000;
        String extfix_ = fix[1];

        //System.out.println(prefix_);

        //System.out.println(extfix_);

        boolean res = mSingleText.markImageByMoreText(src,out,prefix_,extfix_,new Color(255, 153, 153),word,45);

        if(res){
            System.out.println("加水印成功！output:"+out+prefix_+"."+extfix_);
        }else{
            System.out.println("加水印失败");
        }

    }
}
