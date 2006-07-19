package util;

public class Cons<T> extends Pair<T,Cons<T>>
{
    public Cons(T t, Cons<T> list) { super(t, list); }

    public T car() { return first; }
    public Cons<T> cdr() { return second; }

    public static <T> Cons<T> reverse(Cons<T> list) {
	Cons<T> position = list, result = null;
	while (position != null) {
	    result = cons(position.car(), result);
	    position = position.cdr();
	}
	return result;
    }
    public static <T> Cons<T> cons(T t, Cons<T> list) {
	return new Cons<T>(t, list);
    }
}