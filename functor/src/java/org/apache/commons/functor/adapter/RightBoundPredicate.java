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
package org.apache.commons.functor.adapter;

import java.io.Serializable;

import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.functor.UnaryPredicate;

/**
 * Adapts a
 * {@link BinaryPredicate BinaryPredicate} 
 * to the 
 * {@link UnaryPredicate UnaryPredicate} interface 
 * using a constant left-side argument.
 * <p/>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying objects are.  Attempts to serialize
 * an instance whose delegates are not 
 * <code>Serializable</code> will result in an exception.
 * 
 * @version $Revision: 155445 $ $Date: 2005-02-26 05:21:00 -0800 (Sat, 26 Feb 2005) $
 * @author Rodney Waldhoff
 */
public final class RightBoundPredicate<T,U> implements UnaryPredicate<T>, Serializable {
    /**
   * 
   */
  private static final long serialVersionUID = 7877801070136761005L;
    /**
     * @param predicate the predicate to adapt
     * @param arg the constant argument to use
     */
    public RightBoundPredicate(BinaryPredicate<T,U> predicate, U arg) {
        this.predicate = predicate;
        this.param = arg;
    }
 
    public boolean test(T obj) {
        return predicate.test(obj,param);
    }   

    @Override
    public boolean equals(Object that) {
        if(that instanceof RightBoundPredicate) {
            return equals((RightBoundPredicate)that);
        }
        return false;
    }
        
    public boolean equals(RightBoundPredicate<?, ?> that) {
        return that == this || ( 
                (null != that) && 
                (null == predicate ? null == that.predicate : predicate.equals(that.predicate)) &&
                (null == param ? null == that.param : param.equals(that.param)) );
                
    }
    
    @Override
    public int hashCode() {
        int hash = "RightBoundPredicate".hashCode();
        if(null != predicate) {
            hash <<= 2;
            hash ^= predicate.hashCode();
        }
        if(null != param) {
            hash <<= 2;
            hash ^= param.hashCode();
        }
        return hash;
    }
    
    @Override
    public String toString() {
        return "RightBoundPredicate<" + predicate + "(?," + param + ")>";
    }

    public static <T,U> RightBoundPredicate<T,U> bind(BinaryPredicate<T,U> predicate, U arg) {
        return null == predicate ? null : new RightBoundPredicate<T,U>(predicate,arg);
    }

    /** The {@link BinaryPredicate BinaryPredicate} I'm wrapping. */
    private BinaryPredicate<T,U> predicate = null;
    /** The parameter to pass to that predicate. */
    private U param = null;
}
