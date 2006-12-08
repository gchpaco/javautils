package util;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

@Test
public class FunctionsTest
  {
    @SuppressWarnings("unchecked")
    public void constant () {
      Object o = new Object ();
      assertSame (o, Functions.constant (o).apply (3));
      Function<Object, Object> f = Functions.constant (o);
      assertSame (o, f.apply (3));
      assertSame (o, f.apply (o));
    }
  }
