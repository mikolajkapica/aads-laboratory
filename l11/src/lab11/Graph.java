package lab11;


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

//	public String DijkstraSSSP(String startVertexStr) {
//		if (!doc2Int.containsKey(startVertexStr))
//			return null;
//
//		int startVertex = doc2Int.get(startVertexStr);
//		int[] distance = new int[arr.length];
//		int[] parent = new int[arr.length];
//		boolean[] visited = new boolean[arr.length];
//		Arrays.fill(distance, Integer.MAX_VALUE);
//		Arrays.fill(parent, -1);
//		distance[startVertex] = 0;
//
//		for (int i = 0; i < arr.length; i++) {
//			int min = minDistance(distance, visited);
//			visited[min] = true;
//
//			 if (distance[min] == Integer.MAX_VALUE)
//				 break;
//
//			for (int j = 0; j < arr.length; j++) {
//				if (!visited[j] && arr[min][j] != Integer.MAX_VALUE && distance[min] + arr[min][j] < distance[j]) {
//					distance[j] = distance[min] + arr[min][j];
//					parent[j] = min;
//				}
//			}
//		}
//
//		StringBuilder sb = new StringBuilder();
//		for (int idx = 0; idx < arr.length; idx++) {
//			printPathTo(idx, distance, parent, sb);
//			if (distance[idx] != Integer.MAX_VALUE)
//				sb.append("=").append(distance[idx]);
//			sb.append('\n');
//		}
//
//		return sb.toString();
//	}

//	private int minDistance(int[] distance, boolean[] visited) {
//		int min = Integer.MAX_VALUE;
//		int minIndex = -1;
//
//		for (int i = 0; i < arr.length; i++) {
//			if (!visited[i] && distance[i] <= min) {
//				min = distance[i];
//				minIndex = i;
//			}
//		}
//		return minIndex;
//	}
	private void printPathTo(int idx, int[] distance, int[] parent, StringBuilder sb) {
		if (distance[idx] == Integer.MAX_VALUE) {
			sb.append("no path to ").append(arrDoc[idx].getKey());
			return;
		}
		if (parent[idx] == -1) {
			sb.append(arrDoc[idx].getKey());
			return;
		}
		printPathTo(parent[idx], distance, parent, sb);
		sb.append("->").append(arrDoc[idx].getKey());
	}

	// with priority queue
	public String DijkstraSSSP(String startVertexStr) {
		if (!doc2Int.containsKey(startVertexStr))
			return null;

		int startVertex = doc2Int.get(startVertexStr);
		int[] distance = new int[arr.length];
		int[] parent = new int[arr.length];
		boolean[] visited = new boolean[arr.length];
		Arrays.fill(distance, Integer.MAX_VALUE);
		Arrays.fill(parent, -1);
		distance[startVertex] = 0;

		PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(new Comparator<Pair<Integer, Integer>>() {
			@Override
			public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
				return o1.getValue() - o2.getValue();
			}
		});
		pq.offer(new Pair<>(startVertex, 0));

		while (!pq.isEmpty()) {
			Pair<Integer, Integer> current = pq.poll();
			if (visited[current.getKey()]) continue;
			else visited[current.getKey()] = true;

			for (int i = 0; i < arr.length; i++) {
				int newDistance;
				if (!visited[i] && arr[current.getKey()][i] != Integer.MAX_VALUE && (newDistance = distance[current.getKey()] + arr[current.getKey()][i]) < distance[i]) {
					distance[i] = newDistance;
					parent[i] = current.getKey();
					pq.offer(new Pair<>(i, distance[i]));
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < arr.length; idx++) {
			printPathTo(idx, distance, parent, sb);
			if (distance[idx] != Integer.MAX_VALUE)
				sb.append("=").append(distance[idx]);
			sb.append('\n');
		}

		return sb.toString();
 }
	 private class Pair<K, V> {
		 private K key; // document index
		 private V value; // distance

		 public Pair(K key, V value) {
			 this.key = key;
			 this.value = value;
		 }

		 public K getKey() {
			 return this.key;
		 }

		 public V getValue() {
			 return this.value;
		 }
	 }
}

