package util.parser;

import static org.testng.AssertJUnit.*;
import static util.Pair.make;

import java.util.*;

import org.testng.annotations.*;

import util.*;
import util.parser.GrammarTest.NT;
import util.parser.GrammarTest.T;

@Test
public class SemanticTest
  {
    private static <U> Set<Pair<Closure<Boolean>,U>> makeSet (U... tokens)
      {
        Set<Pair<Closure<Boolean>,U>> set = new HashSet<Pair<Closure<Boolean>,U>> ();
        for (U token : tokens)
          set.add (Pair.make ((Closure<Boolean>) null, token));
        return set;
      }
    
    @SuppressWarnings ("unchecked")
    public void semanticAction ()
      {
        final int i[] = { 0 };
        Closure sem = new Closure<Object> ()
          {
            public Object apply ()
              {
                i[0]++;
                return null;
              }
          };
        Grammar g = new Grammar (make (NT.E, L.ist (NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist (sem, T.PLUS, NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist (sem)),
                                 make (NT.T, L.ist (NT.F, NT.Tp)),
                                 make (NT.Tp, L.ist (T.STAR, NT.F, NT.Tp)),
                                 make (NT.Tp, L.ist ()), make (NT.F,
                                                               L.ist (T.LPAREN,
                                                                      NT.E,
                                                                      T.RPAREN)),
                                                                      make (NT.F, L.ist (T.ID)));
        g.setEOFToken (T.EOF);
        g.setEpsilonToken (T.EPSILON);
        g.setStartSymbol (NT.E);
        assertEquals (makeSet (T.EPSILON), g.first (sem));
        assertEquals (makeSet (T.PLUS), g.first (L.ist (sem, T.PLUS, NT.T, NT.Ep)));
        assertEquals (makeSet (T.PLUS, T.EPSILON), g.first (NT.Ep));
        assertEquals (makeSet (T.EOF, T.RPAREN), g.follow (NT.Ep));
        Table<NT, T, List<?>> table = new Table<NT, T, List<?>> (g);
        assertEquals (makeSet (L.ist (sem)), table.get (NT.Ep, T.RPAREN));
        assertEquals (makeSet (L.ist (sem, T.PLUS, NT.T, NT.Ep)), table.get (NT.Ep, T.PLUS));
        Parser<NT, T> parser = new Parser<NT, T> (g);
        assertEquals (0, i[0]);
        parser.witness (T.ID, T.PLUS, T.ID, T.EOF);
        assertEquals (2, i[0]);
      }

    @SuppressWarnings ("unchecked")
    public void semanticPredicate ()
      {
        final int i[] = { 0 };
        SemanticPredicate sem = new SemanticPredicate ()
          {
            public Boolean apply ()
              {
                return i[0] > 0;
              }
          };
        SemanticPredicate inv = Grammar.converse (sem);
        Closure act = new Closure<Object> ()
          {
            public Object apply ()
              {
                i[0]++;
                return null;
              }
          };
        Grammar g = new Grammar (make (NT.E, L.ist (NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist (inv, T.PLUS, act, NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist (sem)),
                                 make (NT.T, L.ist (NT.F, NT.Tp)),
                                 make (NT.Tp, L.ist (T.STAR, NT.F, NT.Tp)),
                                 make (NT.Tp, L.ist ()), make (NT.F,
                                                               L.ist (T.LPAREN,
                                                                      NT.E,
                                                                      T.RPAREN)),
                                                                      make (NT.F, L.ist (T.ID)));
        g.setEOFToken (T.EOF);
        g.setEpsilonToken (T.EPSILON);
        g.setStartSymbol (NT.E);
        assertEquals (S.et (Pair.make (sem, T.EPSILON)), g.first (sem));
        assertEquals (S.et (Pair.make (inv, T.PLUS)), g.first (L.ist (inv, T.PLUS, act, NT.T, NT.Ep)));
        assertEquals (S.et (Pair.make (inv, T.PLUS), Pair.make (sem, T.EPSILON)), g.first (NT.Ep));
        assertEquals (S.et (Pair.make (null, T.EOF), Pair.make (null, T.RPAREN)), g.follow (NT.Ep));
        Table<NT, T, List<?>> table = new Table<NT, T, List<?>> (g);
        assertEquals (S.et (Pair.make (sem, L.ist (sem))), table.get (NT.Ep, T.RPAREN));
        assertEquals (S.et (Pair.make (inv, L.ist (inv, T.PLUS, act, NT.T, NT.Ep))), table.get (NT.Ep, T.PLUS));
        Parser<NT, T> parser = new Parser<NT, T> (g);
        i[0] = 0;
        parser.witness (T.ID, T.PLUS, T.ID, T.EOF);
        assertEquals (1, i[0]);
        i[0] = 0;
        parser.reset ();
        try
          {
            parser.witness (T.ID, T.EOF);
            fail ("Semantic predicate passed when it shouldn't have!");
          }
        catch (Parser.ParseException e) {}
      }
  }
