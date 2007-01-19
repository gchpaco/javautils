package util.parser;

import java.util.*;

import util.Pair;
import static util.Pair.make;
import static java.util.Collections.singleton;
import static util.parser.Grammar.conjunction;

public class Table<NT,T,V extends List<?>>
{
  Map<NT, Map<T, Set<Pair<SemanticPredicate, V>>>> table;
  
  public Table ()
    {
      table = new HashMap<NT, Map<T, Set<Pair<SemanticPredicate, V>>>> ();
    }

  @SuppressWarnings("unchecked")
  public Table (Grammar<NT, T> grammar)
    {
      table = new HashMap<NT, Map<T, Set<Pair<SemanticPredicate, V>>>> ();
      for (NT nt : grammar.getNonTerminals ())
        for (V v : (Collection<V>) grammar.rulesFor (nt))
          {
            Set<Pair<SemanticPredicate,T>> first = v.isEmpty () 
              ? singleton (make ((SemanticPredicate)null, grammar.getEpsilonToken ())) 
              : grammar.first (v);
            Set<Pair<SemanticPredicate,T>> follow = grammar.follow (nt);
            for (T t : (Set<T>) grammar.getTerminals ())
              add (grammar, nt, v, first, follow, t);
            add (grammar, nt, v, first, follow, grammar.getEOFToken ());
          }
    }

  private void add (Grammar<NT, T> grammar, NT nt, V v, Set<Pair<SemanticPredicate,T>> first, 
                    Set<Pair<SemanticPredicate,T>> follow, T t)
    {
      for (Pair<SemanticPredicate,T> p : first)
        {
          if (p.second.equals (t))
            put (nt, t, p.first, v);
          if (p.second.equals (grammar.getEpsilonToken ()))
            for (Pair<SemanticPredicate,T> fp : follow)
              if (fp.second.equals (t))
                put (nt, t, conjunction (p.first, fp.first), v);
        }
    }

  @SuppressWarnings("unchecked")
  public Collection<Pair<SemanticPredicate, V>> get (NT a, T x)
    {
      if (table.containsKey (a) && table.get (a).containsKey (x))
        return table.get (a).get (x);
      return Collections.EMPTY_SET;
    }
  
  public void put (NT nt, T t, SemanticPredicate p, V v)
    {
      if (!table.containsKey (nt))
        table.put (nt, new HashMap<T, Set<Pair<SemanticPredicate, V>>> ());
      if (!table.get (nt).containsKey (t))
        table.get (nt).put (t, new HashSet<Pair<SemanticPredicate, V>> ());
      table.get (nt).get (t).add (make (p, v));
    }
}