package util.lazy;
import org.apache.commons.functor.*;
import org.apache.commons.functor.core.*;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class PromiseTest {
    public void sameObject() {
	final Object o = new Object();
	Promise<Object> p = Promise.delay(Constant.instance(o));
	assertNotSame(o, p);
	assertSame(o, p.force());
    }

    public void repeatedForces() {
	final int[] i = {0};
	final Object o = new Object();
	Promise<Object> p = Promise.delay(new Function() {
		public Object evaluate() { i[0]++; return o; }
	    });
	assertEquals(i[0], 0);
	assertNotSame(o, p);
	assertSame(o, p.force());
	assertEquals(i[0], 1);
	assertSame(o, p.force());
	assertEquals(i[0], 1);
    }
}