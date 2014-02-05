package tree;

import java.util.*;
/**
 * A class to implement an abstract data type for general trees.
 * @author Mark Mercurio
 *
 * @version 1.0
 */
public class Tree<V> {

	private ArrayList<Tree<V>> children;
	private V value;
	
	/**
	 * Creates a new Tree with the specified value and children
	 * @param value The value of the node.
	 * @param children The children of the node.
	 */
	public Tree(V value, Tree<V>... children) {
		this.children = new ArrayList<Tree<V>>(Arrays.asList(children));
		this.value = value;
	}
	
	/**
	 * Returns the node's value.
	 * @return The node's value.
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * Returns the first child of this nose.
	 * @return The first child of this node.
	 */
	public Tree<V> firstChild() {
		if (children.size() > 0) return children.get(0);
		else return null;
	}

	/**
	 * Returns the last child of this nose.
	 * @return The last child of this node.
	 */
	public Tree<V> lastChild() {
		if (this.children.size() > 0) return children.get(children.size() - 1);
		else return null;
	}
	
	/**
	 * Returns the number of children of this node.
	 * @return the number of children of this node.
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**
	 * Returns the child at the specified index.
	 * @return The specified child.
	 * @throws NoSuchElementException If the index is out of range.
	 */
	public Tree<V> child(int index) throws NoSuchElementException {
		if (index < 0 || index >= children.size()) {
			throw new NoSuchElementException();
		}
		
		return children.get(index);
	}
	
	/**
	 * Returns an iterator over the children of the node in proper 
	 * sequence.
	 * @return an iterator over the elements in this list in proper sequence.
	 */
	public Iterator<Tree<V>> children() {
		return children.iterator();
	}
	
	/**
	 * Returns true if a node is a leaf (ie. has no children).
	 * @return true if this node is a leaf.
	 */
	public boolean isLeaf() {
		return children.size() == 0;
	}
	

	private boolean contains(Tree<V> node) {
		if (this == node) return true;
		
		
		for (Tree<V> child : this.children) {
			if (child.contains(node)) return true;
		}
		
		return false;
	}
	
	/**
	 * Compares the specified Object with this node for equality.
	 * Returns true if the specified Object is a Tree, has the same value as
	 * this Tree, and each child of the specified Tree <code>equals</code> the 
	 * children of this Tree.
	 * @return true if the Object equals this tree.
	 */
	@Override 
	public boolean equals(Object object) {
		if (this == object) return true;
		
		if (!(object instanceof Tree)) return false;

		Tree<?> tree = (Tree<?>)object;
		
		if (!tree.value.equals(this.value)) return false;
		
		if (this.children.size() != tree.children.size()) return false;
		
		for (int i = 0; i < this.children.size(); i++) {
			if (!this.children.get(i).equals(tree.children.get(i)))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Returns a string representation of the Tree. The string representation
	 * of this Tree consists of this Tree's value, and each of the String representations
	 * of this Tree's children on new lines and indented two spaces.
	 * @return The String representation.
	 */
	@Override
	public String toString() {
		return generateString(0, "");
	}
	
	private String generateString(int indent, String s) {
		
		for (int i = 0; i < indent; i++) 
			s += "  ";
		
		s += this.value;
		
		s += "\n";
		
		//Recursively add in children's strings
		for (Tree<V> child : this.children) {
			s = child.generateString(indent + 1, s);
		}
				
		return s;
	}
	
	/**
	 * Sets the value of this node to the specified value.
	 * @param value The value.
	 */
	public void setValue(V value) {
		this.value = value;
	}
	
	/**
	 * Adds the specified child to end of this Tree's children. 
	 * @param newChild The Tree to add as a child.
	 * @throws IllegalArgumentException if the specified child will create a loop in this Tree.
	 */
	public void addChild(Tree<V> newChild) throws IllegalArgumentException {
		if (newChild.contains(this)) throw new IllegalArgumentException();
		this.children.add(newChild);
	}
	
	
	/**
	 * Adds a specified child to the specified index of this Tree's children.
	 * @param newChild
	 * @throws IllegalArgumentException if the specified child will create a loop 
	 * in this Tree, or if the specified index is out of range.
	 */
	public void addChild(int index, Tree<V> newChild) throws IllegalArgumentException {
		if (newChild.contains(this) || index < 0 || index > this.children.size()) 
			throw new IllegalArgumentException();
		
		this.children.add(index, newChild);
	}
	
	/**
	 * Adds the specified children to end of the existing children of this Tree.
	 * @param children The children to be added.
	 * @throws IllegalArgumentException if the specified child will create a loop 
	 * in this Tree.
	 */
	public void addChildren(Tree<V>... children) throws IllegalArgumentException {
		for (Tree<V> child : children) {
			if (child.contains(this)) throw new IllegalArgumentException();
			this.children.add(child);
		}
	}
	
	/**
	 * Removes the child at the specified index from this Tree's children and returns it.
	 * @param index The index of the child to remove.
	 * @return The removed child.
	 * @throws NoSuchElementException If the specified index is out of range.
	 */
	public Tree<V> removeChild(int index) throws NoSuchElementException {
		if (index < 0 || index >= children.size()) {
			throw new NoSuchElementException();
		}
		
		return this.children.remove(index);
	}


}
