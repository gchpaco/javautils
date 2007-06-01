package util.parser;

import java.util.*;

import util.Closure;
import util.Pair;

public class Parser<NT, T>
  {
    @SuppressWarnings("serial")
    static class ParseException extends RuntimeException
      {
        public ParseException ()
          {
            super ();
          }

        public ParseException (String message, Throwable cause)
          {
            super (message, cause);
          }

        public ParseException (String message)
          {
            super (message);
          }

        public ParseException (Throwable cause)
          {
            super (cause);
          }
      }

    Table<NT, T, List<?>> table;
    LinkedList<Object> stack;
    NT start;
    T eof;
    @SuppressWarnings("unchecked")
    public Parser (Grammar<NT, T> grammar)
      {
        this.table = new Table<NT,T,List<?>> (grammar);
        this.start = grammar.getStartSymbol ();
        this.eof = grammar.getEOFToken ();
        reset ();
      }
    @SuppressWarnings("unchecked")
    public Parser (Table<NT,T,List<?>> table, NT start, T eof)
      {
        this.table = table;
        this.start = start;
        this.eof = eof;
        reset ();
      }

    public void reset ()
      {
        this.stack = new LinkedList<Object> ();
        stack.addLast (start);
        stack.addLast (eof);
      }

    public void tossUntil (T token)
      {
        while (!stack.isEmpty () && !stack.peek ().equals (token))
          stack.removeFirst ();
        if (!stack.isEmpty ()) stack.removeFirst ();
      }

    @SuppressWarnings("unchecked")
    public void witness (T token)
      {
        while (!stack.peek ().equals (token))
          {
            Object obj = stack.removeFirst ();
            if (obj instanceof SemanticPredicate)
              {
                // real belt-and-suspenders stuff here, in theory this should already have been checked, but can't hurt to be sure
                SemanticPredicate pred = (SemanticPredicate) obj;
                if (pred.apply ())
                  continue;
                throw new ParseException ("Semantic predicate " + pred + " failed during execution");
              }
            else if (obj instanceof Closure)
              {
                Closure cls = (Closure) obj;
                cls.apply ();
                continue;
              }
            NT top = (NT) obj;
            stack.addAll (0, choosePossibility (table.get (top, token),
                                                token, top));
          }
        stack.removeFirst ();
      }

    protected List<?> choosePossibility (Collection<Pair<SemanticPredicate,
                                         List<?>>> possibilities, T token,
                                         NT top)
    {
        List<?> candidate = null;
        for (Pair<SemanticPredicate, List<?>> possibility : possibilities)
            if (possibility.first == null || possibility.first.apply ()) {
                if (candidate != null)
                    throw new ParseException ("Ambiguous parse for " + token +
                                              " in state " + top);
                candidate = possibility.second;
            }
        if (candidate == null)
            throw new ParseException ("No legal parse for " + token +
                                      " in state " + top);
        return candidate;
    }

    public void witness (T... tokens)
      {
        for (T token : tokens)
          witness (token);
      }

    public List<Object> getStack ()
      {
        return stack;
      }
  }
