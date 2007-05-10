package util;

import java.util.Map;

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
    }
}
