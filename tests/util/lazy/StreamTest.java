package util.lazy;
import org.apache.commons.functor.*;
import org.apache.commons.functor.core.*;
import util.Pair;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class StreamTest {
    public void simple() {
	Stream<Integer> ints = Stream.empty();
	assertTrue(ints.isEmpty());
	assertNull(ints.uncons());
	ints = Stream.cons(1, ints);
	Pair<Integer,Stream<Integer>> p = ints.uncons();
	assertFalse(ints.isEmpty());
	assertNotNull(p);
	assertEquals((Integer)1, ints.head());
	assertEquals(p.first, ints.head());
	assertEquals(p.second, ints.tail());
	assertTrue(ints.tail().isEmpty());
	try {
	    Stream.empty().head();
	    fail("Shouldn't be able to take the head of an empty stream");
	} catch (IllegalArgumentException e) {}
	try {
	    Stream.empty().tail();
	    fail("Shouldn't be able to take the head of an empty stream");
	} catch (IllegalArgumentException e) {}
    }
    static class Integers implements Function {
	int current;
	Integers(int i) { current = i; }
	public Stream<Integer> evaluate() {
	    return Stream.cons(current, new Integers(current+1));
	}
	static Stream<Integer> integers = new Integers(0).evaluate();
    }
    public void infinite() {
	Stream<Integer> ints = Integers.integers;
	assertFalse(ints.isEmpty());
	assertEquals((Integer)0, ints.head());
	assertEquals((Integer)1, ints.tail().head());
	assertEquals((Integer)2, ints.tail().tail().head());
	Stream<Integer> some = ints.drop(10).take(3);
	assertEquals((Integer)10, some.head());
	assertEquals((Integer)11, some.tail().head());
	assertEquals((Integer)12, some.tail().tail().head());
	assertTrue(some.tail().tail().tail().isEmpty());
    }
    public void reverse() {
	Stream<Integer> some = Integers.integers.drop(10).take(3).reverse();
	assertEquals((Integer)12, some.head());
	assertEquals((Integer)11, some.tail().head());
	assertEquals((Integer)10, some.tail().tail().head());
	assertTrue(some.tail().tail().tail().isEmpty());
    }
    static class Fibonacci implements Function {
	int last, current;
	Fibonacci(int i, int j) { last = i; current = j; }
	public Stream<Integer> evaluate() {
	    return Stream.cons(current, new Fibonacci(current, current+last));
	}
	static Stream<Integer> fibonacci = new Fibonacci(1,0).evaluate();
    }
    public void fibonacci() {
	Stream<Integer> s = Fibonacci.fibonacci;
	assertEquals((Integer)0, s.drop(0).head());
	assertEquals((Integer)1, s.drop(1).head());
	assertEquals((Integer)1, s.drop(2).head());
	assertEquals((Integer)2, s.drop(3).head());
	assertEquals((Integer)3, s.drop(4).head());
	assertEquals((Integer)5, s.drop(5).head());
	assertEquals((Integer)8, s.drop(6).head());
	assertEquals((Integer)987, s.drop(16).head());
    }
}