package util.parser;

import util.Closure;

/** Something that represents the contract of the interface;
 * accordingly if it fails an exception should be thrown. */
public interface SemanticPredicate extends Closure<Boolean>
  {
  }
