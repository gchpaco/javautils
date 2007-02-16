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

import java.util.Iterator;

import org.apache.commons.functor.BinaryPredicate;

/**
 * {@link #test Tests} <code>true</code> iff
 * none of its children test <code>false</code>.
 * Note that by this definition, the "and" of
 * an empty collection of predicates tests <code>true</code>.
 * <p>
 * Note that although this class implements 
 * {@link java.io.Serializable Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all 
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class BinaryAnd<T,U> extends BaseBinaryPredicateList<T, U> {

    /**
   * 
   */
  private static final long serialVersionUID = 4973040556436736908L;

    // constructor
    // ------------------------------------------------------------------------
    public BinaryAnd() {
        super();
    }

    public BinaryAnd(BinaryPredicate<? super T,? super U> p) {
        super(p);
    }

    public BinaryAnd(BinaryPredicate<? super T,? super U> p, BinaryPredicate<? super T,? super U> q) {
        super(p,q);
    }

    public BinaryAnd(BinaryPredicate<? super T,? super U> p, BinaryPredicate<? super T,? super U> q, BinaryPredicate<? super T,? super U> r) {
        super(p,q,r);
    }
    
    // modifiers
    // ------------------------------------------------------------------------ 
    public BinaryAnd<T, U> and(BinaryPredicate<? super T,? super U> p) {
        super.addBinaryPredicate(p);
        return this;
    }
 
    // predicate interface
    // ------------------------------------------------------------------------
    @Override
    public boolean test(T a, U b) {
        for(Iterator<BinaryPredicate<? super T,? super U>> iter = getBinaryPredicateIterator(); iter.hasNext();) {
            if(!iter.next().test(a,b)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object that) {
        if(that instanceof BinaryAnd) {
            return equals((BinaryAnd)that);
        }
        return false;
    }
    
    public boolean equals(BinaryAnd<?, ?> that) {
        return getBinaryPredicateListEquals(that);
    }
    
    @Override
    public int hashCode() {
        return "BinaryAnd".hashCode() ^ getBinaryPredicateListHashCode();
    }
    
    @Override
    public String toString() {
        return "BinaryAnd<" + getBinaryPredicateListToString() + ">";
    }
    
}
