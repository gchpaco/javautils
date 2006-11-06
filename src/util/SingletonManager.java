package util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SingletonManager
  {
    public static ConcurrentMap<Class<?>, Object> singletonMap;

    static
      {
        singletonMap = new ConcurrentHashMap<Class<?>, Object> ();
      }

    @SuppressWarnings ("unchecked")
    public static <T> T get (Class<T> klass)
      {
        if (singletonMap.containsKey (klass))
          return (T) singletonMap.get (klass);
        throw new IllegalArgumentException ("No instance of "
                                            + klass.getName ()
                                            + " registered");
      }

    @SuppressWarnings ("unchecked")
    public static <T, U extends T> T register (Class<T> klass, U object)
      {
        singletonMap.putIfAbsent (klass, object);
        return (T) singletonMap.get (klass);
      }

    public static void clear ()
      {
        singletonMap.clear ();
      }

    public static boolean has (Class<?> name)
      {
        return singletonMap.containsKey (name);
      }
  }
