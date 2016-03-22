package com;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


/**.
 * 配置文件
 */
public class Config {
    /**数据库主机 */
    public static  String HOST ;
    /** 端口 */
    public static  String PORT ;
    /** 数据库名 */
    public static  String DB_NAME ;
    /** 登录用户 */
    public static String USER ;
    /** 密码 */
    public static  String PASSWORD ;
    /** 图片存储路径  */
    public static String IMG_STORE_PATH;
    /** @Fields 用户列表 */
    public static String USERLIST;
    /**睡眠时间*/
    public static  long THREAD_SLEEP ; //,
    /** 用户ID */
    public static  String UID ; //
    /** 开始时间 */
    public static  String START_DATE ;
    /**结束时间 */
    public static  String END_DATE ;
    /** 登录 */
    public static  boolean IS_LOGIN ; //
    /** 缓存 */
    //public static String cookie = "YF-Ugrow-G0=fe7d42b1b76649f2dada9f1d178cfe5f; SUS=SID-5214997831-1406450565-JA-q2h63-4d9318254db199029f41558bf39776bb; SUBP=002A2c-gVlwEm1uAWxfgXELuuu1xVxBxAACEqUoIrJDib-GEmwxmIOVuHY-u_1%3D; WBtopGlobal_register_version=a8cf3e66ae57c88a; YF-V5-G0=8a3c37d39afd53b5f9eb3c8fb1874eec; un=cisl001@163.com; SUE=es%3D1a75e2d3243fa2bc6f38440850be800d%26ev%3Dv1%26es2%3D3b0ae5c827290a0502131e411a3778c7%26rs0%3DxuE%252FZBhtM7ESlsPlOBbl1xBjRJbh8rePM48d7Z3kvwXPwfGhDdmsNzrm6HbYkMqwiBpcgLLnKJmusWUrAU1ouh3aWhMJQip9PKrgxKdzKx%252B2LQjtTJ1%252BQUv1TBH4O2v0kQ%252B1%252FZxux9%252FsSmYoWHdWUSMLG9jvDXcgwYFrKzv2vsg%253D%26rv%3D0; Apache=4472390254035.896.1406450558105; SINAGLOBAL=4472390254035.896.1406450558105; ULV=1406450558209:1:1:1:4472390254035.896.1406450558105:; SUB=AasoDmb02IJ5IJGyT5jWWe0HcsJzz37Kwy6hrsdBEgo5RmWeRD%2F1Nu6oqABINoBiTSwsOGGUVw6bRhookrJuU7OYOr5nnIIZ5AUULqL2f8BGqEBJ5g4toKqLMMqX5f7TSg5c0bbVSZgAD7FsK4vi4Fo%3D; ALF=1437986565; YF-Page-G0=f9cf428f7c30a82f1d66fab847fbb873; SSOLoginState=1406450565; SUP=cv%3D1%26bt%3D1406450565%26et%3D1406536965%26d%3Dc909%26i%3D76bb%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D%26st%3D0%26uid%3D5214997831%26name%3Dcisl001%2540163.com%26nick%3DCISL001%26fmp%3D%26lcp%3D; _s_tentry=passport.weibo.com;";
    public static String cookie = "UV5PAGE=usr513_105; SUB=_2AkMkrONNa8NlrAZTmfwcxWzqb4xH-jyXeum7An7uJhIyHRgv7mlVqSXB1ixKoXh4q9nVoluyxGwVZACwBw..; SUE=es%3Ddb5d71fde9487bc9d173b8191519bf56%26ev%3Dv1%26es2%3D81216376ba829d4f1e80e70fe1c81774%26rs0%3DiCP1UGJ7J%252FXOCLSo5xDLhhFW65QF69lm1HX32JELz2E5jA86g30yW0GfsQYF0rnfbE4Z8Dw7CkhgOGRNLevhiTvfcbon4WdQLgmjI5%252BmW6qduieE4tVlMOt409DE2IAO2EB8KZWACfO9SSv4X46fiJLbU9LHQN%252FU%252FCp4eNrxtj0%253D%26rv%3D0; UUG=usr1031; WBtopGlobal_register_version=c9fee367f33d1579; Apache=5157020865202.9.1408264981413; SINAGLOBAL=5157020865202.9.1408264981413; v5reg=usr1024; SUP=cv%3D1%26bt%3D1408265338%26et%3D1408351738%26d%3Dc909%26i%3De2b1%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D2%26st%3D0%26uid%3D5214997831%26name%3Dcisl001%2540163.com%26nick%3DCISL001%26fmp%3D%26lcp%3D; SUS=SID-5214997831-1408265337-GZ-7k34o-58ca42396687ddb4f628005cb088e2b1; SSOLoginState=1408265338; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWeMVl0.mTU4dXQM7f97.LP5JpX5K2t; _s_tentry=passport.weibo.com; un=cisl001@163.com; ALF=1439801337; ULV=1408264981432:1:1:1:5157020865202.9.1408264981413:;";

	/*
	 public static final String configurefile = "configure.properties";
	 public static final String HOST ;//= PropertiesLoader.get(configurefile, "HOST");
	 public static final String PORT;// = PropertiesLoader.get(configurefile, "PORT");
	 public static final String DB_NAME = PropertiesLoader.get(configurefile, "DB_NAME");
	 public static final String USER = PropertiesLoader.get(configurefile, "USER");
	 public static final String PASSWORD = PropertiesLoader.get(configurefile, "PASSWORD");
	 public static final long THREAD_SLEEP = PropertiesLoader.getLong(configurefile, "THREAD_SLEEP"); 
	 public static final String UID = PropertiesLoader.get(configurefile, "UID"); 
	 public static final String START_DATE = PropertiesLoader.get(configurefile, "START_DATE");
	 public static final String END_DATE = PropertiesLoader.get(configurefile, "END_DATE");
	 public static final boolean IS_LOGIN = PropertiesLoader.getBoolean(configurefile, "IS_LOGIN"); 
	 public static String cookie = PropertiesLoader.get(configurefile, "cookie");*/
	/**
	* <p>Title: </p>
	* <p>Description:配置所需参数 </p>
	*/
	public Config() {
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream("config/config.properties"));
			Properties properties = new Properties();
			properties.load(in);
			HOST = properties.getProperty("HOST");
			PORT = properties.getProperty("PORT");
			DB_NAME = properties.getProperty("DB_NAME");
			USER = properties.getProperty("USER");
			PASSWORD = properties.getProperty("PASSWORD");
			IMG_STORE_PATH = properties.getProperty("IMGPATH");
			//USERLIST = properties.getProperty("UserList");
			//THREAD_SLEEP = Long.parseLong(properties.getProperty("THREAD_SLEEP"));
			//UID = properties.getProperty("UID");
			//START_DATE = properties.getProperty("START_DATE");
			//END_DATE = properties.getProperty("END_DATE");
			//IS_LOGIN = properties.getProperty("IS_LOGIN").equals("true");
			//cookie  = properties.getProperty("cookie");
			in.close();
		}catch (Exception e) { 
			System.out.println("出错了");
			e.printStackTrace();  
		}		
	}
}
