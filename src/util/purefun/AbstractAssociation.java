package util.purefun;

import java.util.Collection;

import util.*;

public abstract class AbstractAssociation<T,U> implements Association<T,U>
  {

  public Association<T, U> adjust (Function<U, U> f, T t)
    {
      U u = lookup (t);
      return delete (t).insert (t, f.apply (u));
    }

  public Association<T, U> delete (T... ts)
    {
      Association<T, U> value = this;
      for (T t : ts)
        value = value.delete (t);
      return value;
    }

  public Association<T, U> filter (final Function<Boolean, U> f)
    {
      return foldKeysValues (new Function<Association<T,U>, Triple<T, U, Association<T,U>>> () {
        public Association<T, U> apply (Triple<T, U, Association<T, U>> arg)
          {
            if (f.apply (arg.second))
              return arg.third.insert (arg.first, arg.second);
            return arg.third;
          }
      }, empty ());
    }
  
  public <V> V fold (final Function<V, Pair<U, V>> f, V start)
  {
    return foldKeysValues (new Function<V, Triple<T, U, V>> () {
      public V apply (Triple<T, U, V> arg)
        {
          return f.apply (Pair.make (arg.second, arg.third));
        }
    }, start);
  }

  public Association<T, U> insert (Collection<Pair<T, U>> seq)
    {
      Association<T, U> value = this;
      for (Pair<T, U> pair : seq)
        value = value.insert (pair.first, pair.second);
      return value;
    }

  public Association<T, U> insert (Pair<T, U>... pairs)
    {
      Association<T, U> value = this;
      for (Pair<T, U> pair : pairs)
        value = value.insert (pair.first, pair.second);
      return value;
    }

  public Pair<U, Association<T, U>> lookupAndDelete (T t)
    {
      return Pair.make (lookup (t), delete (t));
    }

  public U lookupWithDefault (T t, U u)
    {
      U u2 = lookup (t);
      return u2 == null ? u : u2;
    }

  public Pair<Association<T, U>, Association<T, U>> partition (final Function<Boolean, U> f)
    {
      return foldKeysValues (new Function<Pair<Association<T, U>, Association<T, U>>, Triple<T, U, Pair<Association<T, U>, Association<T, U>>>> () {
        public Pair<Association<T, U>, Association<T, U>> apply (Triple<T, U, Pair<Association<T, U>, Association<T, U>>> arg)
          {
            if (f.apply (arg.second))
              return Pair.make (arg.third.first.insert (arg.first, arg.second),arg.third.second);
            return Pair.make (arg.third.first,arg.third.second.insert (arg.first, arg.second));
          }
      }, Pair.make (empty (), empty ()));
    }

  public Association<T, U> unionWith (Association<T, U>... seq)
    {
      Association<T, U> value = this;
      for (Association<T, U> a : seq)
        value = value.union (a);
      return value;
    }

  public Association<T, U> unionWith (Collection<Association<T, U>> seq)
    {
      Association<T, U> value = this;
      for (Association<T, U> a : seq)
        value = value.union (a);
      return value;
    }

  public int count (T t)
    {
      if (lookup (t) != null)
        return 1;
      return 0;
    }
  }
