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

import org.apache.commons.functor.UnaryPredicate;

/**
 * A {@link UnaryPredicate UnaryPredicate} 
 * similiar to Java's "ternary" 
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given three {@link UnaryPredicate predicate}
 * <i>p</i>, <i>q</i>, <i>r</i>,
 * {@link #test tests}
 * to 
 * <code>p.test(x) ? q.test(x) : r.test(x)</code>.
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
public final class ConditionalUnaryPredicate implements UnaryPredicate, Serializable {

    // constructor
    // ------------------------------------------------------------------------

    public ConditionalUnaryPredicate(UnaryPredicate ifPred, UnaryPredicate thenPred, UnaryPredicate elsePred) {
        this.ifPred = ifPred;
        this.thenPred = thenPred;
        this.elsePred = elsePred;
    }
    
    // predicate interface
    // ------------------------------------------------------------------------
    public boolean test(Object obj) {
        return ifPred.test(obj) ? thenPred.test(obj) : elsePred.test(obj);
    }

    public boolean equals(Object that) {
        if(that instanceof ConditionalUnaryPredicate) {
            return equals((ConditionalUnaryPredicate)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(ConditionalUnaryPredicate that) {
        return null != that && 
                (null == ifPred ? null == that.ifPred : ifPred.equals(that.ifPred)) &&
                (null == thenPred ? null == that.thenPred : thenPred.equals(that.thenPred)) &&
                (null == elsePred ? null == that.elsePred : elsePred.equals(that.elsePred));
    }
    
    public int hashCode() {
        int hash = "ConditionalUnaryPredicate".hashCode();
        if(null != ifPred) {
            hash <<= 4;
            hash ^= ifPred.hashCode();            
        }
        if(null != thenPred) {
            hash <<= 4;
            hash ^= thenPred.hashCode();            
        }
        if(null != elsePred) {
            hash <<= 4;
            hash ^= elsePred.hashCode();            
        }
        return hash;
    }
    
    public String toString() {
        return "ConditionalUnaryPredicate<" + ifPred + "?" + thenPred + ":" + elsePred + ">";
    }

    // attributes
    // ------------------------------------------------------------------------
    private UnaryPredicate ifPred = null;
    private UnaryPredicate thenPred = null;
    private UnaryPredicate elsePred = null;
}
