package util.parser;

import java.util.*;

import util.S;

public class Table<NT,T,V extends List<?>>
{
  Map<NT, Map<T, HashSet<V>>> table;
  
  @SuppressWarnings("unchecked")
  public Table ()
    {
      table = new HashMap<NT,Map<T,HashSet<V>>> ();
    }

  @SuppressWarnings("unchecked")
  public Table (Grammar<NT> grammar)
    {
      table = new HashMap<NT,Map<T,HashSet<V>>> ();
      for (NT nt : grammar.getNonTerminals ())
        for (V v : (Collection<V>) grammar.rulesFor (nt))
          {
            Set first = v.isEmpty () ? S.et (grammar.getEpsilonToken ()) : grammar.first (v);
            Set follow = grammar.follow (nt);
            for (T t : (Set<T>) grammar.getTerminals ())
              add (grammar, nt, v, first, follow, t);
            add (grammar, nt, v, first, follow, (T) grammar.getEOFToken ());
          }
    }

  @SuppressWarnings("unchecked")
  private void add (Grammar<NT> grammar, NT nt, V v, Set first, Set follow, T t)
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
      if (table.containsKey (a) && table.get (a).containsKey (x))
        return table.get (a).get (x);
      return Collections.EMPTY_SET;
    }

  @SuppressWarnings("unchecked")
  public void put (NT a, T b, V v)
    {
      if (!table.containsKey (a))
        table.put (a, new HashMap<T,HashSet<V>> ());
      if (!table.get (a).containsKey (b))
        table.get (a).put (b, new HashSet<V> ());
      table.get (a).get (b).add (v);
    }
}