/**   
* @Title: savesi.java 
* @Package com 
* @Description: TODO
* @author Huaigui Ren
* @date 2016年3月11日 下午7:03:53 
* @version V1.0   
*/
package com;
/*************************************************
 * @Title:  savesi.java 
 * @Description:  TODO<请描述此文件是做什么的> 
 * @author:  Ren Huaigui
 * @time:  2016年3月11日 下午7:03:53 
 * @version:  V1.0 
 ************************************************/

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.image.Image_matrix;
import com.io.readTxt;

import codes.ImageDemo;

/** 
* @ClassName: savesi 
* @Description: TODO
* @author Huigui Ren
* @date 2016年3月11日 下午7:03:53 
*  
*/
public class savesi {

	public static void main(String[] args) throws IOException {  
    	
		String name,path,writ_path;
		ImageDemo test = new ImageDemo();
		path= "E:/Desktop/Image/Original/wechat/wang/";
		writ_path = "E:/Desktop/Image/Original/wang_wechat_data";
      
		readTxt rtxt = new readTxt();
		ArrayList<String>  list = rtxt.getTxt("E:/Desktop/LIst.txt");//获取文件中的文本数据存入列表中
		for(int i=0;i<list.size();i++){
			name = (String)list.get(i);
		 	System.out.println(name);
		 	
		 	SaveSIFT(name);
	    }  
//		test.function(path,"249432478_20150604_15",writ_path);
        System.out.println("|------------------------------------------------|");
        System.out.println("|--------------------| END! |--------------------|");
        System.out.println("|------------------------------------------------|");
    }  
	public static void SaveSIFT(String name){
		String path = "E:/Desktop/1986288542/";
		try{
			Thread.sleep(0);
			String ImgName = "D:/Sina_weibo/image/1986288542/"+name;
			System.out.println(ImgName);
			File file= new File(ImgName);  
			BufferedImage image = ImageIO.read(file); 
			Image_matrix obj = new Image_matrix(image);	
			obj.setsift(ImgName);
//			String [] names = ImgName.split("\\\\");		
			System.out.println(name);
			String fileName = name.split("\\.")[0];
			
    		File sf=new File(path);  
    		if(!sf.exists()){  
    			sf.mkdirs();  
        	}  		
    		write_sift(path+fileName+"_sift.txt",obj.getsift());
            System.out.println("写入"+name+"sift");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("写入文件时出错！");
		}
	}
	
	
	
	
	
	/** 
	* @Title: writesift 
	* @Description: TODO
	* @param @param string
	* @param @param getsift 
	* @return void  
	* @throws 
	*/
	private static void write_sift(String path, int[][] data) {
		// TODO Auto-generated method stub
		try {
			FileWriter fw = new FileWriter(path, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[0].length; j++) {
					if(j == data[0].length-1) bw.write(String.valueOf(data[i][j]));
					else bw.write(String.valueOf(data[i][j])+",");
				}
				bw.write("\n");
			}
	        bw.close();
	        fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
