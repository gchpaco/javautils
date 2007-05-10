package util;

import java.util.*;

public class SymbolTable implements Map {
    HashMap base = new HashMap ();
    public boolean isEmpty () { return base.isEmpty (); }
    public int size () { return base.size (); }
    public boolean containsKey(Object key) { return base.containsKey (key); }
    public boolean containsValue(Object value) { return base.containsValue (value); }
    public Object get(Object key) { return base.get (key); }
    public Object put(Object key, Object value) { return base.put (key, value); }
    public void bind(Object key) {}
    public Object remove(Object key) { return base.remove (key); }
    public void putAll(Map map) {}
    public void clear() { base.clear (); }
    public Set keySet() { return base.keySet (); }
    public Collection values() { return base.values (); }
    public Set entrySet() { return base.entrySet (); }
}