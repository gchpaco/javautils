package util.parser.igc;

import util.parser.SemanticPredicate;

public abstract class Guard extends SemanticPredicate
{
  public abstract String getCode ();
  @Override
  public String toString () { return "guard " + getCode (); }
}