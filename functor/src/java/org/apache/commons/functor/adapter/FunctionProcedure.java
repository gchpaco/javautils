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

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Procedure;

/**
 * Adapts a {@link Function Function}
 * to the {@link Procedure Procedure}
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
public final class FunctionProcedure implements Procedure, Serializable {
    /**
     * Create an {@link Procedure Procedure} wrapping
     * the given {@link Function Function}.
     * @param function the {@link Function Function} to wrap
     */
    public FunctionProcedure(Function function) {
        this.function = function;
    }
 
    /**
     * {@link Function#evaluate Evaluate} my function, 
     * but ignore its returned value.
     */
    public void run() {
        function.evaluate();
    }   

    public boolean equals(Object that) {
        if(that instanceof FunctionProcedure) {
            return equals((FunctionProcedure)that);
        } else {
            return false;
        }
    }
        
    public boolean equals(FunctionProcedure that) {
        return that == this || (null != that && (null == function ? null == that.function : function.equals(that.function)));
    }
    
    public int hashCode() {
        int hash = "FunctionProcedure".hashCode();
        if(null != function) {
            hash ^= function.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "FunctionProcedure<" + function + ">";
    }
    /**
     * Adapt the given, possibly-<code>null</code>, 
     * {@link Function Function} to the
     * {@link Procedure Procedure} interface.
     * When the given <code>Function</code> is <code>null</code>,
     * returns <code>null</code>.
     * 
     * @param function the possibly-<code>null</code> 
     *        {@link Function Function} to adapt
     * @return a {@link Procedure Procedure} wrapping the given
     *         {@link Function Function}, or <code>null</code>
     *         if the given <code>Function</code> is <code>null</code>
     */
    public static FunctionProcedure adapt(Function function) {
        return null == function ? null : new FunctionProcedure(function);
    }

    /** The {@link Function Function} I'm wrapping. */
    private Function function = null;
}