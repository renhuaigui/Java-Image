/*************************************************
 * @Title:  ImageDemo.java 
 * @Description:  TODO<请描述此文件是做什么的> 
 * @author:  RenHuaigui
 * @data:  2015年9月24日 下午1:23:31 
 * @version:  V1.0 
 ************************************************/
package codes;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import com.image.CalGlcm;
import com.image.CalGray;
import com.image.CalHSV;
import com.image.Image_matrix;
import com.io.WriteToTxt;
import com.io.readTxt;

/** 
* @ClassName: ImageDemo 
* @Description: 图片特征算入口
* @author Huigui Ren
* @date 2015年12月23日 上午8:55:32 
*  
*/
public class ImageDemo {  

	public static void main(String[] args) throws IOException {  
    	
		String name,path,writ_path;
		ImageDemo test = new ImageDemo();
		path= "E:/Desktop/Image/Original/wechat/wang/";
		writ_path = "E:/Desktop/Image/Original/wang_wechat_data";
      
		readTxt rtxt = new readTxt();
		ArrayList<String>  list = rtxt.getTxt("E:/Desktop/Image/Original/WangList.txt");//获取文件中的文本数据存入列表中
		for(int i=1;i<list.size();i++){
			name = (String)list.get(i);
		 	System.out.println(name);
			test.function(path,name,writ_path);
	    }  
//		test.function(path,"249432478_20150604_15",writ_path);
        System.out.println("|------------------------------------------------|");
        System.out.println("|--------------------| END! |--------------------|");
        System.out.println("|------------------------------------------------|");
    }  

	
	public void function(String path,String name,String writ_path) throws IOException{
	/**********************************
	 * @Creat on:  2015年9月25日 下午2:39:49
	 * @author: Ren Huaigui
	 * @function: 计算一张图的特征并存入磁盘
	 * @param： path：图片路径，name：图片名，writ_path:特征写入的文件路径
	 **********************************/
		
		//计算的得到图片对象
		String filename;
    	filename = path+name+"";
		File file= new File(filename+".jpg");  
//		System.out.println(filename+".jpg");
		BufferedImage image = ImageIO.read(file); 
		Image_matrix obj = new Image_matrix(image);	 
		obj.setsift(filename+".jpg");
		//计算特征值
		CalGray cgray = new CalGray();
		double [] grayf = cgray.GrayImage(obj);//求灰度特征
		CalGlcm glcm= new CalGlcm();//共生矩阵求纹理  	
    	double [] texture = glcm.CalGLCM(obj);
    	CalHSV hsv = new CalHSV();
    	double [] hsvf = hsv.CalHSVF(obj);
    	//连接特征
    	double [] feature = new double[grayf.length+texture.length+hsvf.length];
    	System.arraycopy(grayf, 0, feature, 0, grayf.length);  
    	System.arraycopy(texture, 0, feature, grayf.length, texture.length); 
    	System.arraycopy(hsvf, 0, feature, grayf.length+texture.length, hsvf.length);
    	
    	//写入到文件
    	WriteToTxt wr = new WriteToTxt();
    	wr.writeTxt(name,feature,writ_path);
 	}
  
}  