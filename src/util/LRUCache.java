package util;

import java.util.LinkedHashMap;

import net.sf.jga.fn.Generator;

public class LRUCache<K, V> extends LinkedHashMap<K, V>
{
  private int max = 10;

  public LRUCache (int initial, int maximum)
    {
      super (initial, 0.75f, true);
      max = maximum;
    }

  @Override
  protected boolean removeEldestEntry (java.util.Map.Entry<K, V> eldest)
    {
      return size () > max;
    }

  public V get (K i, Generator<V> closure)
    {
      if (containsKey (i))
        return get (i);
      else
        {
          V var = closure.fn ();
          put (i, var);
          return var;
        }
    }
}
