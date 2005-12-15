/*
 * Created on Oct 19, 2005
 */
package util;

import java.math.BigInteger;
import java.util.BitSet;

public class IntByteConversion {
	static public byte[] toBytes (int[] numbers) {
		if (numbers.length == 0)
			return new byte[] {};
		BigInteger bi = BigInteger.valueOf (0L);
		for (int i = 0; i < numbers.length; i++)
			bi = bi.setBit (numbers[i]);
		return bi.toByteArray ();
	}

	static public int[] fromBytes (byte[] bytes) {
		if (bytes.length == 0)
			return new int[] {};
		BigInteger bi = new BigInteger (bytes);
		int result[] = new int[bi.bitCount ()];
		int r = 0;
		for (int i = 0; i < bi.bitLength (); i++)
			if (bi.testBit (i))
				result[r++] = i;
		assert r == result.length : "didn't iterate all the way through!";
		return result;
	}
	
	@SuppressWarnings("unchecked")
	static public Pair<Integer,BitSet>[] recoverBitSets (byte [] bytes) {
		int count = selectIntAt (bytes, 0);
		int bc = 2;
		Pair<Integer,BitSet> sets[] = new Pair [count];
		for (int i = 0; i < count; i++) {
			BitSet set = new BitSet ();
			int pc = selectIntAt (bytes, bc);
			bc += 2;
			sets[i] = Pair.make (pc, set);
			int len = selectIntAt (bytes, bc);
			bc += 2;
			byte [] encoding = new byte[len];
			System.arraycopy(bytes, bc, encoding, 0, len);
			bc += len;
			int [] ints = fromBytes (encoding);
			for (int j = 0; j < ints.length; j++)
				set.set (ints[j]);
		}
		return sets;
	}

	private static int selectIntAt (byte[] array, int index) {
		return (fetchInt (array, index) << 8) + fetchInt (array, index + 1);
	}

	private static int fetchInt (byte[] array, int index) {
		if (array[index] < 0) return 256 + array[index];
        return array[index];
	}
}
