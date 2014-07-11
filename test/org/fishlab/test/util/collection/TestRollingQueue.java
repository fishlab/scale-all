package org.fishlab.test.util.collection;

import java.util.Queue;

import org.fishlab.util.collection.RollingQueue;
import org.junit.Test;

public class TestRollingQueue {
	@Test
	public void test(){
		RollingQueue<String> q=new RollingQueue<String>(15);
		for(int i=0;i<100;i++){
			q.offerFirst(""+i);
		}
		String str;
//		q.clear();
		System.out.println("=========size======="+q.size());
		
		while((str=q.poll())!=null){
			System.out.print(str+" ");
		}
	}
}
