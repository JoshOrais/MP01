package datastructures;

public class Queue<T> {
	Link<T> front = null;
	
	private class Link<T> {
		Link<T> next;
		T data;
		
		public Link(T data) {
			this.data = data;
		}
	}
	
	public void enqueue(T item) {
		Link<T> newLink = new Link<T>(item);
		
		if (front == null) {
			front = newLink;
			return;
		}
		
		Link<T> current = front;
		
		while(current.next != null) {
			current = current.next;
		}
		
		current.next = newLink;
	}
	
	public T dequeue() {
		if (front == null) return null;
		
		T ret = front.data;
		front = front.next;
		return ret;
	}
	
	public T peek() {
		if (front == null) return null;
		return front.data;
	}
}