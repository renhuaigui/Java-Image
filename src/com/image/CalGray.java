package com.image;

/*************************************************
 * @Title:  CalGray.java 
 * @Description: 计算灰度值特征
 * @author:  RenHuaigui
 * @data:  2015年9月24日 下午1:24:26 
 * @version:  V1.0 
 ************************************************/



public class CalGray {
	
	
	public double[] GrayImage(Image_matrix obj) {  //计算灰度特征
	/**********************************
	 * @Creat on: 
	 * @author: 
	 * @function: 计算灰度特征值用数组返回
	 * @param： 形参：图片灰度对象，返回值是灰度特征值
	 **********************************/
		int sgray[] = new int[256];//存储灰度值的频数
	    int width = obj.getwidth();
	   	int height = obj.getheight();
	   	int [][] grayTxt = obj.getGray();//存储每个像素点的灰度值
	   	int [][] g_touple = new int [256][256];//存储二元组
		double [] feature = new double[11];//两个特征
	   	double pgray[] = new double[256];

	   	//计算灰度数值和最值
	   
	   	int  temp = 0,x1=0, x2=0, y1=0,y2=0,step=3;//step 领域的步长
	   	for(int i=0; i<width; i++) {  
        	for(int j=0; j<height; j++) {  	                   
	        	sgray[grayTxt[i][j]] ++;//计算个灰度值的值
	            
	        	x1 = Math.max(i-step,0 );
	            x2 = Math.min(i+step, width-1);
	            y1 = Math.max(j-step,0);
	            y2 = Math.min(j+step, height-1);
	           // System.out.println(x1+" "+x2+" "+y1+" "+y2);
	            temp = (int)(grayTxt[x1][y2]+grayTxt[x1][j]+   //计算领域的灰度均值
	            			 grayTxt[x1][y1]+grayTxt[i][y1]+
	            			 grayTxt[x2][y1]+grayTxt[x2][j]+
	            			 grayTxt[x2][y2]+grayTxt[i][y2])/8;
	            g_touple[grayTxt[i][j]][temp]++;//统计领域二元组的频率
	            
	        }     
	   	}
	   	double sum = (double)width*height;
	   	for(int i=0; i<256; i++) { 
	   		pgray[i] = (double)sgray[i]/(sum);   // 各灰度值所占的概率
	   		
	   		if(feature[0]<sgray[i]) feature[0] = i;// 计算灰度众数
	   		if(pgray[i]>0)
 				feature[1] -= pgray[i] * (Math.log(pgray[i])/Math.log(10.0));//计算一维灰度熵
 			feature[2] += pgray[i] * pgray[i] ; //计算一维灰度能量 
 			feature[3] += i * pgray[i];  //计算灰度均值 			
	   	}

	   	for (int i = 0; i < 256; i++) {
			double avg = i- feature[3];
			feature[4] += avg*avg*pgray[i];
			feature[5] += avg*avg*avg*pgray[i];
			feature[6] += avg*avg*avg*avg*pgray[i];
		}
	   	
	   	feature[4] = Math.sqrt(feature[4]); //计算标准差
	   	feature[5] = feature[5] / (feature[4]*feature[4]*feature[4]); //计算偏态
	   	feature[6] = feature[6] / (feature[4]*feature[4]*feature[4]*feature[4]);  //计算峰态
	   	
	   	double [][] pij = new double[256][256];
	  	for (int i = 0; i < 256; i++) { //计算二维灰度特征
			for (int j = 0; j <256; j++) {
				pij[i][j] = (double)g_touple[i][j]/(sum);//计算概率值
				if(pij[i][j]>0)
					feature[7] -= pij[i][j] * (Math.log(pij[i][j])/Math.log(10.0)); //计算熵
				feature[8] += pij[i][j]*pij[i][j]; //计算能量
				feature[9] += (i - j) * (i - j) * pij[i][j];//计算对比度
				feature[10] +=  pij[i][j] / (1 + (i - j) * (i - j));  //计算逆值差
			}
			
		}
	  	return feature;
	} 
	public static void main(String[] args){  
    	
    }  
	
}