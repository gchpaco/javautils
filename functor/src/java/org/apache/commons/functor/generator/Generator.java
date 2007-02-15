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

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.UnaryProcedure;

/**
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Jason Horman (jason@jhorman.org)
 * @author Rodney Waldhoff
 */
public interface Generator<T> {
    /** Generators must implement this method. */
    public abstract void run(UnaryProcedure<? super T> proc);
    /** Stop the generator. Will stop the wrapped generator if one was set. */
    public abstract void stop();
    /** Check if the generator is stopped. */
    public abstract boolean isStopped();
    /*** See {@link org.apache.commons.functor.Algorithms#apply}. */
    public abstract <U> Generator<U> apply(UnaryFunction<? super T,U> func);
    /** See {@link org.apache.commons.functor.Algorithms#contains}. */
    public abstract boolean contains(UnaryPredicate<? super T> pred);
    /** See {@link org.apache.commons.functor.Algorithms#detect}. */
    public abstract T detect(UnaryPredicate<? super T> pred);
    /** See {@link org.apache.commons.functor.Algorithms#detect}. */
    public abstract T detect(UnaryPredicate<? super T> pred, T ifNone);
    /** Synonym for run. */
    public abstract void foreach(UnaryProcedure<? super T> proc);
    /** See {@link org.apache.commons.functor.Algorithms#inject}. */
    public abstract <U> U inject(U seed, BinaryFunction<? super U,? super T,? extends U> func);
    /** See {@link org.apache.commons.functor.Algorithms#reject}. */
    public abstract Generator<T> reject(UnaryPredicate<? super T> pred);
    /** See {@link org.apache.commons.functor.Algorithms#select}. */
    public abstract Generator<T> select(UnaryPredicate<? super T> pred);
    /** See {@link org.apache.commons.functor.Algorithms#select}. */
    public abstract Generator<T> where(UnaryPredicate<? super T> pred);
    /** See {@link org.apache.commons.functor.Algorithms#until}. */
    public abstract Generator<T> until(UnaryPredicate<? super T> pred);
    /**
     * Transforms this generator using the passed in
     * transformer. An example transformer might turn the contents of the
     * generator into a {@link Collection} of elements.
     */
    public abstract <U> U to(UnaryFunction<Generator<? extends T>,U> transformer);
    /** Same as to(new CollectionTransformer(collection)). */
    public abstract Collection<T> to(Collection<T> collection);
    /** Same as to(new CollectionTransformer()). */
    public abstract Collection<T> toCollection();
}