package dsaa.lab09;

public class DisjointSetForest implements DisjointSetDataStructure {

	private class Element{
		int rank;
		int parent;
	}

	Element []arr;

	DisjointSetForest(int size) {
		arr = new Element[size];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Element();
		}
	}

	@Override
	public void makeSet(int item) {
		arr[item].parent = item;
		arr[item].rank = 0;
	}

	@Override
	public int findSet(int item) {
		if (arr[item].parent == item) {
			return item;
		}
		int representant = findSet(arr[item].parent);
		arr[item].parent = representant;
		return representant;
	}

	@Override
	public boolean union(int itemA, int itemB) {
		Element repA = arr[findSet(itemA)];
		Element repB = arr[findSet(itemB)];

		if (repA.equals(repB)) {
			return false;
		}

		if (repA.rank > repB.rank) {
			repB.parent = repA.parent;
		} else if (repA.rank < repB.rank) {
			repA.parent = repB.parent;
		} else {
			repA.parent = repB.parent;
			repB.rank += 1;
		}

		return true;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Disjoint sets as forest:\n");
		for (int i = 0; i < arr.length; i++) {
			sb.append(i).append(" -> ").append(arr[i].parent).append("\n");
		}
		return sb.substring(0, sb.length()-1);
	}
}
