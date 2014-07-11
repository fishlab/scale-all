package org.fishlab.util.collection;

import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RollingQueue<E> extends AbstractQueue<E> implements Serializable,
		Cloneable, Collection<E>, FixSizedQueue<E>,Deque<E> {
	private static final long serialVersionUID = 1L;
	private int maxSize;
//	private int maxPos;
	/**pa:head of queue,incluude,pb:tail of queue,include
	 * */
	private int pa,pb;
	private transient Object[] elementData;

	public RollingQueue(int maxSize) {
		if (maxSize < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + maxSize);
		this.maxSize = maxSize;
		//one element is wasted,whose position is pb
		this.elementData = new Object[maxSize+1];
	}

	/**always success because of overlay
	 * */
	@Override
	public boolean offer(E e) {
//		pa pb
//		== == == == == == ==
		this.elementData[this.pb++]=e;
		if(this.pb>this.maxSize){//end of array
			this.pb=0;
		}
		if(this.pb==this.pa){//offen queue full
			this.pa++;
			if(this.pa>this.maxSize){
				this.pa=0;
			}
		}
//		for(Object o:this.elementData){
//			System.out.print(o+" ");
//		}
//		System.out.println("pa:"+pa+",pb:"+pb);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E poll() {
		if(this.pa==this.pb){
			return null;
		}else {
			Object o=this.elementData[this.pa++];
			if(this.pa>this.maxSize){
				this.pa=0;
			}
			return (E) o;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E peek() {
		if(this.pa==this.pb){
			return null;
		}else {
			return (E)this.elementData[this.pa];
		}
	}

	@Override
	public Iterator<E> iterator() {
//		return new Itr();
		return null;
	}

	@Override
	public int size() {
		return this.pa>this.pb?this.maxSize-(this.pa-this.pb)+1:this.pb-this.pa;
	}

	@Override
	public int getMaxSize() {
		return this.maxSize;
	}
	
	@Override
	public void clear() {
		this.pa=this.pb=0;
	}
	private abstract class Itr implements Iterator<E>{
		//modify time should be added
		protected int cursor;
		protected int lastPos=-1;
		
		Itr(){
		}
		
//		@Override
//		public boolean hasNext() {
//			return this.cursor<RollingQueue.this.pb;
//		}

		@Override
		public E next() {
			
			return null;
		}

		@Override
		public void remove() {
			RollingQueue.this.remove();
		}
		
	}
	private class ABItr implements Iterator<E>{

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			
		}
		
	}
	
	@Override
	public void addFirst(E e) {
		this.offerFirst(e);
	}

	@Override
	public void addLast(E e) {
		this.offer(e);
	}

	@Override
	public boolean offerFirst(E e) {
		this.pa--;
		if(this.pa<0){//start of array
			this.pa=this.maxSize;
		}
		if(this.pa==this.pb){//offen queue full
			this.pb--;
			if(this.pb<0){
				this.pb=this.maxSize;
			}
		}
		this.elementData[this.pa]=e;
//		this.pa--;
//		if(this.pa==0){
//			this.pa=this.maxSize;
//		}
//		this.elementData[this.pa]=e;
//		if(this.pa==this.pb){//offen queue full
//			this.pb--;
//			if(this.pb==0){
//				this.pb=;
//			}
//		}
		return true;
	}

	@Override
	public boolean offerLast(E e) {
		return this.offer(e);
	}

	@Override
	public E removeFirst() {
		return this.remove();
	}

	@Override
	public E removeLast() {
		return this.pollLast();
	}

	@Override
	public E pollFirst() {
		return this.poll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E pollLast() {
		if(this.pa!=this.pb){//not empty
			Object o=null;
			if(this.pb<this.pa){
				if(this.pb!=0){
					o=this.elementData[--this.pb];
				}else{
					o=this.elementData[this.maxSize];
					this.pb=this.maxSize;
				}
			}else{
				
			}
			return (E)o;
		}
		return null;
	}

	@Override
	public E getFirst() {
		return this.element();
	}

	@Override
	public E getLast() {
		E e=this.peekLast();
		if(e==null){
			throw new NoSuchElementException();
		}
		return e;
	}

	@Override
	public E peekFirst() {
		return this.peek();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E peekLast() {
		if(this.pa!=this.pb){//not empty
			Object o=null;
			if(this.pb<this.pa){
				if(this.pb!=0){
					o=this.elementData[this.pb-1];
				}else{
					o=this.elementData[this.maxSize];
				}
			}
			return (E)o;
		}
		return null;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void push(E e) {
		this.offer(e);
	}

	@Override
	public E pop() {
		return this.remove();
	}

	@Override
	public Iterator<E> descendingIterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
