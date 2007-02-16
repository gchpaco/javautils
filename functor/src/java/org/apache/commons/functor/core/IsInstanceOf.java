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
 * <code>true</code> iff its argument 
 * {@link Class#isInstance is an instance} 
 * of some specified {@link Class Class}.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class IsInstanceOf implements UnaryPredicate<Object>, Serializable {

    /**
   * 
   */
  private static final long serialVersionUID = 9149342063375483705L;

    // constructor
    // ------------------------------------------------------------------------
    public IsInstanceOf(Class<?> klass) {
        this.klass = klass;
    }
 
    // predicate interface
    // ------------------------------------------------------------------------

    public boolean test(Object obj) {
        return klass.isInstance(obj);
    }

    @Override
    public boolean equals(Object that) {
        if(that instanceof IsInstanceOf) {
            return equals((IsInstanceOf)that);
        }
        return false;
    }
    
    public boolean equals(IsInstanceOf that) {
        return (null != that && (null == this.klass ? null == that.klass : this.klass.equals(that.klass)));
    }
    
    @Override
    public int hashCode() {
        int hash = "IsInstanceOf".hashCode();
        if(null != klass) {
            hash ^= klass.hashCode();
        }
        return hash;
    }
    
    @Override
    public String toString() {
        return "IsInstanceOf<" + klass + ">";
    }
    
    // attributes
    // ------------------------------------------------------------------------
    private Class<?> klass;

}
