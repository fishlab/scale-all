package org.fishlab.test.sync;

import org.junit.Test;

public class TestSync {
	private Object lock=new Object();
	
	class T1 implements Runnable{

		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(lock){
				lock.notify();
				System.out.println("notify");
			}
		}
		
	}
	class T2 implements Runnable{

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(lock){
				System.out.println("1");
				try {
					lock.wait();
					System.out.println("notified");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("2");
			}
		}
		
	}
	@Test
	public void test1(){
		new Thread(new T1()).start();
		new Thread(new T2()).start();
		try {
			Thread.sleep(1000*3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
