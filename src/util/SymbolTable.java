package util;

import java.util.*;

public class SymbolTable implements Map {
    public boolean isEmpty () { return true; }
    public int size () { return 0; }
    public boolean containsKey(Object key) { return false; }
    public boolean containsValue(Object value) { return false; }
    public Object get(Object key) { return null; }
    public Object put(Object key, Object value) { return null; }
    public Object remove(Object key) { return null; }
    public void putAll(Map map) {}
    public void clear() {}
    public Set keySet() { return Collections.emptySet (); }
    public Collection values() { return Collections.emptySet (); }
    public Set entrySet() { return Collections.emptySet (); }
}