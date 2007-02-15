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

import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryPredicate;

/**
 * Adapts a 
 * {@link UnaryPredicate UnaryPredicate} 
 * to the 
 * {@link UnaryFunction UnaryFunction} interface.
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
public final class UnaryPredicateUnaryFunction implements UnaryFunction, Serializable {
    public UnaryPredicateUnaryFunction(UnaryPredicate predicate) {
        this.predicate = predicate;
    }
 
    /**
     * Returns <code>Boolean.TRUE</code> (<code>Boolean.FALSE</code>)
     * when the {@link UnaryPredicate#test test} method of my underlying 
     * predicate returns <code>true</code> (<code>false</code>).
     * 
     * @return a non-<code>null</code> <code>Boolean</code> instance
     */
    public Object evaluate(Object obj) {
        return predicate.test(obj) ? Boolean.TRUE : Boolean.FALSE;
    }   

    public boolean equals(Object that) {
        if(that instanceof UnaryPredicateUnaryFunction) {
            return equals((UnaryPredicateUnaryFunction)that);
        } else {
            return false;
        }
    }
        
    public boolean equals(UnaryPredicateUnaryFunction that) {
        return that == this || (null != that && (null == predicate ? null == that.predicate : predicate.equals(that.predicate)));
    }
    
    public int hashCode() {
        int hash = "UnaryPredicateUnaryFunction".hashCode();
        if(null != predicate) {
            hash ^= predicate.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "UnaryPredicateUnaryFunction<" + predicate + ">";
    }

    public static UnaryPredicateUnaryFunction adapt(UnaryPredicate predicate) {
        return null == predicate ? null : new UnaryPredicateUnaryFunction(predicate);
    }

    /** The {@link UnaryPredicate UnaryPredicate} I'm wrapping. */
    private UnaryPredicate predicate = null;
}
