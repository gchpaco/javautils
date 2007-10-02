package util.purefun;

import java.util.Collection;

import net.sf.jga.fn.BinaryFunctor;
import net.sf.jga.fn.UnaryFunctor;
import util.Pair;
import util.TernaryFunctor;

public abstract class AbstractAssociation<T, U> implements Association<T, U>
{

  public Association<T, U> adjust (UnaryFunctor<U, U> f, T t)
    {
      U u = lookup (t);
      return delete (t).insert (t, f.fn (u));
    }

  public Association<T, U> delete (T... ts)
    {
      Association<T, U> value = this;
      for (T t : ts)
        value = value.delete (t);
      return value;
    }

  public Association<T, U> filter (final UnaryFunctor<U, Boolean> f)
    {
      return foldKeysValues (
                             new TernaryFunctor<T, U, Association<T, U>, Association<T, U>> ()
                               {
                                 @SuppressWarnings ("unchecked")
                                 @Override
                                 public Association<T, U> fn (
                                                              T t,
                                                              U u,
                                                              Association<T, U> a)
                                   {
                                     if (f.fn (u)) return a.insert (t, u);
                                     return a;
                                   }
                               }, empty ());
    }

  public <V> V fold (final BinaryFunctor<U, V, V> f, V start)
    {
      return foldKeysValues (new TernaryFunctor<T, U, V, V> ()
        {
          @SuppressWarnings ("unchecked")
          @Override
          public V fn (@SuppressWarnings ("unused")
          T t, U u, V v)
            {
              return f.fn (u, v);
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

  public Pair<Association<T, U>, Association<T, U>> partition (
                                                               final UnaryFunctor<U, Boolean> f)
    {
      return foldKeysValues (
                             new TernaryFunctor<T, U, Pair<Association<T, U>, Association<T, U>>, Pair<Association<T, U>, Association<T, U>>> ()
                               {
                                 @Override
                                 public Pair<Association<T, U>, Association<T, U>> fn (
                                                                                       T t,
                                                                                       U u,
                                                                                       Pair<Association<T, U>, Association<T, U>> pair)
                                   {
                                     if (f.fn (u))
                                       return Pair.make (pair.first.insert (t,
                                                                            u),
                                                         pair.second);
                                     return Pair
                                                .make (
                                                       pair.first,
                                                       pair.second
                                                                  .insert (t, u));
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
      if (lookup (t) != null) return 1;
      return 0;
    }
}
