package util.lazy;

import net.sf.jga.fn.Generator;

public class SynchronizedPromise<T> extends Promise<T>
{
  private static final long serialVersionUID = 7299581701168066848L;

  protected SynchronizedPromise (Generator<? extends T> closure)
    {
      super (closure);
    }

  @Override
  public synchronized T force ()
    {
      return super.force ();
    }

  public static <T> SynchronizedPromise<T> delay (Generator<? extends T> closure)
    {
      return new SynchronizedPromise<T> (closure);
    }
}