package util.parser;

import java.util.Collections;
import java.util.HashSet;

import org.testng.annotations.*;

import util.L;
import util.S;
import static org.testng.AssertJUnit.*;

@Test
public class TableTest
  {
    private Table<NT, T, Object> table;

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

    public void testTableFilling ()
      {
        Object a = new Object ();
        Object b = new Object ();
        table.put (NT.A, T.X, a);
        assertEquals (new HashSet<Object> (L.ist (a)), table.get (NT.A, T.X));
        emptyExceptFor (NT.A, T.X);
        table.put (NT.A, T.X, b);
        assertEquals (new HashSet<Object> (L.ist (a, b)), table.get (NT.A, T.X));
        emptyExceptFor (NT.A, T.X);
      }
    
    public void testMultipleFills ()
      {

        Object a = new Object ();
        Object b = new Object ();
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

    @BeforeMethod
    public void setUp ()
      {
        table = new Table<NT, T, Object> (NT.A, T.EOF);
      }
  }
