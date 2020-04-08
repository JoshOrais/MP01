package datastructures;

public class DynamicArray<T> {
	public static final int STARTING_SIZE = 10;
	private int size = 0;
	private T[] array = (T[])new Object[STARTING_SIZE];
	
	private void grow() {
		T[] n_array = (T[])new Object[array.length * 8 / 5];
		
		for (int i = 0; i < array.length; ++i) {
			n_array[i] = array[i];
		}
		
		array = n_array;
	}
	
	public void add(T a){
		if (size == array.length) grow();
		array[size++] = a;
	}
	
	public void remove(T a) {
		for (int i = 0; i < size; ++i) {
			if (a == array[i]) remove(i);
		}
	}
	
	public void remove(int index) {
		for (int i = index; i+1 < size; ++i) {
			array[i] = array[i + 1];
		}
		
		size--;
	}
	
	public T get(int index) {
		if (index >= size) return null;
		return array[index];
	}
	
	public int size() {
		return size;
	}
	
	public int getSize() {
		return size;
	}
	
	
}