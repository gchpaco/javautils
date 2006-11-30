package util.purefun;

import util.*;

abstract public class SizeBalancedTree<T extends Comparable<? super T>,U> extends AbstractAssociation<T,U>
  {
    @SuppressWarnings("unchecked")
    public static SizeBalancedTree EMPTY = new Tip ();
    
    @SuppressWarnings("unchecked")
    public SizeBalancedTree empty ()
      {
        return SizeBalancedTree.emptySet ();
      }
    @SuppressWarnings("unchecked")
    public static SizeBalancedTree emptySet ()
      {
	return EMPTY;
      }
    public abstract SizeBalancedTree<T,U> insert (T t, U u);
    
    public abstract SizeBalancedTree<T, U> delete (T t);
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>,U> SizeBalancedTree<T, U> singleton (T t, U u)
    {
      return new Bin<T, U> (1, t, u, (SizeBalancedTree<T,U>)EMPTY, (SizeBalancedTree<T,U>)EMPTY);
    }
    
    protected abstract SizeBalancedTree<T,U> trim (Function<Integer, T> low, Function<Integer, T> high);
    protected abstract SizeBalancedTree<T,U> filterGt (Function<Integer, T> f);
    protected abstract SizeBalancedTree<T,U> filterLt (Function<Integer, T> f);
    protected abstract SizeBalancedTree<T,U> insertMin (T t, U u);
    protected abstract SizeBalancedTree<T,U> insertMax (T t, U u);
    protected abstract Triple<T,U,SizeBalancedTree<T,U>> deleteFindMin ();
    protected abstract Triple<T,U,SizeBalancedTree<T,U>> deleteFindMax ();
    
    protected static final int DELTA = 5;
    protected static final int RATIO = 2;
  }
