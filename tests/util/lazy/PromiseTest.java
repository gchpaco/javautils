package util.lazy;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;
import net.sf.jga.fn.Generator;
import net.sf.jga.fn.adaptor.Constant;

import org.testng.annotations.Test;

@Test
public class PromiseTest
{
  public void sameObject ()
    {
      final Object o = new Object ();
      Promise<Object> p = Promise.delay (new Constant<Object> (o));
      assertNotSame (o, p);
      assertSame (o, p.force ());
    }

  public void repeatedForces ()
    {
      final int[] i = { 0 };
      final Object o = new Object ();
      Promise<Object> p = Promise.delay (new Generator<Object> ()
        {
          @Override
          public Object gen ()
            {
              i[0]++;
              return o;
            }
        });
      assertEquals (i[0], 0);
      assertNotSame (o, p);
      assertSame (o, p.force ());
      assertEquals (i[0], 1);
      assertSame (o, p.force ());
      assertEquals (i[0], 1);
    }
}