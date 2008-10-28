package util;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import net.sf.jga.fn.Generator;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class LRUCacheTest
{
  private LRUCache<Integer, Integer> cache;

  @BeforeTest
  public void setUp ()
    {
      cache = new LRUCache<Integer, Integer> (1, 3);
    }

  public void simple ()
    {
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

  public void closure ()
    {
      final int[] timesRun = { 0 };

      Generator<Integer> closure = new Generator<Integer> ()
        {
          @Override
          public Integer gen ()
            {
              return timesRun[0]++;
            }
        };
      assertEquals ((int) cache.get (1, closure), 0);
      assertEquals ((int) cache.get (2, closure), 1);
      assertEquals ((int) cache.get (1, closure), 0);
      assertEquals ((int) cache.get (3, closure), 2);
      assertEquals ((int) cache.get (4, closure), 3);
      assertEquals (timesRun[0], 4);
      assertFalse (cache.containsKey (2));
    }
}
