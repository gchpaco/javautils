package util.parser;

import gov.nasa.jpf.jvm.Verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import util.Pair;

public class BacktrackingParser<NT, T> extends Parser<NT, T>
{
  public BacktrackingParser (Grammar<NT, T> grammar)
    {
      super (grammar);
    }

  public BacktrackingParser (Table<NT, T, List<?>> table, NT start, T eof)
    {
      super (table, start, eof);
    }

  @Override
  protected boolean checkChoice (ChoicePredicate pred)
    {
      Verify.ignoreIf (!pred.fn ());
      return true;
    }

  @Override
  protected List<?> choosePossibility (
                                       Collection<Pair<ChoicePredicate, List<?>>> possibilities,
                                       @SuppressWarnings ("unused")
                                       NT top)
    {
      List<List<?>> available = new ArrayList<List<?>> ();
      for (Pair<ChoicePredicate, List<?>> pair : possibilities)
        {
          if (pair.first == null || pair.first.fn ())
            available.add (pair.second);
        }
      Verify.ignoreIf (available.isEmpty ());
      return available.get (Verify.random (available.size ()));
    }
}