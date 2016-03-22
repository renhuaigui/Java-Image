/*************************************************
 * @Title:  Image_matrix.java 
 * @Description:  图像类
 * @author:  RenHuaigui
 * @data:  2015年9月24日 下午1:20:26 
 * @version:  V1.0 
 ************************************************/
package com.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.features2d.*;

public class Image_matrix {
	private int width;//图像宽度
	private int height;//图像高度
	private int [][] gray;//图像灰度矩阵	
	private int [][] S;//饱和度
	private int [][] V;//亮度
	private int [][] H;//色调
	private double [][] Alpha;//alpha 通道的  表示图像的透明度
	private double [][] iR;
	private double [][] iG;
	private double [][] iB;
	private double [][] sobels; //sobels 算子
	private int [][] sift; // sift特征

	
	
	/**
	 * @param image 图片构造函数 建立相关信息
	 * @throws IOException
	 */
	public Image_matrix(BufferedImage image)throws IOException{
		this.width = image.getWidth();
		this.height = image.getHeight(); 
		this.gray = new int [width][height];
		this.S = new int [width][height];
		this.H = new int [width][height];
		this.V = new int [width][height];
		this.Alpha = new double [width][height]; 
		this.iB = new double [width][height];
		this.iG = new double [width][height];
		this.iR = new double [width][height];
		this.sobels = new double [width][height];
		
		
		for(int i=0; i<width; i++) {  
        	for(int j=0; j<height; j++) {  	                 
        		int rgb = image.getRGB(i, j);  
        		this.Alpha[i][j] = (rgb & 0xff000000) >> 24;
	         	
        		int r = (rgb & 0xff0000) >> 16;  
	            int g = (rgb & 0xff00) >> 8;  
	            int b = (rgb & 0xff);  
	            iR[i][j] = r;
	            iG[i][j] = g;
	            iB[i][j] = b;
	           
	            //计算灰度值  
	            this.gray[i][j] = (int)(r * 0.299 + g * 0.587 + b * 0.114); //每个像素点对应的值
	            
	            //计算HSV空间的值
	            double R = (double)r/(r+g+b);
	            double B = (double)b/(r+g+b);
	            double G = (double)g/(r+g+b);
	            double max = Math.max(R, Math.max(G,B));
	            double min = Math.min(R, Math.min(G, B));
	            this.V[i][j] = (int)(max*100);//V值（0-100）
	            if (max == 0 || (max-min == 0)) {
	            	this.S[i][j] = this.H[i][j] = 0;
					
				}
	           
	            else {
	            	double temp=0;
	            	this.S[i][j] = (int)(((max-min)/max)*10);//值（1-10）
	            	if(R==max)
	            		temp = (G-B)/(max-min);
	            	else if(G==max)
	            		temp = 2+(B-R)/(max-min);
	            	else if(B==max)
	            		temp = 4+(R-G)/(max-min);
	            	this.H[i][j] = (int)(temp*60);
	            	if(this.H[i][j]<0) this.H[i][j] +=360;//H值（0-360）
				}
	       	}     
	   	}
		//计算sobel算子
		double	fx, fy;
		for(int i = 1 ; i < this.width - 1 ; i++ ){
			for(int j = 1 ; j < this.height - 1 ; j++ ){
				fx=(this.gray[i-1][j-1] + 2*this.gray[i-1][j] + this.gray[i-1][j+1])	
					-(this.gray[i+1][j-1] + 2*this.gray[i+1][j] + this.gray[i+1][j+1]);
				fy=(this.gray[i-1][j-1] + 2*this.gray[i][j-1] + this.gray[i+1][j-1])
					-(this.gray[i-1][j+1] + 2*this.gray[i][j+1] + this.gray[i+1][j+1]);
				this.sobels[i][j]= Math.abs(fx) > Math.abs(fy) ? Math.abs(fx) :  Math.abs(fy) ;			
			}
		}
		
	}
	
	/** 
	* @Title: setsift 
	* @Description: 设置SIFT特征
	* @param file 
	* @return void  
	* @throws 
	*/
	public void setsift(String file) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat test_mat = Highgui.imread(file);
		Mat desc = new Mat();
		FeatureDetector fd = FeatureDetector.create(FeatureDetector.SIFT);
		MatOfKeyPoint mkp =new MatOfKeyPoint();
		fd.detect(test_mat, mkp);
		DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		de.compute(test_mat,mkp,desc );//提取sift特征
		sift = new int[desc.rows()][desc.cols()];
		for (int i = 0; i < desc.rows(); i++) {
			for (int j = 0; j < desc.cols(); j++) {
				sift[i][j] = (int) desc.get(i, j)[0];
				//System.out.println(sift[i][j]);
			}
		}
	}
	
	
	//获取图像的对应数据
	public int [][] getsift(){
		return this.sift;
	}
	public  int [][] getGray(){
		return this.gray;
	}
	public int getwidth(){
		return this.width;
	}
	public int getheight() {
		return this.height;
	}
	public int [][] getS(){
		return this.S;
	}
	public int [][] getH(){
		return this.H;
	}
	public int [][] getV(){
		return this.V;
	}
	public double [][] getAlpha(){
		return this.Alpha;
	}
	public double [][] getR(){
		return this.iR;
	}
	public double [][] getG(){
		return this.iG;
	}
	public double [][] getB(){
		return this.iB;
	}
	public double[][] getsobels(){
		return this.sobels;
	}
}

//Raster ra = image.getData();     
  /*图像的类型，看看它是多少位的.如果是32位         	
   * 的要考虑aphal值通道,通过Raster对象来读取/写入像素，          	
   * 它自动帮你处理成为32位的. */  
    
  /*Rectangle rect = ra.getBounds(); 
  int w = (int) rect.getWidth(); 
  int h = (int) rect.getHeight();*/  
 //System.out.println(width + ":" + height);  
 //System.out.println(w + ":" + h);  
    
//  int pixels[] = new int[width * height];  
//  pixels = ra.getPixels(0, 0, width, height, pixels); //获得图片每个点的像素 