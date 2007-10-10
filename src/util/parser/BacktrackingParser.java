package util.parser;

import gov.nasa.jpf.jvm.Verify;
import java.util.*;
import util.Pair;

public class BacktrackingParser<NT, T> extends Parser<NT, T>
{
    public BacktrackingParser (Grammar<NT,T> grammar) {
        super (grammar);
    }
    public BacktrackingParser (Table<NT,T,List<?>> table, NT start, T eof) {
        super (table, start, eof);
    }
    protected void checkChoice (ChoicePredicate pred)
    {
        Verify.ignoreIf (!pred.apply ());
    }

    protected List<?> choosePossibility (Collection<Pair<ChoicePredicate,
                                         List<?>>> possibilities, T token,
                                         NT top)
    {
        List<List<?>> available = new ArrayList ();
        for (Pair<ChoicePredicate,List<?>> pair : possibilities) {
            if (pair.first == null || pair.first.apply ())
                available.add (pair.second);
        }
	if (available.isEmpty ())
	    throw new ParseException ("No alternatives available for " + token
				      + " in state " + top);
	return available.get (Verify.random (available.size () - 1));
    }
}