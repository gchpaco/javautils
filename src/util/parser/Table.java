package util.parser;

import java.util.*;

public class Table<NT extends Enum<NT>,T extends Enum<T>,V>
{
  EnumMap<NT, EnumMap<T, HashSet<V>>> table;
  NT start;
  T eof;
  
  @SuppressWarnings("unchecked")
  public Table (NT start, T eof)
    {
      this.start = start;
      this.eof = eof;
      table = new EnumMap<NT,EnumMap<T,HashSet<V>>> ((Class<NT>) start.getClass ());
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
        table.put (a, new EnumMap<T,HashSet<V>> ((Class<T>) eof.getClass ()));
      if (!table.get (a).containsKey (b))
        table.get (a).put (b, new HashSet<V> ());
      table.get (a).get (b).add (v);
    }
}