package util.parser;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.sf.jga.fn.Generator;
import net.sf.jga.fn.UnaryFunctor;
import util.Pair;

public class Parser<NT, T>
{
  @SuppressWarnings ("serial")
  static class ParseException extends RuntimeException
  {
    public ParseException ()
      {
        super ();
      }

    public ParseException (String message, Throwable cause)
      {
        super (message, cause);
      }

    public ParseException (String message)
      {
        super (message);
      }

    public ParseException (Throwable cause)
      {
        super (cause);
      }
  }

  Table<NT, T, List<?>> table;
  LinkedList<Object> stack;
  LinkedList<T> lookahead;
  NT start;
  T eof;

  @SuppressWarnings ("unchecked")
  public Parser (Grammar<NT, T> grammar)
    {
      this.table = new Table<NT, T, List<?>> (grammar);
      this.start = grammar.getStartSymbol ();
      this.eof = grammar.getEOFToken ();
      reset ();
    }

  @SuppressWarnings ("unchecked")
  public Parser (Table<NT, T, List<?>> table, NT start, T eof)
    {
      this.table = table;
      this.start = start;
      this.eof = eof;
      reset ();
    }

  public void reset ()
    {
      this.stack = new LinkedList<Object> ();
      stack.addLast (start);
      stack.addLast (eof);
      this.lookahead = new LinkedList<T> ();
    }

  public void tossUntil (T token)
    {
      while (!stack.isEmpty () && !stack.peek ().equals (token))
        stack.removeFirst ();
      if (!stack.isEmpty ()) stack.removeFirst ();
    }

  public void witness (final T token)
    {
      lookahead.addFirst (token);
      parserLoop (
                  token,
                  new UnaryFunctor<NT, Collection<Pair<SemanticPredicate, List<?>>>> ()
                    {
                      @Override
                      public Collection<Pair<SemanticPredicate, List<?>>> fn (
                                                                              NT t)
                        {
                          return table.get (t, lookahead.getFirst ());
                        }
                    });
      assert lookahead.getFirst () == token;
      lookahead.removeFirst ();
    }

  public void waitFor (final T token)
    {
      parserLoop (
                  token,
                  new UnaryFunctor<NT, Collection<Pair<SemanticPredicate, List<?>>>> ()
                    {
                      @Override
                      public Collection<Pair<SemanticPredicate, List<?>>> fn (
                                                                              NT t)
                        {
                          return table.getRow (t);
                        }
                    });
    }

  private void parserLoop (
                           T stopAt,
                           UnaryFunctor<NT, Collection<Pair<SemanticPredicate, List<?>>>> unaryFunctor)
    {
      while (!stack.peek ().equals (stopAt))
        parserGuts (unaryFunctor);
      stack.removeFirst ();
    }

  @SuppressWarnings ("unchecked")
  private void parserGuts (
                           UnaryFunctor<NT, Collection<Pair<SemanticPredicate, List<?>>>> rowGenerator)
    {
      Object obj = stack.removeFirst ();
      if (obj instanceof SemanticPredicate)
        {
          if (!checkPredicate ((SemanticPredicate) obj))
            throw new ParseException ("Semantic predicate " + obj +
                                      " failed during execution");
        }
      else if (obj instanceof Generator)
        {
          Generator<?> cls = (Generator<?>) obj;
          cls.fn ();
        }
      else
        {
          NT top = (NT) obj;
          Collection<Pair<SemanticPredicate, List<?>>> row =
              rowGenerator.fn (top);
          stack.addAll (0, choosePossibility (row, top));
        }
    }

  protected boolean checkPredicate (SemanticPredicate pred)
    {
      return pred.fn ();
    }

  protected List<?> choosePossibility (
                                       Collection<Pair<SemanticPredicate, List<?>>> possibilities,
                                       NT top)
    {
      List<?> candidate = null;
      for (Pair<SemanticPredicate, List<?>> possibility : possibilities)
        if (possibility.first == null || possibility.first.fn ())
          {
            if (candidate != null)
              if (lookahead.isEmpty ())
                throw new ParseException ("Ambiguous parse in state " + top +
                                          " (no lookahead)");
              else
                throw new ParseException ("Ambiguous parse for " +
                                          lookahead.getFirst () + " in state " +
                                          top);
            candidate = possibility.second;
          }
      if (candidate == null)
        if (lookahead.isEmpty ())
          throw new ParseException ("No legal parse in state " + top +
                                    " (no lookahead)");
        else
          throw new ParseException ("No legal parse for " +
                                    lookahead.getFirst () + " in state " + top);
      return candidate;
    }

  public void witness (T... tokens)
    {
      for (T token : tokens)
        witness (token);
    }

  public List<Object> getStack ()
    {
      return stack;
    }
}
