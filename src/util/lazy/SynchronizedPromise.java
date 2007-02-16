package util.lazy;
import org.apache.commons.functor.*;

public class SynchronizedPromise<T> extends Promise<T>
{
    protected SynchronizedPromise(Function<? extends T> closure) {super(closure);}
    
    @Override
    public synchronized T force() { return super.force(); }
    public static <T> SynchronizedPromise<T> delay(Function<? extends T> closure)
    {
	return new SynchronizedPromise<T>(closure);
    }
}