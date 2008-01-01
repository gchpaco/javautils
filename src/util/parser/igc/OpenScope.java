package util.parser.igc;

import net.sf.jga.fn.Generator;

public abstract class OpenScope extends Generator<Object>
{
  public Object gen ()
  {
    symbols.push ();
    return null;
  }
    
  @Override
  public String toString () { return "push new scope"; }
}