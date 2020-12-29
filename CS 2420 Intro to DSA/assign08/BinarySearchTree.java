package assign08;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Represents a generically-typed binary search tree.
 * 
 * For every node X, all elements in X's left subtree are smaller than X.element
 * and all elements in X's right subtree are larger then X.element.
 * 
 * @author Erin Parker && Kyle Kazemini && Joseph Nortron
 * @version March 5, 2020
 */
public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type> {

	/**
	 * Represents a (generic) node in a binary tree.
	 */
	private class BinaryNode<T> {

		public T element;

		public BinaryNode<T> left;

		public BinaryNode<T> right;
		
		public BinaryNode<T> parent;

		/**
		 * @return a string containing all of the edges in the tree rooted at "this"
		 *         node, in DOT format
		 */
		public String generateDot() {
			String ret = "\tnode" + element + " [label = \"<f0> |<f1> " + element + "|<f2> \"]\n";
			if(left != null)
				ret += "\tnode" + element + ":f0 -> node" + left.element + ":f1\n" + left.generateDot();
			if(right != null)
				ret += "\tnode" + element + ":f2 -> node" + right.element + ":f1\n" + right.generateDot();

			return ret;
		}
	}

	private BinaryNode<Type> root;
	
	private int size;

	/**
	 * Constructs a Binary Search Tree
	 */
	public BinarySearchTree() {
		root = new BinaryNode<Type>();
		size = 0;
	}

	/**
	 * Returns the item in the BST with the largest element value.
	 * 
	 * COST: O(tree height)
	 */
	public Type findMax() {
		BinaryNode<Type> temp = this.root;
		
		if (temp.element == null)
			return null;
			
		while (temp.right != null)
			temp = temp.right;

		return temp.element;
	}

	/**
	 * Returns the item in the BST with the largest element value.
	 * 
	 * COST: O(tree height)
	 */
	public Type findMin() {
		BinaryNode<Type> temp = this.root;
		
		if (temp == null)
			return null;
		
		while (temp.left != null)
			temp = temp.left;
		
		return temp.element;
	}

	/**
	 * Write a DOT representation of this BST to file.
	 * 
	 * @param filename
	 */
	public void generateDotFile(String filename) {
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println("digraph Tree {\n\tnode [shape=record]\n");

			if(root == null)
				out.println("");
			else
				out.print(root.generateDot());

			out.println("}");
			out.close();
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Ensures that this set contains the specified item.
	 * 
	 * @param item - the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         the input item was actually inserted); otherwise, returns false
	 */
	public boolean add(Type item) {
		if(this.root.element == null) {
			this.root.element = item;
			size++;
			return true;
		}
		
		if(this.root.right == null && root.element.compareTo(item) < 0) {
			this.root.right = new BinaryNode<Type>();
			this.root.right.element = item;
			this.root.right.parent = root;
			size++;
			return true;
		}
		
		if(this.root.left == null && root.element.compareTo(item) > 0) {
			this.root.left = new BinaryNode<Type>();
			this.root.left.element = item;
			this.root.left.parent = root;
			size++;
			return true;
		}
		
		BinaryNode<Type> temp = this.root;
		
		while (temp.left != null || temp.right != null) {
			if (temp.element.compareTo(item) == 0) 
				return false;
			
			if (temp.element.compareTo(item) < 0) {
				if (temp.right != null)
					temp = temp.right;
				else {
					temp.right = new BinaryNode<Type>();
					temp.right.parent = temp;
					temp.right.element = item;
					size++;
					return true;
				}
			}
			if (temp.element.compareTo(item) > 0) {
				if (temp.left != null)
					temp = temp.left;
				else {
					temp.left = new BinaryNode<Type>();
					temp.left.parent = temp;
					temp.left.element = item;
					size++;
					return true;
				}
			}
		} 
		
		if (temp.element.compareTo(item) > 0) {
			if (temp.left == null) {
				temp.left = new BinaryNode<Type>();
				temp.left.parent = temp;
				temp.left.element = item;
				size++;
				return true;
			}
		}
		if (temp.element.compareTo(item) < 0) {
			if (temp.right == null) {
				temp.right = new BinaryNode<Type>();
				temp.right.parent = temp;
				temp.right.element = item;
				size++;
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Ensures that this set contains all items in the specified collection.
	 * 
	 * @param items - the collection of items whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         any item in the input collection was actually inserted); otherwise,
	 *         returns false
	 */
	public boolean addAll(Collection<? extends Type> items) {
		boolean didAdd = false;
		
		for (Type i : items)
			if (add(i))
				didAdd = true;
		
		return didAdd;
	}

	/**
	 * Removes all items from this set. The set will be empty after this method
	 * call.
	 */
	public void clear() {
		this.root = new BinaryNode<Type>();
		this.root.element = null;
		this.size = 0;
	}

	/**
	 * Determines if there is an item in this set that is equal to the specified
	 * item.
	 * 
	 * @param item - the item sought in this set
	 * @return true if there is an item in this set that is equal to the input item;
	 *         otherwise, returns false
	 */
	@Override
	public boolean contains(Type item) {
		BinaryNode<Type> temp = this.root;
		
		if (temp.element == null)
			return false;
		
		while (temp.left != null || temp.right != null) {
			if(item.equals(temp.element))
				return true;
			
			if (temp.element.compareTo(item) < 0) {
				if (temp.right != null)
					temp = temp.right;
				else 
					return false;
			}
			if (temp.element.compareTo(item) > 0) {
				if (temp.left != null)
					temp = temp.left;
				else 
					return false;
			}
		}
		
 		if (temp.element.compareTo(item) == 0)
			return true;
		
		return false;
	}

	/**
	 * Determines if for each item in the specified collection, there is an item in
	 * this set that is equal to it.
	 * 
	 * @param items - the collection of items sought in this set
	 * @return true if for each item in the specified collection, there is an item
	 *         in this set that is equal to it; otherwise, returns false
	 */
	public boolean containsAll(Collection<? extends Type> items) {
		
		for (Type i : items)
			if (!contains(i))
				return false;
		
		return true;
	}

	/**
	 * Returns the first (i.e., smallest) item in this set.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	public Type first() throws NoSuchElementException {
		if (this.isEmpty())
			throw new NoSuchElementException();
		
		return findMin();
	}

	/**
	 * Returns true if this set contains no items.
	 */
	public boolean isEmpty() {
		if (this.root.element == null)
			return true;
		
		return false;
	}

	/**
	 * Returns the last (i.e., largest) item in this set.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 */
	public Type last() throws NoSuchElementException {
		if (this.isEmpty())
			throw new NoSuchElementException();
		
		return findMax();
	}

	/**
	 * Ensures that this set does not contain the specified item.
	 * 
	 * @param item - the item whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         the input item was actually removed); otherwise, returns false
	 */
	public boolean remove(Type item) {
		BinaryNode<Type> temp = this.root;
		
		if (temp.element == null)
			return false;
		
		while (temp.left != null || temp.right != null) {
			if (temp.element.compareTo(item) == 0) {
				if(temp.right == null && temp.left == null) {
					if(temp == root)
						root = new BinaryNode<Type>();
					else
						temp = null;
				}
				else if(temp.right == null || temp.left == null) {
					if(temp == root) {
						if(temp.right != null) 
							root = root.right;
						else
							root = root.left;
					}
					
					if(temp.parent.right.element.compareTo(temp.element) == 0)
						if(temp.right != null) {
							temp.parent.right = temp.right;
							temp.right.parent = temp.parent;
						}
						else {
							temp.parent.right = temp.left;
							temp.left.parent = temp.parent;
						}
					else
						if(temp.left != null) {
							temp.parent.left = temp.left;
							temp.left.parent = temp.parent;
						}
						else {
							temp.parent.left = temp.right;
							temp.right.parent = temp.parent;
						}
				}
				else {
					boolean hasLeft = false;
					BinaryNode<Type> smallest = temp.right;
					
					while(smallest.left != null) {
						smallest = smallest.left;
						hasLeft = true;
					}
					
					if(hasLeft) {
						temp.element = smallest.element;
						smallest.parent.left = smallest.right;
					}
					else {
						temp.element = temp.left.element;
						temp.left = temp.left.left;
					}
				}
				
				size--;
				
				return true;
			}
			
			if (temp.element.compareTo(item) < 0) {
				if (temp.right != null)
					temp = temp.right;
				else
					return false;
			}
			
			if (temp.element.compareTo(item) > 0) {
				if (temp.left != null)
					temp = temp.left;
				else
					return false;
			}
		}
		
		if (temp.element.compareTo(item) == 0) {
			if(temp.right == null && temp.left == null) {
				if(temp == root)
					root = new BinaryNode<Type>();
				else {
					temp.parent.right = null;
					temp.parent.left = null;
				}
			}
			else if(temp.right == null || temp.left == null) {
				if(temp == root) {
					if(temp.right != null) 
						root = root.right;
					else
						root = root.left;
				}
				
				temp.parent.right = temp.right;
				temp.parent.left = temp.left;
			}
			else {
				BinaryNode<Type> smallest = temp.right;
				
				while(smallest.left != null)
					smallest = smallest.left;
				temp.element = smallest.element;
				smallest = null;
			}
			size--;
			
			return true;
		}
		
		return false;
	}

	/**
	 * Ensures that this set does not contain any of the items in the specified
	 * collection.
	 * 
	 * @param items - the collection of items whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         any item in the input collection was actually removed); otherwise,
	 *         returns false
	 */
	public boolean removeAll(Collection<? extends Type> items) {
		boolean didRemove = false;
		
		for (Type i : items)
			if (remove(i))
				didRemove = true;
		
		return didRemove;
	}

	/**
	 * Returns the number of items in this set.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns an ArrayList containing all of the items in this set, in sorted
	 * order.
	 */
	public ArrayList<Type> toArrayList() {
		ArrayList<Type> list = inOrder();
		
		return list;
	}
	
	/**
	 * Returns a String representation of the tree.
	 */
	public String toString() {
		ArrayList<Type> list = this.toArrayList();
		String tree = "";
		
		for(Type i : list)
			tree += " " + i;
		
		return tree;
	}
	
	/**
	 * Private driver method for an 
	 * in order traversal of the tree.
	 * 
	 * @return ArrayList
	 */
	private ArrayList<Type> inOrder(){
		ArrayList<Type> list = new ArrayList<Type>();
		BinaryNode<Type> node = this.root;
		
		return inOrder(node, list);
	}
	
	/**
	 * Private recursive method for an 
	 * in order traversal of the tree.
	 * @param node
	 * @param list
	 * @return ArrayList
	 */
	private ArrayList<Type> inOrder(BinaryNode<Type> node, ArrayList<Type> list) {
		if (node != null) {
			inOrder(node.left, list);
			list.add(node.element);
			inOrder(node.right, list);
		}
		return list;
	}

}