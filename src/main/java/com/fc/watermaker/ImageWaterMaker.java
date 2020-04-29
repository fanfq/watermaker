package com.fc.watermaker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @program: watermaker
 * @description:
 * @author: fangqing.fan#hotmail.com
 * @create: 2020-04-28 22:14
 **/

public class ImageWaterMaker {

    /**
     * 给图片添加单个图片水印、可设置水印图片旋转角度
     *
     *icon 水印图片路径（如：F:/images/icon.png）
     *source 没有加水印的图片路径（如：F:/images/6.jpg）
     *output 加水印后的图片路径（如：F:/images/）
     *imageName 图片名称（如：11111）
     *imageType 图片类型（如：jpg）
     *degree 水印图片旋转角度，为null表示不旋转
     */
    public Boolean markImageBySingleIcon(String icon,String source,String output,String imageName,String imageType,Integer degree) {

        try {
            File file = new File(source);
            File ficon = new File(icon);

            if (!file.isFile()) {
                return false;
            }

            //将icon加载到内存中
            Image ic = ImageIO.read(ficon);
            //icon高度
            int icheight = ic.getHeight(null);
            //将源图片读到内存中
            Image img = ImageIO.read(file);

            //图片宽
            int width = img.getWidth(null);
            //图片高
            int height = img.getHeight(null);

            BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            //创建一个指定 BufferedImage 的 Graphics2D 对象
            Graphics2D g = bi.createGraphics();

            //x,y轴默认是从0坐标开始
            int x = 0;
            int y = (height/2)-(icheight/2);

            //设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            //呈现一个图像，在绘制前进行从图像空间到用户空间的转换
            g.drawImage(img.getScaledInstance(width,height,Image.SCALE_SMOOTH),0,0,null);

            if (null != degree) {
                //设置水印旋转
                g.rotate(Math.toRadians(degree),(double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
            }
            //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(icon);
            //得到Image对象。
            Image con = imgIcon.getImage();
            //透明度，最小值为0，最大值为1
            float clarity = 0.6f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,clarity));
            //表示水印图片的坐标位置(x,y)
            //g.drawImage(con, 300, 220, null);
            g.drawImage(con, x, y, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            File sf = new File(output, imageName+"."+imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }



    /**
     * 给图片不同位置添加多个图片水印、可设置水印图片旋转角度
     *
     * icon 水印图片路径
     * source 没有加水印的图片路径
     * output 加水印后的图片路径
     * imageName 图片名称
     * imageType 图片类型
     * degree 水印图片旋转角度，为null表示不旋转
     */
    public Boolean markImageByMoreIcon(String icon,String source,String output,String imageName,String imageType,Integer degree) {

        try {
            File file = new File(source);

            File ficon = new File(icon);

            if (!file.isFile()) {
                return false;
            }
            //将icon加载到内存中
            Image ic = ImageIO.read(ficon);

            //icon高度
            int icheight = ic.getHeight(null);

            //将源图片读到内存中
            Image img = ImageIO.read(file);

            //图片宽
            int width = img.getWidth(null);
            //图片高
            int height = img.getHeight(null);

            BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            //创建一个指定 BufferedImage 的 Graphics2D 对象
            Graphics2D g = bi.createGraphics();

            //x,y轴默认是从0坐标开始
            int x = 0;
            int y = 0;

            //默认两张水印图片的间隔高度是水印图片的1/3
            int temp = icheight/3;
            int space = 1;
            if(height>=icheight){
                space = height/icheight;
                if(space>=2){
                    temp = y = icheight/2;
                    if(space==1||space==0){
                        x = 0;
                        y = 0;
                    }
                }
            }else{
                x = 0;
                y = 0;
            }

            //设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            //呈现一个图像，在绘制前进行从图像空间到用户空间的转换
            g.drawImage(img.getScaledInstance(width,height,Image.SCALE_SMOOTH),0,0,null);
            for(int i=0;i<space;i++){
                if (null != degree) {
                    //设置水印旋转
                    g.rotate(Math.toRadians(degree),(double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
                }
                //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
                ImageIcon imgIcon = new ImageIcon(icon);
                //得到Image对象。
                Image con = imgIcon.getImage();
                //透明度，最小值为0，最大值为1
                float clarity = 0.6f;
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,clarity));
                //表示水印图片的坐标位置(x,y)
                //g.drawImage(con, 300, 220, null);
                g.drawImage(con, x, y, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                y+=(icheight+temp);
            }
            g.dispose();
            File sf = new File(output, imageName+"."+imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
