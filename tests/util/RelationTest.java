/*
 * Created on Oct 19, 2005
 */
package util;

import static org.testng.AssertJUnit.*;

import java.util.Collections;
import java.util.Set;

import org.testng.annotations.*;

public class RelationTest {
	@Test
	public void checkEmptyRelation () {
		Relation<Integer> relation = new Relation<Integer> ();
		assertEquals (0, relation.size ());
		assertFalse (relation.holds (1, 4));
	}

	@Test
	public void checkAddingWorks () {
		Relation<Integer> relation = new Relation<Integer> ();
		assertFalse (relation.holds (1, 4));
		relation.add (1, 4);
		assertTrue (relation.holds (1, 4));
		assertFalse (relation.holds (1, 7));
		assertEquals (1, relation.size ());
		relation.add (1, 7);
		assertTrue (relation.holds (1, 7));
		assertTrue (relation.holds (1, 4));
		assertEquals (2, relation.size ());
	}

	@Test
	public void checkTransitiveClosure () {
		Relation<Integer> relation = new Relation<Integer> ();
		Relation<Integer> emptyClosure = relation.transitiveClosure ();
		assertEquals (0, emptyClosure.size ());

		relation.add (1, 4);
		relation.add (2, 7);
		relation.add (4, 7);
		Relation<Integer> closure = relation.transitiveClosure ();
		assertTrue (closure.holds (1, 1));
		assertTrue (closure.holds (1, 4));
		assertTrue (closure.holds (1, 7));
		assertTrue (closure.holds (2, 2));
		assertTrue (closure.holds (2, 7));
		assertTrue (closure.holds (4, 4));
		assertTrue (closure.holds (4, 7));
		assertTrue (closure.holds (7, 7));
		assertEquals (8, closure.size ());
		assertEquals (3, relation.size ());
	}

	@Test
	public void checkReflexives () {
		Relation<Integer> relation = new Relation<Integer> ();
		relation.add (1, 4);
		relation.add (2, 7);
		relation.add (4, 7);
		relation.add (7, 1);
		relation = relation.transitiveClosure ();
		assertTrue (relation.holds (1, 7) && relation.holds (7, 1));
		assertTrue (relation.holds (1, 4) && relation.holds (4, 1));
		assertTrue (relation.holds (7, 4) && relation.holds (4, 7));
		assertFalse (relation.holds (7, 2) && relation.holds (2, 7));
		Set<Integer> reflexives = relation.reflexives ();
		assertEquals (3, reflexives.size ());
		assertTrue (reflexives.contains (1));
		assertTrue (reflexives.contains (4));
		assertTrue (reflexives.contains (7));
		assertFalse (reflexives.contains (2));
	}

	@Test
	public void reflexiveMapping () {
		Relation<Integer> relation = new Relation<Integer> ();
		relation.add (1, 4);
		relation.add (2, 7);
		relation.add (4, 7);
		relation.add (7, 1);
		relation = relation.transitiveClosure ();
		assertTrue (relation.holds (1, 7) && relation.holds (7, 1));
		assertTrue (relation.holds (1, 4) && relation.holds (4, 1));
		assertTrue (relation.holds (7, 4) && relation.holds (4, 7));
		assertFalse (relation.holds (7, 2) && relation.holds (2, 7));
		Relation<Integer> reflexives = relation.reflexiveMaps ();
		assertEquals (6, reflexives.size ());
		assertTrue (reflexives.containsKey (1));
		assertTrue (reflexives.containsKey (4));
		assertTrue (reflexives.containsKey (7));
		assertFalse (reflexives.containsKey (2));
		assertEquals (2, reflexives.get (1).size ());
		assertTrue (reflexives.holds(1, 4));
		assertTrue (reflexives.holds(1, 7));
		assertTrue (reflexives.holds(4, 1));
		assertTrue (reflexives.holds(4, 7));
		assertTrue (reflexives.holds(7, 1));
		assertTrue (reflexives.holds(7, 4));
	}

	@Test
	public void uncontainedVariables () {
		Relation<Integer> relation = new Relation<Integer> ();
		assertEquals (Collections.emptySet (), relation.get (1));
		assertEquals (0, relation.size ());
	}
}
