package util.parser.igc;

import net.sf.jga.fn.Generator;

public abstract class OpenScope extends Generator<Object>
{
  @Override
  public String toString () { return "push new scope"; }
}