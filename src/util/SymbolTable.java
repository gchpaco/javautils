package util;

import java.util.*;

public class SymbolTable implements Map {
    LinkedList<HashMap> stack;
    public SymbolTable () {
        clear ();
    }

    public boolean isEmpty () {
        boolean value = true;
        for (HashMap map : stack) {
            value &= map.isEmpty ();
        }
        return value;
    }
    public int size () {
        int value = 0;
        for (HashMap map : stack) {
            value += map.size ();
        }
        return value;
    }
    public boolean containsKey(Object key) {
        for (HashMap map : stack)
            if (map.containsKey (key))
                return true;
        return false;
    }
    public boolean containsValue(Object value) {
        for (HashMap map : stack)
            if (map.containsValue (value))
                return true;
        return false;
    }
    public Object get(Object key) {
        for (HashMap map : stack)
            if (map.containsKey (key))
                return map.get (key);
        throw new IllegalArgumentException ("unbound key " + key);
    }
    public Object put(Object key, Object value) {
        for (HashMap map : stack)
            if (map.containsKey (key))
                return map.put (key, value);
        throw new IllegalArgumentException ("unbound key " + key);
    }
    public void bind(Object key) {
        stack.getFirst ().put (key, null);
    }
    public void push () {
        stack.addFirst (new HashMap ());
    }
    public void pop () {
        if (stack.size () == 1)
            throw new IllegalStateException ("too many pops");
        stack.removeFirst ();
    }
    public Object remove(Object key) {
        for (HashMap map : stack)
            if (map.containsKey (key))
                return map.remove (key);
        throw new IllegalArgumentException ("unbound key " + key);
    }
    public void putAll(Map map) {
        for (Object o : map.entrySet ()) {
            Map.Entry entry = (Map.Entry) o;
            put (entry.getKey (), entry.getValue ());
        }
    }
    public void clear() {
        stack = new LinkedList<HashMap>();
        push ();
    }
    public Set keySet() {
        Set s = new HashSet ();
        for (HashMap map : stack)
            s.addAll (map.keySet ());
        return s;
    }
    public Collection values() {
        Set s = new HashSet ();
        for (HashMap map : stack)
            s.addAll (map.values ());
        return s;
    }
    public Set entrySet() {
        Set s = new HashSet ();
        for (HashMap map : stack)
            s.addAll (map.entrySet ());
        return s;
    }
}