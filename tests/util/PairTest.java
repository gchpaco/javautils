/*
 * Created on Oct 19, 2005
 */
package util;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class PairTest {
	public void equivalenceOfConstructorAndMake () {
		assertEquals (Pair.make (1, 2L), new Pair<Integer, Long> (1, 2L));
	}
	
	public void equivalenceOfHashCode () {
		assertEquals (Pair.make (1, 2L).hashCode(), Pair.make (1, 2L).hashCode());
	}
	
	public void equivalenceOfDataItems () {
		Object datum = new Object ();
		Pair<PairTest, Object> pair = Pair.make (this, datum);
		assertSame (this, pair.first);
		assertSame (datum, pair.second);
	}
	
	public void stringOutput () {
		assertEquals ("(1, 2)", Pair.make (1, 2L).toString ());
	}
}
