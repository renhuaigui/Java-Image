/**   
* @Title: TransSvmData.java 
* @Package com.svmdata 
* @Description: TODO
* @author Huaigui Ren
* @date 2015年12月28日 下午1:29:03 
* @version V1.0   
*/
package com.svmdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Config;
import com.io.ImageDB;
import com.io.readTxt;

/** 
* @ClassName: TransSvmData 
* @Description: 数据转换成svm格式
* @author Huigui Ren
* @date 2015年12月28日 下午1:29:03 
*  
*/
public class TransSvmData {
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
		String mid="";
		String sql = "select * from user where accountID =any"+
                        "(select uid from content where mid =?)";
		readTxt rtxt = new readTxt();
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<String>  list = rtxt.getTxt("E:/Desktop/midlist.txt");//获取文件中的文本数据存入列表中
		for(int i=0;i<list.size();i++){
			mid = (String)list.get(i);
			try {
				pst = Dbcon.prepareStatement("select uid from content where mid ="+mid);
				System.out.println(mid);
				//pst.setString(1, mid);
				rs = pst.executeQuery();
				if(rs.next()) System.out.println(rs.getString("uid"));
				/*if(rs.next()) {
					PreparedStatement pst1 = Dbcon.prepareStatement("select * from user where accountID =?");
					String accountID = rs.getString("uid");
					pst1.setString(1, accountID);
					ResultSet rs1 = pst1.executeQuery();
					if(rs1.next()) System.out.println(rs1.getString("gender"));
				}*/
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
	    }  
	}
}
