package util.purefun;

public abstract class LeftistHeap<T extends Comparable<? super T>>
    implements Heap<T>
{
    protected static class Empty<T extends Comparable<? super T>>
	extends LeftistHeap<T> {
	public int rank() { return 0; }
	public boolean isEmpty() { return true; }
	public LeftistHeap<T> merge(Heap<T> h) {
	    if (!(h instanceof LeftistHeap))
		throw new IllegalArgumentException
		    (h + " is not a leftist heap");
	    return (LeftistHeap<T>)h;
	}
	public T findMin() throws EmptyException {
	    throw new EmptyException();
	}
	public LeftistHeap<T> deleteMin() throws EmptyException {
	    throw new EmptyException();
	}
	static protected final Empty empty = new Empty();
    }
    protected static class Node<T extends Comparable<? super T>>
	extends LeftistHeap<T> {
	int rank;
	T element;
	LeftistHeap<T> left, right;

	public Node(int ra, T e, LeftistHeap<T> l, LeftistHeap<T> r) {
	    rank = ra; element = e; left = l; right = r;
	}

	public int rank() { return rank; }
	public boolean isEmpty() { return false; }
	public LeftistHeap<T> merge(Heap<T> h) {
	    if (!(h instanceof LeftistHeap))
		throw new IllegalArgumentException
		    (h + " is not a leftist heap");
	    if (h instanceof Empty) return this;
	    Node<T> h2 = (Node<T>)h;
	    if (element.compareTo(h2.element) <= 0)
		return makeT(element, left, right.merge(h2));
	    else
		return makeT(h2.element, h2.left, h2.right.merge(this));
	}
	public T findMin() {
	    return element;
	}
	public Heap<T> deleteMin() {
	    return left.merge(right);
	}
    }

    public abstract int rank();
    public abstract LeftistHeap<T> merge(Heap<T> h);
    public Heap<T> insert(T x) {
	return merge(new Node<T>(1, x, empty(), empty()));
    }

    protected static <T extends Comparable<? super T>>
	Node<T> makeT(T x, LeftistHeap<T> a, LeftistHeap<T> b) {
	if (a.rank() >= b.rank())
	    return new Node<T>(b.rank() + 1, x, a, b);
	else
	    return new Node<T>(a.rank() + 1, x, b, a);
    }

    public static LeftistHeap empty() {
	return Empty.empty;
    }
}