/*
 * Copyright 2003-2004 The Apache Software Foundation.
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

package org.apache.commons.functor.generator;

import java.util.Collection;

import org.apache.commons.functor.Algorithms;
import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.util.CollectionTransformer;

/**
 * Base class for generators. Adds support for all of the {@link Algorithms} to
 * each subclass.
 *
 * @since 1.0
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author  Jason Horman (jason@jhorman.org)
 */

public abstract class BaseGenerator<T> implements Generator<T> {

    /** A generator can wrap another generator. */
    private Generator<?> wrappedGenerator = null;

    /** Create a new generator. */
    public BaseGenerator() {
    }

    /**
     * A generator can wrap another generator. When wrapping generators you
     * should use probably this constructor since doing so will cause the
     * {@link #stop} method to stop the wrapped generator as well.
     */
    public BaseGenerator(Generator generator) {
        this.wrappedGenerator = generator;
    }

    /** Get the generator that is being wrapped. */
    protected Generator<?> getWrappedGenerator() {
        return wrappedGenerator;
    }

    /** Generators must implement this method. */
    public abstract void run(UnaryProcedure<T> proc);

    /** Stop the generator. Will stop the wrapped generator if one was set. */
    public void stop() {
        if (wrappedGenerator != null) { wrappedGenerator.stop(); } 
        stopped = true;
    }

    /** Check if the generator is stopped. */
    public boolean isStopped() {
        return stopped;
    }

    /** Set to true when the generator is {@link #stop stopped}. */
    private boolean stopped = false;

    /*** See {@link Algorithms#apply}. */
    public final <U> Generator<U> apply(UnaryFunction<T,U> func) {
        return Algorithms.apply(this,func);
    }
    
    /** See {@link Algorithms#contains}. */
    public final boolean contains(UnaryPredicate<T> pred) {
        return Algorithms.contains(this, pred);
    }

    /** See {@link Algorithms#detect}. */
    public final T detect(UnaryPredicate<T> pred) {
        return Algorithms.detect(this, pred);
    }

    /** See {@link Algorithms#detect}. */
    public final T detect(UnaryPredicate<T> pred, T ifNone) {
        return Algorithms.detect(this, pred, ifNone);
    }

    /** Synonym for run. */
    public final void foreach(UnaryProcedure<T> proc) {
        Algorithms.foreach(this, proc);
    }

    /** See {@link Algorithms#inject}. */
    public final <U> U inject(U seed, BinaryFunction<U,T,U> func) {
        return Algorithms.inject(this, seed, func);
    }

    /** See {@link Algorithms#reject}. */
    public final Generator<T> reject(UnaryPredicate<T> pred) {
        return Algorithms.reject(this, pred);
    }

    /** See {@link Algorithms#select}. */
    public final Generator<T> select(UnaryPredicate<T> pred) {
        return Algorithms.select(this, pred);
    }

    /** See {@link Algorithms#select}. */
    public final Generator<T> where(UnaryPredicate<T> pred) {
        return Algorithms.select(this, pred);
    }

    /** See {@link Algorithms#until}. */
    public final Generator<T> until(UnaryPredicate<T> pred) {
        return Algorithms.until(this, pred);
    }

    /**
     * {@link Transformer Transforms} this generator using the passed in
     * transformer. An example transformer might turn the contents of the
     * generator into a {@link Collection} of elements.
     */
    public final <U> U to(UnaryFunction<Generator<T>,U> transformer) {
        return transformer.evaluate(this);
    }

    /** Same as to(new CollectionTransformer(collection)). */
    public final Collection<T> to(Collection<T> collection) {
        return to(new CollectionTransformer<T>(collection));
    }

    /** Same as to(new CollectionTransformer()). */
    public final Collection<T> toCollection() {
        return to(new CollectionTransformer<T>());
    }
}