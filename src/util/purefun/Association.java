package util.purefun;
import java.util.*;

import util.*;

public interface Association<T,U> extends Iterable<U>
  {
    // Some stuff that should be in static methods:
    // empty
    // singleton
    // fromSeq
    // unionSeq
    public Association<T,U> empty ();
    public Association<T,U> insert (T t, U u);
    public Association<T,U> insert (Collection<Pair<T,U>> seq);
    public Association<T,U> insert (Pair<T,U>... pairs);
    public Association<T,U> union (Association<T,U> other);
    public Association<T,U> unionWith (Collection<Association<T,U>> seq);
    public Association<T,U> unionWith (Association<T,U>... seq);
    public Association<T,U> delete (T t);
    public Association<T,U> deleteAll (T t);
    public Association<T,U> delete (T... ts);
    public boolean isNull ();
    public int size ();
    public boolean isMember (T t);
    public int count (T t);
    public U lookup (T t);
    public Pair<U, Association<T,U>> lookupAndDelete (T t);
    public U lookupWithDefault (T t, U u);
    public Association<T,U> adjust (Function<U, U> f, T t);
    
    public <V> V fold (Function<V, Pair<U,V>> f, V start);
    public <V> V foldKeysValues (Function<V, Triple<T,U,V>> f, V start);
    public Association<T,U> filter (Function<Boolean, U> f);
    public Pair<Association<T,U>,Association<T,U>> partition (Function<Boolean, U> f);   
  }
