package dsaa.lab07;

import java.util.LinkedList;

public class HashTable {
	LinkedList arr[]; // use pure array
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private int size;
	private final double maxLoadFactor;

	public HashTable() {
		this(defaultInitSize);
	}

	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}


	public HashTable(int initCapacity, double maxLF) {
		this.maxLoadFactor=maxLF;
		arr = new LinkedList[initCapacity];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new LinkedList();
		}
		size = 0;
	}

	public int hashFunctionDocPlace(Object elem) {
		return elem.hashCode() % arr.length;
	}

	public boolean add(Object elem) {
		// if there is a list with the same name then return false
		if (get(elem) != null) {
			return false;
		}
		size++;
		if (((float)size) / ((float)arr.length) >= maxLoadFactor) {
			doubleArray();
		}
		int hash = hashFunctionDocPlace(elem);
		arr[hash].add(elem);
		return true;
	}


	public void doubleArray() {
		// store old array
		LinkedList[] oldArr = arr;
		// make it twice as big
		arr = new LinkedList[oldArr.length * 2];
		// initialize it
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new LinkedList();
		}
		// rehash all elements
		for (int i = 0; i < oldArr.length; i++) {
			for (int j = 0; j < oldArr[i].size(); j++) {
				int hash = hashFunctionDocPlace(oldArr[i].get(j));
				arr[hash].add(oldArr[i].get(j));
			}
		}
	}


	@Override
	public String toString() {
		// use	IWithName x=(IWithName)elem;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(i).append(":");
			if (!arr[i].isEmpty()) {
				sb.append(" ");
				for (int j = 0; j < arr[i].size(); j++) {
					IWithName x = (IWithName) arr[i].get(j);
					sb.append(x.getName()).append(", ");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public Object get(Object toFind) {
		int hash = hashFunctionDocPlace(toFind);
		for (Object d : arr[hash]) {
			if (d.equals(toFind)) {
				return d;
			}
		}
		return null;
	}

}

