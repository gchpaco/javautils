package util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SingletonManager {
    public static ConcurrentMap<Class<?>, Object> singletonMap;
    
    static {
        singletonMap = new ConcurrentHashMap<Class<?>, Object>();
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> klass) {
        if (!singletonMap.containsKey(klass)) {
            throw new IllegalArgumentException ("No instance of " + klass.getCanonicalName() + " registered");
        }
        return (T) singletonMap.get(klass);
    }
    
    @SuppressWarnings("unchecked")
    public static <T, U extends T> T register(Class<T> klass, U object) {
        singletonMap.putIfAbsent(klass, object);
        return (T) singletonMap.get(klass);
    }
    
    public static void clear () {
        singletonMap.clear();
    }
}
