package util.parser.igc;

import util.parser.ChoicePredicate;

public abstract class Choice extends ChoicePredicate
{
  public abstract String getCode ();
  @Override
  public String toString () { return "choice " + getCode (); }
}