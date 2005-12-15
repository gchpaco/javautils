/*
 * Created on Oct 19, 2005
 */
package util;

import java.util.List;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class LTest {
	public void emptyList () {
		assertTrue (L.ist ().isEmpty ());
		assertEquals (L.ist (), L.ist ());
		assertNotSame (L.ist (), L.ist ());
	}

	public void contentList () {
		assertFalse (L.ist (1, 2, 3).isEmpty ());
		assertEquals (L.ist (1, 2, 3), L.ist (1, 2, 3));
		assertNotSame (L.ist (1, 2, 3), L.ist (1, 2, 3));
	}

	public void modifiable () {
		List<Integer> l = L.ist (1, 2, 3);
		assertFalse (l.contains (4));
		l.add (4);
		assertTrue (l.contains (4));
	}
}
