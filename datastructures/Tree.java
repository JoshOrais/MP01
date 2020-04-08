package datastructures;

import java.util.ArrayList;

public class Tree<T> {
	private ArrayList<Tree<T>> children;
	private T data;
	
	public Tree() {
		this(null);
	}
	
	public Tree(T data) {
		this.data = data;
		this.children = new ArrayList<Tree<T>>();
	}
	
	public ArrayList<Tree<T>> getChildren() {
		return children;
	}
	
	public void addChild(Tree<T> child) {
		children.add(child);
	}
	
	public void addChild(T child) {
		children.add( new Tree<T>(child) );
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	
	public int getSize() {
		int size = 1;
		for (Tree<T> child : children) {
			size+= child.getSize();
		}
		
		return size;
	}
}