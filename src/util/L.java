/*
 * Created on Sep 20, 2005
 */
package util;

import java.util.*;

public class L {
  public static <T> List<T> ist (T... o) {
    return new ArrayList<T> (Arrays.asList (o));
  }
}