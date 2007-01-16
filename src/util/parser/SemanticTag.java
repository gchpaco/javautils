package util.parser;

public class SemanticTag<T> extends TokenTag<T>
  {
    SemanticPredicate pred;
    public SemanticTag (SemanticPredicate sem, T t)
      {
        super (t);
        pred = sem;
      }
    
    @Override
    public String toString ()
      {
        return String.format ("%s<%s>", token, pred);
      }
  }
