package util.parser.igc;

import net.sf.jga.fn.Generator;
import util.Pair;

public abstract class Bind extends Generator<Object>
{
  public abstract Pair<Integer,String>[] getBinds ();
  public Pair<Integer,String> getBinds (int i) { return getBinds()[i]; }
  @Override
  public String toString () { return "binds"; }
}