/*
 * Created on Sep 16, 2005
 */
package util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Pair<T, U>
{
  public T first;
  public U second;

  public Pair (T t, U u)
    {
      first = t;
      second = u;
    }

  @Override
  public boolean equals (Object obj)
    {
      if (obj instanceof Pair == false)
        {
          return false;
        }
      if (this == obj)
        {
          return true;
        }
      Pair<?, ?> rhs = (Pair<?, ?>) obj;
      return new EqualsBuilder ().append (first, rhs.first).append (second,
                                                                    rhs.second)
                                 .isEquals ();
    }

  @Override
  public int hashCode ()
    {
      return new HashCodeBuilder (3, 7).append (first).append (second)
                                       .toHashCode ();
    }

  @Override
  public String toString ()
    {
      return "(" + first + ", " + second + ")";
    }

  public static <V, W> Pair<V, W> make (V v, W w)
    {
      return new Pair<V, W> (v, w);
    }
}
