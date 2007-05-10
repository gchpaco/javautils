package util;

import java.util.*;

import org.testng.annotations.*;
import static org.testng.AssertJUnit.*;

@Test
public class SymbolTableTest {
    SymbolTable table;
    @BeforeTest
    private void setUp () {
        table = new SymbolTable ();
    }

    public void emptyTable () {
        assertTrue (table instanceof Map);
        assertTrue (table.isEmpty ());
        assertEquals (0, table.size ());
        assertNotNull (table.keySet ());
        assertTrue (table.keySet ().isEmpty ());
        assertNotNull (table.values ());
        assertTrue (table.values ().isEmpty ());
        assertNotNull (table.entrySet ());
        assertTrue (table.entrySet ().isEmpty ());
    }

    public void singleLevelTable () {
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
        for (Object v : table.values ()) {
            count++;
            assertSame (value, v);
        }
        assertEquals (1, count);
        Map.Entry entry = new Map.Entry () {
                public Object getKey () { return key; }
                public Object getValue () { return value; }
                public Object setValue (Object o) { return value; }
            };
        assertEquals (Collections.singleton (entry), table.entrySet ());
        table.remove (key);
        emptyTable ();
    }
}
