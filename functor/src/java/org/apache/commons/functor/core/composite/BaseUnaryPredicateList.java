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

import org.apache.commons.functor.UnaryPredicate;

/**
 * Abstract base class for {@link UnaryPredicate UnaryPredicates}
 * composed of a list of {@link UnaryPredicate UnaryPredicates}.
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
abstract class BaseUnaryPredicateList<T> implements UnaryPredicate<T>, Serializable {

    // constructor
    // ------------------------------------------------------------------------
    protected BaseUnaryPredicateList() {
    }

    protected BaseUnaryPredicateList(UnaryPredicate<? super T> p) {
        addUnaryPredicate(p);
    }

    protected BaseUnaryPredicateList(UnaryPredicate<? super T> p, UnaryPredicate<? super T> q) {
        addUnaryPredicate(p);
        addUnaryPredicate(q);
    }

    protected BaseUnaryPredicateList(UnaryPredicate<? super T> p, UnaryPredicate<? super T> q, UnaryPredicate<? super T> r) {
        addUnaryPredicate(p);
        addUnaryPredicate(q);
        addUnaryPredicate(r);
    }
    
    // abstract
    // ------------------------------------------------------------------------ 
    @Override
    public abstract boolean equals(Object that);
    @Override
    public abstract int hashCode();
    @Override
    public abstract String toString();
    public abstract boolean test(Object obj);

    // modifiers
    // ------------------------------------------------------------------------ 
    protected void addUnaryPredicate(UnaryPredicate<? super T> p) {
        list.add(p);
    }
 
    // protected
    // ------------------------------------------------------------------------

    protected Iterator<UnaryPredicate<? super T>> getUnaryPredicateIterator() {
        return list.iterator();
    }
    
    protected boolean getUnaryPredicateListEquals(BaseUnaryPredicateList<?> that) {
        return (null != that && this.list.equals(that.list));
    }
    
    protected int getUnaryPredicateListHashCode() {
        return list.hashCode();
    }
    
    protected String getUnaryPredicateListToString() {
        return String.valueOf(list);
    }
    
    // attributes
    // ------------------------------------------------------------------------
    private List<UnaryPredicate<? super T>> list = new ArrayList<UnaryPredicate<? super T>>();

}
