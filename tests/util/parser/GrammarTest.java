package util.parser;

import static org.testng.AssertJUnit.assertEquals;
import static util.Pair.make;

import org.testng.annotations.Test;

import util.L;
import util.S;

@Test
public class GrammarTest
  {
    static enum NT {
      E, Ep, T, Tp, F;
    }
    static enum T {
      PLUS, STAR, LPAREN, RPAREN, ID, EOF, EPSILON;
    }
    
    public void example ()
      {
        Grammar p = new Grammar (make (NT.E, L.ist (NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist (T.PLUS, NT.T, NT.Ep)),
                                 make (NT.Ep, L.ist ()),
                                 make (NT.T, L.ist (NT.F, NT.Tp)),
                                 make (NT.Tp, L.ist (T.STAR, NT.F, NT.Tp)),
                                 make (NT.Tp, L.ist ()),
                                 make (NT.F, L.ist (T.LPAREN, NT.E, T.RPAREN)),
                                 make (NT.F, L.ist (T.ID)));
        p.setEOFToken (T.EOF);
        p.setEpsilonToken (T.EPSILON);
        p.setStartSymbol (NT.E);
        assertEquals (S.et (T.LPAREN, T.ID), p.first (NT.E));
        assertEquals (S.et (T.PLUS, T.EPSILON), p.first (NT.Ep));
        assertEquals (S.et (T.LPAREN, T.ID), p.first (NT.T));
        assertEquals (S.et (T.STAR, T.EPSILON), p.first (NT.Tp));
        assertEquals (S.et (T.LPAREN, T.ID), p.first (NT.F));

        assertEquals (S.et (T.EOF, T.RPAREN), p.follow (NT.E));
        assertEquals (S.et (T.EOF, T.RPAREN), p.follow (NT.Ep));
        assertEquals (S.et (T.PLUS, T.EOF, T.RPAREN), p.follow (NT.T));
        assertEquals (S.et (T.PLUS, T.EOF, T.RPAREN), p.follow (NT.Tp));
        assertEquals (S.et (T.STAR, T.PLUS, T.EOF, T.RPAREN), p.follow (NT.F));
      }
  }
