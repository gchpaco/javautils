package util.lazy;

import net.sf.jga.fn.Generator;
import net.sf.jga.fn.adaptor.Constant;
import util.Pair;

abstract class StreamCell<T>
{
  public abstract boolean isEmpty ();
}

final class Nil<T> extends StreamCell<T>
{
  private Nil ()
    {
    }

  static Nil<Object> nil = new Nil<Object> ();

  @Override
  public boolean isEmpty ()
    {
      return true;
    }
}

final class Cons<T> extends StreamCell<T>
{
  T element;

  Stream<T> next;

  Cons (T t, Stream<T> n)
    {
      element = t;
      next = n;
    }

  @Override
  public boolean isEmpty ()
    {
      return false;
    }
}

// BTW, yes, using this is horrible. I'm sorry.
public class Stream<T> extends Promise<StreamCell<T>>
{
  protected Stream (Generator<? extends StreamCell<T>> c)
    {
      super (c);
    }

  public Stream<T> concatenate (final Stream<T> t)
    {
      final Pair<T, Stream<T>> p = t.uncons ();
      if (p == null) return t;
      return cons (p.first, new Generator<Stream<T>> ()
        {
          @Override
          public Stream<T> gen ()
            {
              return p.second.concatenate (t);
            }
        });
    }

  public Stream<T> take (final int n)
    {
      if (n == 0) return empty ();
      final Pair<T, Stream<T>> p = uncons ();
      if (p == null) return this;
      return cons (p.first, new Generator<Stream<T>> ()
        {
          @Override
          public Stream<T> gen ()
            {
              return p.second.take (n - 1);
            }
        });
    }

  public Stream<T> drop (final int n)
    {
      if (n == 0) return this;
      final Pair<T, Stream<T>> p = uncons ();
      if (p == null) return this;
      return p.second.drop (n - 1);
    }

  protected Stream<T> reverse (final Stream<T> r)
    {
      final Pair<T, Stream<T>> p = uncons ();
      if (p == null) return r;
      return p.second.reverse (cons (p.first, r));
    }

  @SuppressWarnings ("unchecked")
  public Stream<T> reverse ()
    {
      return reverse ((Stream<T>) empty ());
    }

  // The following are probably feeble attempts to make this less
  // atrocious to use. Damn Java.
  public boolean isEmpty ()
    {
      return force ().isEmpty ();
    }

  public T head ()
    {
      if (isEmpty ())
        throw new IllegalArgumentException ("Tried to take head of"
                                            + " an empty stream");
      return ((Cons<T>) force ()).element;
    }

  public Stream<T> tail ()
    {
      if (isEmpty ())
        throw new IllegalArgumentException ("Tried to take head of"
                                            + " an empty stream");
      return ((Cons<T>) force ()).next;
    }

  public Pair<T, Stream<T>> uncons ()
    {
      if (isEmpty ()) return null;
      Cons<T> c = (Cons<T>) force ();
      return Pair.make (c.element, c.next);
    }

  public static <T> Stream<T> cons (final T head, final Stream<T> rest)
    {
      return Stream.delayS (new Generator<StreamCell<T>> ()
        {
          @Override
          public StreamCell<T> gen ()
            {
              return new Cons<T> (head, rest);
            }
        });
    }

  public static <T> Stream<T> cons (final Generator<T> head,
                                    final Stream<T> rest)
    {
      return Stream.delayS (new Generator<StreamCell<T>> ()
        {
          @Override
          public StreamCell<T> gen ()
            {
              return new Cons<T> (head.fn (), rest);
            }
        });
    }

  public static <T> Stream<T> cons (final T head,
                                    final Generator<Stream<T>> rest)
    {
      return Stream.delayS (new Generator<StreamCell<T>> ()
        {
          @Override
          @SuppressWarnings ("unchecked")
          public StreamCell<T> gen ()
            {
              return new Cons<T> (head, rest.fn ());
            }
        });
    }

  public static <T> Stream<T> cons (final Generator<T> head,
                                    final Generator<Stream<T>> rest)
    {
      return Stream.delayS (new Generator<StreamCell<T>> ()
        {
          @Override
          public StreamCell<T> gen ()
            {
              return new Cons<T> (head.fn (), rest.fn ());
            }
        });
    }

  protected static <U> Stream<U> delayS (Generator<StreamCell<U>> closure)
    {
      return new Stream<U> (closure);
    }

  private static Stream<?> empt =
      new Stream<Object> (new Constant<Nil<Object>> (Nil.nil));

  @SuppressWarnings ("unchecked")
  public static <U> Stream<U> empty ()
    {
      return (Stream<U>) empt;
    }
}