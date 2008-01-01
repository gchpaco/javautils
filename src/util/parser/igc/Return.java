package util.parser.igc;

public abstract class Return extends JavaCode
{
  @Override
  public String toString () { return "return " + getCode (); }
}