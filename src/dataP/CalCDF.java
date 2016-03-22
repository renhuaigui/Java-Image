package dataP;

/*************************************************
 * @Title:  CalCDF.java 
 * @Description:  TODO<请描述此文件是做什么的> 
 * @author:  RenHuaigui
 * @data:  2015年9月24日 下午2:32:51 
 * @version:  V1.0 
 ************************************************/

public class CalCDF {
	
	public double [][] cdf(double [][] data){
	/**********************************
	* @Creat on:  2015年10月8日 下午1:07:53
	* @author: Ren Huaigui
	* @function: 计算累计分布
	* @param：data 数据，返回对应的累计分布值
	**********************************/
		double [][] cdfdata = new double[data.length][data[0].length];
		double [] sum = new double[data[0].length];
		for (int i = 0; i < data[0].length; i++) {
			for (int j = 0; j < data.length; j++) {
				sum[i] += data[i][j];// 求列和
			}
		}   
		
		for (int i = 0; i < data[0].length; i++) {
			for (int j = 0; j < data.length; j++) {
				if (j == 0) cdfdata[i][j] = data[i][j]/sum[i];
				else cdfdata[i][j] = cdfdata[i][j-1] + data[i][j]/sum[i];
				System.out.println(cdfdata[i][j]);
			}
		}   
		return cdfdata;
		
	}
}
