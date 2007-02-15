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

import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryPredicate;

/**
 * A {@link UnaryFunction UnaryFunction} 
 * similiar to Java's "ternary" 
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link UnaryPredicate predicate}
 * <i>p</i> and {@link UnaryFunction functions}
 * <i>f</i> and <i>g</i>, {@link #evaluate evalautes}
 * to 
 * <code>p.test(x) ? f.evaluate(x) : g.evaluate(x)</code>.
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
public final class ConditionalUnaryFunction implements UnaryFunction, Serializable {

    // constructor
    // ------------------------------------------------------------------------

    public ConditionalUnaryFunction(UnaryPredicate ifPred, UnaryFunction thenPred, UnaryFunction elsePred) {
        this.ifPred = ifPred;
        this.thenFunc = thenPred;
        this.elseFunc = elsePred;
    }
    
    // predicate interface
    // ------------------------------------------------------------------------
    public Object evaluate(Object obj) {
        return ifPred.test(obj) ? thenFunc.evaluate(obj) : elseFunc.evaluate(obj);
    }

    public boolean equals(Object that) {
        if(that instanceof ConditionalUnaryFunction) {
            return equals((ConditionalUnaryFunction)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(ConditionalUnaryFunction that) {
        return null != that && 
                (null == ifPred ? null == that.ifPred : ifPred.equals(that.ifPred)) &&
                (null == thenFunc ? null == that.thenFunc : thenFunc.equals(that.thenFunc)) &&
                (null == elseFunc ? null == that.elseFunc : elseFunc.equals(that.elseFunc));
    }
    
    public int hashCode() {
        int hash = "ConditionalUnaryFunction".hashCode();
        if(null != ifPred) {
            hash <<= 4;
            hash ^= ifPred.hashCode();            
        }
        if(null != thenFunc) {
            hash <<= 4;
            hash ^= thenFunc.hashCode();            
        }
        if(null != elseFunc) {
            hash <<= 4;
            hash ^= elseFunc.hashCode();            
        }
        return hash;
    }
    
    public String toString() {
        return "ConditionalUnaryFunction<" + ifPred + "?" + thenFunc + ":" + elseFunc + ">";
    }

    // attributes
    // ------------------------------------------------------------------------
    private UnaryPredicate ifPred = null;
    private UnaryFunction thenFunc = null;
    private UnaryFunction elseFunc = null;
}
