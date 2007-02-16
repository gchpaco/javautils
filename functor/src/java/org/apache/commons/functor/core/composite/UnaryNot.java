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
 * {@link #test Tests} to the logical inverse
 * of some other predicate.
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
public final class UnaryNot<T> implements UnaryPredicate<T>, Serializable {

    // constructor
    // ------------------------------------------------------------------------

    /**
   * 
   */
  private static final long serialVersionUID = -6054565739289894970L;

    public UnaryNot(UnaryPredicate<? super T> p) {
        this.predicate = p;
    }
    
    // predicate interface
    // ------------------------------------------------------------------------
    public boolean test(T obj) {
        return !(predicate.test(obj));
    }

    @Override
    public boolean equals(Object that) {
        if(that instanceof UnaryNot) {
            return equals((UnaryNot)that);
        }
        return false;
    }
    
    public boolean equals(UnaryNot<?> that) {
        return null != that && (null == predicate ? null == that.predicate : predicate.equals(that.predicate));
    }
    
    @Override
    public int hashCode() {
        int hash = "UnaryNot".hashCode();
        if(null != predicate) {
            hash ^= predicate.hashCode();
        }
        return hash;
    }
    
    @Override
    public String toString() {
        return "UnaryNot<" + predicate + ">";
    }

    // static
    // ------------------------------------------------------------------------
    public static <T> UnaryPredicate<T> not(UnaryPredicate<T> that) {
        return null == that ? null : new UnaryNot<T> (that);
    }
    
    // attributes
    // ------------------------------------------------------------------------
    private UnaryPredicate<? super T> predicate = null;
}
