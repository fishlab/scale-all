package com.czway.util.langs;
import java.util.Random;

public class MyRandom extends Random {
	private static final long serialVersionUID = 1L;

	public boolean nextBoolean0(int p) {
		int r = 0;		
		r = this.nextInt(1000);
		if (r <= p)
			return true;
		else
			return false;
	}

	public boolean nextBoolean(int p) {
		int n = 1000;
//		p*=1;
//		if (n <= 0)
//			throw new IllegalArgumentException("n must be positive");
//		if ((n & -n) == n) // i.e., n is a power of 2
//		{
//			System.out.println(1000.0);
//			return ((n * (long) next(31)) >> 31) < p;
//		}
		int bits, val;
		do {
			bits = next(31);
			val = bits % n;
		} while (bits - val + (n - 1) < 0);
		return val <= p;
	}

}
