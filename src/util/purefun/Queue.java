package util.purefun;

public interface Queue<T>
{
    public boolean isEmpty();
    public Queue<T> snoc(T t);
    public T head() throws EmptyException;
    public Queue<T> tail() throws EmptyException;
}