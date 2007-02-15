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

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.UnaryFunction;

/**
 * A {@link BinaryPredicate BinaryPredicate} composed of
 * one binary predicate, <i>p</i>, and two unary
 * functions, <i>f</i> and <i>g</i>,
 * evaluating the ordered parameters <i>x</i>, <i>y</i> 
 * to <code><i>p</i>(<i>f</i>(<i>x</i>),<i>g</i>(<i>y</i>))</code>.
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
public class UnaryCompositeBinaryPredicate implements BinaryPredicate, Serializable {

    // constructor
    // ------------------------------------------------------------------------
    public UnaryCompositeBinaryPredicate(BinaryPredicate f, UnaryFunction g, UnaryFunction h) {
        binary = f;
        leftUnary = g;
        rightUnary = h;        
    }

    // function interface
    // ------------------------------------------------------------------------
    public boolean test(Object left, Object right) {
        return binary.test(leftUnary.evaluate(left), rightUnary.evaluate(right));
    }

    public boolean equals(Object that) {
        if(that instanceof UnaryCompositeBinaryPredicate) {
            return equals((UnaryCompositeBinaryPredicate)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(UnaryCompositeBinaryPredicate that) {
        return (null != that) &&
            (null == binary ? null == that.binary : binary.equals(that.binary)) &&
            (null == leftUnary ? null == that.leftUnary : leftUnary.equals(that.leftUnary)) &&
            (null == rightUnary ? null == that.rightUnary : rightUnary.equals(that.rightUnary));
    }
    
    public int hashCode() {
        int hash = "UnaryCompositeBinaryPredicate".hashCode();
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
        return "UnaryCompositeBinaryPredicate<" + binary + ";" + leftUnary + ";" + rightUnary + ">";
    }
        
    // attributes
    // ------------------------------------------------------------------------
    private BinaryPredicate binary = null;
    private UnaryFunction leftUnary = null;
    private UnaryFunction rightUnary = null;

}
