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
    private Map firstMemoization;
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
        firstMemoization = new HashMap ();
      }

    @SuppressWarnings("unchecked")
    public Set first (Object e)
      {
        if (!map.containsKey (e))
          // e is a terminal!
          return Collections.singleton (e);
        if (firstMemoization.containsKey (e))
          // already did this.
          return (Set) firstMemoization.get (e);
        List<List<?>> productions = map.get (e);
        Set set = S.et ();
        for (List l : productions)
          {
            if (l.isEmpty ())
              set.add (epsilon);
            else
              {
                set.addAll (first (l));
              }
          }
        firstMemoization.put (e, set);
        return set;
      }
    
    @SuppressWarnings("unchecked")
    public Set first (List l)
      {
        if (firstMemoization.containsKey (l))
          // already did this.
          return (Set) firstMemoization.get (l);
        assert ! l.isEmpty () : "Got asked for the first of an empty production!";
        Set set = S.et ();
        for (int i = 0; i < l.size (); i++)
          {
            set.addAll (first (l.get (i)));
            if (! set.contains (epsilon))
              break;
          }
        firstMemoization.put (l, set);
        return set;
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
                      Set first = l.isEmpty () ? S.et (epsilon) : first (l);
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
