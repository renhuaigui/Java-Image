package dataP;

import java.io.IOException;

import com.io.WriteToTxt;
import com.io.readTxt;

/*************************************************
 * @Title:  dataDemo.java 
 * @Description: 数据处理
 * @author:  Ren Huaigui
 * @time:  2015年10月8日 下午12:46:37 
 * @version:  V1.0 
 ************************************************/


public class dataDemo {
	public void nor_cdf(String rpath,String w_path) throws IOException {
		readTxt r = new readTxt();
		double [][] nordata = r.readData(rpath);
		int len = nordata.length;
		//Normalization nor = new Normalization();
		//nordata = nor.Normal(nordata);
		CalCDF cal = new CalCDF();
		double[][] cdfdata = cal.cdf(nordata);
		
		double [][] data = new double[cdfdata.length][len + len];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if(j<len) data[i][j] = nordata[i][j];
				
				else data[i][j] = cdfdata[i][j-len];
			}
		}
		
		WriteToTxt wr = new WriteToTxt();
		wr.writeTxt(data, w_path);	
		System.out.println("End!");
	}
	
	public static void main(String[] args) throws IOException {
		String rpath = "E:/Desktop/Image/SVMData/sina/sina_gender_libsvmdata_scale.txt";
		String write_path = "E:/Desktop/gnuplot/sina/sina_gender_cdf.txt";
		dataDemo test = new dataDemo();
		System.out.println("cal cdf!");	
		test.nor_cdf(rpath,write_path);
	}
}
