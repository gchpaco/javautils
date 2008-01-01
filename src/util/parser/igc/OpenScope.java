package util.parser.igc;

import net.sf.jga.fn.Generator;
import util.SymbolTable;

public abstract class OpenScope extends Generator<Object>
{
  SymbolTable symbols;
  public OpenScope (SymbolTable s) { symbols = s; }
  public Object gen ()
  {
    symbols.push ();
    return null;
  }
    
  @Override
  public String toString () { return "push new scope"; }
}