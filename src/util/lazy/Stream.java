package util.lazy;
import util.Closure;
import util.Pair;

abstract class StreamCell<T> {
    public abstract boolean isEmpty();
}
final class Nil<T> extends StreamCell<T> {
    private Nil() {}
    static Nil nil = new Nil();
    public boolean isEmpty() { return true; }
}
final class Cons<T> extends StreamCell<T> {
    T element;
    Stream<T> next;
    Cons(T t, Stream<T> n) { element = t; next = n; }
    public boolean isEmpty() { return false; }
}
// BTW, yes, using this is horrible.  I'm sorry.
public class Stream<T> extends Promise<StreamCell<T>>
{
    protected Stream(Closure<StreamCell<T>> c) { super(c); }

    public Stream<T> concatenate(final Stream<T> t) {
	final Pair<T,Stream<T>> p = t.uncons();
	if (p == null) return t;
	return cons(p.first, new Closure<Stream<T>>() {
		public Stream<T> apply() {
		    return p.second.concatenate(t);
		}
	    });
    }
    public Stream<T> take(final int n) {
	if (n == 0) return empty();
	final Pair<T,Stream<T>> p = uncons();
	if (p == null) return this;
	return cons(p.first, new Closure<Stream<T>>() {
		public Stream<T> apply() {
		    return p.second.take(n - 1);
		}
	    });
    }
    public Stream<T> drop(final int n) {
	if (n == 0) return this;
	final Pair<T,Stream<T>> p = uncons();
	if (p == null) return this;
	return p.second.drop(n - 1);
    }
    protected Stream<T> reverse(final Stream<T> r) {
	final Pair<T,Stream<T>> p = uncons();
	if (p == null) return r;
	return p.second.reverse(cons(p.first, r));
    }
    public Stream<T> reverse() {
	return reverse((Stream<T>)empty());
    }
    
    // The following are probably feeble attempts to make this less
    // atrocious to use.  Damn Java.
    public boolean isEmpty() { return force().isEmpty(); }
    public T head() {
	if (isEmpty())
	    throw new IllegalArgumentException("Tried to take head of" +
					       " an empty stream");
	return ((Cons<T>) force()).element;
    }
    public Stream<T> tail() {
	if (isEmpty())
	    throw new IllegalArgumentException("Tried to take head of" +
					       " an empty stream");
	return ((Cons<T>) force()).next;
    }
    public Pair<T,Stream<T>> uncons() {
	if (isEmpty()) return null;
	Cons<T> c = (Cons<T>) force();
	return Pair.make(c.element, c.next);
    }
    public static <T> Stream<T> cons(final T head, final Stream<T> rest) {
	return delay(new Closure<StreamCell<T>>() {
		public StreamCell<T> apply() {
		    return new Cons(head, rest);
		}
	    });
    }
    public static <T> Stream<T> cons(final Closure<T> head,
				     final Stream<T> rest) {
	return delay(new Closure<StreamCell<T>>() {
		public StreamCell<T> apply() {
		    return new Cons(head.apply(), rest);
		}
	    });
    }
    public static <T> Stream<T> cons(final T head,
				     final Closure<Stream<T>> rest) {
	return delay(new Closure<StreamCell<T>>() {
		public StreamCell<T> apply() {
		    return new Cons(head, rest.apply());
		}
	    });
    }
    public static <T> Stream<T> cons(final Closure<T> head,
				     final Closure<Stream<T>> rest) {
	return delay(new Closure<StreamCell<T>>() {
		public StreamCell<T> apply() {
		    return new Cons(head.apply(), rest.apply());
		}
	    });
    }
    protected static <T> Stream<T> delay(Closure<StreamCell<T>> closure)
    {
	return new Stream<T>(closure);
    }
    private static Stream empt = null;
    public static <T> Stream<T> empty() {
	if (empt == null)
	    empt = delay(new Closure<StreamCell<T>>() {
		    public StreamCell<T> apply() {
			return Nil.nil;
		    }
		});
	return empt;
    }
}