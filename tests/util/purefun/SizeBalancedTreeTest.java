package util.purefun;

import org.testng.annotations.*;

import util.*;
import static org.testng.AssertJUnit.*;

@Test
public class SizeBalancedTreeTest
  {
    private SizeBalancedTree<Integer, String> set;

    @SuppressWarnings("unchecked")
    @BeforeTest
    public void setUp ()
      {
        set = SizeBalancedTree.emptySet ();
      }

    public void emptyTree ()
      {
        assertTrue (set.isNull ());
        assertSame (set, set.delete (4));
        assertEquals (0, set.count (4));
        assertFalse (set.isMember (4));
        assertSame (set, set.empty ());
        assertNull (set.lookup (4));
        assertEquals ("Tip", set.toString ());
      }
    
    public void smallInsertion ()
      {
        SizeBalancedTree<Integer, String> s = set.insert (4, "foo");
        assertNotSame (set, s);
        assertFalse (set.isMember (4));
        assertTrue (s.isMember (4));
        assertEquals ("foo", s.lookup (4));
        assertEquals (1, s.count (4));
        assertEquals (1, s.size ());
        assertFalse (s.isNull ());
        assertSame (set, s.delete (4));
        assertEquals ("Bin (1, 4, foo, Tip, Tip)", s.toString ());
      }
    
    public void sequenceInsertion ()
      {
        Association<Integer, String> largeSet = makeLargeSet ();
        assertTrue (largeSet.isMember (4)); assertEquals ("foo", largeSet.lookup (4));
        assertTrue (largeSet.isMember (-5)); assertEquals ("bar", largeSet.lookup (-5));
        assertTrue (largeSet.isMember (17)); assertEquals ("quux", largeSet.lookup (17));
        assertEquals (3, largeSet.size ());
        largeSet = makeVeryLargeSet ();
        assertTrue (largeSet.isMember (4)); assertEquals ("foo", largeSet.lookup (4));
        assertTrue (largeSet.isMember (-5)); assertEquals ("bar", largeSet.lookup (-5));
        assertTrue (largeSet.isMember (17)); assertEquals ("b", largeSet.lookup (17));
        assertTrue (largeSet.isMember (50)); assertEquals ("baz", largeSet.lookup (50));
        assertTrue (largeSet.isMember (60)); assertEquals ("quux", largeSet.lookup (60));
        assertTrue (largeSet.isMember (1032)); assertEquals ("quubu", largeSet.lookup (1032));
        assertEquals (6, largeSet.size ());
      }

    @SuppressWarnings("unchecked")
    private Association<Integer, String> makeVeryLargeSet ()
      {
        return set.insert (L.ist (Pair.make (4, "foo"), Pair.make (-5, "bar"), Pair.make (17, "quux"), 
                                      Pair.make (50, "baz"), Pair.make (60, "quux"), Pair.make (17, "b"), 
                                      Pair.make (1032, "quubu")));
      }
    
    public void replacement ()
      {
        SizeBalancedTree<Integer, String> s = set.insert (4, "foo").insert (4, "bar");
        assertTrue (s.isMember (4));
        assertEquals ("bar", s.lookup (4));
        assertEquals (1, s.count (4));
        assertEquals (1, s.size ());
      }
    
    @SuppressWarnings("unchecked")
    public void union ()
      {
        assertSame (set, set.union (set));
        Association<Integer, String> largeSet = makeLargeSet ();
        assertSame (largeSet, largeSet.union (set));
        assertSame (largeSet, set.union (largeSet));
        Association<Integer, String> bigSet = largeSet.union (set.insert (Pair.make (50, "baz"), Pair.make (60, "quux"), Pair.make (17, "b"), Pair.make (1032, "quubu")));
        assertNotSame (bigSet, largeSet);
        assertNotSame (set, largeSet);
        assertEquals ("quux", bigSet.lookup (17));
        assertEquals (1, bigSet.count (17));
        assertEquals (6, bigSet.size ()); // not seven, because two values with key of 17.
      }

    @SuppressWarnings("unchecked")
    private Association<Integer, String> makeLargeSet ()
      {
        return set.insert (Pair.make (4, "foo"), Pair.make (-5, "bar"), Pair.make (17, "quux"));
      }
    
    public void deletions ()
      {
        Association<Integer, String> bigSet = makeVeryLargeSet ();
        assertSame (set, set.delete (4));
        assertEquals (bigSet.size (), bigSet.delete (65).size ());
        Association<Integer, String> slightlySmallerSet = bigSet.delete (60);
        assertEquals (5, slightlySmallerSet.size ());
        assertNull (slightlySmallerSet.lookup (60));
        assertNotNull (bigSet.lookup (60));
        assertTrue (slightlySmallerSet.isMember (17));
        slightlySmallerSet = bigSet.deleteAll (60);
        assertEquals (5, slightlySmallerSet.size ());
        assertNull (slightlySmallerSet.lookup (60));
        assertNotNull (bigSet.lookup (60));
        assertTrue (slightlySmallerSet.isMember (17));
        Association<Integer, String> smallSet = bigSet.delete (50, 60, -5, 72);
        assertEquals (3, smallSet.size ());
        assertNull (smallSet.lookup (-5));
        assertNotNull (smallSet.lookup (1032));
        
        Pair<String, Association<Integer, String>> pair = bigSet.lookupAndDelete (50);
        assertEquals ("baz", pair.first);
        assertFalse (pair.second.isMember (50));
        assertEquals (5, pair.second.size ());
      }
    
    public void defaulting ()
      {
        String o = new String ("foo");
        assertNotSame (o, "foo");
        assertNull (set.lookup (4));
        assertSame (o, set.lookupWithDefault (4, o));
      }
    
    @SuppressWarnings("unchecked")
    public void adjusting ()
      {
        Association<Integer, String> bigSet = makeVeryLargeSet ();
        Association<Integer, String> adjusted = bigSet.adjust (new Function<String,String> () {
          public String apply (String arg)
          {
            return arg + " appended";
          }
        }, 50);
        assertEquals (6, adjusted.size ());
        assertEquals ("baz appended", adjusted.lookup (50));
      }
  }
