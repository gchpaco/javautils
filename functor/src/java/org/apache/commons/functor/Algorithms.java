/*
 * Copyright 2003,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.functor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.apache.commons.functor.core.NoOp;
import org.apache.commons.functor.core.collection.FilteredIterator;
import org.apache.commons.functor.core.collection.TransformedIterator;
import org.apache.commons.functor.core.composite.ConditionalUnaryProcedure;
import org.apache.commons.functor.core.composite.Not;
import org.apache.commons.functor.core.composite.UnaryNot;
import org.apache.commons.functor.generator.BaseGenerator;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.generator.IteratorToGeneratorAdapter;

/**
 * Utility methods and algorithms for applying functors to {@link Generator}s
 * and {@link Iterator}s.
 * {@link Generator}s also many of these utility methods as instance methods. 
 * The {@link #select select} and {@link #reject reject} methods 
 * return new Generators. This becomes useful for constructing nested expressions. 
 * For example:
 *
 * <pre>
 *      Algorithms.apply(new EachElement(list), func1)
 *          .filter(pred)
 *              .apply(func2)
 *                  .reject(pred2)
 *                      .to();
 * </pre>
 *
 * @since 1.0
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Jason Horman (jason@jhorman.org)
 * @author Rodney Waldhoff
 */
public final class Algorithms {

    /** Public constructor for bean-ish APIs */
    public Algorithms() {        
    }

    public static <T> Collection<T> collect(Iterator<T> iter) {
        return collect(iter,new ArrayList<T>());
    }
    
    public static <T> Collection<T> collect(Iterator<? extends T> iter, Collection<T> col) {
        while(iter.hasNext()) {
            col.add(iter.next());
        }
        return col;
    }
    
    /**
     * {@link ListIterator#set Set} each element of the
     * given {@link ListIterator ListIterator} to
     * the result of applying the 
     * given {@link UnaryFunction UnaryFunction} to
     * its original value.
     */
    public static <T> void transform(ListIterator<T> iter, UnaryFunction<? super T,? extends T> func) {
        while(iter.hasNext()) {
            iter.set(func.evaluate(iter.next()));
        }
    }

    /**
     * {@link Iterator#remove Remove} from the
     * given {@link Iterator Iterator} all elements 
     * that fail to match the
     * given {@link UnaryPredicate UnaryPredicate}.
     * 
     * @see #remove(Iterator,UnaryPredicate)
     */
    public static <T> void retain(Iterator<T> iter, UnaryPredicate<? super T> pred) {
        while(iter.hasNext()) {
            if(!(pred.test(iter.next()))) {
                iter.remove();
            }
        }
    }

    /**
     * {@link Iterator#remove Renmove} from the
     * given {@link Iterator Iterator} all elements 
     * that match the
     * given {@link UnaryPredicate UnaryPredicate}.
     * 
     * @see #retain(Iterator,UnaryPredicate)
     */
    public static <T> void remove(Iterator<T> iter, UnaryPredicate<? super T> pred) {
        while(iter.hasNext()) {
            if(pred.test(iter.next())) {
                iter.remove();
            }
        }        
    }
    
    /**
     * Returns an {@link Iterator} that will apply the given {@link UnaryFunction} to each
     * element when accessed.
     */
    public static final <T,U> Iterator<U> apply(Iterator<T> iter, UnaryFunction<? super T,U> func) {
        return TransformedIterator.transform(iter,func);
    }

    /**
     * Returns a {@link Generator} that will apply the given {@link UnaryFunction} to each
     * generated element.
     */
    public static final <T,U> Generator<U> apply(final Generator<T> gen, final UnaryFunction<? super T,U> func) {
        return new BaseGenerator<U>(gen) {
	    public void run(final UnaryProcedure<? super U> proc) {
            gen.run(
                new UnaryProcedure<T>() {
                    public void run(T obj) {
                        proc.run(func.evaluate(obj));
                    }
                });
            }
        };
    }
    
    /**
     * Equivalent to 
     * <code>{@link #contains(Generator,UnaryPredicate) contains}(new {@link org.apache.commons.functor.generator.IteratorToGeneratorAdapter IteratorToGeneratorAdapter}(iter),pred)</code>.
     */
    public static final <T> boolean contains(Iterator<T> iter, UnaryPredicate<? super T> pred) {
        return contains(new IteratorToGeneratorAdapter<T>(iter),pred);
    }

    /**
     * Return <code>true</code> if some element in the given {@link Generator}
     * that matches the given {@link UnaryPredicate UnaryPredicate}.
     *
     * @see #detect(Generator,UnaryPredicate)
     */
    public static final <T> boolean contains(Generator<T> gen, UnaryPredicate<? super T> pred) {
        FindWithinGenerator<T> finder = new FindWithinGenerator<T>(gen,pred);
        gen.run(finder);
        return finder.wasFound();
    }

    /**
     * Equivalent to <code>{@link #detect(Generator,UnaryPredicate) detect}(new {@link org.apache.commons.functor.generator.IteratorToGeneratorAdapter IteratorToGeneratorAdapter}(iter),pred)</code>.
     */
    public static final <T> T detect(Iterator<T> iter, UnaryPredicate<? super T> pred) {
        return detect(new IteratorToGeneratorAdapter<T>(iter), pred);
    }

    /**
     * Equivalent to <code>{@link #detect(Generator,UnaryPredicate,Object) detect}(new {@link org.apache.commons.functor.generator.IteratorToGeneratorAdapter IteratorToGeneratorAdapter}(iter),pred,ifNone)</code>.
     */
    public static final <T> T detect(Iterator<T> iter, UnaryPredicate<? super T> pred, T ifNone) {
        return detect(new IteratorToGeneratorAdapter<T>(iter), pred, ifNone);
    }

    /**
     * Return the first element within the given {@link Generator} that matches
     * the given {@link UnaryPredicate UnaryPredicate}, or throw {@link
     * java.util.NoSuchElementException NoSuchElementException} if no
     * matching element can be found.
     *
     * @see #detect(Generator,UnaryPredicate,Object)
     * @throws NoSuchElementException If no element could be found.
     */
    public static final <T> T detect(final Generator<T> gen, final UnaryPredicate<? super T> pred) {
        FindWithinGenerator<T> finder = new FindWithinGenerator<T>(gen,pred);
        gen.run(finder);
        if(finder.wasFound()) {
            return finder.getFoundObject();
        } else {
            throw new NoSuchElementException("No element matching " + pred + " was found.");
        }
    }

    /**
     * Return the first element within the given {@link Generator} that matches
     * the given {@link UnaryPredicate UnaryPredicate}, or return the given
     * (possibly <code>null</code>) <code>Object</code> if no matching element
     * can be found.
     *
     * @see #detect(Generator,UnaryPredicate)
     */
    public static final <T> T detect(final Generator<T> gen, final UnaryPredicate<? super T> pred, T ifNone) {
        FindWithinGenerator<T> finder = new FindWithinGenerator<T>(gen,pred);
        gen.run(finder);
        return finder.wasFound() ? finder.getFoundObject() : ifNone;
    }

    /**
     * Equivalent to <code>{@link #foreach(Generator,UnaryProcedure) foreach}(new {@link org.apache.commons.functor.generator.IteratorToGeneratorAdapter IteratorToGeneratorAdapter}(iter),proc)</code>.
     */
    public static final <T> void foreach(Iterator<T> iter, UnaryProcedure<? super T> proc) {
        foreach(new IteratorToGeneratorAdapter<T>(iter), proc);
    }

    /**
     * {@link UnaryProcedure#run Apply} the given {@link UnaryProcedure
     * UnaryProcedure} to each element in the given {@link Generator}.
     */
    public static final <T> void foreach(Generator<T> gen, UnaryProcedure<? super T> proc) {
        gen.run(proc);
    }

    /**
     * {@link BinaryFunction#evaluate Evaluate} the pair <i>( previousResult,
     * element )</i> for each element in the given {@link Iterator} where
     * previousResult is initially <i>seed</i>, and thereafter the result of the
     * evaluation of the previous element in the iterator. Returns the result
     * of the final evaluation.
     *
     * <p>
     * In code:
     * <pre>
     * while(iter.hasNext()) {
     *   seed = func.evaluate(seed,iter.next());
     * }
     * return seed;
     * </pre>
     */
    public static final <T,U> U inject(Iterator<T> iter, U seed, BinaryFunction<? super U,? super T,? extends U> func) {
        while(iter.hasNext()) {
            seed = func.evaluate(seed,iter.next());
        }
        return seed;
    }

    /**
     * {@link BinaryFunction#evaluate Evaluate} the pair <i>( previousResult,
     * element )</i> for each element in the given {@link Generator} where
     * previousResult is initially <i>seed</i>, and thereafter the result of the
     * evaluation of the previous element in the iterator. Returns the result
     * of the final evaluation.
     *
     * <p>
     * In code:
     * <pre>
     * while(iter.hasNext()) {
     *   seed = func.evaluate(seed,iter.next());
     * }
     * return seed;
     * </pre>
     */
    public static final <T,U> U inject(Generator<T> gen, final U seed, final BinaryFunction<? super U,? super T,? extends U> func) {
        Injector<T,U> injector = new Injector<T,U>(seed,func);
        gen.run(injector);
        return injector.getResult();
    }

    /**
     * Returns an {@link Iterator} that will only return elements that DO
     * NOT match the given predicate.
     */
    public static <T> Iterator<T> reject(Iterator<T> iter, UnaryPredicate<? super T> pred) {
        return FilteredIterator.filter(iter,UnaryNot.not(pred));
    }

    /**
     * Returns a {@link Generator} that will only "generate" elements that DO
     * NOT match the given predicate.
     */
    public static <T> Generator<T> reject(final Generator<T> gen, final UnaryPredicate<? super T> pred) {
        return new BaseGenerator<T>(gen) {
            public void run(final UnaryProcedure<? super T> proc) {
                gen.run(new ConditionalUnaryProcedure<T>(pred,NoOp.instance(),proc));
            }
        };
    }

    /**
     * Returns an {@link Iterator} that will only return elements that DO
     * match the given predicate.
     */
    public static final <T> Iterator<T> select(Iterator<T> iter, UnaryPredicate<? super T> pred) {
        return FilteredIterator.filter(iter,pred);
    }

    /**
     * Returns a {@link Generator} that will only "generate" elements that DO
     * match the given predicate.
     */
    public static final <T> Generator<T> select(final Generator<T> gen, final UnaryPredicate<? super T> pred) {
        return new BaseGenerator<T>(gen) {
            public void run(final UnaryProcedure<? super T> proc) {
                gen.run(new ConditionalUnaryProcedure<T>(pred,proc,NoOp.instance()));
            }
        };
    }

    public static final void dountil(Procedure proc, Predicate pred) {
        dowhile(proc,Not.not(pred));
    }

    public static final void dowhile(Procedure proc, Predicate pred) {
        do { proc.run(); } while(pred.test());
    }

    public static final void untildo(Predicate pred, Procedure proc) {
        whiledo(Not.not(pred),proc);
    }

    public static final void whiledo(Predicate pred, Procedure proc) {
        while(pred.test()) { proc.run(); }
    }

    /**
     * Equivalent to 
     * <code>{@link #reject(Iterator,UnaryPredicate) reject}(iter,pred)</code>.
     */
    public static final <T> Iterator<T> until(final Iterator<T> iter, final UnaryPredicate<? super T> pred) {
        return reject(iter,pred);
    }

    /**
     * Equivalent to 
     * <code>{@link #reject(Generator,UnaryPredicate) reject}(gen,pred)</code>.
     */
    public static final <T> Generator<T> until(final Generator<T> gen, final UnaryPredicate<? super T> pred) {
        return reject(gen,pred);
    }

    /**
     * Tail recursion for {@link Function functions}. If the {@link Function}
     * returns another function of the same type as the original, that function
     * is executed. Functions are executed until a non function value or a
     * function of a different type is returned.
     */
    public static final <T> T recurse(Function<T> function) {
        T result = null;
        Class recursiveFunctionClass = function.getClass();

        // if the function returns another function, execute it. stop executing
        // when the function doesn't return another function of the same type.
        while(true) {
            result = function.evaluate();
            if(recursiveFunctionClass.isInstance(result)) {
                function = (Function<T>)result;
                continue;
            } else {
                break;
            }
        }

        return result;
    }
    
    // inner classes
    //---------------------------------------------------------------
    
    private static class FindWithinGenerator<T> implements UnaryProcedure<T> {
        FindWithinGenerator(Generator<T> gen, UnaryPredicate<? super T> pred) {
            this.generator = gen;
            this.predicate = pred;
            this.found = false;
            this.foundObject = null;
        }

        public void run(T obj) {
            if(predicate.test(obj)) {
                found = true;
                foundObject = obj;
                generator.stop();
            }
        }
        
        boolean wasFound() {
            return found;
        }
        
        T getFoundObject() {
            return foundObject;
        }
        
        private UnaryPredicate<? super T> predicate = null;
        private boolean found = false;
        private T foundObject = null;
        private Generator<T> generator = null;
    }

    private static class Injector<T,U> implements UnaryProcedure<T> {
        Injector(U seed, BinaryFunction<? super U,? super T,? extends U> function) {
            this.seed = seed;
            this.function = function;
        }
        
        public void run(T obj) {
            seed = function.evaluate(seed,obj);
        }
        
        U getResult() {
            return seed;
        }
        
        private U seed = null;
        private BinaryFunction<? super U,? super T,? extends U> function = null;
    }
    
}