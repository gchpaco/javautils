package util;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

@Test
public class LRUCacheTest
{
  public void simple ()
    {
      LRUCache<Integer, Integer> cache = new LRUCache<Integer, Integer> (1, 3);
      cache.put (1, 42);
      cache.put (3, 52);
      cache.get (1);
      cache.put (2, 56);
      cache.put (4, 56);
      assertFalse (cache.containsKey (3));
      assertTrue (cache.containsKey (1));
      assertTrue (cache.containsKey (2));
      assertTrue (cache.containsKey (4));
    }
}
