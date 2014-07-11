package org.fishlab.test.util.digest;

import org.fishlab.util.digest.Num62;
import org.junit.Test;

public class TestNum62 {
	@Test
	public void main() {
		System.out.println(Num62.longToN62(20025l,8));
	}
}
