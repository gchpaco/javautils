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
import org.apache.commons.functor.BinaryProcedure;

/**
 * Adapts a {@link BinaryFunction BinaryFunction}
 * to the {@link BinaryProcedure BinaryProcedure}
 * interface by ignoring the value returned
 * by the function.
 * <p/>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying function is.  Attempts to serialize
 * an instance whose delegate is not 
 * <code>Serializable</code> will result in an exception.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class BinaryFunctionBinaryProcedure<T,U> implements BinaryProcedure<T,U>, Serializable {
    /**
     * Create an {@link BinaryProcedure BinaryProcedure} wrapping
     * the given {@link BinaryFunction BinaryFunction}.
     * @param function the {@link BinaryFunction BinaryFunction} to wrap
     */
    public BinaryFunctionBinaryProcedure(BinaryFunction<T,U,?> function) {
        this.function = function;
    }
 
    /**
     * {@link BinaryFunction#evaluate Evaluate} my function, but 
     * ignore its returned value.
     */
    public void run(T left, U right) {
        function.evaluate(left,right);
    }   

    public boolean equals(Object that) {
        if(that instanceof BinaryFunctionBinaryProcedure) {
            return equals((BinaryFunctionBinaryProcedure)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(BinaryFunctionBinaryProcedure that) {
        return that == this || (null != that && (null == function ? null == that.function : function.equals(that.function)));
    }
    
    public int hashCode() {
        int hash = "BinaryFunctionBinaryProcedure".hashCode();
        if(null != function) {
            hash ^= function.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "BinaryFunctionBinaryProcedure<" + function + ">";
    }
    
    /**
     * Adapt the given, possibly-<code>null</code>, 
     * {@link BinaryFunction BinaryFunction} to the
     * {@link BinaryProcedure BinaryProcedure} interface.
     * When the given <code>BinaryFunction</code> is <code>null</code>,
     * returns <code>null</code>.
     * 
     * @param function the possibly-<code>null</code> 
     *        {@link BinaryFunction BinaryFunction} to adapt
     * @return a <code>BinaryFunctionBinaryProcedure</code> wrapping the given
     *         {@link BinaryFunction BinaryFunction}, or <code>null</code>
     *         if the given <code>BinaryFunction</code> is <code>null</code>
     */
    public static <T,U> BinaryFunctionBinaryProcedure<T,U> adapt(BinaryFunction<T,U,?> function) {
        return null == function ? null : new BinaryFunctionBinaryProcedure<T,U>(function);
    }

    /** The {@link BinaryFunction BinaryFunction} I'm wrapping. */
    private BinaryFunction<T,U,?> function = null;
}
