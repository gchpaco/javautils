package util.lazy;
import util.Closure;

public class Promise<T>
{
    private Closure<T> c;
    private T value;

    protected Promise(Closure<T> closure) { c = closure; value = null; }
    public T force()
    {
	if (value == null)
	    value = c.apply();
	return value;
    }
    public static <T> Promise<T> delay(Closure<T> closure)
    {
	return new Promise<T>(closure);
    }
}