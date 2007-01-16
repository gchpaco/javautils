package util.parser;

import static org.testng.AssertJUnit.*;
import static util.Pair.make;

import java.util.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.*;

@Test
public class GrammarTest
  {
    private Grammar<NT> grammar;

    static enum NT
      {
        E, Ep, T, Tp, F;
      }

    static enum T
      {
        PLUS, STAR, LPAREN, RPAREN, ID, EOF, EPSILON;
      }
    
    private static <U> Set<TokenTag<U>> makeSet (U... tokens)
      {
        Set<TokenTag<U>> set = new HashSet<TokenTag<U>> ();
        for (U token : tokens)
          set.add (TokenTag.make (token));
        return set;
      }

    public void example ()
      {
        assertEquals (makeSet (T.LPAREN, T.ID), grammar.first (NT.E));
        assertEquals (makeSet (T.PLUS, T.EPSILON), grammar.first (NT.Ep));
        assertEquals (makeSet (T.LPAREN, T.ID), grammar.first (NT.T));
        assertEquals (makeSet (T.STAR, T.EPSILON), grammar.first (NT.Tp));
        assertEquals (makeSet (T.LPAREN, T.ID), grammar.first (NT.F));

        assertEquals (makeSet (T.EOF, T.RPAREN), grammar.follow (NT.E));
        assertEquals (makeSet (T.EOF, T.RPAREN), grammar.follow (NT.Ep));
        assertEquals (makeSet (T.PLUS, T.EOF, T.RPAREN), grammar.follow (NT.T));
        assertEquals (makeSet (T.PLUS, T.EOF, T.RPAREN), grammar.follow (NT.Tp));
        assertEquals (makeSet (T.STAR, T.PLUS, T.EOF, T.RPAREN),
                      grammar.follow (NT.F));
      }

    @SuppressWarnings ("unchecked")
    @BeforeTest
    protected void setUp ()
      {
        grammar = new Grammar (make (NT.E, L.ist (NT.T, NT.Ep)),
                               make (NT.Ep, L.ist (T.PLUS, NT.T, NT.Ep)),
                               make (NT.Ep, L.ist ()),
                               make (NT.T, L.ist (NT.F, NT.Tp)),
                               make (NT.Tp, L.ist (T.STAR, NT.F, NT.Tp)),
                               make (NT.Tp, L.ist ()), make (NT.F,
                                                             L.ist (T.LPAREN,
                                                                    NT.E,
                                                                    T.RPAREN)),
                               make (NT.F, L.ist (T.ID)));
        grammar.setEOFToken (T.EOF);
        grammar.setEpsilonToken (T.EPSILON);
        grammar.setStartSymbol (NT.E);
      }

    @SuppressWarnings("unchecked")
    public void tableFormation ()
      {
        Table<NT, T, List<TokenTag<?>>> table = new Table<NT, T, List<TokenTag<?>>> (grammar);
        assertEquals (S.et (L.ist ()), table.get (NT.Ep, T.RPAREN));
        assertEquals (S.et (L.ist (T.PLUS, NT.T, NT.Ep)), table.get (NT.Ep,
                                                                     T.PLUS));
        assertEquals (S.et (), table.get (NT.Ep, T.LPAREN));
        assertEquals (S.et (), table.get (NT.T, T.EOF));
        assertEquals (S.et (L.ist ()), table.get (NT.Ep, T.EOF));
      }
    
    public void parserAutomaton ()
      {
        Parser<NT, T> parser = new Parser<NT, T> (grammar);
        assertEquals (L.ist (NT.E, T.EOF), parser.getStack ());
        parser.witness (T.LPAREN);
        assertEquals (L.ist (NT.E, T.RPAREN, NT.Tp, NT.Ep, T.EOF), parser.getStack ());
        parser.witness (T.ID);
        assertEquals (L.ist (NT.Tp, NT.Ep, T.RPAREN, NT.Tp, NT.Ep, T.EOF), parser.getStack ());
        parser.witness (T.PLUS);
        assertEquals (L.ist (NT.T, NT.Ep, T.RPAREN, NT.Tp, NT.Ep, T.EOF), parser.getStack ());
        parser.witness (T.ID);
        assertEquals (L.ist (NT.Tp, NT.Ep, T.RPAREN, NT.Tp, NT.Ep, T.EOF), parser.getStack ());
        parser.witness (T.STAR);
        assertEquals (L.ist (NT.F, NT.Tp, NT.Ep, T.RPAREN, NT.Tp, NT.Ep, T.EOF), parser.getStack ());
        parser.witness (T.LPAREN);
        assertEquals (L.ist (NT.E, T.RPAREN, NT.Tp, NT.Ep, T.RPAREN, NT.Tp, NT.Ep, T.EOF), parser.getStack ());
        parser.witness (T.ID);
        assertEquals (L.ist (NT.Tp, NT.Ep, T.RPAREN, NT.Tp, NT.Ep, T.RPAREN, NT.Tp, NT.Ep, T.EOF), parser.getStack ());
        parser.witness (T.RPAREN);
        assertEquals (L.ist (NT.Tp, NT.Ep, T.RPAREN, NT.Tp, NT.Ep, T.EOF), parser.getStack ());
        parser.witness (T.RPAREN);
        assertEquals (L.ist (NT.Tp, NT.Ep, T.EOF), parser.getStack ());
        parser.witness (T.EOF);
        assertEquals (L.ist (), parser.getStack ());
      }
    
    public void badToken ()
      {
        Parser<NT, T> parser = new Parser<NT, T> (grammar);
        parser.witness (T.LPAREN, T.ID, T.PLUS);
        try
          {
            parser.witness (T.PLUS);
            fail ("Shouldn't be able to witness two plusses in a row!");
          }
        catch (Exception e) {}
      }

    @SuppressWarnings ("unchecked")
    public void ambiguousParse ()
      {
        Grammar g = new Grammar (make (NT.E, L.ist (NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist (T.PLUS, NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist (T.PLUS)),
                                 make (NT.Ep, L.ist ()),
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
        Parser<NT, T> parser = new Parser<NT, T> (g);
        parser.witness (T.ID);
        try
          {
            parser.witness (T.PLUS);
            fail ("Shouldn't choose between two indistinguishable rules!");
          }
        catch (Exception e) {}
      }
  }
