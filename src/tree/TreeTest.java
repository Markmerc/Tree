package tree;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TreeTest {

	Tree<String> a;
	Tree<String> b;
	Tree<String> c;
	Tree<String> d;
	Tree<String> e;
	Tree<String> f;
	Tree<String> g;
	Tree<String> h;
	Tree<String> z;
	
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

	@Test
	public void testTree() {
		Tree<String> z = new Tree<String>("Z");
		assertTrue(z instanceof Tree);
	}

	@Test
	public void testGetValue() {
		assertTrue(a.getValue().equals("A"));
	}

	@Test
	public void testSetValue() {
		assertTrue(a.getValue().equals("A"));
		a.setValue("Q");
		assertTrue(a.getValue().equals("Q"));
		a.setValue("1234");
		assertTrue(a.getValue().equals("1234"));
	}

	@Test
	public void testNumberOfChildren() {
		assertTrue(a.numberOfChildren() == 2);
		assertTrue(b.numberOfChildren() == 0);
	}

	@Test
	public void testFirstChild() {
		assertTrue(a.firstChild() == b);
		assertTrue(b.firstChild() == null);
	}

	@Test
	public void testLastChild() {
		assertTrue(a.lastChild() == c);
		assertTrue(b.lastChild() == null);
	}

    @Test
	public void testChild() {
		assertTrue(a.child(1) == c);
		assertTrue(a.child(0) == b);
	}
    
    @Test(expected=NoSuchElementException.class)
    public void testChildNegIndex() {
		//Throw exceptions
		a.child(-1);
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testChildTooLargeIndex() {
		//Throw exceptions
		a.child(2);
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testChildEmptyList() {
		//Throw exceptions
		b.child(1);
    }

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

	@Test
	public void testIsLeaf() {
		assertFalse(a.isLeaf());
		assertTrue(b.isLeaf());
	}

	@Test
	public void testEqualsObject() {
		
		//Recognizes itself as equal to itself
		assertTrue(h.equals(h));
		
		assertFalse(h.equals(a));
		
		//Recognizes a node with the same value and no children
		z = new Tree<String>("H");
		assertTrue(h.equals(z));
		
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

	@Test
	public void testToString() {
		assertTrue(a.toString().equals("A\n  B\n  C\n"));
		b.addChild(d);
		c.addChild(e);
		assertTrue(a.toString().equals("A\n  B\n    D\n  C\n    E\n"));
		b.addChild(e);
		assertTrue(a.toString().equals("A\n  B\n    D\n    E\n  C\n    E\n"));
	}

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
    
    @Test (expected=IllegalArgumentException.class)
	public void testAddChildIndexedNegative() {
		a.addChild(-1, f);
		assertTrue(a.numberOfChildren() == 3);
		assertTrue(a.child(0) == e);
		assertTrue(a.child(1) == b);
		assertTrue(a.child(2) == c);
    }
    
    @Test (expected=IllegalArgumentException.class)
	public void testAddChildIndexedTooLarge() {
		a.addChild(5, f);
		assertTrue(a.numberOfChildren() == 3);
		assertTrue(a.child(0) == e);
		assertTrue(a.child(1) == b);
		assertTrue(a.child(2) == c);
    }

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
    @Test (expected=IllegalArgumentException.class)
	public void testAddChildrenLoop() {
		b.addChildren(f, g, h);
		//Add loop second
		c.addChildren(e, a);
		assertTrue(c.numberOfChildren() == 1);
		assertTrue(c.child(0) == e);
    }

    @Test
	public void testAddChildren() {
		b.addChildren(f, g, h);
		assertTrue(b.numberOfChildren() == 3);
		assertTrue(b.child(0) == f);
		assertTrue(b.child(1) == g);
		assertTrue(b.child(2) == h);
	}

    @Test (expected=NoSuchElementException.class)
    public void testRemoveChildNegative() {
    	a.removeChild(-1);
    	assertTrue(a.child(0) == b);
    	assertTrue(a.child(1) == c);
    }
    
    @Test (expected=NoSuchElementException.class)
	public void testRemoveChildTooLarge() {
    	a.removeChild(5);
    	assertTrue(a.child(0) == b);
    	assertTrue(a.child(1) == c);
    }
    
    @Test (expected=NoSuchElementException.class)
	public void testRemoveChildDoesntExist() {
    	b.removeChild(0);
    }

    @Test
	public void testRemoveChild() {
    	a.addChild(d);
    	a.removeChild(1);
    	assertTrue(a.numberOfChildren() == 2);
    	assertTrue(a.child(0) == b);
    	assertTrue(a.child(1) == d);	
    }

}
