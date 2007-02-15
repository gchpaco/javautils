package util.lazy;
import org.apache.commons.functor.*;

public class SynchronizedPromise<T> extends Promise<T>
{
    protected SynchronizedPromise(Function closure) {super(closure);}
    
    @Override
    public synchronized T force() { return super.force(); }
    public static SynchronizedPromise delay(Function closure)
    {
	return new SynchronizedPromise(closure);
    }
}