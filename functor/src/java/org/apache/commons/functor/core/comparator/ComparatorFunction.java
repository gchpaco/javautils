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
import org.apache.commons.functor.BinaryFunction;

/**
 * Adapts a {@link Comparator Comparator} to the
 * {@link BinaryFunction} interface.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class ComparatorFunction<T extends Comparable<T>> implements BinaryFunction<T,T,Integer>, Serializable {
    /**
   * 
   */
  private static final long serialVersionUID = 8387888908141426649L;

    public ComparatorFunction() {
        this(null);
    }

    @SuppressWarnings("unchecked")
    public ComparatorFunction(Comparator<T> comparator) {
        this.comparator = null == comparator ? ComparableComparator.getInstance() : comparator;
    }
    
    /**
     * @see org.apache.commons.functor.BinaryFunction#evaluate(Object, Object)
     */
    public Integer evaluate(T left, T right) {
        return new Integer(comparator.compare(left,right));
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object that) {
        if(that instanceof ComparatorFunction) {
            return equals((ComparatorFunction)that);
        }
        return false;
    }

    /**
     * @see #equals(Object)
     */
    public boolean equals(ComparatorFunction<?> that) {
        return null != that && comparator.equals(that.comparator);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return "ComparatorFunction".hashCode() ^ comparator.hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ComparatorFunction<" + comparator + ">";
    }

    private Comparator<T> comparator = null;
}
