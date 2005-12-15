/*
 * Created on Oct 19, 2005
 */
package util.soot;

import static org.testng.AssertJUnit.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import soot.EquivTo;

public class EquivWrapperTest {
	private StupidEquivTo i, j;
	private EquivWrapper<StupidEquivTo> wi, wj;

	static class StupidEquivTo implements EquivTo {
		Integer i;

		StupidEquivTo (int i) {
			this.i = i;
		}

		public boolean equivTo (Object obj) {
			if (obj instanceof StupidEquivTo == false) {
				return false;
			}
			if (this == obj) {
				return true;
			}
			StupidEquivTo rhs = (StupidEquivTo) obj;
			return new EqualsBuilder ().append (i, rhs.i).isEquals ();
		}

		public int equivHashCode () {
			return new HashCodeBuilder (5, 13).append (i).toHashCode ();
		}
		
		@Override
		public String toString () {
			return "Something outrageously stupid for " + i.hashCode ();
		}
	}

	@Test
	public void equalityPropertiesOfStupid () {
		setUp ();
		assertNotSame (i, j);
		assertFalse (i.equals (j));
		assertTrue (i.equivTo (j));
		assertFalse (i.hashCode () == j.hashCode ());
	}

	@Configuration(beforeTestMethod=true)
	private void setUp () {
		i = new StupidEquivTo (4);
		j = new StupidEquivTo (4);
		wi = EquivWrapper.wrap (i);
		wj = EquivWrapper.wrap (j);
	}

	@Test
	public void equalityOfWrapped () {
		assertEquals (wi, wj);
		assertEquals (wi.hashCode (), wj.hashCode ());
	}
	
	@Test
	public void strings () {
		assertEquals (i.toString (), wi.toString ());
	}
	
	@Test
	public void unwrapping () {
		assertSame (i, wi.getDatum());
		assertNotSame (i, wj.getDatum ());
	}
	
	@Test
	public void cache () {
		assertSame (EquivWrapper.intern (i), EquivWrapper.intern (i));
		assertSame (EquivWrapper.intern (j), EquivWrapper.intern (i));
		assertSame (EquivWrapper.intern (i), i);
	}

}
