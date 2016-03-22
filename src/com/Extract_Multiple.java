package com;
/*************************************************
 * @Title:  Extract_Multiple.java 
 * @Description:  多线程计算图片特征 实现同步插入数据库
 * @author:  Ren Huaigui
 * @time:  2015年12月24日 上午10:42:20 
 * @version:  V1.0 
 ************************************************/

import java.awt.font.TextHitInfo;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;
import com.image.CalGlcm;
import com.image.CalGray;
import com.image.CalHSV;
import com.image.CalSIFT;
import com.image.Image_matrix;
import com.io.ImageDB;


/** 
* @ClassName: Extract_Multiple 
* @Description: 计算特征
* @author Huigui Ren
* @date 2015年12月24日 上午10:42:20 
*/
public class Extract_Multiple{

	public static int ThreadNum=100;  
	private final static ReentrantLock lock = new ReentrantLock();
	public static void main(String[] args) {
		Config con = new Config();
		
		//连接数据库
		Connection Dbcon = null;
		try {
			Dbcon = ImageDB.getConnection();
			System.out.println("数据库连接成功！");
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

		//String sql = "select * from siftimage where isExtract = 2";
		String sql = "select * from img_has_car_edu where isExtract=0";
		//String sql = "select * from imageyu where isExtract is null";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Dbcon.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			while (rs.next()) {
				//ExtractFeature(rs);	
				SaveSIFT(rs);
				rs.updateInt("isExtract",1);
				rs.updateRow();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	    try {
	    	Dbcon.close(); 
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		System.out.println("+————————————————————————————————————————————————+");
		System.out.println("+------------------->| END! |<-------------------+");
		System.out.println("+————————————————————————————————————————————————+");
		
	}

	/** 
	* @Title: ExtractFeature 
	* @Description: 特征计算，写入数据库
	*/
	public static void ExtractFeature(ResultSet result)  {
		try {
			System.out.println("正在写入"+result.getString("img_name")+"的特征!");	
			String ImgName = result.getString("img_name");
			Thread.sleep(0);
			File file= new File(ImgName);  
			BufferedImage image = ImageIO.read(file); 
			Image_matrix obj = new Image_matrix(image);	 
			obj.setsift(ImgName);
			DecimalFormat df = new DecimalFormat("0.00");
			//图片大小比例
			double col_row = (double)obj.getheight()/(double)obj.getwidth();
			result.updateString("col/row", df.format(col_row));
			
			CalGray cgray = new CalGray();
			double [] grayf = cgray.GrayImage(obj);//求灰度特征
			CalGlcm glcm= new CalGlcm();//共生矩阵求纹理  	
	    	double [] texture = glcm.CalGLCM(obj);
	    	CalHSV hsv = new CalHSV();//求颜色
	    	double [] hsvf = hsv.CalHSVF(obj);
	    	
	    	//连接特征
			
	    	result.updateString("HSVFeature", ArrayToString(hsvf));
	    	result.updateString("GrayFeature", ArrayToString(grayf));
	    	result.updateString("TextureFeature", ArrayToString(texture));
	    	
			//图像SIFT特征
			CalSIFT calSIFT = new CalSIFT();
			String [][] siftfeatur = calSIFT.siftfeature(obj.getsift());
			result.updateString("siftMean", ArrayToString(siftfeatur[0]));
			result.updateString("siftStd", ArrayToString(siftfeatur[1]));
			result.updateString("siftSke", ArrayToString(siftfeatur[2]));
			
			//执行更新
			result.updateRow();
			System.out.println(obj.getsift().length);
	    	System.out.println("已经写入"+result.getString("img_id")+"的特征!");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void SaveSIFT(ResultSet result){
		String path = "E:/SinaSIFT/";
		try{
			System.out.println("正在写入"+result.getString("img_id")+"的特征!");	
			System.out.println("正在写入"+result.getString("img_name")+"的特征!");	
			String ImgName = result.getString("img_name");
			Thread.sleep(0);
			File file= new File(ImgName);  
			BufferedImage image = ImageIO.read(file); 
			Image_matrix obj = new Image_matrix(image);	
			obj.setsift(ImgName);
			String [] names = ImgName.split("\\\\");			
			String fileName = names[4].split("\\.")[0];
    		File sf=new File(path+names[3]+"/");  
    		if(!sf.exists()){  
    			sf.mkdirs();  
        	}  		
    		write_sift(path+names[3]+"/"+fileName+"_sift.txt",obj.getsift());
            System.out.println("写入"+names[4]+"sift");
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

	/** 
	* @Title: ArrayToString 
	* @Description: 数组转字符串
	* @throws 
	*/
	public static String ArrayToString(double[] data)  {
		String feature = "";
		for (int i = 0; i < data.length; i++) {
			if(i == data.length-1) feature +=  String.valueOf(data[i]);
			else feature +=  String.valueOf(data[i])+"," ;
		}
		return feature;
	}
	public static String ArrayToString(String [] data)  {
		String feature = "";
		for (int i = 0; i < data.length; i++) {
			if(i == data.length-1) feature +=  String.valueOf(data[i]);
			else feature +=  String.valueOf(data[i])+"," ;
		}
		return feature;
	}

	
}




