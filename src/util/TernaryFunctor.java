package util;

import java.io.Serializable;

import net.sf.jga.fn.Functor;
import net.sf.jga.fn.Visitable;
import net.sf.jga.fn.Visitor;

/**
 * A Function Object that takes three arguments and returns a result. The three
 * arguments are of type <code>T1</code>, <code>T2</code> and
 * <code>T3</code>, and the result is of type <code>R</code>
 * <p>
 * Copyright &copy; 2007 Graham Hughes
 * 
 * @author <a href="mailto:graham@sigwinch.org">Graham Hughes</a>
 */
public abstract class TernaryFunctor<T1, T2, T3, R> extends Functor<R>
                                                                      implements
                                                                      Visitable,
                                                                      Serializable
{
  /**
   * Executes the function and returns the result.
   */
  abstract public R fn (T1 arg1, T2 arg2, T3 arg3);

  // -----------------
  // Functor interface
  // -----------------

  @SuppressWarnings ("unchecked")
  @Override
  public R eval (Object... args)
    {
      // This generates three unchecked cast warnings: we're crossing the line
      // from
      // unsafe rawform code to typesafe generic code. This interface is known
      // to be unsafe, and documented to the user as such.
      return fn ((T1) args[0], (T2) args[1], (T3) args[2]);
    }

  // -------------------
  // Visitable interface
  // -------------------

  /**
   * No-op implementation of Visitable interface.
   */
  @Override
  public void accept (@SuppressWarnings ("unused")
  Visitor v)
    {
    }
}
