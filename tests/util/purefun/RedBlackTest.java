package util.purefun;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class RedBlackTest {
    public void simplePersistence() throws EmptyException {
        Set<Integer> set = RedBlackSet.empty();
        Set<Integer> set2 = set.insert(5);
        assertTrue(set2.member(5));
        assertFalse(set.member(5));
        Set<Integer> set3 = set2.insert(2);
        assertTrue(set3.member(2));
        assertFalse(set2.member(2));
        assertFalse(set.member(2));
        assertTrue(set3.member(5));
        assertTrue(set2.member(5));
        assertFalse(set.member(5));
    }

    public void insertion() {
        int[] testData = { 6, 4, 2, 7, 8, 1, 3, 0, 6, 22 };
        Set<Integer> set = RedBlackSet.empty();
        for (int i : testData)
            assertFalse(set.member(i));
        for (int i : testData)
            set = set.insert(i);
        for (int i : testData)
            assertTrue(set.member(i));
    }
}