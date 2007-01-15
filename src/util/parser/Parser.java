package util.parser;

import java.util.*;

public class Parser<NT, T>
  {
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
        this.stack = new LinkedList<Object> ();
        stack.addLast (grammar.getStartSymbol ());
        stack.addLast (grammar.getEOFToken ());
        nonTerminals = grammar.getNonTerminals ();
        terminals = (Set<T>) grammar.getTerminals ();
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
              throw new RuntimeException ("Saw a " + token + " when was expecting a " + stack.peek ());
            else
              {
                NT top = (NT) stack.removeFirst ();
                assert nonTerminals.contains (top);
                Collection<List<?>> possibilities = table.get (top, token);
                if (possibilities.isEmpty ())
                  throw new RuntimeException ("No possible parse for " + token + " in state " + top);
                else if (possibilities.size () > 1)
                  throw new RuntimeException ("Ambiguous parse for " + token + " in state " + top);
                stack.addAll (0, possibilities.iterator ().next ());
              }
          }
      }

    public List<Object> getStack ()
      {
        return stack;
      }
  }
