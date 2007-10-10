package util.parser;

import util.Closure;

/** Something that represents the internal state of the parser; if it
 * fails then one branch of the parse may be wrong but it is not
 * necessarily a failing of the interface. */
public interface ChoicePredicate extends Closure<Boolean>
  {
  }
