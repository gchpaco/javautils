/*
 * Created on Oct 19, 2005
 */
package util;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class TripleTest {
	public void equivalenceOfConstructorAndMake () {
		assertEquals (Triple.make (1, 2L, 3.0), new Triple<Integer, Long, Double> (1, 2L, 3.0));
	}
	
	public void equivalenceOfHashCode () {
		assertEquals (Triple.make (1, 2L, 3.0).hashCode(), Triple.make (1, 2L, 3.0).hashCode());
	}
	
	public void equivalenceOfDataItems () {
		Object datum = new Object ();
		Object datum2 = new Object ();
		Triple<TripleTest, Object, Object> pair = Triple.make (this, datum, datum2);
		assertSame (this, pair.first);
		assertSame (datum, pair.second);
		assertSame (datum2, pair.third);
	}
	
	public void stringOutput () {
		assertEquals ("(1, 2, 3.0)", Triple.make (1, 2L, 3.0).toString ());
	}
}
