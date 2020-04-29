package com.fc.watermaker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @program: watermaker
 * @description:
 * @author: fangqing.fan#hotmail.com
 * @create: 2020-04-28 22:16
 **/

public class MosaicImage {
    /**
     * 给图片添加马赛克
     * source 原图片路径
     * output 打马赛克后，图片保存的路径
     * imageName 图片名称
     * imageType 图片类型
     * size 马赛克尺寸，即每个矩形的宽高
     */
    public Boolean markImageByMosaic(String source,String output,String imageName,String imageType,int size){

        try{
            File file = new File(source);
            if (!file.isFile()) {
                return false;
            }
            BufferedImage img = ImageIO.read(file); // 读取该图片
            int width = img.getWidth(null); //原图片宽
            int height = img.getHeight(null); //原图片高
            BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
            //马赛克格尺寸太大或太小
            if (width < size || height < size) {
                return false;
            }
            if(size<=0){
                return false;
            }
            int xcount = 0; //x方向绘制个数
            int ycount = 0; //y方向绘制个数

            if (width % size == 0) {
                xcount = width / size;
            } else {
                xcount = width / size + 1;
            }

            if (height % size == 0) {
                ycount = height / size;
            } else {
                ycount = height / size + 1;
            }

            int x = 0; //x坐标
            int y = 0;//y坐标

            //绘制马赛克(绘制矩形并填充颜色)
            Graphics2D g = bi.createGraphics();
            for (int i = 0; i < xcount; i++) {
                for (int j = 0; j < ycount; j++) {
                    //马赛克矩形格大小
                    int mwidth = size;
                    int mheight = size;
                    if(i==xcount-1){  //横向最后一个不够一个size
                        mwidth = width-x;
                    }

                    if(j == ycount-1){ //纵向最后一个不够一个size
                        mheight = height-y;
                    }

                    //矩形颜色取中心像素点RGB值
                    int centerX = x;
                    int centerY = y;

                    if (mwidth % 2 == 0) {
                        centerX += mwidth / 2;
                    } else {
                        centerX += (mwidth - 1) / 2;
                    }

                    if (mheight % 2 == 0) {
                        centerY += mheight / 2;
                    } else {
                        centerY += (mheight - 1) / 2;
                    }

                    Color color = new Color(img.getRGB(centerX, centerY));
                    g.setColor(color);
                    g.fillRect(x, y, mwidth, mheight);
                    y = y + size;// 计算下一个矩形的y坐标
                }

                y = 0;// 还原y坐标
                x = x + size;// 计算x坐标
            }
            g.dispose();
            File sf = new File(output, imageName+"."+imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片

        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }


}
