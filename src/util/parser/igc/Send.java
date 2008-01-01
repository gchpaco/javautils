package util.parser.igc;

import net.sf.jga.fn.Generator;

public abstract class Send extends Generator<Object>
{
  public abstract String getMethodName ();
  public abstract String getTarget ();
  public abstract String[] getArguments ();
  public String getArguments (int i) { return getArguments()[i]; }
  @Override
  public String toString () { return "send"; }
}