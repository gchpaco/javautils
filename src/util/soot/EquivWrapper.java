/*
 * Created on Sep 21, 2005
 */
package util.soot;

import java.util.HashMap;
import java.util.Map;

import soot.EquivTo;

public class EquivWrapper<T extends EquivTo> {
	T datum;
	
	private EquivWrapper (T t) {
		datum = t;
	}

	@Override
	public boolean equals (Object o) {
		if (o == this)
			return true;
		else if (o instanceof EquivWrapper)
			return datum.equivTo (((EquivWrapper) o).getDatum ());
		else
			return false;
	}

	@Override
	public int hashCode () {
		return datum.equivHashCode ();
	}

	public T getDatum () {
		return datum;
	}

	@Override
	public String toString () {
		return datum.toString ();
	}

	public static <U extends EquivTo> EquivWrapper<U> wrap (U u) {
		return new EquivWrapper<U> (u);
	}
	private final static Map<EquivWrapper, EquivTo> values = new HashMap<EquivWrapper, EquivTo> ();
	@SuppressWarnings("unchecked")
	public static <U extends EquivTo> U intern (U value) {
		EquivWrapper<U> w = EquivWrapper.wrap (value);
		if (!values.containsKey (w))
			values.put (w, value);
		return (U) values.get (w);
	}
}