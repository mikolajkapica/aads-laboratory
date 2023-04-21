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
		size = initCapacity;
	}

	public int hashFunctionDocPlace(int x) {
		int MODVALUE = 100000000;
		int hash = x;
		hash = (hash / 100) % MODVALUE;
		hash = (hash * 6) % MODVALUE;
		hash = (hash + x) % MODVALUE;
		return hash % (arr.length);
	}

	public boolean add(Object elem) {
		// if there is a list with the same name then return false
		if (get(elem) != null) {
			return false;
		}
		int hash = hashFunctionDocPlace(elem.hashCode());
		if (((float)(size + 1))/(float)arr.length > maxLoadFactor) {
			doubleArray();
			hash = hashFunctionDocPlace(elem.hashCode());
		}
		arr[hash].add(elem);
		size++;
		return true;
	}


	private void doubleArray() {
		LinkedList[] newArr = new LinkedList[arr.length * 2];
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = arr[i];
		}
		for (int i = arr.length; i < newArr.length; i++) {
			newArr[i] = new LinkedList();
		}
	}


	@Override
	public String toString() {
		// use	IWithName x=(IWithName)elem;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(i).append(": ");
			if (!arr[i].isEmpty()) {
				for (int j = 0; j < arr[i].size(); j++) {
					IWithName x = (IWithName) arr[i].get(j);
					sb.append(x.getName()).append(" ");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public Object get(Object toFind) {
		Document _toFind = (Document) toFind;
		int hash = hashFunctionDocPlace(_toFind.hashCode());
		for (Object d : arr[hash]) {
			Document _d = (Document) d;
			if (_d.equals(_toFind)) {
				return d;
			}
		}
		return null;
	}

}

