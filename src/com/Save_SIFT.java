/**   
* @Title: Save_SIFT.java 
* @Package com 
* @Description: TODO
* @author Huaigui Ren
* @date 2016年2月29日 下午9:43:32 
* @version V1.0   
*/
package com;
/*************************************************
 * @Title:  Save_SIFT.java 
 * @Description:  TODO<请描述此文件是做什么的> 
 * @author:  Ren Huaigui
 * @time:  2016年2月29日 下午9:43:32 
 * @version:  V1.0 
 ************************************************/

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.image.Image_matrix;
import com.io.ImageDB;
import java.util.concurrent.locks.ReentrantLock;
import javax.imageio.ImageIO;



/** 
* @ClassName: Save_SIFT 
* @Description: TODO
* @author Huigui Ren
* @date 2016年2月29日 下午9:43:32 
*  
*/


public class Save_SIFT extends Thread{
	public static int ThreadNum =10;
	private String FileName;
	private String filePath = "E:/SinaSIFT/";
	private String errorpath = "E:/SinaSIFT/error";
	private final static ReentrantLock lock = new ReentrantLock();	

	public Save_SIFT(String filename) {
		// TODO Auto-generated constructor stub
		this.FileName = filename;
	}
	public void run(){
		String ImgName = this.FileName;
		try{			
			//System.out.println("正在写入"+ImgName+"的特征!");	
			File file= new File(ImgName);  
			BufferedImage image = ImageIO.read(file); 
			Image_matrix obj = new Image_matrix(image);	
			obj.setsift(ImgName);
			String [] names = ImgName.split("\\\\");			
			String fileName = names[4].split("\\.")[0];
    		File sf=new File(filePath+names[3]+"/");  
    		if(!sf.exists()){  
    			sf.mkdirs();  
        	}  		
    	
    		write_sift(filePath+names[3]+"/"+fileName+"_sift.txt",obj.getsift());
            System.out.println("已写入"+names[4]+"_sift");
		}catch(Exception e){
			System.out.println("出错！");
		}
        lock.lock();
		try {
			ThreadNum++;
		} finally {
			lock.unlock();
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
			FileWriter fw = new FileWriter(path);
	        BufferedWriter bw = new BufferedWriter(fw);
	        for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[0].length; j++) {
					if(j == data[0].length-1) bw.write(data[i][j]);
					else bw.write(data[i][j]+",");
				}
				bw.write("\n");
			}
	        bw.close();
	        fw.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("写入文件时出错！");
		}
		
	}
	public static String ArrayToString(int [][] data)  {
		String str = "";
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if(j == data[0].length-1) str +=  String.valueOf(data[i][j]);
				else str +=  String.valueOf(data[i][j])+"," ;
			}
			str+= "\n";
		}
		return str;
	}
	

	
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

		String sql = "select * from img_has_car_edu where img_id>110";
		//String sql = "select * from imageyu where isExtract is null";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = Dbcon.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			while (rs.next()) {
			 	//判断是否能启动新的线程
			 	while(ThreadNum == 0){
			 		  System.out.println("=================无空闲线程，等待一会儿!==============");
			 		  Thread.sleep(1000);
			 	} 
				if(ThreadNum > 0){
			 		//加锁避免出错
			 		lock.lock();
					try {
						ThreadNum--;
					} finally {
						lock.unlock();
					}
			 		//String ImgName = rs.getString("img_name");
			    	System.out.println(rs.getString("img_id"));
			    	Thread thread = new Save_SIFT(rs.getString("img_name"));
			     	thread.start();
			     	Thread.sleep(100);//等1000毫秒启动线程
			    }
				//ExtractFeature(rs);
				//rs.updateString("col/row", x);
				rs.updateInt("isExtract", 1);
				rs.updateRow();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block		
			e1.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
