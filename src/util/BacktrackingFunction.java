/*
 * Created on Oct 7, 2005
 */
package util;

public interface BacktrackingFunction<T, U> extends Function<T, Pair<T, U>> {
	public T empty ();
}
