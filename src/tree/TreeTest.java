package tree;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Tree class
 * @author Mark Mercurio
 */
public class TreeTest {

	private Tree<String> a;
	private Tree<String> b;
	private Tree<String> c;
	private Tree<String> d;
	private Tree<String> e;
	private Tree<String> f;
	private Tree<String> g;
	private Tree<String> h;
	private Tree<String> z;
	
	/**
	 * Defaults the values for each test Tree
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		b = new Tree<String>("B");
		c = new Tree<String>("C");
		a = new Tree<String>("A", b, c);
		
		d = new Tree<String>("D");
		e = new Tree<String>("E");
		f = new Tree<String>("F");
		g = new Tree<String>("G");
		h = new Tree<String>("H");
		
	}

	/**
	 * Tests that the constructor returns type an object of type Tree.
	 */
	@Test
	public void testTree() {
		Tree<String> z = new Tree<String>("Z");
		assertTrue(z instanceof Tree);
	}

	/**
	 * Tests the getValue method.
	 */
	@Test
	public void testGetValue() {
		assertTrue(a.getValue().equals("A"));
	}

	/**
	 * Tests the setValue method.
	 */
	@Test
	public void testSetValue() {
		assertTrue(a.getValue().equals("A"));
		a.setValue("Q");
		assertTrue(a.getValue().equals("Q"));
		a.setValue("1234");
		assertTrue(a.getValue().equals("1234"));
	}

	/**
	 * Tests the numberOfChildren method.
	 */
	@Test
	public void testNumberOfChildren() {
		assertTrue(a.numberOfChildren() == 2);
		assertTrue(b.numberOfChildren() == 0);
	}

	/**
	 * Tests the firstChild method.
	 */
	@Test
	public void testFirstChild() {
		assertTrue(a.firstChild() == b);
		assertTrue(b.firstChild() == null);
	}

	/**
	 * Tests the lastChild method.
	 */
	@Test
	public void testLastChild() {
		assertTrue(a.lastChild() == c);
		assertTrue(b.lastChild() == null);
	}

	/**
	 * Tests the child method for valid indices
	 */
    @Test
	public void testChild() {
		assertTrue(a.child(1) == c);
		assertTrue(a.child(0) == b);
	}

	/**
	 * Tests the child method with a negative index
	 */
    @Test(expected=NoSuchElementException.class)
    public void testChildNegIndex() {
		//Throw exceptions
		a.child(-1);
    }

	/**
	 * Tests the child method with too large an index
	 */
    @Test(expected=NoSuchElementException.class)
    public void testChildTooLargeIndex() {
		//Throw exceptions
		a.child(2);
    }
    
	/**
	 * Tests the child method on an empty children list.
	 */
    @Test(expected=NoSuchElementException.class)
    public void testChildEmptyList() {
		//Throw exceptions
		b.child(1);
    }

    /**
     * Tests the children method.
     */
	@Test
	public void testChildren() {
		assertTrue(a.children() instanceof Iterator<?>);
		
		Iterator<Tree<String>> iter = a.children();
		
		assertTrue(iter.hasNext());
		assertTrue(iter.next() == b);
		assertTrue(iter.hasNext());
		assertTrue(iter.next() == c);
		assertFalse(iter.hasNext());
		
		iter = a.children();
		assertTrue(iter.hasNext());
		iter.next();
		iter.remove();
		
		assertTrue(a.numberOfChildren() == 1);
		assertTrue(a.child(0) == c);
	}

	/**
	 * Tests the isLeaf method.
	 */
	@Test
	public void testIsLeaf() {
		assertFalse(a.isLeaf());
		assertTrue(b.isLeaf());
	}

	/**
	 * Tests the equals method.
	 */
	@Test
	public void testEqualsObject() {
		
		//Recognizes itself as equal to itself
		assertTrue(h.equals(h));
		
		assertFalse(h.equals(a));
		
		//Recognizes a node with the same value and no children
		z = new Tree<String>("H");
		assertTrue(h.equals(z));
		
		//Does not equal a node with different value and no children
		Tree<String> q = new Tree<String>("Q");
		assertFalse(q.equals(z));
		
		//Recognizes trees with same number of children
		Tree<String> _2 = new Tree<String>("B");
		Tree<String> _3 = new Tree<String>("C");
		Tree<String> _1 = new Tree<String>("A", _2, _3);
		Tree<String> _4 = new Tree<String>("E");

		assertTrue(_1.equals(a));
		assertTrue(a.equals(_1));
		
		//Try with shared subtree
		_2.addChild(_4);
		_3.addChild(_4);
		b.addChild(e);
		c.addChild(e);
		assertTrue(_1.equals(a));
		assertTrue(a.equals(_1));
		
		_2.addChild(f);
		assertFalse(_1.equals(a));
	}

	/**
	 * Tests the toString method.
	 */
	@Test
	public void testToString() {
		assertTrue(a.toString().equals("A\n  B\n  C\n"));
		b.addChild(d);
		c.addChild(e);
		assertTrue(a.toString().equals("A\n  B\n    D\n  C\n    E\n"));
		b.addChild(e);
		assertTrue(a.toString().equals("A\n  B\n    D\n    E\n  C\n    E\n"));
	}

	/**
	 * Tests the addChild(Tree<V> newChild) method with valid (non-looping) children.
	 */
    @Test
	public void testAddChildOneParameter() {
		a.addChild(d);
		assertTrue(a.numberOfChildren() == 3);
		assertTrue(a.child(0).getValue().equals("B"));
		assertTrue(a.child(1).getValue().equals("C"));
		assertTrue(a.child(2).getValue().equals("D"));

		b.addChild(e);
		assertTrue(b.numberOfChildren() == 1);
		assertTrue(b.child(0).getValue().equals("E"));
		
		//Make sure a was unchanged
		assertTrue(a.numberOfChildren() == 3);
		assertTrue(a.child(0).getValue().equals("B"));
		assertTrue(a.child(1).getValue().equals("C"));
		assertTrue(a.child(2).getValue().equals("D"));
		
		//Try to add shared subtree
		c.addChild(e);
		assertTrue(c.numberOfChildren() == 1);
		assertTrue(c.child(0).getValue().equals("E"));
	}
    
	/**
	 * Tests the addChild(Tree<V> newChild) method with invalid (looping) children.
	 */
    @Test(expected=IllegalArgumentException.class)
    public void  testAddChildOneParameterLooping() {
		a.addChild(d);
		assertTrue(a.numberOfChildren() == 3);
		assertTrue(a.child(0).getValue().equals("B"));
		assertTrue(a.child(1).getValue().equals("C"));
		assertTrue(a.child(2).getValue().equals("D"));

		b.addChild(e);
		assertTrue(b.numberOfChildren() == 1);
		assertTrue(b.child(0).getValue().equals("E"));
		
		//Make sure a was unchanged
		assertTrue(a.numberOfChildren() == 3);
		assertTrue(a.child(0).getValue().equals("B"));
		assertTrue(a.child(1).getValue().equals("C"));
		assertTrue(a.child(2).getValue().equals("D"));
		
		
		//Try to add a loop in
		b.addChild(a);		
		assertTrue(b.numberOfChildren() == 1);
		assertTrue(b.child(0).getValue().equals("E"));
    }
    
    /**
     * Tests the addChild(int index, Tree<V> newChild) method with looping children.
     */
    @Test (expected=IllegalArgumentException.class)
	public void testAddChildIndexedLoop() {
    	a.addChild(0, e);
    	c.addChild(0, f);
    	//Fail at making loop
    	c.addChild(0, a);
    	assertTrue(a.numberOfChildren() == 3);
    	assertTrue(a.child(0) == e);
    	assertTrue(a.child(1) == b);
    	assertTrue(a.child(2) == c);
    			
    	assertTrue(c.numberOfChildren() == 1);
    	assertTrue(c.child(0) == f);
    }
    
    /**
     * Tests the addChild(int index, Tree<V> newChild) method negative index.
     */
    @Test (expected=IllegalArgumentException.class)
	public void testAddChildIndexedNegative() {
		a.addChild(-1, f);
		assertTrue(a.numberOfChildren() == 3);
		assertTrue(a.child(0) == e);
		assertTrue(a.child(1) == b);
		assertTrue(a.child(2) == c);
    }
    
    /**
     * Tests the addChild(int index, Tree<V> newChild) method with too large an index.
     */
    @Test (expected=IllegalArgumentException.class)
	public void testAddChildIndexedTooLarge() {
		a.addChild(5, f);
		assertTrue(a.numberOfChildren() == 3);
		assertTrue(a.child(0) == e);
		assertTrue(a.child(1) == b);
		assertTrue(a.child(2) == c);
    }

    /**
     * Tests the addChild(int index, Tree<V> newChild) method with valid children.
     */
    @Test 
	public void testAddChildIndexed() {
		a.addChild(0, e);
		assertTrue(a.numberOfChildren() == 3);
		assertTrue(a.child(0) == e);
		assertTrue(a.child(1) == b);
		assertTrue(a.child(2) == c);
		
				
		c.addChild(0, f);
		assertTrue(c.numberOfChildren() == 1);
		assertTrue(c.child(0) == f);

	}
    
    /**
     * Tests the addChildren method with looping children.
     */
    @Test (expected=IllegalArgumentException.class)
	public void testAddChildrenLoop() {
		b.addChildren(f, g, h);
		//Add loop second
		c.addChildren(e, a);
		assertTrue(c.numberOfChildren() == 1);
		assertTrue(c.child(0) == e);
    }

    /**
     * Tests the addChildren method with valid children.
     */
    @Test
	public void testAddChildren() {
		b.addChildren(f, g, h);
		assertTrue(b.numberOfChildren() == 3);
		assertTrue(b.child(0) == f);
		assertTrue(b.child(1) == g);
		assertTrue(b.child(2) == h);
	}

    /**
     * Tests removeChild method with negative index.
     */
    @Test (expected=NoSuchElementException.class)
    public void testRemoveChildNegative() {
    	a.removeChild(-1);
    	assertTrue(a.child(0) == b);
    	assertTrue(a.child(1) == c);
    }
    
    /**
     * Tests removeChild method with too large an index.
     */
    @Test (expected=NoSuchElementException.class)
	public void testRemoveChildTooLarge() {
    	a.removeChild(5);
    	assertTrue(a.child(0) == b);
    	assertTrue(a.child(1) == c);
    }
    
    /**
     * Tests removeChild method on childless Tree.
     */
    @Test (expected=NoSuchElementException.class)
	public void testRemoveChildDoesntExist() {
    	b.removeChild(0);
    }

    /**
     * Tests removeChild method with valid index.
     */
    @Test
	public void testRemoveChild() {
    	a.addChild(d);
    	a.removeChild(1);
    	assertTrue(a.numberOfChildren() == 2);
    	assertTrue(a.child(0) == b);
    	assertTrue(a.child(1) == d);	
    }

}
