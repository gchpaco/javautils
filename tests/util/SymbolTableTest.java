package util;

import static org.testng.AssertJUnit.*;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class SymbolTableTest
{
  SymbolTable<Object, Object> table;

  @SuppressWarnings ("unused")
  @BeforeMethod
  private void setUp ()
    {
      table = new SymbolTable<Object, Object> ();
    }

  public void emptyTable ()
    {
      assertTrue (table.isEmpty ());
      assertEquals (0, table.size ());
      assertNotNull (table.keySet ());
      assertTrue (table.keySet ().isEmpty ());
      assertNotNull (table.values ());
      assertTrue (table.values ().isEmpty ());
      assertNotNull (table.entrySet ());
      assertTrue (table.entrySet ().isEmpty ());
    }

  public void singleLevelTable ()
    {
      final Object key = new Object ();
      final Object value = new Object ();
      table.bind (key);
      table.put (key, value);
      assertFalse (table.isEmpty ());
      assertEquals (1, table.size ());
      assertSame (value, table.get (key));
      assertTrue (table.containsKey (key));
      assertTrue (table.containsValue (value));
      assertEquals (Collections.singleton (key), table.keySet ());
      int count = 0;
      for (Object v : table.values ())
        {
          count++;
          assertSame (value, v);
        }
      assertEquals (1, count);
      Map.Entry<Object, Object> entry = new Map.Entry<Object, Object> ()
        {
          public Object getKey ()
            {
              return key;
            }

          public Object getValue ()
            {
              return value;
            }

          public Object setValue (Object o)
            {
              return value;
            }
        };
      assertEquals (Collections.singleton (entry), table.entrySet ());
      table.remove (key);
      emptyTable ();
    }

  public void multiples ()
    {
      table.bind (1);
      table.bind (2);
      table.put (1, 1);
      table.put (2, 2);
      assertEquals (2, table.size ());
      table.clear ();
      emptyTable ();
    }

  public void putAll ()
    {
      table.bind (1);
      table.bind (2);
      Map<Integer, Integer> map = new TreeMap<Integer, Integer> ();
      map.put (1, 1);
      map.put (2, 2);
      table.putAll (map);
      assertEquals (2, table.size ());
    }

  @Test (expectedExceptions = { IllegalArgumentException.class })
  public void putBeforeBind ()
    {
      table.put (1, 1);
    }

  @Test (expectedExceptions = { IllegalArgumentException.class })
  public void putAllBeforeBind ()
    {
      table.bind (1); // 2 not bound
      Map<Integer, Integer> map = new TreeMap<Integer, Integer> ();
      map.put (1, 1);
      map.put (2, 2);
      table.putAll (map);
    }

  @Test (expectedExceptions = { IllegalStateException.class })
  public void popBeforePush ()
    {
      table.pop ();
    }

  @Test (expectedExceptions = { IllegalStateException.class })
  public void popAfterClear ()
    {
      table.push ();
      table.push ();
      table.clear ();
      table.pop ();
    }

  public void pushing ()
    {
      table.push ();
      table.pop ();
    }

  public void previousBindingsRetained ()
    {
      table.bind (1);
      table.put (1, 2);
      table.push ();
      assertFalse (table.isEmpty ());
      assertEquals (2, table.get (1));
      table.put (1, 4);
      assertEquals (4, table.get (1));
      table.pop ();
      assertEquals (4, table.get (1));
    }

  public void rebindingsNotPropagated ()
    {
      table.bind (1);
      table.put (1, 2);
      table.push ();
      table.bind (1);
      assertEquals (null, table.get (1));
      table.put (1, 4);
      assertEquals (4, table.get (1));
      table.pop ();
      assertEquals (2, table.get (1));
    }
}
