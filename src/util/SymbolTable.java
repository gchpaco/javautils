package util;

import java.util.*;

public class SymbolTable implements Map {
    HashMap base = new HashMap ();
    int level = 0;

    public boolean isEmpty () { return base.isEmpty (); }
    public int size () { return base.size (); }
    public boolean containsKey(Object key) { return base.containsKey (key); }
    public boolean containsValue(Object value) { return base.containsValue (value); }
    public Object get(Object key) { return base.get (key); }
    public Object put(Object key, Object value) {
        if (!containsKey (key))
            throw new IllegalArgumentException ("unbound key");
        return base.put (key, value);
    }
    public void bind(Object key) {
        base.put (key, null);
    }
    public void push () {
        level++;
    }
    public void pop () {
        if (level == 0)
            throw new IllegalStateException ("too many pops");
    }
    public Object remove(Object key) { return base.remove (key); }
    public void putAll(Map map) {
        for (Object o : map.entrySet ()) {
            Map.Entry entry = (Map.Entry) o;
            put (entry.getKey (), entry.getValue ());
        }
    }
    public void clear() { level = 0; base.clear (); }
    public Set keySet() { return base.keySet (); }
    public Collection values() { return base.values (); }
    public Set entrySet() { return base.entrySet (); }
}