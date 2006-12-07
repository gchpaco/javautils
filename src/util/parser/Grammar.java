package util.parser;

import java.util.*;

import util.*;

public class Grammar
  {
    @SuppressWarnings("unchecked")
    private final Pair<Enum,List<?>>[] symbols;
    @SuppressWarnings("unchecked")
    private Enum eof;
    @SuppressWarnings("unchecked")
    private Enum epsilon;
    @SuppressWarnings("unchecked")
    private EnumMap map;
    @SuppressWarnings("unchecked")
    private Enum start;

    @SuppressWarnings({ "unchecked", "cast" })
    public Grammar (Pair... symbols)
      {
        this.symbols = symbols;
        map = new EnumMap ((Class<Enum>) symbols[0].first.getClass ());
        for (Pair p : symbols)
          {
            if (map.containsKey (p.first))
              ((List) map.get (p.first)).add (p.second);
            else
              map.put (p.first, L.ist (p.second));
          }
        firstMemoization = new HashMap ();
      }
    
    @SuppressWarnings("unchecked")
    private Map firstMemoization;
    @SuppressWarnings("unchecked")
    private Map followTable;

    @SuppressWarnings("unchecked")
    public Set first (Object e)
      {
        if (!map.containsKey (e))
          // e is a terminal!
          return Collections.singleton (e);
        if (firstMemoization.containsKey (e))
          // already did this.
          return (Set) firstMemoization.get (e);
        List<List> productions = (List<List>) map.get (e);
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
    public Set follow (Enum e)
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
        Class<? extends Enum> enumclass = symbols[0].first.getClass ();
        followTable = new EnumMap (enumclass);
        Enum[] enumConstants = enumclass.getEnumConstants ();
        assert enumConstants != null;
        for (Enum e : enumConstants)
          followTable.put (e, S.et ());
        ((Set) followTable.get (start)).add (eof);
        // this is kind of inefficient.  oh well.
        boolean changed;
        do
          {
            changed = false;
            for (Pair<Enum, List<?>> p : symbols)
              for (int i = 0; i < p.second.size (); i++)
                {
                  Object o = p.second.get (i);
                  if (!enumclass.isInstance (o))
                    continue;
                  Enum e = (Enum) o;
                  List l = p.second.subList (i + 1, p.second.size ());
                  Set first = l.isEmpty () ? S.et (epsilon) : first (l);
                  Set copy = S.et (first);
                  if (copy.remove (epsilon)) // epsilon was in first
                    copy.addAll ((Collection) followTable.get (p.first));
                  Set<?> oldSet = (Set<?>) followTable.get (e);
                  if (oldSet.containsAll (copy))
                    continue;
                  oldSet.addAll (copy);
                  changed = true;
                }
          }
        while (changed);
      }

    @SuppressWarnings("unchecked")
    public void setEOFToken (Enum eof)
      {
        this.eof = eof;
      }

    @SuppressWarnings("unchecked")
    public void setEpsilonToken (Enum epsilon)
      {
        this.epsilon = epsilon;
      }

    @SuppressWarnings("unchecked")
    public void setStartSymbol (Enum s)
      {
        this.start = s;
      }
  }
