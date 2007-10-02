package util;

import java.util.*;

public class SymbolTable<T, U> implements Map<T, U>
{
  LinkedList<HashMap<T, U>> stack;

  public SymbolTable ()
    {
      clear ();
    }

  public boolean isEmpty ()
    {
      boolean value = true;
      for (HashMap<T, U> map : stack)
        {
          value &= map.isEmpty ();
        }
      return value;
    }

  public int size ()
    {
      int value = 0;
      for (HashMap<T, U> map : stack)
        {
          value += map.size ();
        }
      return value;
    }

  public boolean containsKey (Object key)
    {
      for (HashMap<T, U> map : stack)
        if (map.containsKey (key)) return true;
      return false;
    }

  public boolean containsValue (Object value)
    {
      for (HashMap<T, U> map : stack)
        if (map.containsValue (value)) return true;
      return false;
    }

  public U get (Object key)
    {
      for (HashMap<T, U> map : stack)
        if (map.containsKey (key)) return map.get (key);
      throw new IllegalArgumentException ("unbound key " + key);
    }

  public U put (T key, U value)
    {
      for (HashMap<T, U> map : stack)
        if (map.containsKey (key)) return map.put (key, value);
      throw new IllegalArgumentException ("unbound key " + key);
    }

  public void bind (T key)
    {
      stack.getFirst ().put (key, null);
    }

  public void push ()
    {
      stack.addFirst (new HashMap<T, U> ());
    }

  public void pop ()
    {
      if (stack.size () == 1)
        throw new IllegalStateException ("too many pops");
      stack.removeFirst ();
    }

  public U remove (Object key)
    {
      for (HashMap<T, U> map : stack)
        if (map.containsKey (key)) return map.remove (key);
      throw new IllegalArgumentException ("unbound key " + key);
    }

  public void putAll (Map<? extends T, ? extends U> map)
    {
      for (Map.Entry<? extends T, ? extends U> entry : map.entrySet ())
        {
          put (entry.getKey (), entry.getValue ());
        }
    }

  public void clear ()
    {
      stack = new LinkedList<HashMap<T, U>> ();
      push ();
    }

  public Set<T> keySet ()
    {
      Set<T> s = new HashSet<T> ();
      for (HashMap<T, U> map : stack)
        s.addAll (map.keySet ());
      return s;
    }

  public Collection<U> values ()
    {
      ArrayList<U> s = new ArrayList<U> ();
      for (HashMap<T, U> map : stack)
        s.addAll (map.values ());
      return s;
    }

  public Set<Map.Entry<T, U>> entrySet ()
    {
      Set<Map.Entry<T, U>> s = new HashSet<Map.Entry<T, U>> ();
      for (HashMap<T, U> map : stack)
        s.addAll (map.entrySet ());
      return s;
    }
}