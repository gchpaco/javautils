package util.parser.igc;

import net.sf.jga.fn.Generator;

public abstract class Throw extends Generator<Object>
{
  public abstract String getExpression ();
  @Override
  public String toString () { return "throw"; }
}