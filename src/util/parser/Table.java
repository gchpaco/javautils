package util.parser;

import java.util.*;

import util.Pair;
import util.Triple;
import static util.Pair.make;
import static java.util.Collections.singleton;
import static util.parser.Grammar.conjunction;

public class Table<NT,T,V extends List<?>> implements Iterable<Triple<NT, T, Pair<ChoicePredicate, V>>>
{
  Map<NT, Map<T, Set<Pair<ChoicePredicate, V>>>> table;
  
  public Table ()
    {
      table = new HashMap<NT, Map<T, Set<Pair<ChoicePredicate, V>>>> ();
    }
  
  public Iterator<Triple<NT, T, Pair<ChoicePredicate, V>>> iterator ()
    {
      return new Iterator<Triple<NT,T,Pair<ChoicePredicate,V>>> ()
        {
          Iterator<NT> firstKeyIterator = table.keySet ().iterator ();
          Iterator<T> secondKeyIterator;
          Iterator<Pair<ChoicePredicate,V>> lastIterator;
          NT nt;
          T t;
          public boolean hasNext ()
            {
              if (lastIterator == null || !lastIterator.hasNext ())
                if (secondKeyIterator == null || !secondKeyIterator.hasNext ())
                  return firstKeyIterator.hasNext ();
              return true;
            }
          public Triple<NT, T, Pair<ChoicePredicate, V>> next ()
            {
              if (lastIterator == null || !lastIterator.hasNext ())
                {
                  if (secondKeyIterator == null || !secondKeyIterator.hasNext ())
                    {
                      nt = firstKeyIterator.next ();
                      secondKeyIterator = table.get (nt).keySet ().iterator ();
                    }
                  t = secondKeyIterator.next ();
                  lastIterator = table.get (nt).get (t).iterator ();
                }
              return Triple.make (nt, t, lastIterator.next ());
            }
          public void remove ()
            {
              throw new IllegalStateException ("I refuse to remove entries from the parsing table");
            }
        };
    }
  
  public List<Triple<NT,T,Pair<ChoicePredicate,V>>> getLines ()
    {
      List<Triple<NT,T,Pair<ChoicePredicate,V>>> result = new ArrayList<Triple<NT,T,Pair<ChoicePredicate,V>>> ();
      for (Triple<NT,T,Pair<ChoicePredicate,V>> t : this)
        result.add (t);
      return result;
    }

  @SuppressWarnings("unchecked")
  public Table (Grammar<NT, T> grammar)
    {
      table = new HashMap<NT, Map<T, Set<Pair<ChoicePredicate, V>>>> ();
      for (NT nt : grammar.getNonTerminals ())
        for (V v : (Collection<V>) grammar.rulesFor (nt))
          {
            Set<Pair<ChoicePredicate,T>> first = v.isEmpty () 
              ? singleton (make ((ChoicePredicate)null, grammar.getEpsilonToken ())) 
              : grammar.first (v);
            Set<Pair<ChoicePredicate,T>> follow = grammar.follow (nt);
            for (T t : (Set<T>) grammar.getTerminals ())
              add (grammar, nt, v, first, follow, t);
            add (grammar, nt, v, first, follow, grammar.getEOFToken ());
          }
    }
  
  public Table (Triple<NT, T, Pair<ChoicePredicate,V>>... rules)
    {
      table = new HashMap<NT, Map<T, Set<Pair<ChoicePredicate, V>>>> ();
      for (Triple<NT, T, Pair<ChoicePredicate, V>> rule : rules)
        put (rule.first, rule.second, rule.third.first, rule.third.second);
    }

  private void add (Grammar<NT, T> grammar, NT nt, V v, Set<Pair<ChoicePredicate,T>> first, 
                    Set<Pair<ChoicePredicate,T>> follow, T t)
    {
      for (Pair<ChoicePredicate,T> p : first)
        {
          if (p.second.equals (t))
            put (nt, t, p.first, v);
          if (p.second.equals (grammar.getEpsilonToken ()))
            for (Pair<ChoicePredicate,T> fp : follow)
              if (fp.second.equals (t))
                put (nt, t, conjunction (p.first, fp.first), v);
        }
    }

  @SuppressWarnings("unchecked")
  public Collection<Pair<ChoicePredicate, V>> get (NT a, T x)
    {
      if (table.containsKey (a) && table.get (a).containsKey (x))
        return table.get (a).get (x);
      return Collections.EMPTY_SET;
    }
  
  public void put (NT nt, T t, ChoicePredicate p, V v)
    {
      if (!table.containsKey (nt))
        table.put (nt, new HashMap<T, Set<Pair<ChoicePredicate, V>>> ());
      if (!table.get (nt).containsKey (t))
        table.get (nt).put (t, new HashSet<Pair<ChoicePredicate, V>> ());
      table.get (nt).get (t).add (make (p, v));
    }
}
