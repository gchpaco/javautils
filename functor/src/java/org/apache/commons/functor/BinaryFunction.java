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
package org.apache.commons.functor;

/**
 * A functor that takes two arguments and returns an <code>Object</code> value.
 * <p>
 * Implementors are encouraged but not required to make their functors 
 * {@link java.io.Serializable Serializable}.
 * </p>
 * @since 1.0
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public interface BinaryFunction<T,U,V> {
    /** 
     * Evaluate this function. 
     * 
     * @param left the first element of the ordered pair of arguments
     * @param right the second element of the ordered pair of arguments
     * @return the result of this function for the given arguments
     */
    V evaluate(T left, U right);
    
    /**
     * Returns a human readable description of this functor.
     * Implementators are strongly encouraged but not
     * strictly required to override the default {@link Object}
     * implementation of this method.
     * 
     * @return a human readable description of this functor
     */
    String toString();

    /**
     * Returns a hash code for this functor adhering to the
     * general {@link Object#hashCode Object.hashCode} contract.
     * Implementators are strongly encouraged but not
     * strictly required to override the default {@link Object}
     * implementation of this method.
     * 
     * @see #equals
     * @return a hash code for this functor
     */
    int hashCode();

    /**
     * Indicates whether some other object is &quot;equal to&quot; 
     * this functor.  This method must adhere to
     * general {@link Object#equals Object.equals} contract.
     * Additionally, this method can return
     * <tt>true</tt> <i>only</i> if the specified Object implements
     * the same functor interface and is known to produce the same
     * results and/or side-effects for the same arguments (if any).
     * <p>
     * While implementators are strongly encouraged to override 
     * the default Object implementation of this method,
     * note that the default Object implementation
     * does in fact adhere to the functor <code>equals</code> contract.
     * </p>
     * @param that the object to compare this functor to
     * @see #hashCode
     * @return <code>true</code> iff the given object implements
     *         this functor interface, and is known to produce the same
     *         results and/or side-effects for the same arguments 
     *         (if any).
     */
    boolean equals(Object that);
}
