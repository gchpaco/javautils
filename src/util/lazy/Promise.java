package util.lazy;
import org.apache.commons.functor.*;

public class Promise<T> implements Function
{
    private Function c;
    private T value;

    protected Promise(Function closure) { c = closure; value = null; }
    public T force()
    {
	if (value == null)
	    value = (T) c.evaluate ();
	return value;
    }
    public T evaluate ()
    {
	return force ();
    }
    public static Promise delay(Function closure)
    {
	return new Promise(closure);
    }
}