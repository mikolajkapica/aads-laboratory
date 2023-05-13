package dsaa.lab09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class DisjointSetLinkedList implements DisjointSetDataStructure {

	private class Element{
		int representant;
		int next;
		int length;
		int last;
	}

	private static final int NULL=-1;

	Element arr[];

	public DisjointSetLinkedList(int size) {
		arr = new Element[size];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Element();
		}
	}

	@Override
	public void makeSet(int item) {
		arr[item].representant = item;
		arr[item].next = NULL;
		arr[item].length = 1;
		arr[item].last = item;
	}

	@Override
	public int findSet(int item) {
		return arr[item].representant;
	}

	@Override
	public boolean union(int itemA, int itemB) {
		int repA = arr[itemA].representant;
		int repB = arr[itemB].representant;
		if (repA == repB) {
			return false;
		}
		if (arr[repA].length < arr[repB].length) {
			int tmp = repA;
			repA = repB;
			repB = tmp;
		}
		// zaktualizowac dlugosc
		arr[repA].length += arr[repB].length;

		// zaktualizowac reprezentata drugiego zbioru elementow
		Element e = arr[repB];
		while (e.next != NULL) {
			e.representant = repA;
			e = arr[e.next];
		}
		e.representant = repA;

		// zaktualizowac nexta ogonu pierwszego
		arr[arr[repA].last].next = repB;

		// zaktualizowac tail heada pierwszego
		arr[repA].last = arr[repB].last;
		// usunac tail reprezentata drugiego
		arr[repB].last = NULL;
		return true;
	}


	@Override
	public String toString() {
		ArrayList<Integer> representants = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].representant == i) {
				representants.add(i);
			}
		}
		representants.sort((Integer::compare));
		StringBuilder sb = new StringBuilder();
		sb.append("Disjoint sets as linked list:\n");
		for (Integer r : representants) {
			sb.append(r).append(", ");
			while (arr[r].next != NULL) {
				r = arr[r].next;
				sb.append(r).append(", ");
			}

			sb.setLength(sb.length() - 2);
			sb.append("\n");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

}
