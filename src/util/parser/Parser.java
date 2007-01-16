package util.parser;

import java.util.*;

import util.Closure;

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
    
    Grammar<NT> grammar;
    Table<NT, T, List<?>> table;
    LinkedList<Object> stack;
    private Set<NT> nonTerminals;
    private Set<T> terminals;

    @SuppressWarnings("unchecked")
    public Parser (Grammar<NT> grammar)
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
        stack.addLast (grammar.getEOFToken ().getToken ());
      }
    
    @SuppressWarnings("unchecked")
    public void witness (T token)
      {
        assert terminals.contains (token) || token.equals (grammar.getEOFToken ().getToken ());
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
                if (obj instanceof Closure)
                  {
                    Closure cls = (Closure) obj;
                    cls.apply ();
                    continue;
                  }
                NT top = (NT) obj;
                assert nonTerminals.contains (top);
                Collection<List<?>> possibilities = table.get (top, token);
                List<?> candidate = null;
                for (List<?> possibility : possibilities)
                  {
                    if (!possibility.isEmpty () && possibility.get (0) instanceof SemanticPredicate)
                      {
                        SemanticPredicate p = (SemanticPredicate) possibility.get (0);
                        if (p.apply ())
                          {
                            if (candidate != null)
                              throw new ParseException ("Ambiguous parse for " + token + " in state " + top);
                            candidate = possibility;                            
                          }
                      }
                    else
                      {
                        if (candidate != null)
                          throw new ParseException ("Ambiguous parse for " + token + " in state " + top);
                        candidate = possibility;
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
