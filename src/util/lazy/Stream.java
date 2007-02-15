package util.lazy;
import org.apache.commons.functor.*;
import org.apache.commons.functor.core.Constant;

import util.Pair;

abstract class StreamCell<T> {
    public abstract boolean isEmpty();
}

final class Nil<T> extends StreamCell<T> {
    private Nil() {
    }

    @SuppressWarnings("unchecked")
    static Nil nil = new Nil();

    @Override
    public boolean isEmpty() {
        return true;
    }
}

final class Cons<T> extends StreamCell<T> {
    T element;

    Stream<T> next;

    Cons(T t, Stream<T> n) {
        element = t;
        next = n;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

// BTW, yes, using this is horrible. I'm sorry.
public class Stream<T> extends Promise<StreamCell<T>> {
    protected Stream(Function c) {
        super(c);
    }

    public Stream<T> concatenate(final Stream<T> t) {
        final Pair<T, Stream<T>> p = t.uncons();
        if (p == null)
            return t;
        return cons(p.first, new Function() {
            public Stream<T> evaluate() {
                return p.second.concatenate(t);
            }
        });
    }

    public Stream<T> take(final int n) {
        if (n == 0)
            return empty();
        final Pair<T, Stream<T>> p = uncons();
        if (p == null)
            return this;
        return cons(p.first, new Function() {
            public Stream<T> evaluate () {
                return p.second.take(n - 1);
            }
        });
    }

    public Stream<T> drop(final int n) {
        if (n == 0)
            return this;
        final Pair<T, Stream<T>> p = uncons();
        if (p == null)
            return this;
        return p.second.drop(n - 1);
    }

    protected Stream<T> reverse(final Stream<T> r) {
        final Pair<T, Stream<T>> p = uncons();
        if (p == null)
            return r;
        return p.second.reverse(cons(p.first, r));
    }

    @SuppressWarnings("unchecked")
    public Stream<T> reverse() {
        return reverse((Stream<T>) empty());
    }

    // The following are probably feeble attempts to make this less
    // atrocious to use. Damn Java.
    public boolean isEmpty() {
        return force().isEmpty();
    }

    public T head() {
        if (isEmpty())
            throw new IllegalArgumentException("Tried to take head of"
                    + " an empty stream");
        return ((Cons<T>) force()).element;
    }

    public Stream<T> tail() {
        if (isEmpty())
            throw new IllegalArgumentException("Tried to take head of"
                    + " an empty stream");
        return ((Cons<T>) force()).next;
    }

    public Pair<T, Stream<T>> uncons() {
        if (isEmpty())
            return null;
        Cons<T> c = (Cons<T>) force();
        return Pair.make(c.element, c.next);
    }

    public static <T> Stream<T> cons(final T head, final Stream<T> rest) {
        return Stream.delayS(new Function() {
            public StreamCell<T> evaluate() {
                return new Cons<T>(head, rest);
            }
        });
    }

    public static <T> Stream<T> cons(final Function head, final Stream<T> rest) {
        return Stream.delayS(new Function () {
            public StreamCell<T> evaluate () {
                return new Cons<T>((T) head.evaluate (), rest);
            }
        });
    }

    public static <T> Stream<T> cons(final T head, final Function rest) {
        return Stream.delayS(new Function () {
            @SuppressWarnings("unchecked")
            public StreamCell<T> evaluate() {
                return new Cons<T>(head, (Stream<T>) rest.evaluate());
            }
        });
    }

    public static <T> Stream<T> cons(final Function head, final Function rest) {
        return Stream.delayS(new Function() {
            public StreamCell<T> evaluate() {
                return new Cons<T>((T) head.evaluate(), (Stream<T>) rest.evaluate());
            }
        });
    }

    protected static <U> Stream<U> delayS(Function closure) {
        return new Stream<U>(closure);
    }

    private static Stream<?> empt = new Stream<Object>(Constant.instance(Nil.nil));

    @SuppressWarnings("unchecked")
    public static <U> Stream<U> empty() {
        return (Stream<U>) empt;
    }
}