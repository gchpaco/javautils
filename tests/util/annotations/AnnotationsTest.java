package util.annotations;

import java.lang.annotation.*;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;
import java.lang.reflect.*;

@Test
public class AnnotationsTest
  {
      @Retention(RetentionPolicy.RUNTIME)
      public @interface SampleAnnotation {
      }

      @SampleAnnotation
	  @Test(enabled=false)
	  public void foo () {
      }
      @SampleAnnotation public int fooField;

      public void methods () {
	  Member m = Annotations.getAnnotatedMethod (this.getClass (),
						     SampleAnnotation.class);
	  assertNotNull (m);
	  assertEquals ("foo", m.getName ());
	  assertNull (Annotations.getAnnotatedMethod (this.getClass (),
						      SuppressWarnings.class));
      }

      public void fields () {
	  Field f = Annotations.getAnnotatedField (this.getClass (),
						    SampleAnnotation.class);
	  assertNotNull (f);
	  assertEquals ("fooField", f.getName ());
	  assertNull (Annotations.getAnnotatedField (this.getClass (),
						     SuppressWarnings.class));
      }
  }
