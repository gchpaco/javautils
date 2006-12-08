package util;

import java.util.*;

public class S
  {
    public static <T> Set<T> et (T... o)
      {
        return new HashSet<T> (Arrays.asList (o));
      }

  }
