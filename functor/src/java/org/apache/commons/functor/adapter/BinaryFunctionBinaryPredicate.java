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
 * Adapts a <code>Boolean</code>-valued
 * {@link BinaryFunction BinaryFunction}
 * to the {@link BinaryPredicate BinaryPredicate}
 * interface.
 * <p>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying functor is.  Attempts to serialize
 * an instance whose delegate is not 
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class BinaryFunctionBinaryPredicate<T,U> implements BinaryPredicate<T,U>, Serializable {
    /**
     * Create an {@link BinaryPredicate BinaryPredicate} wrapping
     * the given {@link BinaryFunction BinaryFunction}.
     * @param function the {@link BinaryFunction BinaryFunction} to wrap
     */
    public BinaryFunctionBinaryPredicate(BinaryFunction<T,U,Boolean> function) {
        this.function = function;
    }
 
    /**
     * Returns the <code>boolean</code> value of the non-<code>null</code>
     * <code>Boolean</code> returned by the {@link BinaryFunction#evaluate evaluate}
     * method of my underlying function.
     * 
     * @throws NullPointerException if my underlying function returns <code>null</code>
     * @throws ClassCastException if my underlying function returns a non-<code>Boolean</code>
     */
    public boolean test(T left, U right) {
        return function.evaluate(left,right).booleanValue();
    }   

    public boolean equals(Object that) {
        if(that instanceof BinaryFunctionBinaryPredicate) {
            return equals((BinaryFunctionBinaryPredicate)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(BinaryFunctionBinaryPredicate that) {
        return that == this || (null != that && (null == function ? null == that.function : function.equals(that.function)));
    }
    
    public int hashCode() {
        int hash = "BinaryFunctionBinaryPredicate".hashCode();
        if(null != function) {
            hash ^= function.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "BinaryFunctionBinaryPredicate<" + function + ">";
    }
    
    /**
     * Adapt the given, possibly-<code>null</code>, 
     * {@link BinaryFunction BinaryFunction} to the
     * {@link BinaryPredicate BinaryPredicate} interface.
     * When the given <code>BinaryFunction</code> is <code>null</code>,
     * returns <code>null</code>.
     * 
     * @param function the possibly-<code>null</code> 
     *        {@link BinaryFunction BinaryFunction} to adapt
     * @return a <code>BinaryFunctionBinaryPredicate</code> wrapping the given
     *         {@link BinaryFunction BinaryFunction}, or <code>null</code>
     *         if the given <code>BinaryFunction</code> is <code>null</code>
     */
    public static <T,U> BinaryFunctionBinaryPredicate<T,U> adapt(BinaryFunction<T,U,Boolean> function) {
        return null == function ? null : new BinaryFunctionBinaryPredicate<T,U>(function);
    }

    /** The {@link BinaryFunction BinaryFunction} I'm wrapping. */
    private BinaryFunction<T,U,Boolean> function = null;
}
