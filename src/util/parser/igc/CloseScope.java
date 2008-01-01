package util.parser.igc;

import net.sf.jga.fn.Generator;

public abstract class CloseScope extends Generator<Object>
{
  @Override
  public String toString () { return "pop old scope"; }
}