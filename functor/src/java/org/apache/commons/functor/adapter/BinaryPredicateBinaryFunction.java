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
package org.apache.commons.functor.adapter;

import java.io.Serializable;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryPredicate;

/**
 * Adapts a 
 * {@link BinaryPredicate BinaryPredicate} 
 * to the 
 * {@link BinaryFunction BinaryFunction} interface.
 * <p/>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying predicate is.  Attempts to serialize
 * an instance whose delegate is not 
 * <code>Serializable</code> will result in an exception.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class BinaryPredicateBinaryFunction<T,U> implements BinaryFunction<T,U,Boolean>, Serializable {
    public BinaryPredicateBinaryFunction(BinaryPredicate<T,U> predicate) {
        this.predicate = predicate;
    }

    /**
     * Returns <code>Boolean.TRUE</code> (<code>Boolean.FALSE</code>)
     * when the {@link BinaryPredicate#test test} method of my underlying 
     * predicate returns <code>true</code> (<code>false</code>).
     * 
     * @return a non-<code>null</code> <code>Boolean</code> instance
     */
    public Boolean evaluate(T left, U right) {
        return predicate.test(left,right) ? Boolean.TRUE : Boolean.FALSE;
    }   

    public boolean equals(Object that) {
        if(that instanceof BinaryPredicateBinaryFunction) {
            return equals((BinaryPredicateBinaryFunction)that);
        } else {
            return false;
        }
    }
        
    public boolean equals(BinaryPredicateBinaryFunction that) {
        return that == this || (null != that && (null == predicate ? null == that.predicate : predicate.equals(that.predicate)));
    }
    
    public int hashCode() {
        int hash = "BinaryPredicateBinaryFunction".hashCode();
        if(null != predicate) {
            hash ^= predicate.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "BinaryPredicateBinaryFunction<" + predicate + ">";
    }

    
    /**
     * Adapt the given, possibly-<code>null</code>, 
     * {@link BinaryPredicate BinaryPredicate} to the
     * {@link BinaryFunction BinaryFunction} interface.
     * When the given <code>BinaryPredicate</code> is <code>null</code>,
     * returns <code>null</code>.
     * 
     * @param predicate the possibly-<code>null</code> 
     *        {@link BinaryPredicate BinaryPredicate} to adapt
     * @return a <code>BinaryPredicateBinaryFunction</code> wrapping the given
     *         {@link BinaryPredicate BinaryPredicate}, or <code>null</code>
     *         if the given <code>BinaryPredicate</code> is <code>null</code>
     */
    public static <T,U> BinaryPredicateBinaryFunction<T,U> adapt(BinaryPredicate<T,U> predicate) {
        return null == predicate ? null : new BinaryPredicateBinaryFunction<T,U>(predicate);
    }

    /** The {@link BinaryPredicate BinaryPredicate} I'm wrapping. */
    private BinaryPredicate<T,U> predicate = null;
}
