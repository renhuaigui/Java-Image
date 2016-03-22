package dataP;
/*************************************************
 * @Title:  getfileList.java 
 * @Description:  获取文件夹下的所有文件夹以及文件的绝对路径
 * @author:  Ren Huaigui
 * @time:  2015年10月27日 上午8:58:01 
 * @version:  V1.0 
 ************************************************/


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import jdk.jfr.events.FileWriteEvent;

public class getfileList {
	
	public ArrayList<String> getFileList(String strPath,ArrayList<String> filelist) throws IOException{
		/**********************************
		* @author: Ren Huaigui
		* @function: 获取文件夹下.jpg的绝对路径
		* @param： strpath文件夹
		* @param：filelist文件路径列表
		**********************************/
		File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath(),filelist); // 获取文件绝对路径
                } else if (fileName.endsWith("jpg")) { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getAbsolutePath();
//                    System.out.println("---" + strFileName);
                    filelist.add(strFileName.replace("\\","/"));
//                    System.out.println(strFileName);
                } else {
                    continue;
                }
            }

        }
        return filelist;
    }
	
	
	public void traverseFolder(String path,String wrpath) throws IOException{
		/**********************************
		* @author: Ren Huaigui
		* @function: 递归方法遍历文件件
		* @param： 
		**********************************/
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                    	String filepath = file2.getAbsolutePath();
                    	filepath = filepath.replace("\\", "/");
                        System.out.println("文件夹:" + filepath);
                        traverseFolder(file2.getAbsolutePath(),wrpath);
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath().replace("\\", "/"));
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
	
	
	
	public static void main(String[] args)throws IOException {
		String path = "E:/Desktop/Image/Original/wanglili";
		String wrpath = "E:/Desktop/Image/filelist.txt";
		
		getfileList test = new getfileList();
//		test.traverseFolder(path,wrpath);
		ArrayList<String> flist = new ArrayList<String>();
		ArrayList<String> filelist = test.getFileList(path,flist);
		
//		BufferedWriter cw = new BufferedWriter(new FileWriter(wrpath,true));
//		字符写入时处理中文编码问题'bgk'编码
		FileOutputStream fos=new FileOutputStream(new File(wrpath));
	    OutputStreamWriter osw=new OutputStreamWriter(fos, "gbk");//gbk 或者 utf8的格式存储
	    BufferedWriter  cw=new BufferedWriter(osw);
		for (int i = 0; i < filelist.size(); i++) {
			String str = filelist.get(i);
			System.out.println(str);
			cw.write(str+"\n");

			str = str.substring(str.length()-25,str.length()-4);//获取图片名
			System.out.println(str);
			
		}
		cw.close();
		osw.close();
		fos.close();
	}
}
