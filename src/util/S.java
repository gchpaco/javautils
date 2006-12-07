package util;

import java.util.*;

public class S
  {
    public static <T> Set<T> et (Collection<T> c)
      {
        return new HashSet<T> (c);
      }

    public static <T> Set<T> et (T... o)
      {
        return et (Arrays.asList (o));
      }

  }
