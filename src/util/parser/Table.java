package util.parser;

import java.util.*;

public class Table<NT,T,V>
{
  Map<NT, Map<T, HashSet<V>>> table;
  
  @SuppressWarnings("unchecked")
  public Table ()
    {
      table = new HashMap<NT,Map<T,HashSet<V>>> ();
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