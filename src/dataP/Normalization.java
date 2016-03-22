package dataP;
/*************************************************
 * @Title:  Normalization.java 
 * @Description: 数据归一化(最大-最小标准化)
 * @author:  RenHuaigui
 * @data:  2015年9月24日 下午2:30:41 
 * @version:  V1.0 
 ************************************************/

public class Normalization {
	public double [][] Normal(double [][] data){//二维数组归一化
	/**********************************
	 * @Creat on: 
	 * @author: 
	 * @function: 归一化数据
	 * @param：形参是需要归一化的数据，返回归一化后的数据
	 **********************************/
		int width = data.length;
		int height = data[0].length;
		double [][] normal = data;
		double [] max = new double[height];
		double [] min = new double[height];
		
		for (int i = 0; i < min.length; i++) {
			max[i] = min[i] = normal[0][i];
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if(max[j] < normal[i][j]) max[j] = normal[i][j];
				if(min[j] > normal[i][j]) min[j] = normal[i][j];
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				normal[i][j] = (normal[i][j] - min[j])/(max[j]-min[j]);
				System.out.println(normal[i][j]);
			}
		}
		return normal;
	}
	public double [] Normal(double [] datab){//一维数组归一化
	/**********************************
	 * @Creat on:  2015年9月25日 下午2:36:05
	 * @author: Renhuaigui
	 * @function: 归一化一维数组
	 * @param： 
	 **********************************/
		double max = datab[0], min = datab[0];
		double [] data = datab;
		for (int i = 0; i < data.length; i++) {
			if (max < data[i]) max = data[i];
			if (min > data[i]) min = data[i];
		}
		for (int i = 0; i < data.length; i++) {
			data[i] = (data[i]-min)/(max-min);
		}
		return data;
	}
}
