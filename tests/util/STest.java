package util;

import java.util.Set;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class STest
  {
    public void emptyList ()
      {
        assertTrue (S.et ().isEmpty ());
        assertEquals (S.et (), S.et ());
        assertNotSame (S.et (), S.et ());
      }

    public void contentList ()
      {
        assertFalse (S.et (1, 2, 3).isEmpty ());
        assertEquals (S.et (1, 2, 3), S.et (1, 2, 3));
        assertNotSame (S.et (1, 2, 3), S.et (1, 2, 3));
      }

    public void modifiable ()
      {
        Set<Integer> l = S.et (1, 2, 3);
        assertFalse (l.contains (4));
        l.add (4);
        assertTrue (l.contains (4));
      }

    public void setDetails ()
      {
        assertEquals (S.et (1, 2, 3), S.et (1, 2, 3, 2));
      }
  }
