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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.functor.BinaryPredicate;

/**
 * Abstract base class for {@link BinaryPredicate BinaryPredicates}
 * composed of a list of {@link BinaryPredicate BinaryPredicates}.
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
abstract class BaseBinaryPredicateList<T,U> implements BinaryPredicate<T,U>, Serializable {

    // constructor
    // ------------------------------------------------------------------------
    protected BaseBinaryPredicateList() {
    }

    protected BaseBinaryPredicateList(BinaryPredicate<? super T,? super U> p) {
        addBinaryPredicate(p);
    }

    protected BaseBinaryPredicateList(BinaryPredicate<? super T,? super U> p, BinaryPredicate<? super T,? super U> q) {
        addBinaryPredicate(p);
        addBinaryPredicate(q);
    }

    protected BaseBinaryPredicateList(BinaryPredicate<? super T,? super U> p, BinaryPredicate<? super T,? super U> q, BinaryPredicate<? super T,? super U> r) {
        addBinaryPredicate(p);
        addBinaryPredicate(q);
        addBinaryPredicate(r);
    }
    
    // abstract
    // ------------------------------------------------------------------------ 
    @Override
    public abstract boolean equals(Object that);
    @Override
    public abstract int hashCode();
    @Override
    public abstract String toString();
    public abstract boolean test(T left, U right);

    // modifiers
    // ------------------------------------------------------------------------ 
    protected void addBinaryPredicate(BinaryPredicate<? super T,? super U> p) {
        list.add(p);
    }
 
    // protected
    // ------------------------------------------------------------------------

    protected Iterator<BinaryPredicate<? super T, ? super U>> getBinaryPredicateIterator() {
        return list.iterator();
    }
    
    protected boolean getBinaryPredicateListEquals(BaseBinaryPredicateList<?,?> that) {
        return (null != that && this.list.equals(that.list));
    }
    
    protected int getBinaryPredicateListHashCode() {
        return list.hashCode();
    }
    
    protected String getBinaryPredicateListToString() {
        return String.valueOf(list);
    }
    
    // attributes
    // ------------------------------------------------------------------------
    private List<BinaryPredicate<? super T,? super U>> list = new ArrayList<BinaryPredicate<? super T, ? super U>>();

}
