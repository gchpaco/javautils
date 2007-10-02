/*
 * Created on Oct 7, 2005
 */
package util;

import net.sf.jga.fn.BinaryFunctor;

public abstract class BacktrackingFunctor<T, U> extends BinaryFunctor<T, U, T>
{
  public abstract T empty ();
}
