/**   
* @Title: CalSIFT.java 
* @Package com.image 
* @Description: TODO
* @author Huaigui Ren
* @date 2016年1月14日 下午1:11:15 
* @version V1.0   
*/
package com.image;

import java.text.DecimalFormat;

import javax.sound.sampled.DataLine;

/** 
* @ClassName: CalSIFT 
* @Description: 对SIFT特征进行处理
* @author Huigui Ren
* @date 2016年1月14日 下午1:11:15 
*  
*/
public class CalSIFT {
	public String [][] siftfeature(int [][] data) {
		DecimalFormat df = new DecimalFormat("######0.00");
		double [] siftmean = new double[data[0].length];//均值
		double [] siftstd = new double[data[0].length];//方差
		double [] siftske = new double[data[0].length];//偏差（三阶矩）
		String [][] feature = new String[3][data[0].length];
		System.out.println(data.length);
		for (int i = 0; i < data[0].length; i++) {
			for (int j = 0; j < data.length; j++) {
				siftmean[i] += data[j][i];
			}
		}
		for (int i = 0; i < siftmean.length; i++) {
			siftmean[i] = siftmean[i]/data.length;
		}
		for (int i = 0; i < data[0].length; i++) {
			for (int j = 0; j < data.length; j++) {
				siftstd[i] = (data[j][i]-siftmean[i])*(data[j][i]-siftmean[i]);
				siftske[i] = siftstd[i]*(data[j][i]-siftmean[i]);
			}
		}
		for (int i = 0; i < siftstd.length; i++) {
			siftstd[i] = Math.sqrt(siftstd[i]/data.length);
			siftske[i] = Math.cbrt(siftske[i]/data.length);
		}
		for (int i = 0; i <siftmean.length; i++) {
			feature[0][i] = df.format(siftmean[i]);
			feature[1][i] = df.format(siftstd[i]);
			feature[2][i] = df.format(siftske[i]);
		}
		
		return feature;	
	}	
}
