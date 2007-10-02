package util.lazy;

import net.sf.jga.fn.Generator;

public class Promise<T> extends Generator<T>
{
  private static final long serialVersionUID = 1L;
  private final Generator<? extends T> c;
  private T value;

  protected Promise (Generator<? extends T> closure)
    {
      c = closure;
      value = null;
    }

  public T force ()
    {
      if (value == null) value = c.eval ();
      return value;
    }

  @Override
  public T gen ()
    {
      return force ();
    }

  public static <T> Promise<T> delay (Generator<? extends T> closure)
    {
      return new Promise<T> (closure);
    }
}