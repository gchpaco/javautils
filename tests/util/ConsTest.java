package util;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;
import static util.Cons.*;

@Test
public class ConsTest {
    public void simple() {
	Cons<Integer> c = cons(4, cons(2, null));
	assertEquals((Integer)4, c.car());
	assertEquals((Integer)2, c.cdr().car());
	assertNull(c.cdr().cdr());
    }
    public void reverses() {
	assertNull(reverse(null));
	Cons<Integer> c = cons(4, cons(2, null));
	Cons<Integer> r = reverse(c);
	assertEquals((Integer)2, r.car());
	assertEquals((Integer)4, r.cdr().car());
	assertNull(r.cdr().cdr());
	assertEquals((Integer)4, c.car());
	assertEquals((Integer)2, c.cdr().car());
	assertNull(c.cdr().cdr());
    }
}
