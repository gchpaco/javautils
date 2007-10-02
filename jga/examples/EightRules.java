// ============================================================================
// $Id: EightRules.java,v 1.4 2005/08/02 23:45:05 davidahall Exp $
// Copyright (c) 2005  David A. Hall
// ============================================================================
// The contents of this file are subject to the Common Development and
// Distribution License (CDDL), Version 1.0 (the License); you may not use this 
// file except in compliance with the License.  You should have received a copy
// of the the License along with this file: if not, a copy of the License is 
// available from Sun Microsystems, Inc.
//
// http://www.sun.com/cddl/cddl.html
//
// From time to time, the license steward (initially Sun Microsystems, Inc.) may
// publish revised and/or new versions of the License.  You may not use,  
// distribute, or otherwise make this file available under subsequent versions 
// of the License.
// 
// Alternatively, the contents of this file may be used under the terms of the 
// GNU Lesser General Public License Version 2.1 or later (the "LGPL"), in which
// case the provisions of the LGPL are applicable instead of those above. If you 
// wish to allow use of your version of this file only under the terms of the 
// LGPL, and not to allow others to use your version of this file under the 
// terms of the CDDL, indicate your decision by deleting the provisions above 
// and replace them with the notice and other provisions required by the LGPL. 
// If you do not delete the provisions above, a recipient may use your version 
// of this file under the terms of either the CDDL or the LGPL.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
// ============================================================================

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.sf.jga.fn.UnaryFunctor;
import net.sf.jga.fn.adaptor.AndUnary;
import net.sf.jga.fn.adaptor.OrUnary;
import net.sf.jga.fn.algorithm.ElementOf;
import net.sf.jga.fn.comparison.Between;
import net.sf.jga.fn.comparison.EqualTo;
import net.sf.jga.fn.comparison.Greater;
import net.sf.jga.fn.comparison.GreaterEqual;
import net.sf.jga.fn.comparison.Less;
import net.sf.jga.fn.comparison.LessEqual;
import net.sf.jga.fn.comparison.NotEqualTo;
import net.sf.jga.fn.logical.All;
import net.sf.jga.fn.logical.LogicalNot;
import net.sf.jga.fn.string.DefaultFormat;
import net.sf.jga.fn.string.Match;
import net.sf.jga.fn.logical.Any;

/**
 * An implementation of Chakra
 * Yadavalli's<a href="http://www.jroller.com/page/cyblogue/weblog?anchor=8_simple_rules">8 Simple Rules: Java Generics</a>.  All of Chakra's static build methods are reproduced here as a sample facade
 * that eases the use and learning of jga.
 * <p>
 * Chakra's entry proposes a simplification of functors, based primarily on two items: 1) Ignoring
 * all forms other than a Unary form, thus requiring all functors to evaluate a single argument,
 * and 2) combining logically related functors into a single, multipurpose implementation that
 * tests flags set at construction.  Both of these desires stem from (I believe) a desire to present
 * an interface that is easier to master by being more approachable.  Having much less functionality
 * means that there is far less documentation to work though and the unary form does provide better
 * support for many of the initial uses of functors.
 * <p>
 * Chakra's Rule implementation is basically a UnaryPredicate: in jga, Predicate is losing relevence.
 * In most of the cases that Chakra proposes, the jga functor equivalent is a BinaryFunctor rather
 * than a UnaryFunctor: to achieve the equivalent usage for the eight rules, the testing value is
 * bound to the second argument of a BinaryFunctor, returning a UnaryFunctor.
 * <p>
 * For example: Chakra's first 'rule' was the InRule, which tests its argument for membership in a
 * collection with which the rule was constructed.  The jga equivalent is the <tt>ElementOf</tt>
 * functor, which is a binary form.  To build a unary form that is equivalent to Chakra's, simply
 * build an <tt>ElementOf</tt> functor and bind its second argument to the appropriate collection,
 * as in:
 * <pre>
 *     new ElementOf<T>().bind2nd(collection);
 * </pre>
 * Many of the binary forms have variations that yield more flexibility than is currently
 * implemented in Chakra's rules: typically, you may vary the test's implementation by providing
 * a functor that provides the desired semantics.
 * 
 * Copyright &copy; 2005  David A. Hall
 * @author <a href="mailto:davidahall@users.sf.net">David A. Hall</a>
 */

public class EightRules {
    private EightRules (){}

    // InRule

    /**
     * Returns a predicate that tests an object for membership in the given array, using
     * the argument's <tt>equals()</tt> method.  The jga equivalent of this rule is the
     * <tt>ElementOf</tt> functor.
     */
     
    static public <T> UnaryFunctor<T,Boolean> inRule(T... arr) {
        return new ElementOf<T>().bind2nd(Arrays.asList(arr));
    }


    /**
     * Returns a predicate that tests an object for membership in the given collection, using
     * the argument's <tt>equals()</tt> method.  The jga equivalent of this rule is the
     * <tt>ElementOf</tt> functor.
     */
     
    static public <T> UnaryFunctor<T,Boolean> inRule(Collection<? extends T> values) {
        return new ElementOf<T>().bind2nd(values);
    }

    
    // ComparableRule

    /**
     * Returns a predicate that tests a Comparable object against a constant value using
     * the arguments <tt>compareTo</tt> method.  The jga equivalent of this rule is the
     * <tt>Less.Comparable</tt> functor.
     */
     
    static public <T extends Comparable<? super T>> UnaryFunctor<T,Boolean> lessThan(T value){
        return new Less.Comparable<T>().bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a constant value using
     * the arguments <tt>compareTo</tt> method.  The jga equivalent of this rule is the
     * <tt>LessEqual.Comparable</tt> functor.
     */
     
    static public <T extends Comparable<? super T>> UnaryFunctor<T,Boolean>
    lessThanOrEquals(T value)
    {
        return new LessEqual.Comparable<T>().bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a constant value using
     * the arguments <tt>compareTo</tt> method.  The jga equivalent of this rule is the
     * <tt>Greater.Comparable</tt> functor.
     */
     
    static public <T extends Comparable<? super T>> UnaryFunctor<T,Boolean> greaterThan(T value){
        return new Greater.Comparable<T>().bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a constant value using
     * the arguments <tt>compareTo</tt> method.  The jga equivalent of this rule is the
     * <tt>GreaterEqual.Comparable</tt> functor.
     */
     
    static public <T extends Comparable<? super T>> UnaryFunctor<T,Boolean> greaterThanOrEquals(T value){
        return new GreaterEqual.Comparable<T>().bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a constant value using
     * the arguments <tt>compareTo</tt> method.  The jga equivalent of this rule is the
     * <tt>GreaterEqual.Comparable</tt> functor.
     */
     
    static public <T extends Comparable<? super T>> UnaryFunctor<T,Boolean> equals(T value){
        return new EqualTo<T>().bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a constant value using
     * the arguments <tt>compareTo</tt> method.  The jga equivalent of this rule is the
     * <tt>GreaterEqual.Comparable</tt> functor.
     */
     
    static public <T extends Comparable<? super T>> UnaryFunctor<T,Boolean> notEquals(T value){
        return new NotEqualTo<T>().bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a pair of constant
     * values using the arguments <tt>compareTo</tt> method.  The jga equivalent of
     * this rule is the <tt>Between</tt> functor.
     */

    static public <T extends Comparable<? super T>> UnaryFunctor<T,Boolean>
    between(T lower, T upper, boolean includeBounds)
    {
        return includeBounds ? between(lower, upper) : betweenExcludeBounds(lower, upper);
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a pair of constant
     * values using the arguments <tt>compareTo</tt> method.  The jga equivalent of
     * this rule is the <tt>Between</tt> functor.
     */

    static public <T extends Comparable<? super T>> UnaryFunctor<T,Boolean>
    between(T lower, T upper)
    {
        return new Between<T>(new GreaterEqual.Comparable<T>().bind2nd(lower),
                              new LessEqual.Comparable<T>().bind2nd(upper));
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a pair of constant
     * values using the arguments <tt>compareTo</tt> method.  The jga equivalent of
     * this rule is the <tt>Between</tt> functor.
     */

    static public <T extends Comparable<? super T>> UnaryFunctor<T,Boolean>
    betweenExcludeBounds(T lower, T upper)
    {
        return new Between<T>(new Greater.Comparable<T>().bind2nd(lower),
                              new Less.Comparable<T>().bind2nd(upper));
    }

    
    // Comparator Rule

    /**
     * Returns a predicate that tests an object against a constant value using the given
     * Comparator.  The jga equivalent of this rule is the <tt>Less</tt> functor.
     */
     
    static public <T> UnaryFunctor<T,Boolean> lessThan(T value, Comparator<? super T> comp) {
        return new Less<T>(comp).bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests an object against a constant value using the given
     * Comparator.  The jga equivalent of this rule is the <tt>LessEqual</tt> functor.
     */
     
    static public <T> UnaryFunctor<T,Boolean> lessThanOrEquals(T value, Comparator<? super T> comp) {
        return new LessEqual<T>(comp).bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests an object against a constant value using the given
     * Comparator.  The jga equivalent of this rule is the <tt>Greater</tt> functor.
     */
     
    static public <T> UnaryFunctor<T,Boolean> greaterThan(T value, Comparator<? super T> comp) {
        return new Greater<T>(comp).bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests an object against a constant value using the given
     * Comparator.  The jga equivalent of this rule is the <tt>GreaterEqual</tt> functor.
     */
     
    static public <T> UnaryFunctor<T,Boolean>
    greaterThanOrEquals(T value, Comparator<? super T> comp)
    {
        return new GreaterEqual<T>(comp).bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests an object against a constant value using the given
     * Comparator.  The jga equivalent of this rule is the <tt>EqualTo</tt> functor.
     */
     
    static public <T> UnaryFunctor<T,Boolean> equals(T value, Comparator<? super T> comp) {
        return new EqualTo<T>(comp).bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests an object against a constant value using the given
     * Comparator.  The jga equivalent of this rule is the <tt>NotEqualTo</tt> functor.
     */
     
    static public <T> UnaryFunctor<T,Boolean> notEquals(T value, Comparator<? super T> comp) {
        return new NotEqualTo<T>(comp).bind2nd(value);
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a pair of constant
     * values using the given Comparator.  The jga equivalent of this rule is the
     * <tt>Between</tt> functor.
     */

    static public <T> UnaryFunctor<T,Boolean>
    between(T lower, T upper, Comparator<? super T> comp, boolean includeBounds)
    {
        return includeBounds ? between(lower,upper,comp) : betweenExcludeBounds(lower,upper,comp);
        
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a pair of constant
     * values using the given Comparator.  The jga equivalent of this rule is the
     * <tt>Between</tt> functor.
     */

    static public <T> UnaryFunctor<T,Boolean>
    between(T lower, T upper, Comparator<? super T> comp)
    {
        return new Between<T>(new GreaterEqual<T>(comp).bind2nd(lower),
                              new LessEqual<T>(comp).bind2nd(upper));
    }

    
    /**
     * Returns a predicate that tests a Comparable object against a pair of constant
     * values using the given Comparator.  The jga equivalent of this rule is the
     * <tt>Between</tt> functor.
     */

    static public <T> UnaryFunctor<T,Boolean>
    betweenExcludeBounds(T lower, T upper, Comparator<? super T> comp)
    {
        return new Between<T>(new Greater<T>(comp).bind2nd(lower), new Less<T>(comp).bind2nd(upper));
    }

    
    // Logical rules

    /**
     * Returns a predicate that tests the logical negation of a subpredicate.  The jga
     * equivalent of this rule is the<tt>LogicalNot</tt> functor.
     */

    static public <T> UnaryFunctor<T,Boolean> not(UnaryFunctor<T,Boolean> rule) {
        return new LogicalNot().compose(rule);
    }

    
    /**
     * Returns a predicate that tests two subpredicates using an <i>and</i> conjunction.  The jga
     * equivalent of this rule is the<tt>AndUnary</tt> functor.
     */

    static public <T> UnaryFunctor<T,Boolean>
    and(UnaryFunctor<T,Boolean> rule1, UnaryFunctor<T,Boolean> rule2)
    {
        return new AndUnary<T>(rule1, rule2);
    }

    
    /**
     * Returns a predicate that tests two subpredicates using an <i>or</i> conjunction.  The jga
     * equivalent of this rule is the<tt>OrUnary</tt> functor.
     */

    static public <T> UnaryFunctor<T,Boolean>
    or(UnaryFunctor<T,Boolean> rule1, UnaryFunctor<T,Boolean> rule2)
    {
        return new OrUnary<T>(rule1, rule2);
    }

    
    /**
     * Returns a predicate that tests two subpredicates using an <i>or</i> conjunction.  The jga
     * equivalent is a compound boolean functor.
     */

    static public <T> UnaryFunctor<T,Boolean>
    xor(UnaryFunctor<T,Boolean> rule1, UnaryFunctor<T,Boolean> rule2)
    {
        // oops - no logical xor operation -- have to hack one together
        return and(or(rule1, rule2), not(and(rule1, rule2)));
    }

    
    // Regex rule

    /**
     * Returns a predicate that returns true if a tested string matches all of the regular
     * expressions in the given list.  The jga equivalent of this rule is the <tt>All</tt>
     * functor, constructed with a list of <tt>Match</tt> functors.
     */

    static public <T> UnaryFunctor<T,Boolean> matchAll(String... regex){
        return new All<T>(EightRules.<T>makeRegexList(regex));
    }

    
    /**
     * Returns a predicate that returns true if a tested string matches any of the regular
     * expressions in the given list.  The jga equivalent of this rule is the <tt>All</tt>
     * functor, constructed with a list of <tt>Match</tt> functors.
     */

    static public <T> UnaryFunctor<T,Boolean> matchAny(String... regex){
        return new Any<T>(EightRules.<T>makeRegexList(regex));
    }

    
    /**
     * Returns a predicate that returns true if a tested string matches any of the regular
     * expressions in the given list.  The jga equivalent of this rule is a negation that
     * flips the All test.
     */

    static public <T> UnaryFunctor<T,Boolean> matchNone(String... regex){
        return not(EightRules.<T>matchAny(regex));
    }

    
    static private <T> List<UnaryFunctor<T,Boolean>> makeRegexList(String... regexList) {
        List<UnaryFunctor<T,Boolean>> list = new ArrayList<UnaryFunctor<T,Boolean>>();
        
        for (String regex : regexList) {
            list.add(new Match(regex).compose(new DefaultFormat<T>()));
        }

        return list;
    }
}
