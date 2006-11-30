/**
 * 
 */
package util.purefun;

import java.util.Iterator;

import util.*;

class Bin<T extends Comparable<? super T>, U> extends SizeBalancedTree<T, U>
  {
    int size;

    T key;

    U value;

    SizeBalancedTree<T, U> left, right;

    public Bin (int i, T t, U u, SizeBalancedTree<T, U> l,
                SizeBalancedTree<T, U> r)
      {
        size = i;
        key = t;
        value = u;
        left = l;
        right = r;
      }

    public Bin (T t, U u, SizeBalancedTree<T, U> l, SizeBalancedTree<T, U> r)
      {
        size = l.size () + r.size () + 1;
        key = t;
        value = u;
        left = l;
        right = r;
      }

    public boolean isNull ()
      {
        return false;
      }

    public int size ()
      {
        return size;
      }

    public U lookup (T t)
      {
        int i = key.compareTo (t);
        if (i < 0)
          return left.lookup (t);
        else if (i > 0) return right.lookup (t);
        return value;
      }

    @Override
    public SizeBalancedTree<T, U> delete (T t)
      {
        int i = key.compareTo (t);
        if (i < 0)
          return Bin.balance (key, value, left.delete (t), right);
        else if (i > 0)
          return Bin.balance (key, value, left, right.delete (t));
        return Bin.glue (left, right);
      }

    public Association<T, U> deleteAll (T t)
      {
        return delete (t);
      }

    public <V> V foldKeysValues (Function<V, Triple<T, U, V>> f, V start)
      {
        V temp = left.foldKeysValues (f, start);
        temp = f.apply (Triple.make (key, value, temp));
        return right.foldKeysValues (f, temp);
      }

    @Override
    public SizeBalancedTree<T, U> insert (T t, U u)
      {
        int i = key.compareTo (t);
        if (i < 0)
          return Bin.balance (key, value, left.insert (t, u), right);
        else if (i > 0)
          return Bin.balance (key, value, left, right.insert (t, u));
        return new Bin<T, U> (size, t, u, left, right);
      }

    public boolean isMember (T t)
      {
        int i = key.compareTo (t);
        if (i < 0)
          return left.isMember (t);
        else if (i > 0) return right.isMember (t);
        return true;
      }

    @SuppressWarnings ("unchecked")
    public Association<T, U> union (Association<T, U> other)
      {
        if (other.isNull ()) return this;
        return hedgeUnionL (new Function<Integer, T> () { public Integer apply (T t) { return -1; } },
                            new Function<Integer, T> () { public Integer apply (T t) { return 1; } },
                            this, (SizeBalancedTree<T, U>) other);
      }

    public Iterator<U> iterator ()
      {
        // TODO Auto-generated method stub
        return null;
      }

    static private <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> balance (
                                                                                        T k,
                                                                                        U v,
                                                                                        SizeBalancedTree<T, U> l,
                                                                                        SizeBalancedTree<T, U> r)
      {
        int sizeX = l.size () + r.size () + 1;
        if (l.size () + r.size () <= 1)
          return new Bin<T, U> (sizeX, k, v, l, r);
        else if (r.size () >= SizeBalancedTree.DELTA * l.size ())
          return rotateL (k, v, l, (Bin<T, U>) r);
        else if (l.size () >= SizeBalancedTree.DELTA * r.size ())
          return rotateR (k, v, (Bin<T, U>) l, r);
        else return new Bin<T, U> (sizeX, k, v, l, r);
      }

    static private <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> rotateL (
                                                                                        T k,
                                                                                        U v,
                                                                                        SizeBalancedTree<T, U> l,
                                                                                        Bin<T, U> r)
      {
        if (r.left.size () < SizeBalancedTree.RATIO * r.right.size ())
          return singleL (k, v, l, r);
        return doubleL (k, v, l, r);
      }

    static private <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> rotateR (
                                                                                        T k,
                                                                                        U v,
                                                                                        Bin<T, U> l,
                                                                                        SizeBalancedTree<T, U> r)
      {
        if (l.right.size () < SizeBalancedTree.RATIO * l.left.size ())
          return singleR (k, v, l, r);
        return doubleR (k, v, l, r);
      }

    static private <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> singleL (
                                                                                        T k,
                                                                                        U v,
                                                                                        SizeBalancedTree<T, U> l,
                                                                                        Bin<T, U> r)
      {
        return new Bin<T, U> (r.key, r.value, new Bin<T, U> (k, v, l, r.left),
                              r.right);
      }

    static private <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> singleR (
                                                                                        T k,
                                                                                        U v,
                                                                                        Bin<T, U> l,
                                                                                        SizeBalancedTree<T, U> r)
      {
        return new Bin<T, U> (l.key, l.value, l.left,
                              new Bin<T, U> (k, v, l.right, r));
      }

    static private <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> doubleL (
                                                                                        T k,
                                                                                        U v,
                                                                                        SizeBalancedTree<T, U> l,
                                                                                        Bin<T, U> r)
      {
        Bin<T, U> rl = (Bin<T, U>) r.left;
        return new Bin<T, U> (rl.key, rl.value,
                              new Bin<T, U> (k, v, l, rl.left),
                              new Bin<T, U> (r.key, r.value, rl.right, r.right));
      }

    static private <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> doubleR (
                                                                                        T k,
                                                                                        U v,
                                                                                        Bin<T, U> l,
                                                                                        SizeBalancedTree<T, U> r)
      {
        Bin<T, U> lr = (Bin<T, U>) l.right;
        return new Bin<T, U> (lr.key, lr.value,
                              new Bin<T, U> (l.key, l.value, l.left, lr.left),
                              new Bin<T, U> (k, v, lr.right, r));
      }

    private static <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> glue (
                                                                                     SizeBalancedTree<T, U> left,
                                                                                     SizeBalancedTree<T, U> right)
      {
        if (left.isNull ()) return right;
        if (right.isNull ()) return left;
        if (left.size () > right.size ())
          {
            Triple<T, U, SizeBalancedTree<T, U>> t = left.deleteFindMax ();
            return balance (t.first, t.second, t.third, right);
          }
        Triple<T, U, SizeBalancedTree<T, U>> t = right.deleteFindMin ();
        return balance (t.first, t.second, left, t.third);
      }

    static private <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> hedgeUnionL (
                                                                                            Function<Integer, T> low,
                                                                                            Function<Integer, T> high,
                                                                                            SizeBalancedTree<T, U> t1,
                                                                                            SizeBalancedTree<T, U> t2)
      {
        if (t2.isNull ()) return t1;
        if (t1.isNull ())
          {
            Bin<T, U> bin = (Bin<T, U>) t2;
            return join (bin.key, bin.value, bin.left.filterGt (low),
                         bin.right.filterLt (high));
          }
        final Bin<T, U> bin = (Bin<T, U>) t1;
        Function<Integer, T> cmpx = new Function<Integer, T> ()
          {
            public Integer apply (T arg)
              {
                return arg.compareTo (bin.key);
              }
          };
        return join (bin.key, bin.value, hedgeUnionL (low, cmpx, bin.left,
                                                      t2.trim (low, cmpx)),
                     hedgeUnionL (cmpx, high, bin.right, t2.trim (cmpx, high)));
      }

    @Override
    protected SizeBalancedTree<T, U> insertMax (T t, U u)
      {
        return balance (key, value, left, right.insertMax (t, u));
      }

    @Override
    protected SizeBalancedTree<T, U> insertMin (T t, U u)
      {
        return balance (key, value, left.insertMin (t, u), right);
      }

    @Override
    protected SizeBalancedTree<T, U> filterLt (Function<Integer, T> f)
      {
        if (f.apply (key) < 0)
          return left.filterLt (f);
        else if (f.apply (key) > 0)
          return join (key, value, left, right.filterLt (f));
        else return left;
      }

    @Override
    protected SizeBalancedTree<T, U> filterGt (Function<Integer, T> f)
      {
        if (f.apply (key) < 0)
          return join (key, value, left.filterGt (f), right);
        else if (f.apply (key) > 0)
          return right.filterGt (f);
        else return right;
      }

    @Override
    protected SizeBalancedTree<T, U> trim (Function<Integer, T> low,
                                           Function<Integer, T> high)
      {
        if (low.apply (key) < 0)
          {
            if (high.apply (key) > 0) return this;
            return left.trim (low, high);
          }
        return right.trim (low, high);
      }

    static private <T extends Comparable<? super T>, U> SizeBalancedTree<T, U> join (
                                                                                     T key,
                                                                                     U value,
                                                                                     SizeBalancedTree<T, U> left,
                                                                                     SizeBalancedTree<T, U> right)
      {
        if (left.isNull ())
          return right.insertMin (key, value);
        else if (right.isNull ())
          return left.insertMax (key, value);
        else
          {
            Bin<T, U> l = (Bin<T, U>) left;
            Bin<T, U> r = (Bin<T, U>) right;
            if (SizeBalancedTree.DELTA * l.size <= r.size)
              return balance (r.key, r.value, join (key, value, l, r.left),
                              r.right);
            else if (SizeBalancedTree.DELTA * r.size <= l.size)
              return balance (l.key, l.value, l.left, join (key, value,
                                                            l.right, r));
            else return new Bin<T, U> (key, value, left, right);
          }
      }

    @Override
    protected Triple<T, U, SizeBalancedTree<T, U>> deleteFindMax ()
      {
        if (right.isNull ()) return Triple.make (key, value, left);
        Triple<T, U, SizeBalancedTree<T, U>> t = right.deleteFindMax ();
        return Triple.make (t.first, t.second, balance (key, value, left,
                                                        t.third));
      }

    @Override
    protected Triple<T, U, SizeBalancedTree<T, U>> deleteFindMin ()
      {
        if (left.isNull ()) return Triple.make (key, value, right);
        Triple<T, U, SizeBalancedTree<T, U>> t = left.deleteFindMin ();
        return Triple.make (t.first, t.second, balance (key, value, t.third,
                                                        right));
      }
    
    @Override
    public String toString ()
      {
        return String.format ("Bin (%d, %s, %s, %s, %s)", size, key, value, left, right);
      }
  }
