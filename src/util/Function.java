/*
 * Created on Oct 3, 2005
 */
package util;

public interface Function<T, U>
  {
    public T apply (U arg);
  }