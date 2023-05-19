package dsaa.lab10;

import javax.print.Doc;
import java.util.*;

public class Graph {
	int arr[][];
	//TODO? Collection to map Document to index of vertex
	// You can change it
	HashMap<String, Integer> doc2Int;

	//	@SuppressWarnings("unchecked")
	//TODO? Collection to map index of vertex to Document
	// You can change it
	Map.Entry<String, Document>[] arrDoc;

	// The argument type depend on a selected collection in the Main class
	public Graph(SortedMap<String,Document> internet){
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
					arr[doc2Int.get(link.ref)][doc2Int.get(document.name)] = link.weight;
				}
			}
		}
	}

	public String bfs(String start) {
		StringBuilder sb = new StringBuilder();
		Queue<Document> queue = new LinkedList<>();
		ArrayList<Document> explored = new ArrayList<>();

		// start
		Map.Entry<String, Document> startDocumentEntry = arrDoc[doc2Int.get(start)];

		// check if there is a vertex with start name
		if (startDocumentEntry == null) {
			return "";
		}

		// make and enqueue the document
		Document startDocument = startDocumentEntry.getValue();
		queue.offer(startDocument);

		do {
			Document currentDocument = queue.poll();
			sb.append(currentDocument.name).append(", ");
			explored.add(currentDocument);
			currentDocument.link.forEach((name, link) -> {
				Map.Entry<String, Document> neighbouringDocumentEntry = arrDoc[doc2Int.get(name)];

				// Check if there is a vertex for the linked neighbour
				if (neighbouringDocumentEntry == null) {
					return;
				}

				Document neighbouringDocument = neighbouringDocumentEntry.getValue();
				if (!explored.contains(neighbouringDocument)) {
					queue.offer(neighbouringDocument);
				}
			});
		} while (!queue.isEmpty());

		return sb.substring(0, sb.length() - 2);
	}

	public String dfs(String start) {
		StringBuilder sb = new StringBuilder();
		ArrayList<Document> explored = new ArrayList<>();
		Map.Entry<String, Document> startDocumentEntry = arrDoc[doc2Int.get(start)];
		// check if there is a vertex with start name
		if (startDocumentEntry == null) {
			return "";
		}
		Document startDocument = startDocumentEntry.getValue();
		dfsHelper(startDocument, sb, explored);
		return sb.substring(0, sb.length()-2);
	}

	public void dfsHelper(Document document, StringBuilder sb, ArrayList<Document> explored) {
		sb.append(document.name).append(", ");
		explored.add(document);
		document.link.forEach((name, link) -> {
			Map.Entry<String, Document> deeperDocumentEntry = arrDoc[doc2Int.get(name)];
			// Check if there is a vertex for the linked neighbour
			if (deeperDocumentEntry == null) {
				return;
			}

			Document deeperDocument = deeperDocumentEntry.getValue();
			if (!explored.contains(deeperDocument)) {
				dfsHelper(deeperDocument, sb, explored);
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
