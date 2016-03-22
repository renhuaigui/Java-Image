
package com.image;
/*************************************************
 * @Title:  CalHSV.java 
 * @Description:  计算HSV空间特征
 * @author:  RenHuaigui
 * @data:  2015年9月24日 下午1:21:37 
 * @version:  V1.0 
 ************************************************/

public class CalHSV {
	private  double sum;//像素个数
	public double [] CalHSVF(Image_matrix obj) {
		int [][] S = obj.getS();
		int [][] H = obj.getH();
		int [][] V = obj.getV();
		double [] s = new double[11];
		double [] h = new double[361];
		double [] v = new double[101];
		this.sum = obj.getheight()*obj.getwidth();
		
		double[] fmean = new  double[6];
		for (int i = 0; i < S.length; i++) {
			for (int j = 0; j < S[0].length; j++) {
				fmean[0] += H[i][j];//图片所有的像素色调均值
				fmean[1] += S[i][j];//图片所有的像素饱和度均值
				fmean[2] += V[i][j];//图片所有的像素亮度均值
				s[S[i][j]]++;
				h[H[i][j]]++;
				v[V[i][j]]++;
			}
		}
		for (int i = (int)(S.length/4); i < (int)(S.length*3/4); i++) {
			for (int j = (int)(S[0].length/4); j < (int)(S[0].length*3/4); j++) {
				fmean[3] += H[i][j];//图片中心区域的像素色调均值（等分成16个网格  中心区域为中间间4个网格）
				fmean[4] += S[i][j];//图片中心区域像素饱和度均值
				fmean[5] += V[i][j];//图片中心区域像素亮度均值
			}
			
		}
		for (int i = 0; i < 3; i++) {
			fmean[i] = fmean[i]/sum;
			fmean[i+3] = fmean[i+3]*4/sum;
		}
		double[] sf = calfeture(s);
		double[] hf = calfeture(h);
		double[] vf = calfeture(v);
		double [] feature = new double[sf.length+hf.length+vf.length+fmean.length];
		System.arraycopy(hf, 0, feature, 0, hf.length);  
		System.arraycopy(sf, 0, feature, hf.length, sf.length); 
		System.arraycopy(vf, 0, feature, sf.length+hf.length, vf.length);
		System.arraycopy(fmean, 0, feature, sf.length+hf.length+vf.length, fmean.length);
		return feature;
	}
	public double [] calfeture(double [] data){
		/**********************************
		* @author: Ren Huaigui
		* @function: 计算图片的颜色矩--颜色特征
		* @param： 
		**********************************/
		double [] feature = new double[3];
		int len = data.length;
		for(int i=0;i<len;i++){
			data[i] = data[i]/this.sum;
		}
		
		for (int i = 0; i < len; i++) {
			feature[0] +=i*data[i];
		}
		feature[0] =feature[0]/len;//计算一阶矩
		for (int i = 0; i < len; i++) {
			double avg = data[i] - feature[0];
			feature[1] += avg*avg*data[i];
			feature[2] += avg*avg*avg*data[i];
		}
		feature[1] = Math.sqrt(feature[1]/len);//计算二阶矩
		feature[2] = Math.cbrt(feature[2]/len);//计算三阶矩
		
		return feature;
	}
}

