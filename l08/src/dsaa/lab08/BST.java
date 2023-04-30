package dsaa.lab08;

public class BST<T> {
	private class Node{
		T value;
		Node left,right,parent;

		public Node(T v) {
			value=v;
		}

		public Node(T value, Node left, Node right, Node parent) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}

	private Node root=null;

	public BST() {
	}

	public T getElement(T toFind) {
		Node currentNode = this.root;
		while (currentNode != null) {
			switch (((Comparable)toFind).compareTo(currentNode.value)) {
				case 0:
					return currentNode.value;
				case -1:
					currentNode = currentNode.left;
					break;
				case 1:
					currentNode = currentNode.right;
					break;
			}
		}
		return null;
	}

	public T successor(T elem) {
		Node currentNode = this.root;
		while (currentNode != null) {
			int relation = ((Comparable)elem).compareTo(currentNode.value);
			if (relation == 0) {
				if (currentNode.right != null) {
					currentNode = currentNode.right;
					while (currentNode.left != null) {
						currentNode = currentNode.left;
					}
					return currentNode.value;
				}
				if (currentNode.parent != null && currentNode.parent.left == currentNode) {
					return currentNode.parent.value;
				}
				return null;
			}
			if (relation < 0) {
				if (currentNode.left != null) {
					currentNode = currentNode.left;
				} else {
					return currentNode.value;
				}
			} else {
				if (currentNode.right != null) {
					currentNode = currentNode.right;
				} else {
					while (currentNode.parent != null && currentNode.parent.right == currentNode) {
						currentNode = currentNode.parent;
					}
					return currentNode.parent.value;
				}
			}
		}
		return null;
	}


	public String toStringInOrder() {
		StringBuilder sb = new StringBuilder();
		inOrderTraveler(sb, root);
		return sb.toString().length() < 2 ? "" : sb.substring(0, sb.length() - 2);
	}

	private void inOrderTraveler(StringBuilder sb, Node node) {
		if (node == null) {
			return;
		}
		inOrderTraveler(sb, node.left);
		sb.append(node.value).append(", ");
		inOrderTraveler(sb, node.right);
	}


	public String toStringPreOrder() {
		StringBuilder sb = new StringBuilder();
		preOrderTraveler(sb, root);
		return sb.length() < 2 ? "" : sb.substring(0, sb.length() - 2);
	}

	private void preOrderTraveler(StringBuilder sb, Node node) {
		if (node == null) {
			return;
		}
		sb.append(node.value).append(", ");
		preOrderTraveler(sb, node.left);
		preOrderTraveler(sb, node.right);
	}


	public String toStringPostOrder() {
		StringBuilder sb = new StringBuilder();
		postOrderTraveler(sb, root);
		return sb.length() < 2 ? "" : sb.substring(0, sb.length() - 2);
	}

	private void postOrderTraveler(StringBuilder sb, Node node) {
		if (node == null) {
			return;
		}
		postOrderTraveler(sb, node.left);
		postOrderTraveler(sb, node.right);
		sb.append(node.value).append(", ");
	}


	public boolean add(T elem) {
		if (root == null) {
			root = new Node(elem);
			return true;
		}
		Node currentNode = root;
		while (true) {
			int relation = ((Comparable)elem).compareTo(currentNode.value);
			if (relation == 0) {
				return false;
			}
			if (relation < 0) {
				if (currentNode.left == null) {
					currentNode.left = new Node(elem);
					currentNode.left.parent = currentNode;
					return true;
				}
				currentNode = currentNode.left;
			} else {
				if (currentNode.right == null) {
					currentNode.right = new Node(elem);
					currentNode.right.parent = currentNode;
					return true;
				}
				currentNode = currentNode.right;
			}
		}

	}

	public T remove(T value) {
		// find node
		Node node = root;
		while (node != null) {
			int relation = ((Comparable)value).compareTo(node.value);
			if (relation == 0) {
				break;
			} else if (relation < 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		// remove node
		// 0. node not found
		if (node == null) {
			return null;
		}
		// 1. node is leaf
		if (node.left == null && node.right == null) {
			if (node.parent == null) {
				root = null;
			} else {
				if (node.parent.left == node) {
					node.parent.left = null;
				} else {
					node.parent.right = null;
				}
			}
			return node.value;
		}
		// 2. node has one child
		if (node.left == null || node.right == null) {
			Node child = node.left == null ? node.right : node.left;
			if (node.parent == null) {
				child.parent = null;
				root = child;
			} else {
				if (node.parent.left == node) {
					node.parent.left = child;
					child.parent = node.parent;
				} else {
					node.parent.right = child;
					child.parent = node.parent;
				}
			}
			return node.value;
		}
		// 3. node has two children
		T valueToReturn = node.value;
		Node successor = new Node(successor(node.value));
		remove(successor.value);
		node.value = successor.value;
		return valueToReturn;
	}

	public void clear() {
		root = null;
	}

	public int size() {
		Node node = root;
		return sizeHelper(node);
	}

	private int sizeHelper(Node node){
		if (node == null) {
			return 0;
		}
		return sizeHelper(node.left) + sizeHelper(node.right) + 1;
	}

}
