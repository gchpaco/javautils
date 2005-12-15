/*
 * Created on Oct 28, 2005
 */
package util.soot;

import java.util.BitSet;

import soot.tagkit.AttributeValueException;
import soot.tagkit.Tag;
import util.IntByteConversion;

public abstract class BitSetTag implements Tag {
	public BitSetTag (BitSet tags) {
		this.tags = (BitSet) tags.clone ();
	}

	public BitSetTag () {
		tags = new BitSet ();
	}

	protected BitSet tags = new BitSet ();

	public byte[] getValue () throws AttributeValueException {
		int values[] = new int[tags.cardinality ()];
		int index = 0;
		for (int i = tags.nextSetBit (0); i >= 0; i = tags.nextSetBit (i + 1)) {
			values[index++] = i;
		}
		final byte[] bytes = IntByteConversion.toBytes (values);
		byte[] result = new byte[bytes.length + 2];
		result[0] = (byte) (bytes.length >> 8);
		result[1] = (byte) (bytes.length % (1 << 8));
		System.arraycopy(bytes, 0, result, 2, bytes.length);
		return result;
	}

	public void add (int edgenumber) {
		tags.set (edgenumber);
	}

	public BitSet edges () {
		return tags;
	}

	public abstract BitSetTag clone ();

	public void mergeInto (Tag tag) {
		assert tag instanceof BitSetTag;
		BitSetTag bt = (BitSetTag) tag;
		bt.tags.or (tags);
	}

}
