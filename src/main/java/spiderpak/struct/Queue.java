package spiderpak.struct;

import java.util.LinkedList;

@Deprecated
public class Queue {
	private LinkedList<Object> queue = new LinkedList<Object>();
	 void enQueue(Object t) {
		queue.addLast(t);
	}
	 void enfiQueue(Object t) {
		queue.addFirst(t);
	}
	public Object deQueue() {
		return queue.removeFirst();
	}

	public long size(){
		return queue.size();
	}

	// �ж϶����Ƿ����t
	public boolean contians(Object t) {
		return queue.contains(t);
	}

	public boolean empty() {
		return queue.isEmpty();
	}
}
