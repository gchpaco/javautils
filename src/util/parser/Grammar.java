package util.parser;

import java.util.*;

import util.*;

public class Grammar<NT>
  {
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
    private Map<Object,Set<Object>> firstTable;
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

    public Set<Object> first (Object e)
      {
        if (firstTable == null)
          initializeFirstTable ();
        if (firstTable.containsKey (e))
          return firstTable.get (e);
        if (e instanceof Closure)
          return S.et (getEpsilonToken ());
        System.out.println ("Saw an " + e);
        return S.et (e);
      }

    private void initializeFirstTable ()
      {
        firstTable = new HashMap<Object, Set<Object>> ();
        for (Map.Entry<NT, List<List<?>>> entry : map.entrySet ())
          {
            firstTable.put (entry.getKey (), S.et ());
            for (List<?> rule : entry.getValue ())
                firstTable.put (rule, S.et ());
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
                    Set<Object> s = firstTable.get (rule);
                    boolean done = false;
                    for (Object o : rule)
                      {
                        if (o instanceof Closure)
                          continue;
                        else if (map.containsKey (o))
                          {
                            Set<Object> target = firstTable.get (o);
                            if (target.contains (getEpsilonToken ()))
                              {
                                Set<Object> putative = new HashSet<Object> (target);
                                putative.remove (getEpsilonToken());
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
                            if (!s.contains (o))
                              {
                                s.clear ();
                                s.add (o);
                                changed = true;
                              }
                            done = true;
                            break;
                          }
                      }
                    if (!done) s.add (getEpsilonToken ());
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
    public Set follow (Object e)
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
        ((Set) followTable.get (start)).add (eof);
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
                      Set first = l.isEmpty () ? S.et (epsilon) : firstOf (l);
                      Set copy = new HashSet<Object> (first);
                      if (copy.remove (epsilon)) // epsilon was in first
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

    private Set<Object> firstOf (List<Object> l)
      {
        if (firstTable == null)
          initializeFirstTable ();
        if (firstTable.containsKey (l))
          return firstTable.get (l);
        Set<Object> s = new HashSet<Object> ();
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
              s.add (o);
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
    public Object getEOFToken ()
      {
        return eof;
      }

    @SuppressWarnings("unchecked")
    public void setEpsilonToken (Object epsilon)
      {
        this.epsilon = epsilon;
      }

    @SuppressWarnings("unchecked")
    public Object getEpsilonToken ()
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
  }
