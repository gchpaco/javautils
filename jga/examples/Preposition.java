// ============================================================================
// $Id: Preposition.java,v 1.5 2006/12/16 16:48:57 davidahall Exp $
// Copyright (c) 2003-2006  David A. Hall
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

/* Alex */ 
import net.sf.jga.fn.UnaryFunctor;
import static net.sf.jga.algorithms.Filter.*;

/* Neil */
import net.sf.jga.util.FindIterator;

/**
 * A sample facade that presents jga's functionality in the form described in Alex Winston's
 * <a href="http://weblogs.java.net/blog/alexwinston/archive/2005/01/functional_obje.html">Functional
 * objects made easy with tiger</a>.
 * <p>
 * Alex starts with two classes:
 * <pre>
 * public interface Predicate<T> {
 *     public boolean evaluate(T t);
 * }
 *  
 * public interface Closure<T> {
 *     public void execute(T t);
 * }
 *
 * </pre>
 * In jga terms, these are simply <tt>UnaryFunctor&lt;T,Boolean&gt;</tt> (<tt>UnaryPredicate</tt>
 * being all but deprecated) and <tt>UnaryFunctr&lt;T,?&gt;</tt>.
 */

public class Preposition {

    /**
     * Applies the given functor to the argument, returning the result. 
     */
    public static <T> void with(T subject, UnaryFunctor<T,?> c) {
        c.fn(subject);
    }

    
    /**
     * Tests the argument using the predicate, and if the result is true, applies the closure.
     */
    public static <T> void when(T subject, UnaryFunctor<T,Boolean> p, UnaryFunctor<T,?> c) {
        if (p.fn(subject))
            with(subject, c);
    }

    
    /**
     * Applies the closure to all objects in the set.
     */
    public static <T> void foreach(Iterable<T> subjects, UnaryFunctor<T,?> c) {
        for (T subject : subjects)
            with(subject, c);
    }

    
    /**
     * Applies the closure to all objects in the set for which the predicate is true.
     */
    public static <T> void foreach(Iterable<? extends T> subjects, UnaryFunctor<T,Boolean> p,
                                   UnaryFunctor<T,?> c)
    {
        for (T subject : filter(subjects, p)) 
            with(subject, c);
    }
        

    /**
     * Applies the closure to all objects in the array.
     */
    public static <T> void foreach(T[] subjects, UnaryFunctor<T,?> c) {
        for (T subject : subjects)
            with(subject, c);
    }

    /**
     * Applies the closure to all objects in the array for which the predicate is true.
     */
    public static <T> void foreach(T[] subjects, UnaryFunctor<T,Boolean> p,
                                   UnaryFunctor<T,?> c)
    {
        for (T subject : filter(subjects, p)) 
            with(subject, c);
    }

    /**
     * Supports Neil Swingler's proposal to Alex (in a comment to Alex's blog):
     * <cite>
     * Interesting stuff. I use the commons-collections Closure quite a bit so it will
     * be nice when I can use generics etc. to reduce the clutter a bit. How about a
     * builder style a la JMock e.g. instead of
     * <pre>
     *     foreach(C.class.getDeclaredMethods(), isProtected, addMethod)
     * </pre>    
     * use
     * <pre>
     *     foreach(C.class.getDeclaredMethods()).where(isProtected).doo(addMethod);
     * </pre>
     * Posted by: neilswingler on January 19, 2005 at 09:03 AM
     * </cite>
     */
    public static <T> FindX<T> foreach(Iterable<T> iter) {
        return new FindX<T>(iter);
    }

    /**
     * FindIterator that provides an easy build method that produces a FilterIterator
     */

    static public class FindX<T> extends FindIterator<T> {
        /**
         * Builds a FindX iterator for the given source iterator.
         */
        public FindX(Iterable<T> source) {
            super(source.iterator());
        }
     
        /**
         * Wraps this iterator in a FilterX iterator that uses the given predicate.  It
         * is not safe to use this iterator once the FilterX has been built, as it will
         * presumably be used for some purpose and it's use will consume it (and this).
         */
        public FilterX<T> where(UnaryFunctor<T,Boolean> fn) {
            return new FilterX<T>(this, fn);
        }
    }


    /**
     * FilterIterator that allows the elements of the iterator to be passed to an
     * arbitrary functor in a single pass.
     */
    static public class FilterX<T> extends FilterIterator<T> {

        /**
         * Builds the FilterX such that only elements that pass the given test will be
         * visible to users of this iterator.  Elements in the source iterator that do
         * not meet the test will be ignored in all ways by this iterator.
         */
        public FilterX(Iterable<T> source, UnaryFunctor<T,Boolean> fn) {
            super(source.iterator(), fn);
        }

        /**
         * Passes all qualified elements in the iterator to the given functor.  This iterator
         * will be consumed by this operation -- making any further use of this iterator after
         * this method returns will cause a NoSuchElementException to be thrown.
         */
     
        public void doo(UnaryFunctor<T,?> fn) {
            while(hasNext()) 
                fn.fn(next());
        }
    }
}
