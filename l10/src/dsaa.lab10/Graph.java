package dsaa.lab10;


import java.util.*;

public class Graph {
	int arr[][];
	HashMap<String, Integer> doc2Int;
	Map.Entry<String, Document>[] arrDoc;

	public Graph(SortedMap<String, Document> internet) {
		int size=internet.size();
		arr=new int[size][size];
		doc2Int = new HashMap<>();
		arrDoc = (Map.Entry<String, Document>[])new Map.Entry[size];


		// doc2Int and arrDoc
		Document[] documentsNames = internet.values().toArray(new Document[size]);
		for (int i = 0; i < size; i++) {
			doc2Int.put(documentsNames[i].name, i);
			arrDoc[i] = new AbstractMap.SimpleEntry<>(documentsNames[i].name, documentsNames[i]);
		}

		// Matrix
		Iterator<Document> valuesIterator = internet.values().iterator();
		for (int i = 0; i < size; i++) {
			doc2Int.put(valuesIterator.next().name, i);
		}

		// fill arr with infinities
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				arr[i][j] = Integer.MAX_VALUE;
			}
		}

		// fill the diagonal with 0s
		for (int i = 0; i < size; i++) {
			arr[i][i] = 0;
		}

		// if there's a relation put link.weight
		for (Document document : internet.values()) {
			for (Link link : document.link.values()) {
				if (internet.containsKey(link.ref)) {
					arr[doc2Int.get(document.name)][doc2Int.get(link.ref)] = link.weight;
				}
			}
		}
//		 print arr
//		for (int i = 0; i < arr.length; i++) {
//			for (int j = 0; j < arr[i].length; j++) {
//				System.out.print(arr[i][j] + " ");
//			}
//			System.out.print('\n');
//		}
	}

	public String bfs(String start) {
		if (!doc2Int.containsKey(start))
			return null;

		Queue<Integer> queue = new LinkedList<>();
		Set<Integer> explored = new HashSet<>();
		StringBuilder sb = new StringBuilder();

		queue.offer(doc2Int.get(start));
		explored.add(doc2Int.get(start));

		while (!queue.isEmpty()) {
			int current = queue.poll();
			explored.add(current);
			sb.append(arrDoc[current].getKey()).append(", ");

			arrDoc[current].getValue().link.values().forEach(link -> {
				if (!explored.contains(doc2Int.get(link.ref))) {
					queue.offer(doc2Int.get(link.ref));
					explored.add(doc2Int.get(link.ref));
				}
			});

		}
		return sb.substring(0, sb.length() - 2);
	}

	public String dfs(String start) {
		if (!doc2Int.containsKey(start))
			return null;

		Set<Integer> explored = new HashSet<>();
		StringBuilder sb = new StringBuilder();

		dfsHelper(doc2Int.get(start), explored, sb);
		return sb.substring(0, sb.length() - 2);
	}

	private void dfsHelper(int start, Set<Integer> explored, StringBuilder sb) {
		if (explored.contains(start))
			return;

		explored.add(start);
		sb.append(arrDoc[start].getKey()).append(", ");
		arrDoc[start].getValue().link.values().forEach(link -> {
			if (!explored.contains(doc2Int.get(link.ref))) {
				dfsHelper(doc2Int.get(link.ref), explored, sb);
			}
		});
	}

	public int connectedComponents() {
		DisjointSetForest disjointSetForest = new DisjointSetForest(arr.length);
		// fill with indexes of documents
		for (int i = 0; i < arrDoc.length; i++) {
			disjointSetForest.makeSet(doc2Int.get(arrDoc[i].getKey()));
		}

		// union
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i][j] != Integer.MAX_VALUE && arr[i][j] != 0) {
					disjointSetForest.union(i, j);
				}
			}
		}

		return disjointSetForest.countSets();
	}
}

