package com.image;

/*************************************************
 * @Title:  CalGlcm.java 
 * @Description:  利用灰度矩阵计算纹理特征
 * @author:  RenHuaigui
 * @data:  2015年9月24日 下午1:31:34 
 * @version:  V1.0 
 ************************************************/



public class CalGlcm {
	private static int GLCM_CLASS= 16;//灰度分级等级
	
	public double [] CalTexture(Image_matrix obj,int GLCM_DIS_W ,int GLCM_DIS_H){
	/**********************************
	 * @Creat on: 
	 * @author: 
	 * @function:  //计算的特征值
	 * @param：obj图片对象 ，GLCM_DIS_W,GLCM_DIS_H角度
	 **********************************/
		
		double [] feature = new double[4]; //entropy = 0,energy = 0,contrast = 0,homogenity = 0; 
		int glcm [][] = new int [GLCM_CLASS][GLCM_CLASS];//存储共生矩阵
		double pglcm [][] = new double [GLCM_CLASS][GLCM_CLASS];//存储概率
		int width = obj.getwidth();
		int height = obj.getheight();
		int [][] Gray = obj.getGray();
		int [][] histImage = new int[width][height];
		
//		String angle = "_0";//角度
//		if(GLCM_DIS_W==1&&GLCM_DIS_H==0)
//			angle = "_0";
//		if(GLCM_DIS_W==1&&GLCM_DIS_H==1)
//			angle = "_45";
//		if(GLCM_DIS_W==0&&GLCM_DIS_H==1)
//			angle = "_90";
//		if(GLCM_DIS_W==-1&&GLCM_DIS_H==1)
//			angle = "_135";
		
		for(int i=0; i<width; i++) {  //计算图像的灰度值  并分成16个等级
			for(int j=0; j<height; j++) {  
				histImage[i][j] = (Gray[i][j]*GLCM_CLASS)/256;  //每个像素点对应的值/等级化
			} 
        }
		
	     //计算矩阵
		int l,k ;

/*		for(int i= 0;i < width; i++){//计算共生矩阵
			for(int j= 0;j < height; j++){
				l = histImage[i][j];
				k = histImage[(i+GLCM_DIS_W)%width][(j+GLCM_DIS_H)%height];
				glcm[l][k]++;
			}
		}*/
		for(int j= 0;j < height; j++){//计算共生矩阵
			for(int i= 0;i < width; i++){
				l = histImage[i][j];
				if(j + GLCM_DIS_H >= 0 && j + GLCM_DIS_H < height && i + GLCM_DIS_W >= 0 && i + GLCM_DIS_W < width)  
                {  
                    k = histImage[i + GLCM_DIS_W][j + GLCM_DIS_H];  
                    glcm[l][k]++;  
                }  
                if(j - GLCM_DIS_H >= 0 && j - GLCM_DIS_H < height && i - GLCM_DIS_W >= 0 && i - GLCM_DIS_W < width)  
                {  
                    k = histImage[i - GLCM_DIS_W ][j - GLCM_DIS_H];  
                    glcm[l][k]++;  
                }  
			}
		}
		
	
		double sum=0;
		for(int i =0; i < GLCM_CLASS; i++){
			for (int j = 0; j < GLCM_CLASS; j++) {
				sum += (double)glcm[i][j];
			}
		}
		for(int i =0; i < GLCM_CLASS; i++){
			for (int j = 0; j < GLCM_CLASS; j++) {
				pglcm[i][j] = glcm[i][j]/sum ;//计算分布概率
			}	
		}
				
		//计算特征
		for (int i = 0;i < GLCM_CLASS;i++){ 
			for (int j = 0;j < GLCM_CLASS;j++){ 
			 	//熵  
		    	if(glcm[i ][j] > 0)  
		    		feature[0] -= pglcm[i][j] * (Math.log(pglcm[i][j]));  
		    		//entropy -= pglcm[i][j] * (Math.log(pglcm[i][j])/Math.log(10.0));      
		    	//能量  （角二阶矩）
		        feature[1] += pglcm[i][j] * pglcm[i][j];  
		            //对比度  （主对角线惯性矩）
		        feature[2] += (i - j) * (i - j) * pglcm[i][j];  
		            //一致性  （局部平稳，逆值差）
		        feature[3] += 1.0 / (1 + (i - j) * (i - j)) * pglcm[i][j];  
			}  
		}
		

		return feature;

	}
	
	public double [] CalGLCM(Image_matrix obj) {
	/**********************************
	 * @Creat on:  2015年9月25日 下午2:36:49
	 * @author: Renhuaigui
	 * @function: 计算不同的角度的共生矩阵
	 * @param： obj图像对象，返回不同方向的纹理特征
	 **********************************/
		double [] feature = new double[16];
		
		double [] f0 = CalTexture(obj,1,0);//计算0°
		double [] f45 = CalTexture(obj,1,1);//计算45°
		double [] f90 = CalTexture(obj,0,1);//计算90°
		double [] f135 = CalTexture(obj,-1,1);//计算135°
		
		for (int i = 0; i < 4; i++) {
			feature[i] = f0[i];
			feature[i+4] = f45[i];
			feature[i+8] = f90[i];
			feature[i+12] = f135[i];
		}
		
		return feature;//返回四个方向的纹理特征
	}

	public static void main(String[] args) {  
    	
    }  
}