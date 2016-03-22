/**   
* @Title: ImageDB.java 
* @Package com.io 
* @Description: TODO
* @author Huaigui Ren
* @date 2015年12月24日 上午11:13:09 
* @version V1.0   
*/
package com.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.Config;

/** 
* @ClassName: ImageDB 
* @Description: TODO
* @author Huigui Ren
* @date 2015年12月24日 上午11:13:09 
*  
*/
public class ImageDB {
	private static String url = "";
	/**
	* @Title: getConnection 
	* @Description: 连接数据库
	*/
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");//加载mysql的驱动
		url = "jdbc:mysql://" + Config.HOST + ":" + Config.PORT + "/" + Config.DB_NAME
	                          + "?user=" + Config.USER + "&password=" + Config.PASSWORD
	                          + "&amp;useUnicode=true&amp;characterEncoding=utf8";
		return DriverManager.getConnection(url);//获取数据库连接
	}
}
