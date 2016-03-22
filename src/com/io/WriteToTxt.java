package com.io;
/*************************************************
 * @Title:  WriteToTxt.java 
 * @Description:  数组数据写入文件中
 * @author:  RenHuaigui
 * @data:  2015年9月24日 下午1:23:01 
 * @version:  V1.0 
 ************************************************/


import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
  
/** 
* @ClassName: WriteToTxt 
* @Description: TODO
* @author Huigui Ren
* @date 2015年11月4日 下午4:11:56 
*  
*/
public class WriteToTxt {

	public void  writeTxt(String filename,double [] data,String path) throws IOException{
		FileWriter fw=new FileWriter(path+".txt",true);
		BufferedWriter cw=new BufferedWriter(fw);
		cw.write(filename+ " ");
		for (int i = 0; i < data.length; i++) {
			if(i == data.length) cw.write(data[i]+"");
			else cw.write(data[i]+" ");
		}
		cw.write("\r\n");
		cw.close();
		fw.close();
	}
	public void  writeTxt(double [][] data,String path) throws IOException{
		FileWriter fw=new FileWriter(path+".txt",true);
		BufferedWriter cw=new BufferedWriter(fw);
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if(i == data[0].length) cw.write(data[i][j]+"");
				else cw.write(data[i][j]+" ");	
			}
			cw.write("\r\n");
		}
		cw.close();
		fw.close();
	}
}
