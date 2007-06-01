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
    protected boolean checkPredicate (SemanticPredicate pred)
    {
        Verify.ignoreIf (!pred.apply ());
        return true;
    }

    protected List<?> choosePossibility (Collection<Pair<SemanticPredicate,
                                         List<?>>> possibilities, T token,
                                         NT top)
    {
        List<List<?>> available = new ArrayList ();
        for (Pair<SemanticPredicate,List<?>> pair : possibilities) {
            if (pair.first == null || pair.first.apply ())
                available.add (pair.second);
        }
        Verify.ignoreIf (available.isEmpty ());
        return available.get (Verify.random (available.size ()));
    }
}