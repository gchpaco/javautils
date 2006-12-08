package util.parser;

import static org.testng.AssertJUnit.assertEquals;
import static util.Pair.make;

import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.L;
import util.S;

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

    public void example ()
      {
        assertEquals (S.et (T.LPAREN, T.ID), grammar.first (NT.E));
        assertEquals (S.et (T.PLUS, T.EPSILON), grammar.first (NT.Ep));
        assertEquals (S.et (T.LPAREN, T.ID), grammar.first (NT.T));
        assertEquals (S.et (T.STAR, T.EPSILON), grammar.first (NT.Tp));
        assertEquals (S.et (T.LPAREN, T.ID), grammar.first (NT.F));

        assertEquals (S.et (T.EOF, T.RPAREN), grammar.follow (NT.E));
        assertEquals (S.et (T.EOF, T.RPAREN), grammar.follow (NT.Ep));
        assertEquals (S.et (T.PLUS, T.EOF, T.RPAREN), grammar.follow (NT.T));
        assertEquals (S.et (T.PLUS, T.EOF, T.RPAREN), grammar.follow (NT.Tp));
        assertEquals (S.et (T.STAR, T.PLUS, T.EOF, T.RPAREN),
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
        Table<NT, T, List<?>> table = new Table<NT, T, List<?>> (grammar);
        assertEquals (S.et (L.ist ()), table.get (NT.Ep, T.RPAREN));
        assertEquals (S.et (L.ist (T.PLUS, NT.T, NT.Ep)), table.get (NT.Ep,
                                                                     T.PLUS));
        assertEquals (S.et (), table.get (NT.Ep, T.LPAREN));
        assertEquals (S.et (), table.get (NT.T, T.EOF));
      }
  }
