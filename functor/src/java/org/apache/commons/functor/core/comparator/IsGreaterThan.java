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
package org.apache.commons.functor.core.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.adapter.RightBoundPredicate;

/**
 * A {@link BinaryPredicate BinaryPredicate} that {@link #test tests}
 * <code>true</code> iff the left argument is greater than the
 * right argument under the specified {@link Comparator}.
 * When no (or a <code>null</code> <code>Comparator</code> is specified,
 * a {@link Comparable Comparable} <code>Comparator</code> is used.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class IsGreaterThan<T> implements BinaryPredicate<T,T>, Serializable {
    /**
   * 
   */
  private static final long serialVersionUID = -7870723161733468990L;
    /**
     * Construct a <code>IsGreaterThan</code> {@link BinaryPredicate predicate}
     * for {@link Comparable Comparable}s.
     */
    public IsGreaterThan() {
        this(null);
    }

    /**
     * Construct a <code>IsGreaterThan</code> {@link BinaryPredicate predicate}
     * for the given {@link Comparator Comparator}.
     * 
     * @param comparator the {@link Comparator Comparator}, when <code>null</code>,
     *        a <code>Comparator</code> for {@link Comparable Comparable}s will
     *        be used.
     */
    @SuppressWarnings("unchecked")
    public IsGreaterThan(Comparator<T> comparator) {
        this.comparator = null == comparator ? ComparableComparator.getInstance() : comparator;
    }
    
    /**
     * Return <code>true</code> iff the <i>left</i> parameter is 
     * greater than the <i>right</i> parameter under my current
     * {@link Comparator Comparator}.
     */
    public boolean test(T left, T right) {
        return comparator.compare(left,right) > 0;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object that) {
        if(that instanceof IsGreaterThan) {
            return equals((IsGreaterThan)that);
        }
        return false;
    }

    /**
     * @see #equals(Object)
     */
    @SuppressWarnings("null")
    public boolean equals(IsGreaterThan<?> that) {
        return null != that && 
            null == comparator ? null == that.comparator : comparator.equals(that.comparator);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = "IsGreaterThan".hashCode();
        // by construction, comparator is never null
        hash ^= comparator.hashCode();
        return hash;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "IsGreaterThan<" + comparator + ">";
    }

    @SuppressWarnings("unchecked")
    public static final IsGreaterThan instance() {
        return COMPARABLE_INSTANCE;
    }
    
    @SuppressWarnings("unchecked")
    public static final <T extends Comparable<T>> UnaryPredicate<T> instance(T right) {
        return RightBoundPredicate.bind(instance(),right);
    }
   
    private Comparator<T> comparator = null;
    private static final IsGreaterThan<?> COMPARABLE_INSTANCE = new IsGreaterThan<Object>();
}
