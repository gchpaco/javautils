package util.lazy;
import org.apache.commons.functor.*;

public class Promise<T> implements Function<T>
{
    private Function<? extends T> c;
    private T value;

    protected Promise(Function<? extends T> closure) { c = closure; value = null; }
    public T force()
    {
	if (value == null)
	    value = c.evaluate ();
	return value;
    }
    public T evaluate ()
    {
	return force ();
    }
    public static <T> Promise<T> delay(Function<? extends T> closure)
    {
	return new Promise<T>(closure);
    }
}