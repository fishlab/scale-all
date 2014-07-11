package org.fishlab.util.digest;

import java.util.Random;

public class KeyGenerator {
	private final static Random RAND;
	static {
		RAND = new Random();
	}

	public static final String randomKey(int length) {
		char ch[] = new char[length];
		for (int i = 0; i < length; i++) {
			ch[i] = Num62.N62_CHARS[RAND.nextInt(Num62.N62_CHARS_LEN)];
		}
		return new String(ch);
	}

}
