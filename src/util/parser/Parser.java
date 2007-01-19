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
    
    Grammar<NT, T> grammar;
    Table<NT, T, List<?>> table;
    LinkedList<Object> stack;
    private Set<NT> nonTerminals;
    private Set<T> terminals;

    @SuppressWarnings("unchecked")
    public Parser (Grammar<NT, T> grammar)
      {
        this.grammar = grammar;
        this.table = new Table<NT,T,List<?>> (grammar);
        nonTerminals = grammar.getNonTerminals ();
        terminals = (Set<T>) grammar.getTerminals ();
        reset ();
      }

    public void reset ()
      {
        this.stack = new LinkedList<Object> ();
        stack.addLast (grammar.getStartSymbol ());
        stack.addLast (grammar.getEOFToken ());
      }
    
    @SuppressWarnings("unchecked")
    public void witness (T token)
      {
        assert terminals.contains (token) || token.equals (grammar.getEOFToken ());
        while (true)
          {
            if (stack.peek ().equals (token))
              {
                stack.removeFirst ();
                break;
              }
            else if (terminals.contains (stack.peek ()))
              throw new ParseException ("Saw a " + token + " when was expecting a " + stack.peek ());
            else
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
                assert nonTerminals.contains (top);
                Collection<Pair<SemanticPredicate, List<?>>> possibilities = table.get (top, token);
                List<?> candidate = null;
                for (Pair<SemanticPredicate, List<?>> possibility : possibilities)
                  {
                    if (possibility.first == null || possibility.first.apply ())
                      {
                        if (candidate != null)
                          throw new ParseException ("Ambiguous parse for " + token + " in state " + top);
                        candidate = possibility.second;
                      }
                  }
                if (candidate == null)
                  throw new ParseException ("No legal parse for " + token + " in state " + top);
                stack.addAll (0, candidate);
              }
          }
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
