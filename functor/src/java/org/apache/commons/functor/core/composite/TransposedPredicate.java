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

/**
 * Transposes (swaps) the arguments to some other
 * {@link BinaryPredicate predicate}.  
 * For example, given a predicate <i>p</i>
 * and the ordered pair of arguments <i>a</i>,
 * <i>b</i>.
 * {@link #test tests}  
 * <code>p.test(b,a)</code>.
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
public class TransposedPredicate implements BinaryPredicate, Serializable {

    // constructor
    // ------------------------------------------------------------------------
    public TransposedPredicate(BinaryPredicate p) {
        predicate = p;
    }

    // functor interface
    // ------------------------------------------------------------------------
    public boolean test(Object left, Object right) {        
        return predicate.test(right,left);
    }

    public boolean equals(Object that) {
        if(that instanceof TransposedPredicate) {
            return equals((TransposedPredicate)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(TransposedPredicate that) {
        return null != that && (null == predicate ? null == that.predicate : predicate.equals(that.predicate));
    }
    
    public int hashCode() {
        int hash = "TransposedPredicate".hashCode();
        if(null != predicate) {
            hash ^= predicate.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "TransposedPredicate<" + predicate + ">";
    }
        
    // static
    // ------------------------------------------------------------------------
    public static TransposedPredicate transpose(BinaryPredicate p) {
        return null == p ? null : new TransposedPredicate(p);
    }
        
    // attributes
    // ------------------------------------------------------------------------
    private BinaryPredicate predicate = null;

}
