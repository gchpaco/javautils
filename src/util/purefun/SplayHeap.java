package util.purefun;
import util.Pair;

public abstract class SplayHeap<T extends Comparable<? super T>>
    implements Heap<T>
{
    // I give you the finger, Java generics!
    protected static <T extends Comparable<? super T>>
	Pair<SplayHeap<T>,SplayHeap<T>> make (SplayHeap<T> l, SplayHeap<T> r) {
	return Pair.make (l, r);
    }
    protected static class Empty<T extends Comparable<? super T>>
	extends SplayHeap<T> {
	static protected final Empty empty = new Empty();
	public boolean isEmpty() { return true; }
	protected Pair<SplayHeap<T>,SplayHeap<T>> partition(T pivot) {
	    return make(empty(), empty());
	}
	public T findMin() throws EmptyException {
	    throw new EmptyException();
	}
	public SplayHeap<T> deleteMin() throws EmptyException {
	    throw new EmptyException();
	}
	public SplayHeap<T> merge(Heap<T> h) { return (SplayHeap<T>) h; }
    }
    protected static class Node<T extends Comparable<? super T>>
	extends SplayHeap<T> {
	T element;
	SplayHeap<T> left, right;

	protected Node(SplayHeap<T> l, T t, SplayHeap<T> r) {
	    element = t; left = l; right = r;
	}
	public boolean isEmpty() { return false; }
	protected Pair<SplayHeap<T>,SplayHeap<T>> partition(T pivot) {
	    // XXX
	    if (element.compareTo(pivot) <= 0) {
		if (right.isEmpty()) return make(this, right);
		Node<T> r = (Node<T>) right;
		if (r.element.compareTo(pivot) <= 0) {
		    Pair<SplayHeap<T>,SplayHeap<T>> p =
			r.right.partition(pivot);
		    return make(new Node<T>(new Node<T>(left, element,
							r.left),
					    r.element, p.first),
				p.second);
		} else {
		    Pair<SplayHeap<T>,SplayHeap<T>> p =
			r.left.partition(pivot);
		    return make(new Node<T>(left, element, p.first),
				new Node<T>(p.second, r.element, r.right));
		}
	    } else {
		if (left.isEmpty()) return make(left, this);
		Node<T> l = (Node<T>) left;
		if (l.element.compareTo(pivot) <= 0) {
		    Pair<SplayHeap<T>,SplayHeap<T>> p =
			l.right.partition(pivot);
		    return make(new Node<T>(l.left, l.element, p.first),
				new Node<T>(p.second, element, right));
		} else {
		    Pair<SplayHeap<T>,SplayHeap<T>> p =
			l.left.partition(pivot);
		    return make(p.first,
				new Node<T>(p.second,
					    l.element,
					    new Node<T>(l.right, element,
							right)));
		}
	    }
	}
	public T findMin() throws EmptyException {
	    if (left.isEmpty()) return element;
	    else return left.findMin();
	}
	public SplayHeap<T> deleteMin() throws EmptyException {
	    if (left.isEmpty()) return right;
	    Node<T> l = (Node<T>) left;
	    if (l.left.isEmpty()) return new Node<T>(l.right, element, right);
	    else return new Node<T>(l.left.deleteMin(), l.element,
				    new Node<T>(l.right, element, right));
	}
	public SplayHeap<T> merge(Heap<T> h) {
	    assert h instanceof SplayHeap;
	    SplayHeap<T> heap = (SplayHeap<T>)h;
	    Pair<SplayHeap<T>,SplayHeap<T>> p = heap.partition(element);
	    return new Node<T>(p.first.merge(left), element,
			       p.second.merge(right));
	}
    }

    protected abstract Pair<SplayHeap<T>,SplayHeap<T>> partition(T pivot);
    public abstract SplayHeap<T> deleteMin() throws EmptyException;
    public abstract SplayHeap<T> merge(Heap<T> h);

    public SplayHeap<T> insert(T t) {
	Pair<SplayHeap<T>,SplayHeap<T>> p = partition(t);
	return new Node<T>(p.first, t, p.second);
    }

    public static SplayHeap empty() {
	return Empty.empty;
    }
}