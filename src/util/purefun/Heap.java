package util.purefun;

public interface Heap<T>
{
    public abstract boolean isEmpty();
    public abstract Heap<T> insert(T t);
    public abstract Heap<T> merge(Heap<T> h);
    public abstract T findMin() throws EmptyException;
    public abstract Heap<T> deleteMin() throws EmptyException;
}