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
 * at least one of its children test <code>true</code>.
 * Note that by this definition, the "or" of
 * an empty collection of predicates tests <code>false</code>.
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
public final class BinaryOr extends BaseBinaryPredicateList {

    // constructor
    // ------------------------------------------------------------------------
    public BinaryOr() {
        super();
    }

    public BinaryOr(BinaryPredicate p) {
        super(p);
    }

    public BinaryOr(BinaryPredicate p, BinaryPredicate q) {
        super(p,q);
    }

    public BinaryOr(BinaryPredicate p, BinaryPredicate q, BinaryPredicate r) {
        super(p,q,r);
    }
    
    // modifiers
    // ------------------------------------------------------------------------ 
    public BinaryOr or(BinaryPredicate p) {
        super.addBinaryPredicate(p);
        return this;
    }
 
    // predicate interface
    // ------------------------------------------------------------------------
    public boolean test(Object a, Object b) {
        for(Iterator iter = getBinaryPredicateIterator(); iter.hasNext();) {
            if(((BinaryPredicate)iter.next()).test(a,b)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object that) {
        if(that instanceof BinaryOr) {
            return equals((BinaryOr)that);
        } else {
            return false;
        }
    }
    
    public boolean equals(BinaryOr that) {
        return getBinaryPredicateListEquals(that);
    }
    
    public int hashCode() {
        return "BinaryOr".hashCode() ^ getBinaryPredicateListHashCode();
    }
    
    public String toString() {
        return "BinaryOr<" + getBinaryPredicateListToString() + ">";
    }
    
}
