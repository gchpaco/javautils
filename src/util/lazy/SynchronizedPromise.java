package util.lazy;
import util.Closure;

public class SynchronizedPromise<T> extends Promise<T>
{
    private SynchronizedPromise(Closure<T> closure) {super(closure);}
    
    @Override
    public synchronized T force() { return super.force(); }
    public static <T> SynchronizedPromise<T> delay(Closure<T> closure)
    {
	return new SynchronizedPromise<T>(closure);
    }
}