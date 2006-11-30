package util;

public final class Functions
  {
    @SuppressWarnings("unchecked")
    static public Function constant (final Object o)
      {
        return new Function ()
          {
            public Object apply(Object arg)
              {
                return o;
              }
          };
      }
  }
