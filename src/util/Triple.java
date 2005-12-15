/*
 * Created on Oct 24, 2005
 */
package util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Triple<T, U, V> {
	public T first;
	public U second;
	public V third;

	public Triple (T t, U u, V v) {
		first = t;
		second = u;
		third = v;
	}

	@Override
	public boolean equals (Object obj) {
		if (obj instanceof Triple == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Triple rhs = (Triple) obj;
		return new EqualsBuilder ().append (first, rhs.first).append (second,
				rhs.second).append (third, rhs.third).isEquals ();
	}

	@Override
	public int hashCode () {
		return new HashCodeBuilder (7, 23).append (first).append (second)
				.append (third).toHashCode ();
	}

	@Override
	public String toString () {
		return "(" + first + ", " + second + ", " + third + ")";
	}

	public static <X, Y, Z> Triple<X, Y, Z> make (X x, Y y, Z z) {
		return new Triple<X, Y, Z> (x, y, z);
	}
}
