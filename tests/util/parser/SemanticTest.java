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
    private static <U> Set<TokenTag<U>> makeSet (U... tokens)
      {
        Set<TokenTag<U>> set = new HashSet<TokenTag<U>> ();
        for (U token : tokens)
          set.add (TokenTag.make (token));
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
        assertEquals (S.et (L.ist (sem)), table.get (NT.Ep, T.RPAREN));
        assertEquals (S.et (L.ist (sem, T.PLUS, NT.T, NT.Ep)), table.get (NT.Ep, T.PLUS));
        Parser<NT, T> parser = new Parser<NT, T> (g);
        assertEquals (0, i[0]);
        parser.witness (T.ID, T.PLUS, T.ID, T.EOF);
        assertEquals (2, i[0]);
      }

    @SuppressWarnings ("unchecked")
    @Test(enabled=false)
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
        SemanticPredicate inverted = Grammar.converse (sem);
        Closure act = new Closure<Object> ()
          {
            public Object apply ()
              {
                i[0]++;
                return null;
              }
          };
        Grammar g = new Grammar (make (NT.E, L.ist (NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist (sem, T.PLUS, NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist (inverted, act)),
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
        assertEquals (S.et (new SemanticTag (sem, T.EPSILON)), g.first (sem));
        assertEquals (S.et (new SemanticTag (sem, T.PLUS)), g.first (L.ist (sem, T.PLUS, NT.T, NT.Ep)));
        assertEquals (S.et (new SemanticTag (sem, T.PLUS), new SemanticTag (inverted, T.EPSILON)), g.first (NT.Ep));
        assertEquals (S.et (T.EOF, T.RPAREN), g.follow (NT.Ep));
        Table<NT, T, List<?>> table = new Table<NT, T, List<?>> (g);
        assertEquals (S.et (L.ist (inverted, act)), table.get (NT.Ep, T.RPAREN));
        assertEquals (S.et (L.ist (sem, T.PLUS, NT.T, NT.Ep)), table.get (NT.Ep, T.PLUS));
        Parser<NT, T> parser = new Parser<NT, T> (g);
        fail ("Not done yet");
        assertEquals (0, i[0]);
        parser.witness (T.ID, T.PLUS, T.ID, T.EOF);
        assertEquals (2, i[0]);
      }
  }
