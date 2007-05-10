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
package org.apache.commons.functor.core;

import java.io.Serializable;

import org.apache.commons.functor.BinaryPredicate;

/**
 * {@link #test Tests} 
 * <code>true</code> iff its arguments are 
 * {@link Object#equals equal} or both 
 * <code>null</code>.
 * <p>
 * This relation is 
 * an equivalence relation on 
 * the set of objects that adhere to the 
 * <code>Object.equals</code> contract.
 * </p>
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class IsEqual implements BinaryPredicate<Object,Object>, Serializable {

    /**
   * 
   */
  private static final long serialVersionUID = 9113813181440444540L;

    // constructor
    // ------------------------------------------------------------------------
    public IsEqual() {
    }
 
    // predicate interface
    // ------------------------------------------------------------------------

    public boolean test(Object left, Object right) {
        return (null == left ? null == right : left.equals(right));
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof IsEqual;
    }
    
    @Override
    public int hashCode() {
        return "IsEqual".hashCode();
    }
    
    @Override
    public String toString() {
        return "IsEqual";
    }
        
    // static methods
    // ------------------------------------------------------------------------
    public static IsEqual instance() {
        return INSTANCE;
    }
    
    // static attributes
    // ------------------------------------------------------------------------
    private static final IsEqual INSTANCE = new IsEqual();

}