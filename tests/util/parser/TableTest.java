package util.parser;

import java.util.*;

import org.testng.annotations.*;

import util.L;
import util.S;
import static org.testng.AssertJUnit.*;

@Test
public class TableTest
  {
    @SuppressWarnings("unchecked")
    private Table<NT, T, List<?>> table;

    static enum NT
      {
        A, B, C;
      }

    static enum T
      {
        X, Y, Z, EOF;
      }

    public void testTableCreation ()
      {
        assertNotNull (table);
        assertEquals (S.et (), table.get (NT.A, T.X));
      }

    @SuppressWarnings("unchecked")
    public void testTableFilling ()
      {
        List a = L.ist (1);
        List b = L.ist (2);
        table.put (NT.A, T.X, a);
        assertEquals (new HashSet<Object> (L.ist (a)), table.get (NT.A, T.X));
        emptyExceptFor (NT.A, T.X);
        table.put (NT.A, T.X, b);
        assertEquals (new HashSet<Object> (L.ist (a, b)), table.get (NT.A, T.X));
        emptyExceptFor (NT.A, T.X);
      }
    
    @SuppressWarnings("unchecked")
    public void testMultipleFills ()
      {
        List a = L.ist (1);
        List b = L.ist (2);
        table.put (NT.A, T.X, a);
        table.put (NT.A, T.X, b);
        table.put (NT.A, T.X, b);
        assertFalse (table.get (NT.A, T.X).isEmpty ());
        assertEquals (new HashSet<Object> (L.ist (a, b)), table.get (NT.A, T.X));
        emptyExceptFor (NT.A, T.X);
      }

    private void emptyExceptFor (NT a, T b)
      {
        for (NT nt : NT.values ())
          for (T t : T.values ())
            if (nt != a || t != b)
              assertEquals (Collections.EMPTY_SET, table.get (nt, t));
      }

    @SuppressWarnings("unchecked")
    @BeforeMethod
    public void setUp ()
      {
        table = new Table<NT, T, List<?>> ();
      }
  }
