package util.parser;

import java.util.*;

import util.S;

public class Table<NT,T,V extends List<?>>
{
  Map<NT, Map<TokenTag<T>, HashSet<V>>> table;
  
  public Table ()
    {
      table = new HashMap<NT,Map<TokenTag<T>,HashSet<V>>> ();
    }

  @SuppressWarnings("unchecked")
  public Table (Grammar<NT> grammar)
    {
      table = new HashMap<NT,Map<TokenTag<T>,HashSet<V>>> ();
      for (NT nt : grammar.getNonTerminals ())
        for (V v : (Collection<V>) grammar.rulesFor (nt))
          {
            Set<TokenTag<Object>> first = v.isEmpty () ? S.et (grammar.getEpsilonToken ()) : grammar.first (v);
            Set<TokenTag<Object>> follow = grammar.follow (nt);
            for (T t : (Set<T>) grammar.getTerminals ())
              add (grammar, nt, v, first, follow, TokenTag.make (t));
            add (grammar, nt, v, first, follow, grammar.getEOFToken ());
          }
    }

  @SuppressWarnings("unchecked")
  private void add (Grammar<NT> grammar, NT nt, V v, Set first, Set follow, TokenTag<?> t)
    {
      if (first.contains (t))
        put (nt, t, v);
      else if (first.contains (grammar.getEpsilonToken ()) &&
          follow.contains (t))
        put (nt, t, v);
    }

  @SuppressWarnings("unchecked")
  public Collection<V> get (NT a, T x)
    {
      if (table.containsKey (a) && table.get (a).containsKey (TokenTag.make (x)))
        return table.get (a).get (TokenTag.make (x));
      return Collections.EMPTY_SET;
    }

  @SuppressWarnings("unchecked")
  public void put (NT a, Object b, V v)
    {
      if (b instanceof TokenTag)
        put (a, (TokenTag) b, v);
      else
        put (a, TokenTag.make (b), v);
    }

  @SuppressWarnings("unchecked")
  public void put (NT a, TokenTag<T> b, V v)
    {
      if (!table.containsKey (a))
        table.put (a, new HashMap<TokenTag<T>,HashSet<V>> ());
      if (!table.get (a).containsKey (b))
        table.get (a).put (b, new HashSet<V> ());
      table.get (a).get (b).add (v);
    }
}