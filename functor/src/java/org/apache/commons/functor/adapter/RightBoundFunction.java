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
import org.apache.commons.functor.UnaryFunction;

/**
 * Adapts a
 * {@link BinaryFunction BinaryFunction} 
 * to the 
 * {@link UnaryFunction UnaryFunction} interface 
 * using a constant right-side argument.
 * <p/>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying objects are.  Attempts to serialize
 * an instance whose delegates are not 
 * <code>Serializable</code> will result in an exception.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class RightBoundFunction implements UnaryFunction, Serializable {
    /**
     * @param function the function to adapt
     * @param arg the constant argument to use
     */
    public RightBoundFunction(BinaryFunction function, Object arg) {
        this.function = function;
        this.param = arg;
    }
 
    public Object evaluate(Object obj) {
        return function.evaluate(obj,param);
    }   

    public boolean equals(Object that) {
        if(that instanceof RightBoundFunction) {
            return equals((RightBoundFunction)that);
        } else {
            return false;
        }
    }
        
    public boolean equals(RightBoundFunction that) {
        return that == this || ( 
                (null != that) && 
                (null == function ? null == that.function : function.equals(that.function)) &&
                (null == param ? null == that.param : param.equals(that.param)) );
                
    }
    
    public int hashCode() {
        int hash = "RightBoundFunction".hashCode();
        if(null != function) {
            hash <<= 2;
            hash ^= function.hashCode();
        }
        if(null != param) {
            hash <<= 2;
            hash ^= param.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "RightBoundFunction<" + function + "(?," + param + ")>";
    }

    public static RightBoundFunction bind(BinaryFunction function, Object arg) {
        return null == function ? null : new RightBoundFunction(function,arg);
    }

    /** The {@link BinaryFunction BinaryFunction} I'm wrapping. */
    private BinaryFunction function = null;
    /** The parameter to pass to that function. */
    private Object param = null;
}
