package util.purefun;

public abstract class RedBlackSet<T extends Comparable<? super T>>
    implements Set<T>
{
    protected static enum Color { RED, BLACK; }
    protected static class Empty<T extends Comparable<? super T>>
	extends RedBlackSet<T> {
	static final protected Empty empty = new Empty();
	private Empty() {}

	public boolean member(T t) { return false; }
	protected Node<T> ins(T t) {
	    return new Node<T>(Color.RED, this, t, this);
	}
    }
    protected static class Node<T extends Comparable<? super T>>
	extends RedBlackSet<T> {
	Color color;
	RedBlackSet<T> left, right;
	T element;

	protected Node(Color c, RedBlackSet<T> l, T t, RedBlackSet<T> r) {
	    color = c; left = l; element = t; right = r;
	}
	public boolean member(T t) {
	    switch (element.compareTo(t)) {
	    case -1: return left.member(t);
	    case 0: return true;
	    case 1: return right.member(t);
	    default: throw new IllegalArgumentException();
	    }
	}
	protected Node<T> ins(T t) {
	    switch (element.compareTo(t)) {
	    case -1: return balance(color, left.ins(t), element, right);
	    case 0: return this;
	    case 1: return balance(color, left, element, right.ins(t));
	    default: throw new IllegalArgumentException();
	    }
	}
    }

    protected static <T extends Comparable<? super T>> Node<T>
	balance (Color color, RedBlackSet<T> left, T element,
		 RedBlackSet<T> right) {
	RedBlackSet<T> a = null, b = null, c = null, d = null;
	T x = null, y = null, z = null;
	boolean rebalance = false;
	if (color == Color.BLACK) {
	    if (left instanceof Node && ((Node)left).color == Color.RED) {
		Node<T> n = (Node<T>)left;
		if (n.left instanceof Node &&
		    ((Node)n.left).color == Color.RED) {
		    rebalance = true;
		    Node<T> m = (Node<T>)n.left;
		    a = m.left;
		    x = m.element;
		    b = m.right;
		    y = n.element;
		    c = n.right;
		    z = element;
		    d = right;
		} else if (n.right instanceof Node &&
			   ((Node)n.right).color == Color.RED) {
		    rebalance = true;
		    Node<T> m = (Node<T>)n.right;
		    a = n.left;
		    x = n.element;
		    b = m.left;
		    y = m.element;
		    c = m.right;
		    z = element;
		    d = right;
		}
	    } else if (right instanceof Node &&
		       ((Node)right).color == Color.RED) {
		Node<T> n = (Node<T>)right;
		if (n.left instanceof Node &&
		    ((Node)n.left).color == Color.RED) {
		    rebalance = true;
		    Node<T> m = (Node<T>)n.left;
		    a = left;
		    x = element;
		    b = m.left;
		    y = m.element;
		    c = m.right;
		    z = n.element;
		    d = n.right;
		} else if (n.right instanceof Node &&
			   ((Node)n.right).color == Color.RED) {
		    rebalance = true;
		    Node<T> m = (Node<T>)n.right;
		    a = left;
		    x = element;
		    b = n.left;
		    y = n.element;
		    c = m.left;
		    z = m.element;
		    d = m.right;
		}
	    }
	}

	if (rebalance)
	    return new Node<T>(Color.RED, new Node<T>(Color.BLACK, a, x, b), y,
			       new Node<T>(Color.BLACK, c, z, d));
	else
	    return new Node<T>(color, left, element, right);
    }

    protected abstract Node<T> ins(T t);
    public Node<T> insert(T t) {
	Node<T> result = this.ins(t);
	return new Node<T>(Color.BLACK, result.left, result.element,
			   result.right);
    }

    public static RedBlackSet empty() { return Empty.empty; }
}