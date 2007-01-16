package util.parser;

import java.util.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import util.*;

import static util.parser.TokenTag.make;

public class Grammar<NT>
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
    private Object eof;
    @SuppressWarnings("unchecked")
    private Object epsilon;
    @SuppressWarnings("unchecked")
    private Map<NT,List<List<?>>> map;
    @SuppressWarnings("unchecked")
    private NT start;
    @SuppressWarnings("unchecked")
    private Map<Object,Set<TokenTag<Object>>> firstTable;
    @SuppressWarnings("unchecked")
    private Map followTable;
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
    public Set<TokenTag<Object>> first (Object e)
      {
        if (firstTable == null)
          initializeFirstTable ();
        if (firstTable.containsKey (e))
          return firstTable.get (e);
        if (e instanceof Closure)
          return S.et (getEpsilonToken ());
        return S.et (make (e));
      }

    @SuppressWarnings("unchecked")
    private void initializeFirstTable ()
      {
        firstTable = new HashMap<Object, Set<TokenTag<Object>>> ();
        for (Map.Entry<NT, List<List<?>>> entry : map.entrySet ())
          {
            firstTable.put (entry.getKey (), new HashSet<TokenTag<Object>> ());
            for (List<?> rule : entry.getValue ())
                firstTable.put (rule, new HashSet<TokenTag<Object>> ());
          }
        firstTable.put (L.ist (), S.et (getEpsilonToken ()));
        boolean changed;
        do
          {
            changed = false;
            for (List<List<?>> rules : map.values ())
                for (List<?> rule : rules)
                  {
                    if (rule.isEmpty ())
                      continue;
                    Set<TokenTag<Object>> s = firstTable.get (rule);
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
                            Set<TokenTag<Object>> target = firstTable.get (o);
                            if (target.contains (getEpsilonToken ()))
                              {
                                Set<TokenTag<Object>> putative = new HashSet<TokenTag<Object>> (target);
                                putative.remove (getEpsilonToken ());
                                if (s.equals (putative))
                                  continue;
                                changed = true;
                                s.clear ();
                                s.addAll (putative);
                              }
                            else
                              {
                                if (!s.equals (target))
                                  {
                                    changed = true;
                                    s.clear ();
                                    s.addAll (target);
                                  }
                                done = true;
                                break;
                              }
                          }
                        else
                          {
                            if (!s.contains (make (o)))
                              {
                                s.clear ();
                                s.add (make (o));
                                changed = true;
                              }
                            done = true;
                            break;
                          }
                      }
                    if (!done)
                      {
                        boolean diff = s.add (getEpsilonToken ());
                        if (diff) changed = true;
                      }
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
    public Set<TokenTag<Object>> follow (Object e)
      {
        if (followTable == null)
          initializeFollowTable ();
        if (followTable.containsKey (e))
          return (Set) followTable.get (e);
        return null;
      }

    @SuppressWarnings("unchecked")
    private void initializeFollowTable ()
      {
        followTable = new HashMap ();
        for (Object e : map.keySet ())
          followTable.put (e, S.et ());
        ((Set) followTable.get (start)).add (getEOFToken ());
        // this is kind of inefficient.  oh well.
        boolean changed;
        do
          {
            changed = false;
            Class<? extends Object> ntClass = symbols[0].first.getClass ();
            for (Pair<NT, List<?>> p : symbols)
              for (int i = 0; i < p.second.size (); i++)
                {
                  Object o = p.second.get (i);
                  if (ntClass.isInstance (o))
                    {
                      Enum e = (Enum) o;
                      List l = p.second.subList (i + 1, p.second.size ());
                      Set first = l.isEmpty () ? S.et (getEpsilonToken ()) : firstOf (l);
                      Set copy = new HashSet<Object> (first);
                      if (copy.remove (getEpsilonToken ())) // epsilon was in first
                        copy.addAll ((Collection) followTable.get (p.first));
                      Set<?> oldSet = (Set<?>) followTable.get (e);
                      if (oldSet.containsAll (copy))
                        continue;
                      oldSet.addAll (copy);
                      changed = true;
                    }
                }
          }
        while (changed);
      }

    @SuppressWarnings("unchecked")
    private Set<TokenTag<Object>> firstOf (List<Object> l)
      {
        if (firstTable == null)
          initializeFirstTable ();
        if (firstTable.containsKey (l))
          return firstTable.get (l);
        Set<TokenTag<Object>> s = new HashSet<TokenTag<Object>> ();
        boolean done = false;
        for (Object o : l)
          if (o instanceof Closure)
            continue;
          else if (map.containsKey (o))
            {
              s.clear ();
              s.addAll (firstTable.get (o));
              if (!s.contains (getEpsilonToken ()))
                {
                  done = true;
                  break;
                }
              s.remove (getEpsilonToken ());
            }
          else
            {
              s.add (make (o));
              done = true;
              break;
            }
        if (!done) s.add (getEpsilonToken ());
        return s;
      }

    @SuppressWarnings("unchecked")
    public void setEOFToken (Object eof)
      {
        this.eof = eof;
      }

    @SuppressWarnings("unchecked")
    public TokenTag<Object> getEOFToken ()
      {
        return make (eof);
      }

    @SuppressWarnings("unchecked")
    public void setEpsilonToken (Object epsilon)
      {
        this.epsilon = epsilon;
      }

    @SuppressWarnings("unchecked")
    public TokenTag<Object> getEpsilonToken ()
      {
        return make (epsilon);
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
        return new ConversePredicate (sem);
      }

    public static SemanticPredicate conjunction (SemanticPredicate a, SemanticPredicate b)
      {
        return new ConjunctionPredicate (a, b);
      }
  }
