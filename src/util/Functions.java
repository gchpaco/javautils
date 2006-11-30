package util;

public final class Functions
  {
    static public <T,U> Function<T,U> constant (final T t) {
      return new Function<T,U> () {
        public T apply (U arg) { return t; }
      };
    }
  }
