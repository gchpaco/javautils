/*
 * Created on Oct 21, 2005
 */
package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import util.Pair;

public class Path<T> implements Iterable<Pair<T, T>> {
    T element;

    Path<T> rest;

    public Path(T t, Path<T> rest) {
        this.element = t;
        this.rest = rest;
    }

    public Iterator<Pair<T, T>> iterator() {
        return Path.iterator(this);
    }

    public boolean contains(T prev, T next) {
        for (Pair<T, T> t : this) {
            if (prev == null) {
                if (t.first != null)
                    continue;
                return t.second.equals(next);
            }
            if (prev.equals(t.first) && next.equals(t.second))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append("null");
        for (Pair<T, T> t : this) {
            b.append("->");
            b.append(t.second);
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Path == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Path rhs = (Path) obj;
        return new EqualsBuilder().append(element, rhs.element).append(rest,
                rhs.rest).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 19).append(element).append(rest)
                .toHashCode();
    }

    public static <U> Path<U> cons(U u, Path<U> p) {
        return new Path<U>(u, p);
    }

    public static <U> Path<U> make(U... u) {
        Path<U> result = null;
        for (int i = 0; i < u.length; i++) {
            result = cons(u[i], result);
        }
        return result;
    }

    public static <U> Iterator<Pair<U, U>> iterator(Path<U> p) {
        final ArrayList<U> nodesInOrder = new ArrayList<U>();
        for (Path<U> ptr = p; ptr != null; ptr = ptr.rest)
            nodesInOrder.add(ptr.element);
        Collections.reverse(nodesInOrder);
        return new Iterator<Pair<U, U>>() {
            Iterator<U> i = nodesInOrder.iterator();

            U last = null;

            public boolean hasNext() {
                return i.hasNext();
            }

            public Pair<U, U> next() {
                U oldLast = last;
                last = i.next();
                return Pair.make(oldLast, last);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static <U> boolean isEmpty(Path<U> p) {
        return p == null;
    }

    public static <U> int length(Path<U> p) {
        int i = 0;
        for (Path<U> ptr = p; ptr != null; ptr = ptr.cdr())
            i++;
        return i;
    }

    public T car() {
        return element;
    }

    public Path<T> cdr() {
        return rest;
    }
}
