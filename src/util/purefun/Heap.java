package util.purefun;
import java.util.Comparator;

public interface Heap<T extends Comparable<? super T>>
{
    public abstract boolean isEmpty();
    public abstract Heap<T> insert(T t);
    public abstract Heap<T> merge(Heap<T> h);
    public abstract T findMin() throws EmptyException;
    public abstract Heap<T> deleteMin() throws EmptyException;
}