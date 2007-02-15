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
package org.apache.commons.functor.core.composite;

import java.io.Serializable;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.UnaryFunction;

/**
 * A {@link BinaryFunction BinaryFunction} composed of
 * one binary function, <i>f</i>, and two unary
 * functions, <i>g</i> and <i>h</i>,
 * evaluating the ordered parameters <i>x</i>, <i>y</i> 
 * to <code><i>f</i>(<i>g</i>(<i>x</i>),<i>h</i>(<i>y</i>))</code>.
 * <p>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all 
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public class UnaryCompositeBinaryFunction implements BinaryFunction, Serializable {

    // constructor
    // ------------------------------------------------------------------------
    public UnaryCompositeBinaryFunction(BinaryFunction f, UnaryFunction g, UnaryFunction h) {
        binary = f;
        leftUnary = g;
        rightUnary = h;        
    }

    // function interface
    // ------------------------------------------------------------------------
    public Object evaluate(Object left, Object right) {
        return binary.evaluate(leftUnary.evaluate(left), rightUnary.evaluate(right));
    }

    public boolean equals(Object that) {
        if(that instanceof UnaryCompositeBinaryFunction) {
            return equals((UnaryCompositeBinaryFunction)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(UnaryCompositeBinaryFunction that) {
        return (null != that) &&
            (null == binary ? null == that.binary : binary.equals(that.binary)) &&
            (null == leftUnary ? null == that.leftUnary : leftUnary.equals(that.leftUnary)) &&
            (null == rightUnary ? null == that.rightUnary : rightUnary.equals(that.rightUnary));
    }
    
    public int hashCode() {
        int hash = "UnaryCompositeBinaryFunction".hashCode();
        if(null != binary) {
            hash <<= 4;
            hash ^= binary.hashCode();            
        }
        if(null != leftUnary) {
            hash <<= 4;
            hash ^= leftUnary.hashCode();            
        }
        if(null != rightUnary) {
            hash <<= 4;
            hash ^= rightUnary.hashCode();            
        }
        return hash;
    }
    
    public String toString() {
        return "UnaryCompositeBinaryFunction<" + binary + ";" + leftUnary + ";" + rightUnary + ">";
    }
        
    // attributes
    // ------------------------------------------------------------------------
    private BinaryFunction binary = null;
    private UnaryFunction leftUnary = null;
    private UnaryFunction rightUnary = null;

}
