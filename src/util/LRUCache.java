package util;

import java.util.LinkedHashMap;

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
}
