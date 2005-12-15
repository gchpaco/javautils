/*
 * Created on Oct 18, 2005
 */
package util;

import java.util.*;

@SuppressWarnings ("serial")
public class Relation<T> extends HashMap<T, Set<T>> implements Cloneable {
	public Relation () {
		super ();
	}

	public boolean add (T n, T t) {
		if (!containsKey (n))
			put (n, new HashSet<T> ());
		return get (n).add (t);
	}

	public boolean holds (T x, T y) {
		return containsKey (x) && get (x).contains (y);
	}

	public Relation<T> transitiveClosure () {
		Relation<T> target = this.clone ();
		Queue<T> worklist = new LinkedList<T> ();
		for (Map.Entry<T, Set<T>> entry : entrySet ()) {
			worklist.add (entry.getKey ());
			worklist.addAll (entry.getValue ());
		}
		for (T n : worklist)
			target.add (n, n);
		// so now reflexivity is satisfied.

		// Proof sketch that this does what I want:
		// Worklist is initially the entire set. So we pass over it entirely.
		// If an element x is not re-added to the worklist, then there were no
		// y, z so that x R y and y R z. So for any z so that x R+ z, x R z now.
		// If the element x was re-added, then for all y, z so that x R y and y
		// R z, now x R z. This is equivalent to R \cup R^2. Then we repeat.
		// So this computes R+ = T.
		while (worklist.size () != 0) {
			T x = worklist.remove ();
			// duplicates are needed because we might be modifying the set while
			// iterating over it.
			for (T y : duplicate (target.get (x))) {
				for (T z : duplicate (target.get (y))) {
					if (target.add (x, z))
						worklist.add (x);
				}
			}
		}
		return target;
	}

	public Set<T> reflexives () {
		Set<T> result = new HashSet<T> ();
		// We denote orders.get (x).contains (y) by x R y. If there is some
		// chain of p_{0 .. n}s
		// so that x R p_0, \forall i p_i R p_{i+1}, and p_n R x, then by
		// transitive closure x R p_n and p_n R x. So we need merely look for
		// reflexive pairs. We can't look for x R x, because by symmetry that's
		// already given.
		for (Map.Entry<T, Set<T>> entry : entrySet ())
			for (T y : entry.getValue ())
				if (entry.getKey () != y && get (y).contains (entry.getKey ()))
					result.add (entry.getKey ());
		return result;
	}

	@Override
	public Relation<T> clone () {
		Relation<T> relation = new Relation<T> ();
		for (T key : keySet ())
			relation.put (key, duplicate (get (key)));
		return relation;
	}

	@Override
	public int size () {
		int result = 0;
		for (Map.Entry<T, Set<T>> entry : entrySet ())
			result += entry.getValue ().size ();
		return result;
	}

	@Override
	public Set<T> get (Object key) {
		if (containsKey (key))
			return super.get (key);
		else
			return Collections.emptySet ();
	}

	private HashSet<T> duplicate (final Set<T> set) {
		return new HashSet<T> (set);
	}

	public Relation<T> reflexiveMaps () {
		Relation<T> result = new Relation<T> ();
		for (Map.Entry<T, Set<T>> entry : entrySet ())
			for (T y : entry.getValue ())
				if (entry.getKey () != y && get (y).contains (entry.getKey ()))
					result.add (entry.getKey (), y);
		return result;
	}
}
