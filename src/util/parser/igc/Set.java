package util.parser.igc;

import net.sf.jga.fn.Generator;
import util.Pair;

public abstract class Set extends Generator<Object>
{
  public abstract Pair<String,String>[] getSets ();
  public Pair<String,String> getSets (int i) { return getSets ()[i]; }
  @Override
  public String toString () { return "set"; }
}