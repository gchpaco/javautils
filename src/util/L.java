/*
 * Created on Sep 20, 2005
 */
package util;

import java.util.*;

public class L {

	private static final String TAB = "	";

	public static <T> List<T> ist (Collection<T> c) {
		return new ArrayList<T> (c);
	}

	public static <T> List<T> ist (T... o) {
		return ist (Arrays.asList (o));
	}

	public static void main (String[] args) {
		for (int i = 1; i < 100; i++) {
			System.out.println (TAB + "public static List l(" + args (i)
					+ ") {");
			System.out.println (TAB + TAB + result (i));
			System.out.println (TAB + "}");
		}
	}

	private static String args (int count) {
		StringBuffer buf = new StringBuffer ();
		for (int i = 0; i < count; i++)
			buf.append ("Object o" + i + (commaIfNext (count, i)));
		return buf.toString ();
	}

	private static String commaIfNext (int count, int i) {
		return i < count - 1 ? ", " : "";
	}

	private static String result (int count) {
		StringBuffer buf = new StringBuffer ();
		buf.append ("return ist(new Object[] {");
		for (int i = 0; i < count; i++)
			buf.append ("o" + i + commaIfNext (count, i));
		buf.append ("});");
		return buf.toString ();
	}

	private L () {
		super ();
	}
}