package util.parser;

import java.util.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import util.*;

import static util.Pair.make;
import static java.util.Collections.singleton;

public class Grammar<NT, T>
  {
    static public class ConversePredicate implements SemanticPredicate
    {
      SemanticPredicate pred;

      public ConversePredicate (SemanticPredicate sem)
        {
          pred = sem;
        }

      public Boolean apply ()
        {
          return ! pred.apply ();
        }
      
      @Override
      public int hashCode ()
        {
          return new HashCodeBuilder (337, 113).append (pred).toHashCode ();
        }
      
      @Override
      public boolean equals (Object obj)
        {                
          if (obj instanceof ConversePredicate == false)
              return false;
          if (this == obj)
            return true;
          ConversePredicate rhs = (ConversePredicate) obj;
          return new EqualsBuilder ().append (pred, rhs.pred).isEquals ();
        }
      
      @Override
      public String toString ()
        {
          return "\u00AC" + pred.toString ();
        }
    }
    static public class ConjunctionPredicate implements SemanticPredicate
    {
      SemanticPredicate a, b;

      public ConjunctionPredicate (SemanticPredicate a, SemanticPredicate b)
        {
          this.a = a; this.b = b;
        }

      public Boolean apply ()
        {
          return a.apply () && b.apply ();
        }
      
      @Override
      public int hashCode ()
        {
          return new HashCodeBuilder (373, 113).append (a).append (b).toHashCode ();
        }
      
      @Override
      public boolean equals (Object obj)
        {                
          if (obj instanceof ConjunctionPredicate == false)
              return false;
          if (this == obj)
            return true;
          ConjunctionPredicate rhs = (ConjunctionPredicate) obj;
          return new EqualsBuilder ().append (a, rhs.a).append (b, rhs.b).isEquals ();
        }
      
      @Override
      public String toString ()
        {
          return a.toString () + "\u2227" + b.toString ();
        }
    }

    @SuppressWarnings("unchecked")
    private final Pair<NT,List<?>>[] symbols;
    @SuppressWarnings("unchecked")
    private T eof;
    @SuppressWarnings("unchecked")
    private T epsilon;
    @SuppressWarnings("unchecked")
    private Map<NT,List<List<?>>> map;
    @SuppressWarnings("unchecked")
    private NT start;
    @SuppressWarnings("unchecked")
    private Map<Object,Set<Pair<SemanticPredicate,T>>> firstTable;
    @SuppressWarnings("unchecked")
    private Map<Object,Set<Pair<SemanticPredicate,T>>> followTable;
    @SuppressWarnings("unchecked")
    private EnumSet terminals;

    @SuppressWarnings("unchecked")
    public Grammar (Pair<NT,List<?>>... symbols)
      {
        this.symbols = symbols;
        Class<? extends Object> ntClass = symbols[0].first.getClass ();
        map = new EnumMap (ntClass);
        for (Pair<NT,List<?>> p : symbols)
          {
            assert p.first instanceof Enum;
            if (!map.containsKey (p.first))
              map.put (p.first, new ArrayList<List<?>> ());
            map.get (p.first).add (p.second);
            for (Object o : p.second)
              if (o instanceof Enum && !ntClass.isInstance (o))
                {
                  if (terminals == null)
                    terminals = EnumSet.of ((Enum)o);
                  else
                    terminals.add (o);
                }
          }
      }

    @SuppressWarnings("unchecked")
    public Set<Pair<SemanticPredicate,T>> first (Object e)
      {
        if (firstTable == null)
          initializeFirstTable ();
        if (firstTable.containsKey (e))
          return firstTable.get (e);
        if (e instanceof SemanticPredicate)
          return singleton (make ((SemanticPredicate)e, getEpsilonToken ()));
        if (e instanceof Closure)
          return singleton (make ((SemanticPredicate)null, getEpsilonToken ()));
        return singleton (make ((SemanticPredicate)null, (T) e));
      }

    @SuppressWarnings("unchecked")
    private void initializeFirstTable ()
      {
        firstTable = new HashMap<Object, Set<Pair<SemanticPredicate,T>>> ();
        for (Map.Entry<NT, List<List<?>>> entry : map.entrySet ())
          {
            firstTable.put (entry.getKey (), new HashSet<Pair<SemanticPredicate,T>> ());
            for (List<?> rule : entry.getValue ())
                firstTable.put (rule, new HashSet<Pair<SemanticPredicate,T>> ());
          }
        firstTable.put (L.ist (), singleton (make ((SemanticPredicate)null, getEpsilonToken ())));
        boolean changed;
        do
          {
            changed = false;
            for (List<List<?>> rules : map.values ())
                for (List<?> rule : rules)
                  {
                    if (rule.isEmpty ())
                      continue;
                    Set<Pair<SemanticPredicate, T>> s = firstTable.get (rule);
                    boolean diff = firstOfList (rule, s);
                    if (diff) changed = true;
                  }
            for (Map.Entry<NT, List<List<?>>> entry : map.entrySet ())
                for (List<?> rule : entry.getValue ())
                  {
                    boolean diff = firstTable.get (entry.getKey ()).addAll (firstTable.get (rule));
                    if (diff) changed = true;
                  }
          }
        while (changed);
      }

    @SuppressWarnings("unchecked")
    private boolean firstOfList (List<?> rule, Set<Pair<SemanticPredicate, T>> set)
      {
        boolean changed = false;
        SemanticPredicate currentPredicate = null;
        boolean done = false;
        for (Object o : rule)
          {
            if (o instanceof SemanticPredicate)
              {
                SemanticPredicate p = (SemanticPredicate) o;
                if (currentPredicate == null)
                  currentPredicate = p;
                else
                  currentPredicate = conjunction (currentPredicate, p);
                continue;
              }
            else if (o instanceof Closure)
              continue;
            else if (map.containsKey (o))
              {
                Set<Pair<SemanticPredicate, T>> target = firstTable.get (o);
                if (hasToken (target, getEpsilonToken ()))
                  {
                    Set<Pair<SemanticPredicate, T>> putative = 
                      new HashSet<Pair<SemanticPredicate, T>> (target);
                    removeToken (putative, getEpsilonToken ());
                    if (set.equals (putative))
                      continue;
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

    private <U> boolean removeToken (Set<Pair<SemanticPredicate, U>> set, U token)
      {
        boolean changed = false;
        for (Iterator<Pair<SemanticPredicate, U>> iter = set.iterator (); iter.hasNext ();)
          {
            Pair<SemanticPredicate, U> element = iter.next ();
            if (element.second.equals (token))
              {
                iter.remove ();
                changed = true;
              }
          }
        return changed;
      }

    private <U> boolean hasToken (Set<Pair<SemanticPredicate, U>> set, U token)
      {
        for (Iterator<Pair<SemanticPredicate, U>> iter = set.iterator (); iter.hasNext ();)
          {
            Pair<SemanticPredicate, U> element = iter.next ();
            if (element.second.equals (token))
              return true;
          }
        return false;
      }

    @SuppressWarnings("unchecked")
    public Set<Pair<SemanticPredicate,T>> follow (Object e)
      {
        if (followTable == null)
          initializeFollowTable ();
        if (followTable.containsKey (e))
          return followTable.get (e);
        return null;
      }

    @SuppressWarnings("unchecked")
    private void initializeFollowTable ()
      {
        followTable = new HashMap<Object,Set<Pair<SemanticPredicate,T>>> ();
        for (Object e : map.keySet ())
          followTable.put (e, new HashSet<Pair<SemanticPredicate,T>> ());
        followTable.get (start).add (make ((SemanticPredicate) null, getEOFToken ()));
        // this is kind of inefficient.  oh well.
        boolean changed;
        do
          {
            changed = false;
            for (Pair<NT, List<?>> p : symbols)
              for (int i = 0; i < p.second.size (); i++)
                {
                  Object o = p.second.get (i);
                  if (map.containsKey (o))
                    {
                      List l = p.second.subList (i + 1, p.second.size ());
                      Set<Pair<SemanticPredicate,T>> first = l.isEmpty () 
                        ? S.et (make ((SemanticPredicate) null, getEpsilonToken ()))
                        : firstOf (l);
                      Set<Pair<SemanticPredicate,T>> copy = new HashSet<Pair<SemanticPredicate,T>> (first);
                      if (removeToken (copy, getEpsilonToken ())) // epsilon was in first
                        copy.addAll (followTable.get (p.first));
                      Set<Pair<SemanticPredicate,T>> oldSet = followTable.get (o);
                      if (oldSet.addAll (copy))
                        changed = true;
                    }
                }
          }
        while (changed);
      }

    @SuppressWarnings("unchecked")
    private Set<Pair<SemanticPredicate,T>> firstOf (List<Object> l)
      {
        if (firstTable == null)
          initializeFirstTable ();
        if (firstTable.containsKey (l))
          return firstTable.get (l);
        Set<Pair<SemanticPredicate,T>> s = new HashSet<Pair<SemanticPredicate,T>> ();
        firstOfList (l, s);
        return s;
      }

    @SuppressWarnings("unchecked")
    public void setEOFToken (T eof)
      {
        this.eof = eof;
      }

    @SuppressWarnings("unchecked")
    public T getEOFToken ()
      {
        return eof;
      }

    @SuppressWarnings("unchecked")
    public void setEpsilonToken (T epsilon)
      {
        this.epsilon = epsilon;
      }

    @SuppressWarnings("unchecked")
    public T getEpsilonToken ()
      {
        return epsilon;
      }

    @SuppressWarnings("unchecked")
    public void setStartSymbol (NT s)
      {
        this.start = s;
      }

    @SuppressWarnings("unchecked")
    public NT getStartSymbol ()
      {
        return this.start;
      }

    public Set<NT> getNonTerminals ()
      {
        return map.keySet ();
      }
    
    @SuppressWarnings({ "unchecked", "cast" })
    public Collection<List<?>> rulesFor (Object nt)
      {
        return (Collection<List<?>>) map.get (nt);
      }

    @SuppressWarnings("unchecked")
    public Set<Enum> getTerminals ()
      {
        return terminals;
      }

    public static SemanticPredicate converse (SemanticPredicate sem)
      {
        // XXX: won't work if sem == null
        return new ConversePredicate (sem);
      }

    public static SemanticPredicate conjunction (SemanticPredicate a, SemanticPredicate b)
      {
        if (a == null) return b;
        if (b == null) return a;
        return new ConjunctionPredicate (a, b);
      }
  }
