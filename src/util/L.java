/*
 * Created on Sep 20, 2005
 */
package util;

import java.util.*;

public class L {
	public static <T> List<T> ist (Collection<T> c) {
		return new ArrayList<T> (c);
	}

	public static <T> List<T> ist (T... o) {
		return ist (Arrays.asList (o));
	}
}