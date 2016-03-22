/**   
* @Title: MyThread.java 
* @Package com.io 
* @Description: TODO
* @author Huaigui Ren
* @date 2015年12月24日 下午12:53:45 
* @version V1.0   
*/
package com.io;
/*************************************************
 * @Title:  MyThread.java 
 * @Description:  TODO<请描述此文件是做什么的> 
 * @author:  Ren Huaigui
 * @time:  2015年12月24日 下午12:53:45 
 * @version:  V1.0 
 ************************************************/

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 将要写入文件的数据存入队列中
 */
class WriteQueue implements Runnable{
	private ConcurrentLinkedQueue<String> queue;
	private String contents;
	public WriteQueue(){}
	public WriteQueue(ConcurrentLinkedQueue<String> queue, String contents){
		this.queue = queue;
		this.contents = contents;
	}
	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		queue.add(contents);
	}
}
 
/**
 * 将队列中的数据写入到文件
 */
class WriteThread implements Runnable{
	private FileOutputStream out;
	private ConcurrentLinkedQueue<String> queue;
	public WriteThread(){}
	public WriteThread(FileOutputStream out,ConcurrentLinkedQueue<String> queue){
		this.out = out;
		this.queue = queue;
	}
	public void run() {
		while(true){//循环监听
			if(!queue.isEmpty()){ 
				try {
					out.write(queue.poll().getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
/** 
* @ClassName: MyThread 
* @Description: TODO
* @author Huigui Ren
* @date 2015年12月24日 下午12:53:45 
*  
*/
public class MyThread {

}
