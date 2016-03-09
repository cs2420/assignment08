package assignment08;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * A class representing a binary search tree.
 * 
 * @author Connor Ottenbacher and Doug Garding
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T> {

	private Node<T> root;
	private int size;

	public BinarySearchTree() {
		size = 0;
	}

	/**
	 * Recursive method to insert an item into the tree at its correct spot
	 * 
	 * @param item
	 *            to be inserted
	 * @param n
	 *            Node with which to begin the search
	 */
	private void add(T item, Node<T> n) {
		// If the item is smaller than the node
		if (item.compareTo(n.data) < 0) {
			// If we found an empty spot (null), put the item there
			if (n.left == null) {
				size++;
				n.left = new Node<T>(item);
			} else {
				add(item, n.left);
			}
		}
		// If the item is larger than the node
		else {
			// If we found an empty spot (null), put the item there
			if (n.right == null) {
				size++;
				n.right = new Node<T>(item);
			} else
				add(item, n.right);
		}
	}

	/**
	 * Ensures that this set contains the specified item. Driver method.
	 * 
	 * @param item
	 *            - the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if the input item was actually inserted); otherwise, returns
	 *         false
	 * @throws NullPointerException
	 *             if the item is null
	 */
	@Override
	public boolean add(T item) throws NullPointerException {
		nullCheck(item);
		// List is empty, root becomes this item
		if (root == null) {
			size++;
			root = new Node<T>(item);
			return true;
		}
		// List already contains the item, no need to add it
		if (contains(item)) {
			return false;
		}
		// Calls the recursive add method to insert item at the correct location
		add(item, root);
		return true;
	}

	/**
	 * Ensures that this set contains all items in the specified collection.
	 * 
	 * @param items
	 *            - the collection of items whose presence is ensured in this
	 *            set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if any item in the input collection was actually inserted);
	 *         otherwise, returns false
	 * @throws NullPointerException
	 *             if any of the items is null
	 */
	@Override
	public boolean addAll(Collection<? extends T> items) throws NullPointerException {
		int startSize = size;
		for (T item : items) {
			add(item);
		}
		return size > startSize;
	}

	/**
	 * Removes all items from this set. The set will be empty after this method
	 * call.
	 */
	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	/**
	 * Determines if there is an item in this set that is equal to the specified
	 * item. Driver Method.
	 * 
	 * @param item
	 *            - the item sought in this set
	 * @return true if there is an item in this set that is equal to the input
	 *         item; otherwise, returns false
	 * @throws NullPointerException
	 *             if the item is null
	 */
	@Override
	public boolean contains(T item) throws NullPointerException {
		nullCheck(item);
		if (root == null) {
			return false;
		}
		if (root.data.equals(item)) {
			return true;
		}
		return contains(item, root);
	}

	/**
	 * Recursive method determines if the list contains the specified element
	 * 
	 * @param item
	 *            element to be verified
	 * @param n
	 *            Node to begin the search with
	 * @return true if the element is contained in the list. Otherwise, returns
	 *         false
	 */
	private boolean contains(T item, Node<T> n) {
		// Item is less than the current node
		if (item.compareTo(n.data) < 0) {
			// If this left node is null, the item is not contained in the list
			if (n.left == null) {
				return false;
				// If this left node doesn't equal the item, continues searching
			} else if (!n.left.data.equals(item)) {
				return contains(item, n.left);
				// Left node equals the item, returns true
			} else {
				return true;
			}
			// Item is greater than or equal to the current node
		} else {
			// If the right node is null, item is not contained in the list
			if (n.right == null) {
				return false;
				// If the right node doesn't equal the item, continues searching
			} else if (!n.right.data.equals(item)) {
				return contains(item, n.right);
				// right node equals the item, returns true
			} else {
				return true;
			}
		}
	}

	/**
	 * Determines if for each item in the specified collection, there is an item
	 * in this set that is equal to it.
	 * 
	 * @param items
	 *            - the collection of items sought in this set
	 * @return true if for each item in the specified collection, there is an
	 *         item in this set that is equal to it; otherwise, returns false
	 * @throws NullPointerException
	 *             if any of the items is null
	 */
	@Override
	public boolean containsAll(Collection<? extends T> items) throws NullPointerException {
		for (T item : items) {
			if (!contains(item)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the first (i.e., smallest) item in this set. Driver Method.
	 * 
	 * @throws NoSuchElementException
	 *             if the set is empty
	 */
	@Override
	public T first() throws NoSuchElementException {
		nullCheck(root);
		return first(root);
	}

	/**
	 * Recursive method to find the smallest item in the set.
	 * 
	 * @param n
	 *            Node with which to begin the search
	 */
	private T first(Node<T> n) {
		// Farthest node to the left has been found, returns it's data
		if (n.left == null) {
			return n.data;
		}
		// Continues down the left
		return first(n.left);
	}

	/**
	 * Returns true if this set contains no items.
	 */
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the last (i.e., largest) item in this set. Driver Method.
	 * 
	 * @throws NoSuchElementException
	 *             if the set is empty
	 */
	@Override
	public T last() throws NoSuchElementException {
		nullCheck(root);
		return last(root);
	}

	/**
	 * Recursive method finds the largest item in the list
	 * 
	 * @param n
	 *            Node with which to begin the search
	 */
	public T last(Node<T> n) {
		// Farthest node to the right has been found, returns it's data
		if (n.right == null) {
			return n.data;
		}
		// Continues traversing down the right side
		return last(n.right);
	}

	/**
	 * Ensures that this set does not contain the specified item.
	 * 
	 * @param item
	 *            - the item whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if the input item was actually removed); otherwise, returns
	 *         false
	 * @throws NullPointerException
	 *             if the item is null
	 */
	@Override
	public boolean remove(T item) throws NullPointerException {
		nullCheck(item);

		// If the item is also the root
		if (this.root.data.equals(item)) {
			// If it has no children
			if (isLeafNode(root)) {
				clear();
				return true;
			} else {
				 remove(item, this.root);
				 return true;
			}
		}

		Node<T> parentNode = findNode(item, this.root);
		// If the item doesn't exist in the tree
		if (parentNode == null) {
			return false;
		}

		// If the item does exist in the tree
		remove(item, parentNode);
		return true;
	}

	/**
	 * Private helper method that does the recursive work of the public remove
	 * method.
	 * 
	 * @param item
	 * @param parentNode
	 * @return
	 */
	private void remove(T item, Node<T> parentNode) {
		
		// assign the node to be removed to nodeToRemove
		Node<T> nodeToRemove = isRight(parentNode, item)? parentNode.right: parentNode.left;
		System.out.println(nodeToRemove.data);

		if (isLeafNode(nodeToRemove)) {
			if(nodeToRemove == parentNode.left){
				parentNode.left = null;
				return;
			}
			else {
				parentNode.right = null;
				return;
			}
		}
		//Node to be removed has just one child
		if(nodeToRemove.left==null || nodeToRemove.right==null){
			if(nodeToRemove.right!=null){
				parentNode.left = nodeToRemove.right;
				return;
			}
			else{
				parentNode.right = nodeToRemove.left;
				return;
			}
			
		}
	}

	/**
	 * Private Helper method to determine if a node is a leaf.
	 * 
	 * @param nodeToRemove
	 * @return
	 */
	private boolean isLeafNode(Node<T> nodeToRemove) {
		if (nodeToRemove.left == null && nodeToRemove.right == null) {
			return true;
		}
		return false;
	}
	
	private boolean isRight(Node<T> n, T item){
		if (n.left != null) {
			if (n.left.data.equals(item)) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Private helper method that returns the parent of the node that we're
	 * searching for. If the node doesn't exist, returns null. If the node is
	 * the root, returns null.
	 * 
	 * @param item
	 *            element to be verified
	 * @param n
	 *            Node to begin the search with
	 * @return Node<T>
	 */
	private Node<T> findNode(T item, Node<T> n) {
		// If node n has no children, item doesn't exist
		if (n.left == null && n.right == null) {
			return null;
		}

		// If node n has 2 children
		if (n.left != null && n.right != null) {
			// If left child has item
			if (n.left.data.equals(item)) {
				return n;
			}
			if (n.right.data.equals(item)) {
				return n;
			}

			if (n.data.compareTo(item) < 0) {
				return findNode(item, n.right);
			} else {
				return findNode(item, n.left);
			}

		}

		// If node n has only a left child
		if (n.left != null && n.right == null) {
			if (n.left.data.equals(item)) {
				return n;
			}
			return findNode(item, n.left);
		}

		// If node n has only a right child
		if (n.right != null && n.left == null) {
			if (n.right.data.equals(item)) {
				return n;
			}
			return findNode(item, n.right);
		}

		return null;
	}

	@Override
	public boolean removeAll(Collection<? extends T> items) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public ArrayList<T> toArrayList() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Helper methods throw NullPointerException if input is null
	 */
	public void nullCheck(T item) throws NullPointerException {
		if (item == null) {
			throw new NullPointerException();
		}
	}

	public void nullCheck(Node<T> item) throws NullPointerException {
		if (item == null) {
			throw new NullPointerException();
		}
	}

	/**
	 * Node Class that stores elements for a Binary Search Tree.
	 *
	 * @param <E>
	 */
	private static final class Node<T> {

		T data; // The element contained in the node.
		Node<T> left; // Daughter node to the left.
		Node<T> right; // Daughter node to the right.

		/**
		 * Default constructor for a node.
		 * 
		 * @param data
		 *            the list element
		 */
		Node(T data) {
			this.data = data;
		}
	}

	// Driver for writing this tree to a dot file
	public void writeDot(String filename) {
		try {
			// PrintWriter(FileWriter) will write output to a file
			PrintWriter output = new PrintWriter(new FileWriter(filename));

			// Set up the dot graph and properties
			output.println("digraph BST {");
			output.println("node [shape=record]");

			if (root != null)
				writeDotRecursive(root, output);
			// Close the graph
			output.println("}");
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Recursive method for writing the tree to a dot file
	private void writeDotRecursive(Node<T> n, PrintWriter output) throws Exception {
		output.println(n.data + "[label=\"<L> |<D> " + n.data + "|<R> \"]");
		if (n.left != null) {
			// write the left subtree
			writeDotRecursive(n.left, output);

			// then make a link between n and the left subtree
			output.println(n.data + ":L -> " + n.left.data + ":D");
		}
		if (n.right != null) {
			// write the left subtree
			writeDotRecursive(n.right, output);

			// then make a link between n and the right subtree
			output.println(n.data + ":R -> " + n.right.data + ":D");
		}

	}

}
