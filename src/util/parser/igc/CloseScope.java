package util.parser.igc;

import net.sf.jga.fn.Generator;
import util.SymbolTable;

public abstract class CloseScope extends Generator<Object>
{
  SymbolTable symbols;
  public CloseScope (SymbolTable s) { symbols = s; }
  public Object gen ()
  {
    symbols.pop ();
    return null;
  }
    
  @Override
  public String toString () { return "pop old scope"; }
}