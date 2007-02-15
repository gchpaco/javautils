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

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.UnaryPredicate;

/**
 * Adapts a
 * {@link UnaryPredicate UnaryPredicate} 
 * to the 
 * {@link BinaryPredicate BinaryPredicate} interface 
 * by ignoring the second binary argument.
 * <p/>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying functor is.  Attempts to serialize
 * an instance whose delegate is not 
 * <code>Serializable</code> will result in an exception.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class IgnoreRightPredicate<T,U> implements BinaryPredicate<T,U>, Serializable {
    public IgnoreRightPredicate(UnaryPredicate<T> predicate) {
        this.predicate = predicate;
    }
 
    public boolean test(T left, U right) {
        return predicate.test(left);
    }   

    public boolean equals(Object that) {
        if(that instanceof IgnoreRightPredicate) {
            return equals((IgnoreRightPredicate)that);
        } else {
            return false;
        }
    }
        
    public boolean equals(IgnoreRightPredicate that) {
        return that == this || (null != that && (null == predicate ? null == that.predicate : predicate.equals(that.predicate)));
    }
    
    public int hashCode() {
        int hash = "IgnoreRightPredicate".hashCode();
        if(null != predicate) {
            hash ^= predicate.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "IgnoreRightPredicate<" + predicate + ">";
    }

    public static <T,U> IgnoreRightPredicate<T,U> adapt(UnaryPredicate<T> predicate) {
        return null == predicate ? null : new IgnoreRightPredicate<T,U>(predicate);
    }

    /** The {@link UnaryPredicate UnaryPredicate} I'm wrapping. */
    private UnaryPredicate<T> predicate = null;
}
