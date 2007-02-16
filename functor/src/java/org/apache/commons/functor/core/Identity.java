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

import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryPredicate;

/**
 * {@link #evaluate Evaluates} to its input argument.
 * 
 * {@link #test Tests} to the <code>boolean</code>
 * value of the <code>Boolean</code>-valued parameter.
 * The {@link #test} method throws an exception if 
 * the parameter isn't a non-<code>null</code> 
 * <code>Boolean</code>.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class Identity<T> implements UnaryFunction<T,T>, UnaryPredicate<T>, Serializable {

    /**
   * 
   */
  private static final long serialVersionUID = 7568532708952203064L;

    // constructor
    // ------------------------------------------------------------------------
    public Identity() {
    }
 
    // function interface
    // ------------------------------------------------------------------------
    public T evaluate(T obj) {
        return obj;
    }

    public boolean test(T obj) {
        return test((Boolean)obj);
    }

    public boolean test(Boolean bool) {
        return bool.booleanValue();
    }

    @Override
    public boolean equals(Object that) {
        return (that instanceof Identity);
    }
    
    @Override
    public int hashCode() {
        return "Identity".hashCode();
    }
    
    @Override
    public String toString() {
        return "Identity";
    }
    
    // static methods
    // ------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public static <T> Identity<T> instance() {
        return (Identity<T>) INSTANCE;
    }
    
    // static attributes
    // ------------------------------------------------------------------------
    private static final Identity<?> INSTANCE = new Identity<Object>();
}
