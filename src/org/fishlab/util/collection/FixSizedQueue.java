package org.fishlab.util.collection;

import java.util.Queue;

public interface FixSizedQueue<E> extends Queue<E>{
	public int getMaxSize();
}
