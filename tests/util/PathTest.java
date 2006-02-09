/*
 * Created on Oct 21, 2005
 */
package util;

import static org.testng.AssertJUnit.*;

import java.util.Iterator;

import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import util.Pair;

import static util.Path.*;

public class PathTest {
	private Path<Integer> p;

	@Configuration (beforeTestMethod = true)
	private void setUp () {
		p = null;
		p = cons (1, p);
		p = cons (2, p);
		p = cons (1, p);
	}

	@Test
	public void simple () {
		assertTrue (isEmpty (null));
		assertEquals (0, length (null));
		setUp ();
		assertFalse (isEmpty (p));
		assertEquals (3, length (p)); // null -> 1, 1 -> 2
	}

	@Test
	public void iterator () {
		Iterator<Pair<Integer, Integer>> i = p.iterator ();
		assertTrue (i.hasNext ());
		assertEquals (Pair.make (null, 1), i.next ());
		assertTrue (i.hasNext ());
		assertEquals (Pair.make (1, 2), i.next ());
		assertTrue (i.hasNext ());
		assertEquals (Pair.make (2, 1), i.next ());
		assertFalse (i.hasNext ());
	}

	@Test
	public void contains () {
		assertTrue (p.contains (1, 2));
		assertFalse (p.contains (2, 5));
		assertTrue (p.contains (null, 1));
	}

	@Test
	public void strings () {
		assertEquals ("null", String.format ("%s", (Object) null));
		assertEquals ("null->1->2->1", p.toString ());
	}

	@Test
	public void equality () {
		assertEquals (p, make (1, 2, 1));
		assertEquals (p.hashCode (), make (1, 2, 1).hashCode ());
	}

	@Test
	public void consSharing () {
		Path<Integer> q = Path.cons (5, p), r = Path.cons (5, p);
		assertNotSame (q, r);
		assertEquals (q, r);
		assertSame (q.cdr (), r.cdr ());
		assertSame (p, q.cdr ());
		assertEquals ((Integer) 5, q.car ());
	}
}
