package util.parser;

import static java.util.Collections.singleton;
import static util.Pair.make;

import java.util.*;

import net.sf.jga.fn.Generator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import util.L;
import util.Pair;
import util.S;

public class Grammar<NT, T>
{
  static public class AdapterPredicate extends ChoicePredicate
  {
    SemanticPredicate pred;
    public AdapterPredicate (SemanticPredicate sem)
      {
	pred = sem;
      }

    @Override
    public Boolean gen ()
      {
	return pred.fn ();
      }

    @Override
    public int hashCode ()
      {
	return new HashCodeBuilder (337, 113).append (pred).toHashCode ();
      }

    @Override
    public boolean equals (Object obj)
      {                
	if (obj instanceof AdapterPredicate == false)
	    return false;
	if (this == obj)
	  return true;
	AdapterPredicate rhs = (AdapterPredicate) obj;
	return new EqualsBuilder ().append (pred, rhs.pred).isEquals ();
      }

    @Override
    public String toString ()
      {
	return pred.toString ();
      }
  }
  static public class ConversePredicate extends ChoicePredicate
  {
    ChoicePredicate pred;

    public ConversePredicate (ChoicePredicate sem)
      {
        pred = sem;
      }

    @Override
    public Boolean gen ()
      {
        return !pred.fn ();
      }

    @Override
    public int hashCode ()
      {
        return new HashCodeBuilder (337, 113).append (pred).toHashCode ();
      }

    @Override
    public boolean equals (Object obj)
      {
        if (obj instanceof ConversePredicate == false) return false;
        if (this == obj) return true;
        ConversePredicate rhs = (ConversePredicate) obj;
        return new EqualsBuilder ().append (pred, rhs.pred).isEquals ();
      }

    @Override
    public String toString ()
      {
        return "\u00AC" + pred.toString ();
      }
  }
  static public class ConjunctionPredicate extends ChoicePredicate
  {
    ChoicePredicate a, b;

    public ConjunctionPredicate (ChoicePredicate a, ChoicePredicate b)
      {
        this.a = a;
        this.b = b;
      }

    @Override
    public Boolean gen ()
      {
        return a.fn () && b.fn ();
      }

    @Override
    public int hashCode ()
      {
        return new HashCodeBuilder (373, 113).append (a).append (b)
                                             .toHashCode ();
      }

    @Override
    public boolean equals (Object obj)
      {
        if (obj instanceof ConjunctionPredicate == false) return false;
        if (this == obj) return true;
        ConjunctionPredicate rhs = (ConjunctionPredicate) obj;
        return new EqualsBuilder ().append (a, rhs.a).append (b, rhs.b)
                                   .isEquals ();
      }

    @Override
    public String toString ()
      {
        return a.toString () + "\u2227" + b.toString ();
      }
  }

  @SuppressWarnings ("unchecked")
  private T eof;
  @SuppressWarnings ("unchecked")
  private T epsilon;
  @SuppressWarnings ("unchecked")
  private final Map<NT, List<List<?>>> map;
  @SuppressWarnings ("unchecked")
  private NT start;
  @SuppressWarnings ("unchecked")
  private Map<Object, Set<Pair<ChoicePredicate, T>>> firstTable;
  @SuppressWarnings ("unchecked")
  private Map<Object, Set<Pair<ChoicePredicate, T>>> followTable;
  @SuppressWarnings ("unchecked")
  private Set terminals;

  @SuppressWarnings ("unchecked")
  public Grammar (Map<NT, List<List<?>>> rules)
    {
      map = rules;
      for (List<List<?>> lists : map.values ())
        for (List<?> list : lists)
          for (Object o : list)
            if (!map.containsKey (o)) if (terminals == null)
              terminals = S.et (o);
            else
              terminals.add (o);
    }

  @SuppressWarnings ("unchecked")
  public static Grammar makeFrom (Map rules)
    {
      return new Grammar (rules);
    }

  @SuppressWarnings ("unchecked")
  public Grammar (Pair<NT, List<?>>... symbols)
    {
      map = new HashMap<NT, List<List<?>>> ();
      for (Pair<NT, List<?>> p : symbols)
        if (!map.containsKey (p.first))
          map.put (p.first, new ArrayList<List<?>> ());
      for (Pair<NT, List<?>> p : symbols)
        {
          map.get (p.first).add (p.second);
          for (Object o : p.second)
            if (!map.containsKey (o))
              {
                if (terminals == null)
                  terminals = S.et (o);
                else
                  terminals.add (o);
              }
        }
    }

  @SuppressWarnings ("unchecked")
  public Set<Pair<ChoicePredicate, T>> first (Object e)
    {
      if (firstTable == null) initializeFirstTable ();
      if (firstTable.containsKey (e)) return firstTable.get (e);
      if (e instanceof SemanticPredicate)
	return singleton (make (adapt ((SemanticPredicate) e),
				getEpsilonToken ()));
      if (e instanceof ChoicePredicate)
        return singleton (make ((ChoicePredicate) e, getEpsilonToken ()));
      if (e instanceof Generator)
        return singleton (make ((ChoicePredicate) null, getEpsilonToken ()));
      return singleton (make ((ChoicePredicate) null, (T) e));
    }

  @SuppressWarnings ("unchecked")
  private void initializeFirstTable ()
    {
      firstTable = new HashMap<Object, Set<Pair<ChoicePredicate, T>>> ();
      for (Map.Entry<NT, List<List<?>>> entry : map.entrySet ())
        {
          firstTable.put (entry.getKey (),
                          new HashSet<Pair<ChoicePredicate, T>> ());
          for (List<?> rule : entry.getValue ())
            firstTable.put (rule, new HashSet<Pair<ChoicePredicate, T>> ());
        }
      firstTable.put (L.ist (), singleton (make ((ChoicePredicate) null,
                                                 getEpsilonToken ())));
      boolean changed;
      do
        {
          changed = false;
          for (List<List<?>> rules : map.values ())
            for (List<?> rule : rules)
              {
                if (rule.isEmpty ()) continue;
                Set<Pair<ChoicePredicate, T>> s = firstTable.get (rule);
                boolean diff = firstOfList (rule, s);
                if (diff) changed = true;
              }
          for (Map.Entry<NT, List<List<?>>> entry : map.entrySet ())
            for (List<?> rule : entry.getValue ())
              {
                boolean diff =
                    firstTable.get (entry.getKey ())
                              .addAll (firstTable.get (rule));
                if (diff) changed = true;
              }
        }
      while (changed);
    }

  @SuppressWarnings ("unchecked")
  private boolean firstOfList (List<?> rule, Set<Pair<ChoicePredicate, T>> set)
    {
      boolean changed = false;
      ChoicePredicate currentPredicate = null;
      boolean done = false;
      for (Object o : rule)
        {
          if (o instanceof ChoicePredicate)
            {
              ChoicePredicate p = (ChoicePredicate) o;
              if (currentPredicate == null)
                currentPredicate = p;
              else
                currentPredicate = conjunction (currentPredicate, p);
              continue;
            }
          else if (o instanceof SemanticPredicate)
            {
	      ChoicePredicate p = adapt ((SemanticPredicate) o);
              if (currentPredicate == null)
                currentPredicate = p;
              else
                currentPredicate = conjunction (currentPredicate, p);
              continue;
            }
          else if (o instanceof Generator)
            continue;
          else if (map.containsKey (o))
            {
              Set<Pair<ChoicePredicate, T>> target = firstTable.get (o);
              if (hasToken (target, getEpsilonToken ()))
                {
                  Set<Pair<ChoicePredicate, T>> putative =
                      new HashSet<Pair<ChoicePredicate, T>> (target);
                  removeToken (putative, getEpsilonToken ());
                  if (set.equals (putative) || set.equals (target)) continue;
                  changed = true;
                  set.clear ();
                  set.addAll (putative);
                }
              else
                {
                  if (!set.equals (target))
                    {
                      changed = true;
                      set.clear ();
                      set.addAll (target);
                    }
                  done = true;
                  break;
                }
            }
          else
            {
              if (!hasToken (set, (T) o))
                {
                  set.clear ();
                  set.add (make (currentPredicate, (T) o));
                  changed = true;
                }
              done = true;
              break;
            }
        }
      if (!done)
        {
          boolean diff = set.add (make (currentPredicate, getEpsilonToken ()));
          if (diff) changed = true;
        }
      return changed;
    }

  private <U> boolean removeToken (Set<Pair<ChoicePredicate, U>> set, U token)
    {
      boolean changed = false;
      for (Iterator<Pair<ChoicePredicate, U>> iter = set.iterator ();
	   iter.hasNext ();)
        {
          Pair<ChoicePredicate, U> element = iter.next ();
          if (element.second.equals (token))
            {
              iter.remove ();
              changed = true;
            }
        }
      return changed;
    }

  private <U> boolean hasToken (Set<Pair<ChoicePredicate, U>> set, U token)
    {
      for (Iterator<Pair<ChoicePredicate, U>> iter = set.iterator ();
	   iter.hasNext ();)
        {
          Pair<ChoicePredicate, U> element = iter.next ();
          if (element.second.equals (token)) return true;
        }
      return false;
    }

  @SuppressWarnings ("unchecked")
  public Set<Pair<ChoicePredicate, T>> follow (Object e)
    {
      if (followTable == null) initializeFollowTable ();
      if (followTable.containsKey (e)) return followTable.get (e);
      return null;
    }

  @SuppressWarnings ("unchecked")
  private void initializeFollowTable ()
    {
      followTable = new HashMap<Object, Set<Pair<ChoicePredicate, T>>> ();
      for (Object e : map.keySet ())
        followTable.put (e, new HashSet<Pair<ChoicePredicate, T>> ());
      followTable.get (start).add (make ((ChoicePredicate) null,
                                         getEOFToken ()));
      // this is kind of inefficient. oh well.
      boolean changed;
      do
        {
          changed = false;
          for (Map.Entry<NT, List<List<?>>> entry : map.entrySet ())
            for (List<?> list : entry.getValue ())
              for (int i = 0; i < list.size (); i++)
                {
                  Object o = list.get (i);
                  if (map.containsKey (o))
                    {
                      List<?> l = list.subList (i + 1, list.size ());
                      Set<Pair<ChoicePredicate, T>> first =
                          l.isEmpty () ? S.et (make ((ChoicePredicate) null,
                                                     getEpsilonToken ()))
                                      : firstOf (l);
                      Set<Pair<ChoicePredicate, T>> copy =
                          new HashSet<Pair<ChoicePredicate, T>> (first);
		      // if epsilon was in first, remove it and use follow
                      if (removeToken (copy, getEpsilonToken ()))
                        copy.addAll (followTable.get (entry.getKey ()));
                      Set<Pair<ChoicePredicate, T>> oldSet =
                          followTable.get (o);
                      if (oldSet.addAll (copy)) changed = true;
                    }
                }
        }
      while (changed);
    }

  @SuppressWarnings ("unchecked")
  private Set<Pair<ChoicePredicate, T>> firstOf (List<?> l)
    {
      if (firstTable == null) initializeFirstTable ();
      if (firstTable.containsKey (l)) return firstTable.get (l);
      Set<Pair<ChoicePredicate, T>> s =
          new HashSet<Pair<ChoicePredicate, T>> ();
      firstOfList (l, s);
      return s;
    }

  @SuppressWarnings ("unchecked")
  public void setEOFToken (T eof)
    {
      this.eof = eof;
    }

  @SuppressWarnings ("unchecked")
  public T getEOFToken ()
    {
      return eof;
    }

  @SuppressWarnings ("unchecked")
  public void setEpsilonToken (T epsilon)
    {
      this.epsilon = epsilon;
    }

  @SuppressWarnings ("unchecked")
  public T getEpsilonToken ()
    {
      return epsilon;
    }

  @SuppressWarnings ("unchecked")
  public void setStartSymbol (NT s)
    {
      if (!map.containsKey (s))
        throw new IllegalArgumentException (s + " is not a legal nonterminal!");
      this.start = s;
    }

  @SuppressWarnings ("unchecked")
  public NT getStartSymbol ()
    {
      return this.start;
    }

  public Set<NT> getNonTerminals ()
    {
      return map.keySet ();
    }

  @SuppressWarnings ( { "unchecked", "cast" })
  public Collection<List<?>> rulesFor (Object nt)
    {
      return map.get (nt);
    }

  @SuppressWarnings ("unchecked")
  public Set<Enum> getTerminals ()
    {
      return terminals;
    }

  public static ChoicePredicate adapt (SemanticPredicate sem)
    {
      // XXX: won't work if sem == null
      return new AdapterPredicate (sem);
    }

  public static ChoicePredicate converse (ChoicePredicate sem)
    {
      // XXX: won't work if sem == null
      return new ConversePredicate (sem);
    }

  public static ChoicePredicate conjunction (ChoicePredicate a,
					     ChoicePredicate b)
    {
      if (a == null) return b;
      if (b == null) return a;
      return new ConjunctionPredicate (a, b);
    }

  public Table<NT, T, ?> toTable ()
    {
      return new Table<NT, T, List<?>> (this);
    }
}
