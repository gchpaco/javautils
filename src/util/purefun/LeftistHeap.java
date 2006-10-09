package util.purefun;

public abstract class LeftistHeap<T extends Comparable<? super T>> implements
        Heap<T> {
    protected static class Empty<T extends Comparable<? super T>> extends
            LeftistHeap<T> {
        @Override
        public int rank() {
            return 0;
        }

        public boolean isEmpty() {
            return true;
        }

        @Override
        public LeftistHeap<T> merge(Heap<T> h) {
            if (!(h instanceof LeftistHeap))
                throw new IllegalArgumentException(h + " is not a leftist heap");
            return (LeftistHeap<T>) h;
        }

        public T findMin() throws EmptyException {
            throw new EmptyException();
        }

        public LeftistHeap<T> deleteMin() throws EmptyException {
            throw new EmptyException();
        }

        @SuppressWarnings("unchecked")
        static protected final Empty empty = new Empty();
    }

    protected static class Node<T extends Comparable<? super T>> extends
            LeftistHeap<T> {
        int rank;

        T element;

        LeftistHeap<T> left, right;

        public Node(int ra, T e, LeftistHeap<T> l, LeftistHeap<T> r) {
            rank = ra;
            element = e;
            left = l;
            right = r;
        }

        @Override
        public int rank() {
            return rank;
        }

        public boolean isEmpty() {
            return false;
        }

        @Override
        public LeftistHeap<T> merge(Heap<T> h) {
            if (!(h instanceof LeftistHeap))
                throw new IllegalArgumentException(h + " is not a leftist heap");
            if (h instanceof Empty)
                return this;
            Node<T> h2 = (Node<T>) h;
            if (element.compareTo(h2.element) <= 0)
                return makeT(element, left, right.merge(h2));
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

    @SuppressWarnings("unchecked")
    public Heap<T> insert(T x) {
        return merge(new Node<T>(1, x, Empty.empty, Empty.empty));
    }

    protected static <T extends Comparable<? super T>> Node<T> makeT(T x,
            LeftistHeap<T> a, LeftistHeap<T> b) {
        if (a.rank() >= b.rank())
            return new Node<T>(b.rank() + 1, x, a, b);
        return new Node<T>(a.rank() + 1, x, b, a);
        
    }

    @SuppressWarnings("unchecked")
    public static <T> Heap<T> empty() {
        return Empty.empty;
    }
}
