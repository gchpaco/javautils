/*
 * Created on Oct 28, 2005
 */
package util.soot;

import static org.testng.AssertJUnit.*;

import java.util.Arrays;

import org.testng.annotations.Test;

import util.IntByteConversion;

public class BitSetTagTest {
	@Test
	public void bitSetValueGeneration () {
		BitSetTag t = makeBitTag ();
		t.add (10);
		t.add (11);
		t.add (34);
		byte[] expected = expectedValueFor (new int [] { 10, 11, 34 });
		final byte[] value = t.getValue ();
		assertEquals (expected, value);
	}
	@Test
	public void bigSet () {
		BitSetTag t = makeBitTag ();
		t.add (10);
		t.add (11);
		t.add (34000);
		byte[] expected = expectedValueFor (new int [] { 10, 11, 34000 });
		final byte[] value = t.getValue ();
		assertEquals (expected, value);
	}
	private void assertEquals (byte[] expected, final byte[] value) {
		final String message = String.format ("Saw %s when was expecting %s",
				Arrays.toString (value), Arrays.toString (expected));
		assertTrue (message, Arrays.equals (expected, value));
	}
	private BitSetTag makeBitTag () {
		BitSetTag t = new BitSetTag () {
			@Override
			public BitSetTag clone () {
				return null;
			}

			public String getName () {
				return "foo";
			}
		};
		return t;
	}
	private byte[] expectedValueFor (int[] array) {
		byte [] converted = IntByteConversion.toBytes(array);
		byte[] expected = new byte[converted.length + 2];
		expected[0] = (byte) (converted.length / 256);
		expected[1] = (byte) (converted.length % 256);
		System.arraycopy(converted, 0, expected, 2, converted.length);
		return expected;
	}
}
