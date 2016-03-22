package dataP;
/*************************************************
 * @Title:  readTxt.java 
 * @Description: 读取文件数据
 * @author:  RenHuaigui
 * @data:  2015年9月24日 下午1:23:13 
 * @version:  V1.0 
 ************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class readTxt {
	public ArrayList<String> getTxt(String filepath){  
	/**********************************
	 * @Creat on: 
	 * @author: 
	 * @function: 读取文件到列中，一行一个元素
	 * @param： 读取的文件路径，返回文件内容列表
	 **********************************/	
		try{  
		    String temp = null;  
		    File f = new File(filepath);  
		    //指定读取编码用于读取中文  
		    InputStreamReader read = new InputStreamReader(new FileInputStream(f),"gbk");  
		    ArrayList<String> readList = new ArrayList<String>();   
		    BufferedReader reader=new BufferedReader(read);   
		    //bufReader = new BufferedReader(new FileReader(filepath));  
		    while((temp=reader.readLine())!=null &&!"".equals(temp)){  
		        readList.add(temp);  
		    }  
		    read.close();  
		    return readList;  
		}catch (Exception e) { 
			System.out.println("出错了");
			e.printStackTrace();  
		    return null;  
		}     
	} 
	public double[][] readData(String path){
	/**********************************
	 * @Creat on: 
	 * @author: 
	 * @function: 读文件中的数据，存到二维数组中
	 * @param： 读取的文件路径，返回文件数据矩阵
	 **********************************/
		ArrayList<String>  list = this.getTxt(path);
		int row = list.size();// 计算行数
		String a[] = list.get(0).split(" ");//第一个列表的元素个数
		int col = a.length;//计算列数
		double data[][] = new double[row][col-1];//第一列是图片名去掉
		for (int i = 0; i < list.size(); i++) {
			String arr[] = list.get(i).split(" ");
			for (int j = 1; j < arr.length; j++) { //从第2个数据开始。第一个是图片名
				data[i][j-1]=java.lang.Double.parseDouble(arr[j]); 
			} 
		}
/*		double [][] data = null;
		String s = new String();
		int lin=0;
		try { 
			BufferedReader br = new BufferedReader(new FileReader(path)); 
			while ((s = br.readLine()) != null) { // 判断是否读到了最后一行 
				String arr[]=s.split(" "); 
				for (int i = 1; i < arr.length; i++) { //从第2个数据开始
					System.out.print(arr[i]);
					data[lin][i]=java.lang.Double.parseDouble(arr[i]); 
				} 
				lin++; 
			} 

		} catch (FileNotFoundException e1) { 
			System.out.println("出错了");
			// TODO Auto-generated catch block 
			e1.printStackTrace(); 
		} 
*/
		
		return data;
	}
	public static void main(String[] args){  
/*    	//例子：
		readTxt r = new readTxt();
    	double [][] data = r.readData("D:/Desktop/Image/Original/feature_wechat.txt");
    	for (int i = 0; i < data.length; i++) {
    		for (int j = 0; j < data[0].length; j++) {
				System.out.print(data[i][j]);
			}
    		System.out.print("\n");
		}
  */  	
    }  
}