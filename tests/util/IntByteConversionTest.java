/*
 * Created on Oct 19, 2005
 */
package util;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Arrays;
import java.util.BitSet;

import org.testng.annotations.Test;

public class IntByteConversionTest
{

  @Test
  static public void notNull ()
    {
      int numbers[] = { 4, 5, 6 };
      assertNotNull (IntByteConversion.toBytes (numbers));
    }

  @Test
  static public void roundTrip ()
    {
      int[] numbers = { 0, 2, 4, 5 };
      assertEqualArrays (
                         IntByteConversion
                                          .fromBytes (IntByteConversion
                                                                       .toBytes (numbers)),
                         numbers);
      numbers = new int[] { 0, 2, 4, 5, 3268, 402, 165 };
      Arrays.sort (numbers);
      assertEqualArrays (
                         IntByteConversion
                                          .fromBytes (IntByteConversion
                                                                       .toBytes (numbers)),
                         numbers);
    }

  @Test
  static public void enormous ()
    {
      int numbers[] = { 16 };
      byte expected[] = { 1, 0, 0 };
      assertEqualArrays (IntByteConversion.toBytes (numbers), expected);
    }

  @Test
  static public void simpleWithHoles ()
    {
      int numbers[] = { 0, 2, 4, 5 };
      byte expected[] = { 0 };
      for (int i = 0; i < numbers.length; i++)
        expected[0] += 1 << numbers[i];
      assertEqualArrays (IntByteConversion.toBytes (numbers), expected);
    }

  @Test
  static public void simple ()
    {
      int numbers[] = { 0, 1, 2, 3, 4, 5 };
      byte expected[] = { 0 };
      for (int i = 0; i < numbers.length; i++)
        expected[0] += 1 << numbers[i];
      assertEqualArrays (IntByteConversion.toBytes (numbers), expected);
    }

  @Test
  static public void emptyToEmpty ()
    {
      assertEqualArrays (IntByteConversion.toBytes (new int[] {}),
                         new byte[] {});
      assertEqualArrays (IntByteConversion.fromBytes (new byte[] {}),
                         new int[] {});
    }

  @SuppressWarnings ("unchecked")
  @Test
  static public void decode ()
    {
      byte[] data =
          { 0x00, 0x18, 0x00, 0x00, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x01,
           0x01, 0x00, 0x03, 0x00, 0x01, 0x01, 0x00, 0x04, 0x00, 0x01, 0x01,
           0x00, 0x07, 0x00, 0x05, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08,
           0x00, 0x05, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0B, 0x00, 0x05,
           0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0C, 0x00, 0x05, 0x01, 0x00,
           0x00, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x01, 0x02, 0x00, 0x10, 0x00,
           0x01, 0x02, 0x00, 0x13, 0x00, 0x01, 0x02, 0x00, 0x14, 0x00, 0x01,
           0x02, 0x00, 0x15, 0x00, 0x01, 0x02, 0x00, 0x16, 0x00, 0x01, 0x02,
           0x00, 0x19, 0x00, 0x01, 0x10, 0x00, 0x1C, 0x00, 0x01, 0x10, 0x00,
           0x1D, 0x00, 0x01, 0x10, 0x00, 0x1E, 0x00, 0x01, 0x10, 0x00, 0x1F,
           0x00, 0x01, 0x10, 0x00, 0x22, 0x00, 0x02, 0x00, 0xFFFFFF80, 0x00,
           0x23, 0x00, 0x02, 0x03, 0xFFFFFF80, 0x00, 0x26, 0x00, 0x04, 0x38,
           0x00, 0x00, 0x00, 0x00, 0x27, 0x00, 0x04, 0x38, 0x00, 0x00, 0x00,
           0x00, 0x2A, 0x00, 0x05, 0x00, 0xFFFFFF80, 0x00, 0x00, 0x00 };
      Pair<Integer, BitSet>[] sets = IntByteConversion.recoverBitSets (data);
      assertEquals (24, sets.length);
      Pair[] example =
          { Pair.make (0, makeSet (0)), Pair.make (0, makeSet (0)),
           Pair.make (3, makeSet (0)), Pair.make (4, makeSet (0)),
           Pair.make (7, makeSet (32)), Pair.make (8, makeSet (32)),
           Pair.make (11, makeSet (32)), Pair.make (12, makeSet (32)),
           Pair.make (15, makeSet (1)), Pair.make (16, makeSet (1)),
           Pair.make (19, makeSet (1)), Pair.make (20, makeSet (1)),
           Pair.make (21, makeSet (1)), Pair.make (22, makeSet (1)),
           Pair.make (25, makeSet (4)), Pair.make (28, makeSet (4)),
           Pair.make (29, makeSet (4)), Pair.make (30, makeSet (4)),
           Pair.make (31, makeSet (4)), Pair.make (34, makeSet (7)),
           Pair.make (35, makeSet (7, 8, 9)),
           Pair.make (38, makeSet (27, 28, 29)),
           Pair.make (39, makeSet (27, 28, 29)), Pair.make (42, makeSet (31)), };
      assertEqualArrays (sets, example);
    }

  @SuppressWarnings ("unchecked")
  @Test
  static public void decodeNegativeOffsets ()
    {
      byte[] data = { 0x00, 0x01, 0x00, (byte) 0xFA, 0x00, 0x01, 0x01 };
      Pair<Integer, BitSet>[] sets = IntByteConversion.recoverBitSets (data);
      assertEquals (1, sets.length);
      Pair[] example = { Pair.make (250, makeSet (0)), };
      assertEqualArrays (sets, example);
    }

  @SuppressWarnings ("unchecked")
  @Test
  static public void decodeLargeNegativeOffsets ()
    {
      byte[] data = { 0x00, 0x01, (byte) 0xFA, (byte) 0xFA, 0x00, 0x01, 0x01 };
      Pair<Integer, BitSet>[] sets = IntByteConversion.recoverBitSets (data);
      assertEquals (1, sets.length);
      Pair[] example = { Pair.make (64250, makeSet (0)), };
      assertEqualArrays (sets, example);
    }

  private static BitSet makeSet (int... i)
    {
      BitSet b = new BitSet ();
      for (int j = 0; j < i.length; j++)
        b.set (i[j]);
      return b;
    }

  static private void assertEqualArrays (byte[] result, byte[] expected)
    {
      assertTrue ("Saw " + Arrays.toString (result) + " but expected " +
                  Arrays.toString (expected), Arrays.equals (result, expected));
    }

  static private void assertEqualArrays (int[] result, int[] expected)
    {
      assertTrue ("Saw " + Arrays.toString (result) + " but expected " +
                  Arrays.toString (expected), Arrays.equals (result, expected));
    }

  static private void assertEqualArrays (Object[] result, Object[] expected)
    {
      assertTrue ("Saw " + Arrays.toString (result) + " but expected " +
                  Arrays.toString (expected), Arrays.equals (result, expected));
    }

}
