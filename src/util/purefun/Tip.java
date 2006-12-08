/**
 * 
 */
package util.purefun;

import java.util.Iterator;

import util.Function;
import util.Triple;

@SuppressWarnings("unchecked")
class Tip<T extends Comparable<? super T>, U> extends SizeBalancedTree<T, U>
  {

    public boolean isNull ()
      {
        return true;
      }

    public int size ()
      {
        return 0;
      }

    public U lookup (T t)
      {
        return null;
      }

    @Override
    public SizeBalancedTree<T, U> delete (T t)
      {
        return this;
      }

    public Association<T, U> deleteAll (T t)
      {
        return this;
      }

    public <V> V foldKeysValues (Function<V, Triple<T, U, V>> f, V start)
      {
        return start;
      }

    @Override
    public SizeBalancedTree<T, U> insert (T t, U u)
      {
        return SizeBalancedTree.singleton (t, u);
      }

    public boolean isMember (T t)
      {
        return false;
      }

    public Association<T, U> union (Association<T, U> other)
      {
        return other;
      }

    public Iterator<U> iterator ()
      {
        // TODO Auto-generated method stub
        return null;
      }

    @Override
    protected SizeBalancedTree<T, U> trim (Function<Integer, T> low,
                                           Function<Integer, T> high)
      {
        return this;
      }

    @Override
    protected SizeBalancedTree<T, U> filterGt (Function<Integer, T> f)
      {
        return this;
      }

    @Override
    protected SizeBalancedTree<T, U> filterLt (Function<Integer, T> f)
      {
        return this;
      }

    @Override
    protected SizeBalancedTree<T, U> insertMax (T t, U u)
      {
        return SizeBalancedTree.singleton (t, u);
      }

    @Override
    protected SizeBalancedTree<T, U> insertMin (T t, U u)
      {
        return SizeBalancedTree.singleton (t, u);
      }

    @Override
    protected Triple<T, U, SizeBalancedTree<T, U>> deleteFindMax ()
      {
        throw new IllegalStateException (
                                         "can't delete the minimum node of an empty tree!");
      }

    @Override
    protected Triple<T, U, SizeBalancedTree<T, U>> deleteFindMin ()
      {
        throw new IllegalStateException (
                                         "can't delete the minimum node of an empty tree!");
      }
    
    @Override
    public String toString ()
      {
        return "Tip";
      }
  }