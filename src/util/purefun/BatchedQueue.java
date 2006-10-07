package util.purefun;

import util.Cons;

public class BatchedQueue<T> implements Queue<T> {
    Cons<T> front, rear;

    protected BatchedQueue(Cons<T> f, Cons<T> r) {
        front = f;
        rear = r;
    }

    @SuppressWarnings("unchecked")
    public static <T> BatchedQueue<T> empty() {
        return new BatchedQueue(null, null);
    }

    public boolean isEmpty() {
        return front == null;
    }

    protected Queue<T> checkF() {
        if (front == null)
            return new BatchedQueue<T>(Cons.reverse(rear), null);
        return this;
    }

    public Queue<T> snoc(T t) {
        return new BatchedQueue<T>(front, Cons.cons(t, rear)).checkF();
    }

    public T head() throws EmptyException {
        if (isEmpty())
            throw new EmptyException();
        return front.car();
    }

    public Queue<T> tail() throws EmptyException {
        if (isEmpty())
            throw new EmptyException();
        return new BatchedQueue<T>(front.cdr(), rear).checkF();
    }
}