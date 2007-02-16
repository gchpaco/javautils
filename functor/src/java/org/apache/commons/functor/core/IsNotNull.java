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

import org.apache.commons.functor.UnaryPredicate;

/**
 * {@link #test Tests} 
 * <code>false</code> iff its argument 
 * is <code>null</code>.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class IsNotNull implements UnaryPredicate<Object>, Serializable {

    /**
   * 
   */
  private static final long serialVersionUID = 8808844410870513560L;

    // constructor
    // ------------------------------------------------------------------------
    public IsNotNull() {
    }
 
    // predicate interface
    // ------------------------------------------------------------------------

    public boolean test(Object obj) {
        return (null != obj);
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof IsNotNull;
    }
    
    @Override
    public int hashCode() {
        return "IsNotNull".hashCode();
    }
    
    @Override
    public String toString() {
        return "IsNotNull";
    }
        
    // static methods
    // ------------------------------------------------------------------------
    public static IsNotNull instance() {
        return INSTANCE;
    }
    
    // static attributes
    // ------------------------------------------------------------------------
    private static final IsNotNull INSTANCE = new IsNotNull();

}
